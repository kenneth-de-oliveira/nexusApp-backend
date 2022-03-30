package br.com.nexusapp.api.enums;

import java.util.stream.Stream;

public enum ParametroEnum {
	NOME("Nexus Pagamentos LTDA");

	private final String descricao;

	ParametroEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String of(ParametroEnum parametroEnum) {
		return Stream.of(ParametroEnum.values()).filter(s -> s.equals(parametroEnum)).findFirst()
				.orElseThrow(IllegalArgumentException::new).getDescricao();
	}
	
}
