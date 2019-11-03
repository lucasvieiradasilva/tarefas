package br.com.tarefas.gui.panel.painelConsole;

import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.tarefas.lib.windows.PainelLogavel;

public class PainelLog extends JScrollPane implements PainelLogavel {
	private static final long serialVersionUID = 1L;

	private String processName;

	private JPanel contentPane = new JPanel();

	public PainelLog(String processName) {
		this.processName = processName;

		this.contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		this.contentPane.setBackground(Color.DARK_GRAY);

		this.getViewport().add(this.contentPane);
	}

	@Override
	public void loga(String log) {
		this.contentPane.add(new Label(log));
		scrollToDown();
		this.contentPane.revalidate();
	}

	private class Label extends JLabel {
		private static final long serialVersionUID = 1L;

		public Label(String text) {
			super(text);

			this.setForeground(Color.WHITE);
		}
	}

	private void scrollToDown() {
		this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	        }
	    });
	}

	@Override
	public String getProcessName() {
		return this.processName;
	}

	@Override
	public void destroy() {
	}
}
