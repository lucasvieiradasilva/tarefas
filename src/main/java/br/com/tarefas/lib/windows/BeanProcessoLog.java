package br.com.tarefas.lib.windows;

public class BeanProcessoLog {
	private String nomeProcesso;
	private CMDStreamReader reader;
	private Thread threadLog;

	public BeanProcessoLog(String nomeProcesso, CMDStreamReader reader, Thread threadLog) {
		this.nomeProcesso = nomeProcesso;
		this.reader = reader;
		this.threadLog = threadLog;
	}

	public String getNomeProcesso() {
		return nomeProcesso;
	}

	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}

	public CMDStreamReader getReader() {
		return reader;
	}

	public void setReader(CMDStreamReader reader) {
		this.reader = reader;
	}

	public Thread getThreadLog() {
		return threadLog;
	}

	public void setThreadLog(Thread threadLog) {
		this.threadLog = threadLog;
	}
}
