package br.com.tarefas.data.tarefa;

public class TarefaBean {
	private int id;
	private String descricao;
	private String path;
	private String parametros;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
}
