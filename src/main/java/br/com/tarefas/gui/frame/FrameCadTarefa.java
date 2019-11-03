package br.com.tarefas.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;

import br.com.tarefas.data.tarefa.TarefaBean;
import br.com.tarefas.data.tarefa.TarefaDAO;
import br.com.tarefas.gui.utils.FrameUtils;
import br.com.tarefas.gui.utils.bean.FrameBounds;
import br.com.tarefas.lib.StringUtils;

public class FrameCadTarefa extends JFrame {
	private static final long serialVersionUID = 1L;

	private final String TITULO = "Tarefa";
	private final int[] TAMANHO_JANELA = { 300, 200 };

	private JPanel painelPrincipal;

	private JPanel painelNorth;
	private JPanel painelCenter;
	private JPanel painelSouth;

	private TarefaBean tarefa;

	private JLabel lblId = new JLabel("ID");
	private JLabel lblDescricao = new JLabel("Descrição");
	private JLabel lblParametros = new JLabel("Parametros");

	private JTextField txtId = new JTextField();
	private JTextField txtDescricao = new JTextField();
	private JTextField txtParametros = new JTextField();

	private JButton btnLoadArquivo = new JButton("Arquivo");
	private JTextField txtPath = new JTextField();

	public FrameCadTarefa() throws Exception {
		this(new TarefaBean());
	}

	public FrameCadTarefa(TarefaBean tarefa) throws Exception {
		this.tarefa = tarefa;

		preparaJanela();
		preparaPainelPrincipal();
		carregaCampos();

		mostraJanela();
	}

	private void preparaJanela() {
		this.setTitle(TITULO);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void preparaPainelPrincipal() {
		this.painelPrincipal = new JPanel();
		this.painelPrincipal.setLayout(new BorderLayout());

		preparaPainelNorth();
		preparaPainelCenter();
		preparaPainelSouth();

		this.add(this.painelPrincipal);
	}

	private void preparaPainelNorth() {
		this.painelNorth = new JPanel();
		this.painelPrincipal.add(this.painelNorth, BorderLayout.NORTH);
	}

	private void preparaPainelCenter() {
		this.painelCenter = new JPanel();
		this.painelCenter.setLayout(new GridLayout(4, 2));
		this.painelCenter.add(this.lblId);
		this.painelCenter.add(this.txtId);
		this.painelCenter.add(this.lblDescricao);
		this.painelCenter.add(this.txtDescricao);
		this.painelCenter.add(this.lblParametros);
		this.painelCenter.add(this.txtParametros);
		this.painelCenter.add(this.btnLoadArquivo);
		this.painelCenter.add(this.txtPath);

		configuraCampos();

		this.painelPrincipal.add(painelCenter, BorderLayout.CENTER);
	}

	private void preparaPainelSouth() {
		this.painelSouth = new JPanel();
		this.painelPrincipal.add(this.painelSouth, BorderLayout.SOUTH);

		acaoBotaoSalvar();

		if(!(this.tarefa.getId() == 0)) {
			acaoBotaoExcluir();
		}
	}

	private void configuraCampos() {

		Color color = UIManager.getColor ( "Panel.background" );

		MatteBorder bordaLabel = BorderFactory.createMatteBorder(0, 10, 5, 0, color);
		MatteBorder bordaCampo = BorderFactory.createMatteBorder(0, 0, 5, 10, color);
		MatteBorder bordabtnArquivo = BorderFactory.createMatteBorder(0, 10, 5, 40, color);

		this.lblId.setBorder(bordaLabel);
		this.txtId.setBorder(bordaCampo);
		this.txtId.setEditable(false);

		this.lblDescricao.setBorder(bordaLabel);
		this.txtDescricao.setBorder(bordaCampo);

		this.lblParametros.setBorder(bordaLabel);
		this.txtParametros.setBorder(bordaCampo);

		this.btnLoadArquivo.setBorder(bordabtnArquivo);
		this.txtPath.setBorder(bordaCampo);
		acaoBotaoArquivo(btnLoadArquivo, txtPath);

		javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { txtDescricao.requestFocusInWindow(); } });
	}

	private void carregaCampos() {
		if(this.tarefa.getId() == 0) {
			tarefa.setId(new TarefaDAO().getNovoId());
		}

		txtId.setText(Integer.toString(this.tarefa.getId()));
		txtDescricao.setText(this.tarefa.getDescricao());
		txtParametros.setText(this.tarefa.getParametros());
		txtPath.setText(this.tarefa.getPath());
	}

	private void acaoBotaoArquivo(JButton botaoArquivo, JTextField txtPath ) {

		botaoArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					txtPath.setText(selectedFile.getAbsolutePath());
				}
			}
		});
	}

	private void acaoBotaoSalvar() {
		JButton botaoSalvar = new JButton("Salvar");

		botaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TarefaBean tarefaSalvar = new TarefaBean();
					tarefaSalvar.setId(tarefa.getId());
					tarefaSalvar.setDescricao(StringUtils.trim(txtDescricao.getText()));
					tarefaSalvar.setParametros(StringUtils.trim(txtParametros.getText()));
					tarefaSalvar.setPath(StringUtils.trim(txtPath.getText()));

					new TarefaDAO().salvar(tarefaSalvar);
					fechar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoSalvar);
	}

	private void acaoBotaoExcluir() {
		JButton botaoExcluir = new JButton("Excluir");

		botaoExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new FrameCadTarefa();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		painelSouth.add(botaoExcluir);
	}

	private void fechar() {
		this.dispose();
	}

	private void mostraJanela() throws Exception {
		this.pack();

		FrameBounds frameBounds = FrameUtils.getFrameBounds(TAMANHO_JANELA[0], TAMANHO_JANELA[1]);
		this.setBounds(frameBounds.getPosX(), frameBounds.getPosY(), frameBounds.getWidth(), frameBounds.getHeight());

		this.setSize(TAMANHO_JANELA[0], TAMANHO_JANELA[1]);
		this.setResizable(false);
		this.setVisible(true);
	}

}
