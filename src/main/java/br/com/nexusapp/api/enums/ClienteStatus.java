package br.com.nexusapp.api.enums;

import java.util.stream.Stream;

public enum ClienteStatus {
    ATIVO(1),
    INATIVO(2);

    public int getId() {
        return id;
    }

    private final int id;

    ClienteStatus(int id) {
        this.id = id;
    }

    public static ClienteStatus of(int id) {
        return Stream.of(ClienteStatus.values())
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
