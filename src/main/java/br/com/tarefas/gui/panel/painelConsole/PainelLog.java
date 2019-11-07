package br.com.tarefas.gui.panel.painelConsole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import br.com.tarefas.lib.StringUtils;
import br.com.tarefas.lib.windows.PainelLogavel;

public class PainelLog extends JPanel implements PainelLogavel {
	private static final long serialVersionUID = 1L;

	private String processName;
	private boolean travaScroll = true;

	private JPanel painelAtalhos = new JPanel();
	private PainelScroll painelScroll = new PainelScroll();
	private JPanel painelLog = new JPanel();
	private JTextArea txtLog = new JTextArea();

	public PainelLog(String processName) {
		this.processName = processName;

		this.setLayout(new BorderLayout());

		this.painelAtalhos.setLayout(new FlowLayout(FlowLayout.LEFT));

		adcionaAtalhoTravaConsole();
		adcionaAtalhoLimpaConsole();

		this.painelLog.setLayout(new BoxLayout(this.painelLog, BoxLayout.Y_AXIS));
		this.painelLog.setBackground(Color.DARK_GRAY);
		this.txtLog.setBackground(Color.DARK_GRAY);
		this.txtLog.setForeground(Color.WHITE);
		this.txtLog.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		this.txtLog.setEditable(false);
		this.painelLog.add(this.txtLog);
		this.txtLog.append("\n");

		this.painelScroll.getViewport().add(this.painelLog);

		this.setBackground(Color.DARK_GRAY);
		this.add(this.painelAtalhos, BorderLayout.NORTH);
		this.add(this.painelScroll, BorderLayout.CENTER);


		this.painelScroll.lockScroll();

		this.txtLog.addKeyListener( new KeyListener(){

         @Override
         public void keyTyped(KeyEvent e) {
            if(StringUtils.equals( String.valueOf( e.getKeyChar() ), "1" )) { // 1
               travaDestravaConsole();
               return;
            }

            if(StringUtils.equals( String.valueOf( e.getKeyChar() ), "2" )) { // 2
               txtLog.setText("CONSOLE LIMPO!\n");
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

	public void travaDestravaConsole() {
	   if(travaScroll) {
         travaScroll = false;
         this.painelScroll.unlockScroll();
         txtLog.append( "CONSOLE DESTRAVADO!" + "\n" );
      }else {
         travaScroll = true;
         this.painelScroll.lockScroll();
         txtLog.append("CONSOLE TRAVADO!" + "\n");
      }
	}

	private void adcionaAtalhoTravaConsole() {
	   JButton botao = new JButton("Trava/Destrava console (1)");

	   botao.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            travaDestravaConsole();
         }
      });

      this.painelAtalhos.add(botao);
   }

	private void adcionaAtalhoLimpaConsole() {
      JButton botao = new JButton("Limpa console (2)");

      botao.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            txtLog.setText("CONSOLE LIMPO!\n");
         }
      });

      this.painelAtalhos.add(botao);
   }

   @Override
	public void loga(String log) {
	   this.txtLog.append( log + "\n" );
		this.revalidate();
	}

	@Override
	public String getProcessName() {
		return this.processName;
	}

	@Override
	public void destroy() {
	}

	private class PainelScroll extends JScrollPane {
	   private static final long serialVersionUID = 1L;

      private AdjustmentListener adjustmentListener = new AdjustmentListener() {

	      @Override
	      public void adjustmentValueChanged(AdjustmentEvent e) {
	         e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	      }

	   };

	   public void lockScroll() {
	      this.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
	   }

	   public void unlockScroll() {
	      this.getVerticalScrollBar().removeAdjustmentListener(adjustmentListener);
	   }
	}
}
