package br.com.tarefas.lib.windows;

public interface PainelLogavel {
	public void loga(String log);
	public String getProcessName();
	public void destroy();
	public void setFinalizado(boolean finalizado);
}
