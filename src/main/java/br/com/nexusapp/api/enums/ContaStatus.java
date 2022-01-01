package br.com.nexusapp.api.enums;

import java.util.stream.Stream;

public enum ContaStatus {
    ATIVO(1),
    INATIVO(2);

    public int getId() {
        return id;
    }

    private final int id;

    ContaStatus(int id) {
        this.id = id;
    }

    public static ContaStatus of(int id) {
        return Stream.of(ContaStatus.values())
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}