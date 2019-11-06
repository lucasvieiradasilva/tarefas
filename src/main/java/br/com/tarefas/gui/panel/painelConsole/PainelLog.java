package br.com.tarefas.gui.panel.painelConsole;

import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import br.com.tarefas.lib.StringUtils;
import br.com.tarefas.lib.windows.PainelLogavel;

public class PainelLog extends JScrollPane implements PainelLogavel {
	private static final long serialVersionUID = 1L;

	private String processName;
	private boolean travaScroll = true;

	private JPanel contentPane = new JPanel();
	private JTextArea txtLog = new JTextArea();

	public PainelLog(String processName) {
		this.processName = processName;

		this.contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		this.contentPane.setBackground(Color.DARK_GRAY);

		txtLog.setBackground(Color.DARK_GRAY);
		txtLog.setForeground(Color.WHITE);
		txtLog.setEditable( false );

		this.contentPane.add(txtLog);

		this.getViewport().add(this.contentPane);

		this.txtLog.addKeyListener( new KeyListener(){

         @Override
         public void keyTyped(KeyEvent e) {
            if(StringUtils.equals( String.valueOf( e.getKeyChar() ), "1" )) { // 1
               if(travaScroll) {
                  travaScroll = false;
               }else {
                  travaScroll = true;
               }
            }
         }

         @Override
         public void keyReleased( KeyEvent e ) {
         }

         @Override
         public void keyPressed( KeyEvent e ) {
         }
      } );
	}

	@Override
	public void loga(String log) {
	   this.txtLog.append( log + "\n" );

		if(this.travaScroll) {
		   scrollToDown();
		}

		this.contentPane.revalidate();
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
