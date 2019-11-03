package br.com.tarefas.gui.panel.painelConsole;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.com.tarefas.gui.frame.FramePrincipal;
import br.com.tarefas.lib.windows.PainelLogavel;

public class PainelConsole extends JPanel {
	private static final long serialVersionUID = 1L;

	private FramePrincipal frame;

	private JTabbedPane abas;

	private int tabIndex = 0;

	private Map< String, PainelLog > mapPaineis = new HashMap<>();

	private int tabsCount = 0;

	public PainelConsole(FramePrincipal frame) {
		this.frame = frame;

		this.setLayout(new BorderLayout());

		preparaAbas();
	}

	private void preparaAbas() {
		abas = new JTabbedPane();

		this.add(abas);
	}

	public PainelLogavel criaNovoPainelLogavel(String processName) {
		PainelLog painelLog = new PainelLog(processName);

		abas.addTab(processName, painelLog);

		mapPaineis.put(processName, painelLog);

		tabsCount++;

		setPainelConsoleQtdeTabs();

		return painelLog;
	}

	public void setPainelConsoleQtdeTabs() {

		if(this.tabsCount < 1) {
			this.frame.getAbas().setTitleAt(getTabIndex(), "Console");
			return;
		}
		this.frame.getAbas().setTitleAt(getTabIndex(), "Console (" + this.tabsCount + ")");
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void removeTodos() {
		this.abas.removeAll();
		this.tabsCount = 0;
		this.frame.getAbas().setTitleAt(getTabIndex(), "Console");
	}
}
