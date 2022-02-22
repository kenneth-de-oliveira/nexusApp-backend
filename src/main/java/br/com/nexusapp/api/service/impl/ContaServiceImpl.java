package br.com.nexusapp.api.service.impl;

import static br.com.nexusapp.api.enums.OperacaoEnum.DEPOSITO;
import static br.com.nexusapp.api.enums.OperacaoEnum.SAQUE;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.dtos.ContaMinimumDTO;
import br.com.nexusapp.api.dtos.InfoContaDTO;
import br.com.nexusapp.api.dtos.InfoContaFullDTO;
import br.com.nexusapp.api.enums.ContaStatus;
import br.com.nexusapp.api.enums.OperacaoEnum;
import br.com.nexusapp.api.exception.BadRequestException;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Conta;
import br.com.nexusapp.api.repository.ClienteRepository;
import br.com.nexusapp.api.repository.ContaRepository;
import br.com.nexusapp.api.service.IClienteService;
import br.com.nexusapp.api.service.IContaService;
import br.com.nexusapp.api.service.IEnderecoService;
import br.com.nexusapp.api.service.ISeqAgenciaService;
import br.com.nexusapp.api.service.ISeqContaService;

@Service
public class ContaServiceImpl implements IContaService {

    private final ContaRepository repository;
    private final ISeqContaService iSeqContaService;
    private final ISeqAgenciaService iSeqAgenciaService;
    private final IEnderecoService iEnderecoService;
    private final IClienteService clienteService;
    private final MessageSource ms;

    @Autowired
    public ContaServiceImpl(
        ContaRepository repository,
        ISeqContaService iSeqContaService,
        ISeqAgenciaService iSeqAgenciaService,
        IEnderecoService iEnderecoService,
        ClienteRepository clienteRepository,
        IClienteService clienteService,
        MessageSource ms) {
		this.repository = repository;
        this.iSeqContaService = iSeqContaService;
        this.iSeqAgenciaService = iSeqAgenciaService;
        this.iEnderecoService = iEnderecoService;
        this.clienteService = clienteService;
        this.ms = ms;
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
         this.isContaAtiva(contaOpt);
         return getContaMinimumDTO(contaOpt.get());
    }

    @Override
    public ContaFullDTO buscarContaPorId(Long id) {
        Optional<Conta> contaOpt = repository.findById(id);
        this.isContaAtiva(contaOpt);
        return getContaMinimumDTO(contaOpt.get());
    }

    @Override
    public ContaFullDTO buscarContaAgencia(String agencia) {
        Optional<Conta> contaOpt = repository.findByAgencia(agencia);
        this.isContaAtiva(contaOpt);
        return getContaMinimumDTO(contaOpt.get());
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
        this.realizaSaque(InfoContaDTO.toInfoContaDTO(infoContaDTO.getAgencia(), infoContaDTO.getNumero(), infoContaDTO.getValor()));
        this.realizaDeposito(InfoContaDTO.toInfoContaDTO(infoContaDTO.getAgenciaDestino(), infoContaDTO.getNumeroDestino(), infoContaDTO.getValor()));
    }

    @Override
    public ContaFullDTO buscarContaPorNumero(String numero) {
        Optional<Conta> contaOpt = repository.findByNumero(numero);
        this.isContaAtiva(contaOpt);
        return getContaMinimumDTO(contaOpt.get());
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
        atualizaSaldo(infoContaDTO, repository.findByAgenciaAndNumeroAndStatus(infoContaDTO.getAgencia(), infoContaDTO.getNumero(), ContaStatus.ATIVO)
                .orElseThrow(() -> new BadRequestException(ms.getMessage("conta.consulta.erro",
                        null, LocaleContextHolder.getLocale()))), DEPOSITO);
    }

    private void realizaSaque(InfoContaDTO infoContaDTO) {
        atualizaSaldo(infoContaDTO, repository.findByAgenciaAndNumeroAndStatus(infoContaDTO.getAgencia(), infoContaDTO.getNumero(), ContaStatus.ATIVO)
                .orElseThrow(() -> new BadRequestException(ms.getMessage("conta.consulta.erro",
                        null, LocaleContextHolder.getLocale()))), SAQUE);
    }

    
    private void isContaAtiva(Optional<Conta> contaOpt) {
    	if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
    }
}
