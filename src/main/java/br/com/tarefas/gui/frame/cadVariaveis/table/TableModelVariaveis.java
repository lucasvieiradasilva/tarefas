package br.com.tarefas.gui.frame.cadVariaveis.table;

import java.util.List;
import javax.swing.JTable;
import br.com.tarefas.data.variaveis.VariavelBean;
import br.com.tarefas.gui.table.TableModelDefault;

public class TableModelVariaveis extends TableModelDefault {
   private static final long serialVersionUID = 1L;

   private List<VariavelBean> variaveis;

   private String[] colunas = new String[]{EnumVariaveisColunas.ID.getNome(),
                                           EnumVariaveisColunas.VALOR.getNome()};

   public TableModelVariaveis(List<VariavelBean> variaveis, JTable table) {
      super(table);
      this.variaveis = variaveis;
   }

   @Override
   public int getRowCount() {
       return this.variaveis.size();
   }

   @Override
   public int getColumnCount() {
       return this.colunas.length;
   }

   @Override
   public String getColumnName(int columnIndex) {
       return this.colunas[columnIndex];
   }

   @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
     return columnIndex == EnumVariaveisColunas.VALOR.getPosicao();
  }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Override
  public Class getColumnClass(int columnIndex) {
       return String.class;
   }

   @Override
   public Object getValueAt(int row, int column) {

       VariavelBean variavel = this.variaveis.get(row);

       if(column == EnumVariaveisColunas.ID.getPosicao()) {
          return variavel.getId();
       }

       if(column == EnumVariaveisColunas.VALOR.getPosicao()) {
          return variavel.getValor();
       }

       return "";
   }

   @Override
   public void setValueAt(Object aValue, int row, int column) {
       VariavelBean variavel = this.variaveis.get(row);

       if(column == EnumVariaveisColunas.ID.getPosicao()) {
          variavel.setId(aValue.toString());
          return;
       }

       if(column == EnumVariaveisColunas.VALOR.getPosicao()) {
          variavel.setValor( aValue.toString() );
       }
   }

   public List<VariavelBean> getVariaveis() {
      return this.variaveis;
   }

   public VariavelBean getVariavel(int indiceLinha) {
       return this.variaveis.get(indiceLinha);
   }

   public void addVariavel(VariavelBean variavel) {
       this.variaveis.add(variavel);
       int ultimoIndice = getRowCount() - 1;
       fireTableRowsInserted(ultimoIndice, ultimoIndice);
   }

   public void updateVariavel(int indiceLinha, VariavelBean variavel) {
       this.variaveis.set(indiceLinha, variavel);
       fireTableRowsUpdated(indiceLinha, indiceLinha);
   }

   public void removeVariavel(int indiceLinha) {
       this.variaveis.remove(indiceLinha);
       fireTableRowsDeleted(indiceLinha, indiceLinha);
   }

   public void update(List<VariavelBean> variaveis) {
     this.variaveis = variaveis;
     fireTableDataChanged();
   }
}
