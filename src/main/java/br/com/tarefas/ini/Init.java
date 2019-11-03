package br.com.tarefas.ini;

import br.com.tarefas.gui.frame.FramePrincipal;

public class Init {
	public static void main(String[] args) {
		try {
			new FramePrincipal().montaTela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
