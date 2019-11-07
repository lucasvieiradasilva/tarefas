package br.com.tarefas.gui.panel.painelArquivos.table;

import java.util.List;
import javax.swing.JTable;
import br.com.tarefas.data.arquivo.ArquivoBean;
import br.com.tarefas.gui.table.TableModelDefault;

public class TableModelArquivos extends TableModelDefault {
   private static final long serialVersionUID = 1L;

   private List<ArquivoBean> arquivos;

   private String[] colunas = new String[]{EnumArquivosColunas.SELECT.getNome(),
                                           EnumArquivosColunas.ID.getNome(),
                                           EnumArquivosColunas.DESCRICAO.getNome(),
                                           EnumArquivosColunas.PATH.getNome()};

   public TableModelArquivos(List<ArquivoBean> arquivos, JTable table) {
      super(table);
      this.arquivos = arquivos;
   }

   @Override
   public int getRowCount() {
       return this.arquivos.size();
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
     return columnIndex == EnumArquivosColunas.SELECT.getPosicao();
  }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Override
  public Class getColumnClass(int columnIndex) {
       if (columnIndex == EnumArquivosColunas.SELECT.getPosicao()) {
           return Boolean.class;
       }
       return String.class;
   }

   @Override
   public Object getValueAt(int row, int column) {

       ArquivoBean arquivo = this.arquivos.get(row);

       if(column == EnumArquivosColunas.SELECT.getPosicao()) {
          return arquivo.isSelected();
       }

       if(column == EnumArquivosColunas.ID.getPosicao()) {
          return arquivo.getId();
       }

       if(column == EnumArquivosColunas.DESCRICAO.getPosicao()) {
          return arquivo.getDescricao();
       }

       if(column == EnumArquivosColunas.PATH.getPosicao()) {
          return arquivo.getPath();
       }

       return "";
   }

   @Override
   public void setValueAt(Object aValue, int row, int column) {
       ArquivoBean arq = this.arquivos.get(row);

       if(column == EnumArquivosColunas.SELECT.getPosicao()) {
          arq.setSelected((Boolean) aValue);
          return;
       }

       if(column == EnumArquivosColunas.ID.getPosicao()) {
          arq.setId((Integer) aValue);
          return;
       }

       if(column == EnumArquivosColunas.DESCRICAO.getPosicao()) {
          arq.setDescricao(aValue.toString());
          return;
       }

       if(column == EnumArquivosColunas.PATH.getPosicao()) {
          arq.setPath(aValue.toString());
       }
   }

   public List<ArquivoBean> getArquivos() {
      return this.arquivos;
   }

   public ArquivoBean getArquivo(int indiceLinha) {
       return this.arquivos.get(indiceLinha);
   }

   public void addArquivo(ArquivoBean arquivo) {
       this.arquivos.add(arquivo);
       int ultimoIndice = getRowCount() - 1;
       fireTableRowsInserted(ultimoIndice, ultimoIndice);
   }

   public void updateTarefa(int indiceLinha, ArquivoBean arquivo) {
       this.arquivos.set(indiceLinha, arquivo);
       fireTableRowsUpdated(indiceLinha, indiceLinha);
   }

   public void removeArquivo(int indiceLinha) {
       this.arquivos.remove(indiceLinha);
       fireTableRowsDeleted(indiceLinha, indiceLinha);
   }

   public void update(List<ArquivoBean> arquivos) {
     this.arquivos = arquivos;
     fireTableDataChanged();
   }
}
