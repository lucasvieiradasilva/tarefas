package br.com.tarefas.gui.panel.painelTarefas.table;

public enum EnumTarefasColunas {
	SELECT("", 0),
	ID("Id", 1),
	DESCRICAO("Descrição", 2),
	PATH("Arquivo", 3),
	PARAMETROS("Parametros", 4);

	private final String nome;
	private final int posicao;

	EnumTarefasColunas(String nome, int posicao) {
		this.nome = nome;
		this.posicao = posicao;
	}

	public String getNome() {
		return this.nome;
	}

	public int getPosicao() {
		return this.posicao;
	}
}
