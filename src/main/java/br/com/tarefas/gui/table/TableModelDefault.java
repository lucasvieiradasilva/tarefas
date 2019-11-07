package br.com.tarefas.gui.table;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public abstract class TableModelDefault extends AbstractTableModel {
   private static final long serialVersionUID = 1L;

   protected JTable table;
   protected Font fonteCabecalho;
   protected Font fonteConteudo;
   protected int alturaConteudo;

   public TableModelDefault(JTable table) {
      this.table = table;

      setFonteCabecalho(new Font("Arial", Font.BOLD, 15));
      setFonteConteudo(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 15), 20);
   }

   protected void setFonteCabecalho(Font fonte) {
      this.fonteCabecalho = fonte;
      this.table.getTableHeader().setFont(fonte);
   }

   protected void setFonteConteudo(Font fonte, int height) {
      this.fonteConteudo = fonte;
      this.alturaConteudo = height;
      this.table.setFont(this.fonteConteudo);
      this.table.setRowHeight( this.alturaConteudo );
   }
}
