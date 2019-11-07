package br.com.tarefas.gui.panel.painelTarefas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import br.com.tarefas.data.tarefa.TarefaBean;
import br.com.tarefas.data.tarefa.TarefaDAO;
import br.com.tarefas.gui.frame.FramePrincipal;
import br.com.tarefas.gui.frame.cadTarefa.FrameCadTarefa;
import br.com.tarefas.gui.panel.painelConsole.PainelConsole;
import br.com.tarefas.gui.panel.painelTarefas.table.EnumTarefasColunas;
import br.com.tarefas.gui.panel.painelTarefas.table.TableModelTarefas;
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
	private TableModelTarefas model;

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

		model = new TableModelTarefas(new TarefaDAO().getTarefas(), this.tabela);

		this.tabela.setModel(this.model);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		TableColumn colunaSelect = this.tabela.getColumn(EnumTarefasColunas.SELECT.getNome());
		colunaSelect.setMaxWidth(20);
		colunaSelect.setResizable(false);

		TableColumn colunaId = this.tabela.getColumn(EnumTarefasColunas.ID.getNome());
		colunaId.setCellRenderer(centerRenderer);
		colunaId.setMaxWidth(70);
		colunaId.setResizable(false);


		TableColumn colunaDescricao = this.tabela.getColumn(EnumTarefasColunas.DESCRICAO.getNome());
		colunaDescricao.setPreferredWidth(150);

		TableColumn colunaPath = this.tabela.getColumn(EnumTarefasColunas.PATH.getNome());
		colunaPath.setPreferredWidth(300);

		TableColumn colunaParametros = this.tabela.getColumn(EnumTarefasColunas.PARAMETROS.getNome());
		colunaParametros.setPreferredWidth(200);

		this.painelCenter.getViewport().add(this.tabela);
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
		acaoBotaoEditar();
		acaoBotaoExecutar();
		acaoBotaoPararTodos();
	}

	private void acaoBotaoNovaTarefa() {
		JButton botaoNovaTarefa = new JButton("Nova tarefa");

		botaoNovaTarefa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FrameCadTarefa frameCadTarefa = new FrameCadTarefa(frame);
					frameCadTarefa.addWindowListener(new java.awt.event.WindowAdapter() {
					    public void windowClosed(java.awt.event.WindowEvent e) {
					    	model.update(new TarefaDAO().getTarefas());
				    	};
					});
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoNovaTarefa);
	}

	private void acaoBotaoEditar() {
		JButton botaoEditarTarefa = new JButton("Editar tarefa");

		botaoEditarTarefa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TarefaBean tarefa = null;

				for(int i = 0; i < tabela.getRowCount(); i++  ) {
					TarefaBean t = model.getTarefa(i);

					if(t != null && t.isSelected()) {
						if(tarefa != null) {
							JOptionPane.showMessageDialog(PainelTarefas.this, "Somente uma tarefa deverá estar selecionada!", "Alerta", JOptionPane.ERROR_MESSAGE);
							return;
						}

						tarefa = t;
					}
				}

				if(tarefa == null) {
					JOptionPane.showMessageDialog(PainelTarefas.this, "Nenhuma tarefa selecionada!", "Alerta", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					FrameCadTarefa frameCadTarefa = new FrameCadTarefa(tarefa, frame);
					frameCadTarefa.addWindowListener(new java.awt.event.WindowAdapter() {
					    public void windowClosed(java.awt.event.WindowEvent e) {
					    	model.update(new TarefaDAO().getTarefas());
				    	};
					});
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoEditarTarefa);
	}

	private void acaoBotaoExecutar() {
		JButton botaoExecutar = new JButton("Executar");

		botaoExecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean hasTarefa = false;

				try {
					for(int i = 0; i < tabela.getRowCount(); i++  ) {
						TarefaBean tarefa = model.getTarefa(i);

						if(tarefa == null || !tarefa.isSelected()) {
							continue;
						}

						hasTarefa = true;

						String cmd = "";

						if(tarefa.getPath().toUpperCase().contains(".JAR") || tarefa.getPath().toUpperCase().contains(".WAR")) {
							cmd = "java -jar";
						}

						new CMDUtils().executaCmd(cmd, tarefa.getPath(), painelConsole.criaNovoPainelLogavel(CMDUtils.getNewProcessName(tarefa.getPath())), tarefa.getParametros());
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(PainelTarefas.this, "Ocorreu um erro ao executar a(s) tarefa(s)!", "Alerta", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

				if(CMDUtils.mapProcessos.size() > 0) {
					frame.getAbas().setEnabledAt(painelConsole.getTabIndex(), true);
				}
				else {
					try {
						CMDUtils.pararTodosProcessos(painelConsole);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				if(!hasTarefa) {
					JOptionPane.showMessageDialog(PainelTarefas.this, "Nenhuma tarefa selecionada!", "Alerta", JOptionPane.ERROR_MESSAGE);
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
					CMDUtils.pararTodosProcessos(painelConsole);
					painelConsole.removeTodos();
					frame.getAbas().setEnabledAt(painelConsole.getTabIndex(), false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoPararTodos);
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
}