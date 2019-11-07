package br.com.tarefas.gui.panel.painelTarefas.table;

import java.util.List;
import javax.swing.JTable;
import br.com.tarefas.data.tarefa.TarefaBean;
import br.com.tarefas.gui.table.TableModelDefault;

public class TableModelTarefas extends TableModelDefault {
	private static final long serialVersionUID = 1L;

	private List<TarefaBean> tarefas;

    private String[] colunas = new String[]{EnumTarefasColunas.SELECT.getNome(),
			                                EnumTarefasColunas.ID.getNome(),
			                                EnumTarefasColunas.DESCRICAO.getNome(),
			                                EnumTarefasColunas.PATH.getNome(),
			                                EnumTarefasColunas.PARAMETROS.getNome()};

    public TableModelTarefas(List<TarefaBean> tarefas, JTable table) {
       super(table);
       this.tarefas = tarefas;
    }

    @Override
    public int getRowCount() {
        return this.tarefas.size();
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
		return columnIndex == EnumTarefasColunas.SELECT.getPosicao();
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
	public Class getColumnClass(int columnIndex) {
        if (columnIndex == EnumTarefasColunas.SELECT.getPosicao()) {
            return Boolean.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int column) {

        TarefaBean tarefa = this.tarefas.get(row);

        if(column == EnumTarefasColunas.SELECT.getPosicao()) {
        	return tarefa.isSelected();
        }

        if(column == EnumTarefasColunas.ID.getPosicao()) {
        	return tarefa.getId();
        }

        if(column == EnumTarefasColunas.DESCRICAO.getPosicao()) {
        	return tarefa.getDescricao();
        }

        if(column == EnumTarefasColunas.PATH.getPosicao()) {
        	return tarefa.getPath();
        }

        if(column == EnumTarefasColunas.PARAMETROS.getPosicao()) {
        	return tarefa.getParametros();
        }

        return "";
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        TarefaBean tarefa = this.tarefas.get(row);

        if(column == EnumTarefasColunas.SELECT.getPosicao()) {
        	tarefa.setSelected((Boolean) aValue);
        	return;
        }

        if(column == EnumTarefasColunas.ID.getPosicao()) {
        	tarefa.setId((Integer) aValue);
        	return;
        }

        if(column == EnumTarefasColunas.DESCRICAO.getPosicao()) {
        	tarefa.setDescricao(aValue.toString());
        	return;
        }

        if(column == EnumTarefasColunas.PATH.getPosicao()) {
        	tarefa.setPath(aValue.toString());
        	return;
        }

        if(column == EnumTarefasColunas.PARAMETROS.getPosicao()) {
        	tarefa.setParametros(aValue.toString());
        }
    }

    public TarefaBean getTarefa(int indiceLinha) {
        return this.tarefas.get(indiceLinha);
    }

    public void addTarefa(TarefaBean tarefa) {
        this.tarefas.add(tarefa);
        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void updateTarefa(int indiceLinha, TarefaBean tarefa) {
        this.tarefas.set(indiceLinha, tarefa);
        fireTableRowsUpdated(indiceLinha, indiceLinha);
    }

    public void removeTarefa(int indiceLinha) {
        this.tarefas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }

    public void update(List<TarefaBean> tarefas) {
    	this.tarefas = tarefas;
    	fireTableDataChanged();
    }
}
