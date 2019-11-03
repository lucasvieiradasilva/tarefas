package br.com.tarefas.gui.frame;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.com.tarefas.gui.panel.painelConsole.PainelConsole;
import br.com.tarefas.gui.panel.painelTarefas.PainelTarefas;
import br.com.tarefas.gui.utils.FrameUtils;
import br.com.tarefas.gui.utils.bean.FrameBounds;
import br.com.tarefas.lib.windows.CMDUtils;

public class FramePrincipal extends JFrame{
	private static final long serialVersionUID = 1L;

	private final String TITULO = "Trata tarefas";
	private final int[] TAMANHO_JANELA = { 800, 700 };

	private JPanel painelPrincipal;

	private JTabbedPane abas;

	private PainelConsole painelConsole = new PainelConsole(this);
	private PainelTarefas painelTarefas = new PainelTarefas(this, painelConsole);

	public void montaTela() throws Exception {
		preparaJanela();
		preparaPainelPrincipal();
		preparaAbas();

		mostraJanela();
	}

	private void preparaJanela() {
		this.setTitle(TITULO);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        try {
					CMDUtils.pararTodosProcessos(painelConsole);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        e.getWindow().dispose();
		        System.exit(0);
		    }
		});
	}

	private void preparaPainelPrincipal() {
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());
		this.add(painelPrincipal);
	}

	private void preparaAbas() {
		abas = new JTabbedPane();
		abas.addTab("Tarefas", painelTarefas);
		abas.addTab("Variáveis", new JPanel());
		abas.addTab("Console", painelConsole);
		painelPrincipal.add(abas);

		painelTarefas.setTabIndex(0);
		painelConsole.setTabIndex(2);

		abas.setEnabledAt(1, false);
		abas.setEnabledAt(painelConsole.getTabIndex(), false);
	}

	private void mostraJanela() throws Exception {
		this.pack();

		FrameBounds frameBounds = FrameUtils.getFrameBounds(TAMANHO_JANELA[0], TAMANHO_JANELA[1]);
		this.setBounds(frameBounds.getPosX(), frameBounds.getPosY(), frameBounds.getWidth(), frameBounds.getHeight());

		this.setSize(TAMANHO_JANELA[0], TAMANHO_JANELA[1]);
		this.setVisible(true);
	}

	public JTabbedPane getAbas() {
		return abas;
	}
}
