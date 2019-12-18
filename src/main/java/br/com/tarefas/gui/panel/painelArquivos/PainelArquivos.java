package br.com.tarefas.gui.panel.painelArquivos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import br.com.tarefas.data.arquivo.ArquivoBean;
import br.com.tarefas.data.arquivo.ArquivoDAO;
import br.com.tarefas.gui.frame.FramePrincipal;
import br.com.tarefas.gui.frame.cadArquivo.FrameCadArquivo;
import br.com.tarefas.gui.frame.cadVariaveis.FrameCadVariaveis;
import br.com.tarefas.gui.panel.painelArquivos.table.EnumArquivosColunas;
import br.com.tarefas.gui.panel.painelArquivos.table.TableModelArquivos;

public class PainelArquivos extends JPanel {
   private static final long serialVersionUID = 1L;

   private FramePrincipal frame;

   private int tabIndex = 0;

   private JPanel painelNorth;
   private JScrollPane painelCenter;
   private JPanel painelSouth;

   private JTable tabela;
   private TableModelArquivos model;

   public PainelArquivos(FramePrincipal frame) {
      this.frame = frame;

      this.setLayout(new BorderLayout());

      preparaPainelNorth();
      preparaPainelCenter();
      preparaPainelSouth();
   }

   private void preparaPainelNorth() {
      this.painelNorth = new JPanel();
      this.add(painelNorth, BorderLayout.NORTH);
   }

   private void preparaPainelCenter() {
      this.painelCenter = new JScrollPane();

      preparaTabela();

      this.add(painelCenter, BorderLayout.CENTER);
   }

   private void preparaPainelSouth() {
      painelSouth = new JPanel();
      this.add(painelSouth, BorderLayout.SOUTH);

      acaoBotaoNovo();
      acaoBotaoEditar();
      acaoBotaoVariaveis();
   }

   private void preparaTabela() {

      this.tabela = new JTable();
      this.tabela.setBorder(new LineBorder(Color.black));
      this.tabela.setGridColor(Color.black);
      this.tabela.setShowGrid(true);
      this.tabela.setAlignmentX(0);
      this.tabela.setAlignmentY(0);

      model = new TableModelArquivos(new ArquivoDAO().getArquivos(), this.tabela);
      this.tabela.setModel(this.model);


      this.tabela.getTableHeader().addMouseListener( new MouseAdapter() {
         @Override
         public void mouseClicked( MouseEvent e ) {
            if( EnumArquivosColunas.SELECT.getPosicao() == tabela.columnAtPoint( e.getPoint() )) {
               marcarDesmarcarTodos();
            }
         }
      });

      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment( JLabel.CENTER );

      TableColumn colunaSelect = this.tabela.getColumn(EnumArquivosColunas.SELECT.getNome());
      colunaSelect.setMaxWidth(20);
      colunaSelect.setResizable(false);

      TableColumn colunaId = this.tabela.getColumn(EnumArquivosColunas.ID.getNome());
      colunaId.setCellRenderer(centerRenderer);
      colunaId.setMaxWidth(70);
      colunaId.setResizable(false);

      TableColumn colunaDescricao = this.tabela.getColumn(EnumArquivosColunas.DESCRICAO.getNome());
      colunaDescricao.setPreferredWidth(150);

      TableColumn colunaPath = this.tabela.getColumn(EnumArquivosColunas.PATH.getNome());
      colunaPath.setPreferredWidth(300);

      this.painelCenter.getViewport().add(this.tabela);
   }

   private void acaoBotaoNovo() {
      JButton botaoNovo = new JButton("Novo");

      botaoNovo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               FrameCadArquivo frameCadArquivo = new FrameCadArquivo(frame);
               frameCadArquivo.addWindowListener(new java.awt.event.WindowAdapter() {
                   public void windowClosed(java.awt.event.WindowEvent e) {
                     model.update(new ArquivoDAO().getArquivos());
                  };
               });
            } catch (Exception e1) {
               e1.printStackTrace();
            }
         }
      });

      painelSouth.add(botaoNovo);
   }

   private void acaoBotaoEditar() {
      JButton botaoEditar = new JButton("Editar");

      botaoEditar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ArquivoBean arquivo = null;

            for(int i = 0; i < tabela.getRowCount(); i++  ) {
               ArquivoBean arq = model.getArquivo(i);

               if(arq != null && arq.isSelected()) {
                  if(arquivo != null) {
                     JOptionPane.showMessageDialog(PainelArquivos.this, "Somente um arquivo deverá estar selecionado!", "Alerta", JOptionPane.ERROR_MESSAGE);
                     return;
                  }

                  arquivo = arq;
               }
            }

            if(arquivo == null) {
               JOptionPane.showMessageDialog(PainelArquivos.this, "Nenhuma arquivo selecionado!", "Alerta", JOptionPane.ERROR_MESSAGE);
               return;
            }

            try {
               FrameCadArquivo frameCadArquivo = new FrameCadArquivo(arquivo, frame);
               frameCadArquivo.addWindowListener(new java.awt.event.WindowAdapter() {
                   public void windowClosed(java.awt.event.WindowEvent e) {
                     model.update(new ArquivoDAO().getArquivos());
                  };
               });
            } catch (Exception e1) {
               e1.printStackTrace();
            }
         }
      });

      painelSouth.add(botaoEditar);
   }

   private void acaoBotaoVariaveis() {
      JButton botaoVariaveis = new JButton("Variáveis");

      botaoVariaveis.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try{
               new FrameCadVariaveis(model.getArquivos(), frame);
            }
            catch( Exception e1 ){
               e1.printStackTrace();
            }
         }
      });

      painelSouth.add(botaoVariaveis);
   }

   public int getTabIndex() {
      return tabIndex;
   }

   public void setTabIndex( int tabIndex ) {
      this.tabIndex = tabIndex;
   }

   private boolean isTodosItensSelecionados() {
      for(int i = 0; i < tabela.getRowCount(); i++  ) {
         ArquivoBean arq = model.getArquivo(i);

         if( arq != null && !arq.isSelected() ) {
            return false;
         }
      }
      return true;
   }

   private void marcarDesmarcarTodos() {
      boolean marcar = !isTodosItensSelecionados();
      for(int i = 0; i < tabela.getRowCount(); i++  ) {
         tabela.setValueAt( marcar, i, EnumArquivosColunas.SELECT.getPosicao() );
      }
   }
}
