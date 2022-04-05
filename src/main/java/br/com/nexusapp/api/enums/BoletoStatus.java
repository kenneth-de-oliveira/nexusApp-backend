package br.com.nexusapp.api.enums;

import java.util.stream.Stream;

public enum BoletoStatus {
    PAGO("PAGAMENTO EFETUADO"),
    ANALISE("EM ANALISE"),
    REJEITADO("PAGAMENTO REJEITADO");

    private final String descricao;

    BoletoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static String of(BoletoStatus boletoStatus) {
        return Stream.of(BoletoStatus.values()).filter(s -> s.equals(boletoStatus)).findFirst()
                .orElseThrow(IllegalArgumentException::new).getDescricao();
    }
}