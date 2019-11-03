package br.com.tarefas.lib.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import br.com.tarefas.gui.panel.painelConsole.PainelConsole;
import br.com.tarefas.lib.StringUtils;

public class CMDUtils {
	public static Map< String, BeanProcessoLog > mapProcessos = new HashMap<>();

	public synchronized static String execCommandSimples(final String commandLine) throws IOException {

		boolean success = false;
		String result;

		Process p;
		BufferedReader input;
		StringBuffer cmdOut = new StringBuffer();
		String lineOut = null;
		int numberOfOutline = 0;

		try {
			p = Runtime.getRuntime().exec(commandLine);

			input = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((lineOut = input.readLine()) != null) {
				if (numberOfOutline > 0) {
					cmdOut.append("\n");
				}
				cmdOut.append(lineOut);
				numberOfOutline++;
			}

			result = cmdOut.toString();

			success = true;

			input.close();

		} catch (IOException e) {
			result = String.format("Falha ao executar comando %s. Erro: %s", commandLine, e.toString());
		}

		// Se não executou com sucesso, lançará falha
		if (!success) {
			throw new IOException(result);
		}

		return result;
	}

	public static void executa(final String commandLine) throws IOException {
		/* Cria ProcessBuilder */
		ProcessBuilder pb = new ProcessBuilder(commandLine);
		pb.redirectErrorStream(true);

		/* Inicia o processo */
		Process proc = pb.start();
		System.out.println("Processo iniciado!");

		/* Read the process's output */
		String line;
		BufferedReader in = new BufferedReader(new InputStreamReader(
		        proc.getInputStream()));
		while ((line = in.readLine()) != null) {
		    System.out.println(line);
		}

		/* Termina o processo */
		proc.destroy();
		System.out.println("Processo finalizado!");
	}

	public void executaCmd(final String comando, final String path, PainelLogavel painel) throws IOException {
		Process process = Runtime.getRuntime().exec(comando + " " + path);

		CMDStreamReader reader = new CMDStreamReader(process, process.getInputStream(), painel);
		Thread thread = new Thread(reader, painel.getProcessName());
		thread.start();

		mapProcessos.put(painel.getProcessName(), new BeanProcessoLog(painel.getProcessName(), reader, thread));
	}

	public static void pararTodosProcessos(PainelConsole painel) throws IOException {
		for(BeanProcessoLog bean : mapProcessos.values()) {
			parar(bean);
		}
		mapProcessos.clear();
		painel.removeTodos();
	}

	public static void pararProcesso(String nomeProcesso) throws IOException {
		parar(mapProcessos.get(nomeProcesso));
		mapProcessos.remove(nomeProcesso);
	}

	private static void parar(BeanProcessoLog bean) throws IOException {
		if(bean == null) {
			return;
		}

		bean.getReader().parar();
		bean.getThreadLog().interrupt();
	}

	public static String getNewProcessName(String path) {
		if(!path.contains(".") && !path.contains("\\")) {
			return getNext(path);
		}

		if(path.contains(".") && path.contains("\\")) {
			int indexBarra = path.lastIndexOf("\\");
			int indexPonto = path.lastIndexOf(".");

			return getNext(path.substring(indexBarra + 1, indexPonto));
		}

		if(path.contains(".")) {
			int indexPonto = path.lastIndexOf(".");
			return getNext(path.substring(0, indexPonto));
		}

		int indexBarra = path.lastIndexOf("\\");
		return getNext(path.substring(indexBarra + 1, path.length()));

	}

	private static String getNext(String tab) {
		String txtDefault = "TAB";

		String text = StringUtils.trim(tab).toUpperCase();

		if(text.contains(" ")) {
			int indexEspaco = text.lastIndexOf(" ");
			text = text.substring(indexEspaco + 1, text.length());
		}

		if(text.isEmpty()) {
			text = txtDefault;
		}

		int num = 0;

		while(true) {
			String txtTemp = text;

			if(num > 0) {
				txtTemp = text + "_" + num;
			}

			if(!mapProcessos.containsKey(txtTemp)) {
				text = txtTemp;
				break;
			}

			num++;
		}

		return text;
	}
}
