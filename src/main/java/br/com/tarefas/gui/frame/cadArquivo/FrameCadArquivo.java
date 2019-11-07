package br.com.tarefas.gui.frame.cadArquivo;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;
import br.com.tarefas.data.arquivo.ArquivoBean;
import br.com.tarefas.data.arquivo.ArquivoDAO;
import br.com.tarefas.gui.utils.FrameUtils;
import br.com.tarefas.gui.utils.bean.FrameBounds;
import br.com.tarefas.lib.StringUtils;

public class FrameCadArquivo extends JFrame {
   private static final long serialVersionUID = 1L;

   private final String TITULO = "Arquivo";
   private final int[] TAMANHO_JANELA = { 600, 180 };

   private JPanel painelPrincipal;
   private JFrame frameAnterior;

   private JPanel painelNorth;
   private JPanel painelCenter;
   private JPanel painelSouth;

   private ArquivoBean arquivo;

   private JLabel lblId = new JLabel("ID");
   private JLabel lblDescricao = new JLabel("Descrição");

   private JTextField txtId = new JTextField();
   private JTextField txtDescricao = new JTextField();

   private JButton btnLoadArquivo = new JButton("Arquivo");
   private JTextField txtPath = new JTextField();

   public FrameCadArquivo(JFrame frame) throws Exception {
      this(new ArquivoBean(), frame);
   }

   public FrameCadArquivo(ArquivoBean arquivo, JFrame frame) throws Exception {
      this.arquivo = arquivo;
      this.frameAnterior = frame;

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
      this.painelCenter.setLayout(new GridLayout(3, 2));
      this.painelCenter.add(this.lblId);
      this.painelCenter.add(this.txtId);
      this.painelCenter.add(this.lblDescricao);
      this.painelCenter.add(this.txtDescricao);
      this.painelCenter.add(this.btnLoadArquivo);
      this.painelCenter.add(this.txtPath);

      configuraCampos();

      this.painelPrincipal.add(painelCenter, BorderLayout.CENTER);
   }

   private void preparaPainelSouth() {
      this.painelSouth = new JPanel();
      this.painelPrincipal.add(this.painelSouth, BorderLayout.SOUTH);

      acaoBotaoSalvar();

      if(!(this.arquivo.getId() == 0)) {
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

      this.btnLoadArquivo.setBorder(bordabtnArquivo);
      this.txtPath.setBorder(bordaCampo);
      acaoBotaoArquivo(btnLoadArquivo, txtPath);

      javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { txtDescricao.requestFocusInWindow(); } });
   }

   private void carregaCampos() {
      if(this.arquivo.getId() == 0) {
         arquivo.setId(new ArquivoDAO().getNovoId());
      }

      txtId.setText(Integer.toString(this.arquivo.getId()));
      txtDescricao.setText(this.arquivo.getDescricao());
      txtPath.setText(this.arquivo.getPath());
   }

   private void acaoBotaoArquivo(JButton botaoArquivo, JTextField txtPath ) {

      botaoArquivo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(FrameCadArquivo.this);

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

               if(StringUtils.trim(txtDescricao.getText()).isEmpty()) {
                  JOptionPane.showMessageDialog(FrameCadArquivo.this, "O arquivo deverá conter uma descrição!", "Alerta", JOptionPane.ERROR_MESSAGE);
                  return;
               }

               if(StringUtils.trim(txtPath.getText()).isEmpty()) {
                  JOptionPane.showMessageDialog(FrameCadArquivo.this, "O arquivo deverá conter um caminho!", "Alerta", JOptionPane.ERROR_MESSAGE);
                  return;
               }

               ArquivoBean arquivoSalvar = new ArquivoBean();
               arquivoSalvar.setId(arquivo.getId());
               arquivoSalvar.setDescricao(StringUtils.trim(txtDescricao.getText()));
               arquivoSalvar.setPath(StringUtils.trim(txtPath.getText()));
               arquivoSalvar.setSelected(false);

               new ArquivoDAO().salvar(arquivoSalvar);
               dispose();
            } catch (Exception e1) {
               e1.printStackTrace();
            } catch (Throwable e1) {
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
               new ArquivoDAO().remover(arquivo.getId());
               dispose();
            } catch (Exception e1) {
               e1.printStackTrace();
            }
         }
      });

      painelSouth.add(botaoExcluir);
   }

   private void mostraJanela() throws Exception {
      this.pack();

      FrameBounds frameBounds = FrameUtils.getFrameBounds(TAMANHO_JANELA[0], TAMANHO_JANELA[1], this.frameAnterior);
      this.setBounds(frameBounds.getPosX(), frameBounds.getPosY(), frameBounds.getWidth(), frameBounds.getHeight());

      this.setSize(TAMANHO_JANELA[0], TAMANHO_JANELA[1]);
      this.setResizable(false);
      this.setVisible(true);
   }
}
