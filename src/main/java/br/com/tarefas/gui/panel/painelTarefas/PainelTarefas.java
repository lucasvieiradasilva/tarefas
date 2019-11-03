package br.com.tarefas.gui.panel.painelTarefas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import br.com.tarefas.data.tarefa.TarefaBean;
import br.com.tarefas.data.tarefa.TarefaDAO;
import br.com.tarefas.gui.frame.FrameCadTarefa;
import br.com.tarefas.gui.frame.FramePrincipal;
import br.com.tarefas.gui.panel.painelConsole.PainelConsole;
import br.com.tarefas.lib.windows.CMDUtils;

public class PainelTarefas extends JPanel {
	private static final long serialVersionUID = 1L;

	private FramePrincipal frame;
	private final PainelConsole painelConsole;

	private int tabIndex = 0;

	private JPanel painelNorth;
	private JScrollPane painelCenter;
	private JPanel painelSouth;

	private JTable tabela;
	private DefaultTableModel model;

	public PainelTarefas(FramePrincipal frame, PainelConsole painelConsole ) {
		this.frame = frame;
		this.painelConsole = painelConsole;

		this.setLayout(new BorderLayout());

		preparaPainelNorth();
		preparaPainelCenter();
		preparaPainelSouth();
	}

	private void preparaPainelNorth() {
		this.painelNorth = new JPanel();
		this.add(painelNorth, BorderLayout.NORTH);
	}

	private void preparaTabela() {

		this.tabela = new JTable();
		this.tabela.setBorder(new LineBorder(Color.black));
		this.tabela.setGridColor(Color.black);
		this.tabela.setShowGrid(true);
		this.tabela.setAlignmentX(0);
		this.tabela.setAlignmentY(0);

		this.model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == Colunas.SELECT.getPosicao();
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
//				if(columnIndex == Colunas.SELECT.getPosicao() ) {
//					return Boolean.class;
//				}

				return super.getColumnClass(columnIndex);
			}
		};

		this.model.addColumn(Colunas.SELECT.getNome());
		this.model.addColumn(Colunas.ID.getNome());
		this.model.addColumn(Colunas.DESCRICAO.getNome());
		this.model.addColumn(Colunas.PATH.getNome());
		this.model.addColumn(Colunas.PARAMETROS.getNome());

		this.tabela.setModel(this.model);
		this.tabela.getTableHeader().revalidate();

		this.painelCenter.getViewport().add(this.tabela);

		TableColumn colunaSelect = this.tabela.getColumn(Colunas.SELECT.getNome());
		colunaSelect.setMaxWidth(5);
		colunaSelect.setResizable(false);

		TableColumn colunaId = this.tabela.getColumn(Colunas.ID.getNome());
		colunaId.setMaxWidth(70);
		colunaId.setResizable(false);


		TableColumn colunaDescricao = this.tabela.getColumn(Colunas.DESCRICAO.getNome());
		colunaDescricao.setPreferredWidth(150);

		TableColumn colunaPath = this.tabela.getColumn(Colunas.PATH.getNome());
		colunaPath.setPreferredWidth(300);

		TableColumn colunaParametros = this.tabela.getColumn(Colunas.PARAMETROS.getNome());
		colunaParametros.setPreferredWidth(200);

		carregaTabela();
	}


	private void preparaPainelCenter() {
		this.painelCenter = new JScrollPane();

		preparaTabela();

		this.add(painelCenter, BorderLayout.CENTER);
	}

	private void preparaPainelSouth() {
		painelSouth = new JPanel();
		this.add(painelSouth, BorderLayout.SOUTH);

		acaoBotaoNovaTarefa();
		acaoBotaoEditarTarefa();
		acaoBotaoExecutar();
		acaoBotaoPararTodos();
	}

	private void acaoBotaoNovaTarefa() {
		JButton botaoNovaTarefa = new JButton("Nova tarefa");

		botaoNovaTarefa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FrameCadTarefa frameCadTarefa = new FrameCadTarefa();
					frameCadTarefa.addWindowListener(new java.awt.event.WindowAdapter() {
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					    	carregaTabela();
					    }
					});
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoNovaTarefa);
	}

	private void acaoBotaoEditarTarefa() {
		JButton botaoEditarTarefa = new JButton("Editar tarefa");

		botaoEditarTarefa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		painelSouth.add(botaoEditarTarefa);
	}

	private void acaoBotaoExecutar() {
		JButton botaoExecutar = new JButton("Executar");

		botaoExecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for(int i = 0; i < tabela.getRowCount(); i++  ) {

						String path = (String) tabela.getValueAt(i, Colunas.PATH.getPosicao());
						String parametros = (String) tabela.getValueAt(i, Colunas.PARAMETROS.getPosicao());

						String cmd = "";

						if(path.toUpperCase().contains(".JAR") || path.toUpperCase().contains(".WAR")) {
							cmd = "java -jar" + parametros;
						}

						new CMDUtils().executaCmd(cmd, path, painelConsole.criaNovoPainelLogavel(CMDUtils.getNewProcessName(path)));
					}
				}catch (Exception ex) {
					ex.printStackTrace();
				}

				if(CMDUtils.mapProcessos.size() > 0) {
					frame.getAbas().setEnabledAt(painelConsole.getTabIndex(), true);
				}
			}
		});

		painelSouth.add(botaoExecutar);
	}

	private void acaoBotaoPararTodos() {
		JButton botaoPararTodos = new JButton("Parar todos");

		botaoPararTodos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CMDUtils.pararTodosProcessos();
					painelConsole.removeTodos();
					frame.getAbas().setEnabledAt(painelConsole.getTabIndex(), false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoPararTodos);
	}

	private void carregaTabela() {
		for( int i = 0; i < this.model.getRowCount(); i++) {
			this.model.removeRow(i);
		}

		this.model.setRowCount(0);

		List<TarefaBean> tarefas = new TarefaDAO().getTarefas();

		for(TarefaBean tarefa : tarefas) {
			Vector<String> vec = new Vector<>();

			vec.add("");
			vec.add(Integer.toString(tarefa.getId()));
			vec.add(tarefa.getDescricao());
			vec.add(tarefa.getPath());
			vec.add(tarefa.getParametros());

			this.model.addRow(vec);
		}
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	private enum Colunas {
		SELECT("", 0),
		ID("Id", 1),
		DESCRICAO("Descrição", 2),
		PATH("Arquivo", 3),
		PARAMETROS("Parametros", 4);

		private final String nome;
		private final int posicao;

		Colunas(String nome, int posicao) {
			this.nome = nome;
			this.posicao = posicao;
		}

		public String getNome() {
			return this.nome;
		}

		public int getPosicao() {
			return this.posicao;
		}
	}
}