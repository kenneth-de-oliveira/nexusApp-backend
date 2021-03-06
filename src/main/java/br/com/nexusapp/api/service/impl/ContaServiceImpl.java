package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.*;
import br.com.nexusapp.api.enums.ClienteStatus;
import br.com.nexusapp.api.enums.ContaStatus;
import br.com.nexusapp.api.enums.OperacaoEnum;
import br.com.nexusapp.api.exception.BadRequestException;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Cliente;
import br.com.nexusapp.api.model.Conta;
import br.com.nexusapp.api.model.Extrato;
import br.com.nexusapp.api.model.Usuario;
import br.com.nexusapp.api.repository.ClienteRepository;
import br.com.nexusapp.api.repository.ContaRepository;
import br.com.nexusapp.api.repository.ExtratoRepository;
import br.com.nexusapp.api.repository.UsuarioRepository;
import br.com.nexusapp.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.nexusapp.api.enums.OperacaoEnum.DEPOSITO;
import static br.com.nexusapp.api.enums.OperacaoEnum.SAQUE;
import static br.com.nexusapp.api.enums.RoleStatus.USER;

@Service
public class ContaServiceImpl implements IContaService {

    private final ContaRepository repository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ExtratoRepository extratoRepository;
    private final ISeqContaService iSeqContaService;
    private final ISeqAgenciaService iSeqAgenciaService;
    private final IEnderecoService iEnderecoService;
    private final IClienteService clienteService;
    private final MessageSource ms;

    @Autowired
    public ContaServiceImpl(
        ContaRepository repository,
        ClienteRepository clienteRepository,
        UsuarioRepository usuarioRepository,
        ISeqContaService iSeqContaService,
        ISeqAgenciaService iSeqAgenciaService,
        IEnderecoService iEnderecoService,
        ExtratoRepository extratoRepository,
        IClienteService clienteService,
        MessageSource ms) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.extratoRepository = extratoRepository;
        this.repository = repository;
        this.iSeqContaService = iSeqContaService;
        this.iSeqAgenciaService = iSeqAgenciaService;
        this.iEnderecoService = iEnderecoService;
        this.clienteService = clienteService;
        this.ms = ms;
    }

//    @Override
//    public byte[] extratoPdfConta(Long id) {
//        var extratos = this.listarExtratos(id);
//        var jrBeanCollectionDataSource = new JRBeanCollectionDataSource(extratos, false);
//        return gerarArquivoPdf(jrBeanCollectionDataSource, isContaAtiva(repository.findById(id)));
//    }

    @Override
    public ContaFullDTO consultarSaldo(String agencia, String numero) {
        var contaOpt = repository.findByAgenciaAndNumeroAndStatus(agencia, numero, ContaStatus.ATIVO);
        return this.isContaAtiva(contaOpt);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ContaFullDTO cadastrar(ContaDTO contaDTO) {
        ClienteDTO clienteDTO = cadastraClienteConta(contaDTO);

        var conta = contaDTO.toModel();
        conta.setNumero(iSeqContaService.gerarNumeroContaCliente(clienteDTO.toModel()));
        conta.setAgencia(iSeqAgenciaService.gerarNumeroAgenciaCliente(clienteDTO.toModel()));

        conta.setCliente(clienteDTO.toModel());

        repository.save(conta);

        return toMinimumDTO(clienteDTO, conta);
    }

    @Override
    public ContaFullDTO buscarContaPorCpf(String cpf) {
    	 ClienteDTO clienteDTO = clienteService.buscarClientePorCpf(cpf);
         Optional<Conta> contaOpt = repository.consultaPorIdCliente(clienteDTO.getId());
         return this.isContaAtiva(contaOpt);
    }

    @Override
    public ContaFullDTO buscarContaPorId(Long id) {
        Optional<Conta> contaOpt = repository.findById(id);
        return this.isContaAtiva(contaOpt);
    }

    @Override
    public ContaFullDTO buscarContaAgencia(String agencia) {
        Optional<Conta> contaOpt = repository.findByAgencia(agencia);
        return this.isContaAtiva(contaOpt);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void depositar(InfoContaDTO infoContaDTO) {
        this.realizaDeposito(infoContaDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sacar(InfoContaDTO infoContaDTO) {
        this.realizaSaque(infoContaDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transferir(InfoContaFullDTO infoContaDTO) {
    	InfoContaDTO infoContaSaque = InfoContaDTO.toInfoContaDTO(infoContaDTO.getAgencia(), infoContaDTO.getNumero(), infoContaDTO.getValor());
        this.realizaSaque(infoContaSaque);

        InfoContaDTO infoContaDeposito = InfoContaDTO.toInfoContaDTO(infoContaDTO.getAgenciaDestino(), infoContaDTO.getNumeroDestino(), infoContaDTO.getValor());
        this.realizaDeposito(infoContaDeposito);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletaContaById(Long id) {
        Conta conta = repository.findByIdAndStatus(id, ContaStatus.ATIVO).orElseThrow(() -> {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro", null, LocaleContextHolder.getLocale()));
        });

        if (conta.getSaldo() > 0){
            throw new BadRequestException(ms.getMessage("conta.deletar.erro", null, LocaleContextHolder.getLocale()));
        }

        Usuario usuario = usuarioRepository.buscarPorIdConta(conta.getId()).orElseThrow(() -> {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro", null, LocaleContextHolder.getLocale()));
        });
        encerrarContaCliente(conta);
        usuarioRepository.delete(usuario);
    }
    @Override
    public List<ExtratoDTO> listarExtratos(Long idConta) {
        Conta conta = repository.findById(idConta).orElseThrow(() -> {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        });
        List<Extrato> allByAgenciaAndNumero = extratoRepository.findByAgenciaAndNumeroOrderByDataExtratoDesc(conta.getAgencia(), conta.getNumero());
        return allByAgenciaAndNumero.stream().map(Extrato::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContaFullDTO buscarContaPorNomeUsuario(String nomeUsuario) {
        Long idUsuario = Objects.requireNonNull(usuarioRepository.findByUsername(nomeUsuario).orElse(null)).getId();
        Long idCliente = Objects.requireNonNull(clienteRepository.consultaPorIdUsuario(idUsuario).orElse(null)).getId();
        return isContaAtiva(repository.consultaPorIdCliente(idCliente));
    }

    @Override
    public ContaFullDTO buscarContaPorNumero(String numero) {
        Optional<Conta> contaOpt = repository.findByNumero(numero);
        return isContaAtiva(contaOpt);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var message = ms.getMessage("login-usuario.error",
                null, LocaleContextHolder.getLocale());

        var usuario = usuarioRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(message));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(String.valueOf(USER))
                .build();
    }

    private ContaMinimumDTO toMinimumDTO(ClienteDTO clienteDTO, Conta conta) {
        ContaMinimumDTO contaMinimumDTO = new ContaMinimumDTO(conta.toDTO());
        contaMinimumDTO.setEnderecoDTO(clienteDTO.getEnderecoDTO());
        return contaMinimumDTO;
    }

    private ClienteDTO getCliente(ContaFullDTO contaDTO) {
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(contaDTO.getClienteDTO().getId());
        clienteDTO.setEnderecoDTO(iEnderecoService.buscarDoClientePorId(clienteDTO.getId()));
        clienteDTO.getEnderecoDTO().setIdCliente(clienteDTO.getId());
        contaDTO.setClienteDTO(clienteDTO);
        return clienteDTO;
    }

    private ClienteDTO cadastraClienteConta(ContaDTO contaDTO) {
        contaDTO.setClienteDTO(clienteService.cadastrar(contaDTO.getClienteDTO()));
        ClienteDTO clienteDTO = getCliente(contaDTO);
        contaDTO.setClienteDTO(clienteDTO);
        return clienteDTO;
    }

    private ContaMinimumDTO getContaMinimumDTO(Conta conta) {
        ClienteDTO clienteDTO = getCliente(conta.toDTO());
        return toMinimumDTO(clienteDTO, conta);
    }

    private void atualizaSaldo(InfoContaDTO infoContaDTO, Conta conta, OperacaoEnum operacao) {
        this.verificarInfoValor(infoContaDTO);
        conta.setSaldo(getValor(infoContaDTO, conta, operacao));
        conta.setUpdatedAt(LocalDateTime.now());
        repository.save(conta);
    }

    private double getValor(InfoContaDTO infoContaDTO, Conta conta, OperacaoEnum operacao) {
        double valor = 0;
        if (operacao.equals(DEPOSITO)){
            valor = conta.getSaldo() + infoContaDTO.getValor();
        } else if (operacao.equals(SAQUE)){
            valor = conta.getSaldo() + conta.getLimite();
            if ((valor - infoContaDTO.getValor()) >= 0) {
                valor = conta.getSaldo() - infoContaDTO.getValor();
            } else {
                throw new BadRequestException(ms.getMessage("conta-saque.erro",
            null, LocaleContextHolder.getLocale()));
            }
        }
        return valor;
    }

    private void verificarInfoValor(InfoContaDTO infoContaDTO) {
        if (infoContaDTO.getValor() < 0) {
            throw new BadRequestException(ms.getMessage("conta-deposito.erro",
            null, LocaleContextHolder.getLocale()));
        }
    }

    private void realizaDeposito(InfoContaDTO infoContaDTO) {
    	this.registrarMovimentacao(infoContaDTO, DEPOSITO);
        atualizaSaldo(infoContaDTO, repository.findByAgenciaAndNumeroAndStatus(infoContaDTO.getAgencia(), infoContaDTO.getNumero(), ContaStatus.ATIVO)
                .orElseThrow(() -> new BadRequestException(ms.getMessage("conta.consulta.erro",
                        null, LocaleContextHolder.getLocale()))), DEPOSITO);
    }

    private void realizaSaque(InfoContaDTO infoContaDTO) {
    	this.registrarMovimentacao(infoContaDTO, SAQUE);
        atualizaSaldo(infoContaDTO, repository.findByAgenciaAndNumeroAndStatus(infoContaDTO.getAgencia(), infoContaDTO.getNumero(), ContaStatus.ATIVO)
                .orElseThrow(() -> new BadRequestException(ms.getMessage("conta.consulta.erro",
                        null, LocaleContextHolder.getLocale()))), SAQUE);
    }

    private ContaFullDTO isContaAtiva(Optional<Conta> contaOpt) {
    	if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return getContaMinimumDTO(contaOpt.get());
    }

    private void registrarMovimentacao(InfoContaDTO infoContaDTO, OperacaoEnum operacaoEnum) {
		extratoRepository.save(new Extrato(infoContaDTO, operacaoEnum));
    }

//    private byte[] gerarArquivoPdf(JRBeanCollectionDataSource jrBeanCollectionDataSource, ContaFullDTO contaFullDTO) {
//        try {
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("NEXUS_IMAGEM", new ClassPathResource(JasperUtil.LOGO_APLICACAO).getURI().getPath());
//            parametros.put("NOME", contaFullDTO.getClienteDTO().getNome() + " " + contaFullDTO.getClienteDTO().getSobrenome());
//            parametros.put("AGENCIA", contaFullDTO.getAgencia());
//            parametros.put("NUMERO", contaFullDTO.getNumero());
//            parametros.put("DOCUMENTO", contaFullDTO.getClienteDTO().getDocumento());
//            var uri = new ClassPathResource(JasperUtil.ARQUIVO_RELATORIO_EXTRATO_CONTA).getURI();
//            var caminhoArquivo = new FileInputStream(uri.getPath());
//            var jasperReport = JasperCompileManager.compileReport(caminhoArquivo);
//            var fillReport = JasperFillManager.fillReport(jasperReport, parametros, jrBeanCollectionDataSource);
//            return JasperExportManager.exportReportToPdf(fillReport);
//        } catch (JRException | IOException ex) {
//            throw new ServiceUnavailableException(ex.getMessage());
//        }
//    }

    private void encerrarContaCliente(Conta conta) {
        Cliente cliente = conta.getCliente();

        conta.setStatus(ContaStatus.INATIVO);
        conta.setUpdatedAt(LocalDateTime.now());

        cliente.setStatus(ClienteStatus.INATIVO);
        cliente.setUpdatedAt(LocalDateTime.now());

        repository.save(conta);
        clienteRepository.save(cliente);
    }

}
