package br.com.tarefas.gui.frame.cadVariaveis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import br.com.tarefas.data.arquivo.ArquivoBean;
import br.com.tarefas.data.variaveis.VariavelBean;
import br.com.tarefas.data.variaveis.VariavelDAO;
import br.com.tarefas.gui.frame.cadVariaveis.table.EnumVariaveisColunas;
import br.com.tarefas.gui.frame.cadVariaveis.table.TableModelVariaveis;
import br.com.tarefas.gui.utils.FrameUtils;
import br.com.tarefas.gui.utils.bean.FrameBounds;
import br.com.tarefas.lib.StringUtils;

public class FrameCadVariaveis extends JFrame {
   private static final long serialVersionUID = 1L;

   private final String TITULO = "Variáveis";
   private final int[] TAMANHO_JANELA = { 800, 700 };

   private JPanel painelPrincipal;

   private JPanel painelNorth;
   private JScrollPane painelCenter;
   private JPanel painelSouth;

   private String abertura = "{{";
   private String fechamento = "}}";

   private List<ArquivoBean> arquivos;
   private JFrame frameAnterior;

   private JTable tabela;
   private TableModelVariaveis model;

   public FrameCadVariaveis(List<ArquivoBean> arquivos, JFrame frame) throws Exception {
      this.arquivos = arquivos;
      this.frameAnterior = frame;

      preparaJanela();
      preparaPainelPrincipal();

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
      this.painelCenter = new JScrollPane();

      preparaTabela();

      this.painelPrincipal.add(painelCenter, BorderLayout.CENTER);
   }

   private void preparaPainelSouth() {
      this.painelSouth = new JPanel();
      this.painelPrincipal.add(this.painelSouth, BorderLayout.SOUTH);

      acaoBotaoSalvar();
   }

   private void preparaTabela() {

      this.tabela = new JTable();
      this.tabela.setBorder(new LineBorder(Color.black));
      this.tabela.setGridColor(Color.black);
      this.tabela.setShowGrid(true);
      this.tabela.setAlignmentX(0);
      this.tabela.setAlignmentY(0);

      this.model = new TableModelVariaveis(getVariaveis(), this.tabela);

      this.tabela.setModel(this.model);

      TableColumn colunaId = this.tabela.getColumn(EnumVariaveisColunas.ID.getNome());
      colunaId.setPreferredWidth(150);

      TableColumn colunaDescricao = this.tabela.getColumn(EnumVariaveisColunas.VALOR.getNome());
      colunaDescricao.setPreferredWidth(150);

      this.painelCenter.getViewport().add(this.tabela);
   }

   private void acaoBotaoSalvar() {
      JButton botaoSalvar = new JButton("Salvar");

      botaoSalvar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new VariavelDAO().salvar( model.getVariaveis() );
            reescreveArquivos();
            JOptionPane.showMessageDialog(FrameCadVariaveis.this, "Variáveis salvas com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            dispose();
         }
      });

      painelSouth.add(botaoSalvar);
   }

   private void mostraJanela() throws Exception {
      this.pack();

      FrameBounds frameBounds = FrameUtils.getFrameBounds(TAMANHO_JANELA[0], TAMANHO_JANELA[1], this.frameAnterior);
      this.setBounds(frameBounds.getPosX(), frameBounds.getPosY(), frameBounds.getWidth(), frameBounds.getHeight());

      this.setSize(TAMANHO_JANELA[0], TAMANHO_JANELA[1]);
      this.setResizable(false);
      this.setVisible(true);
   }

   private List<VariavelBean> getVariaveis() {
      Map<String, VariavelBean> variaveisMap = new HashMap<>();

      for(ArquivoBean arquivoBean : this.arquivos) {

         try {
            BufferedReader br = new BufferedReader(new FileReader(arquivoBean.getPath()));

            while(br.ready()){
               String variavel = getVariavelFromString(br.readLine());

               if(variavel != null && !variavel.isEmpty()  ) {
                  if(!variaveisMap.containsKey(StringUtils.trim(variavel))) {
                     variaveisMap.put( StringUtils.trim(variavel), new VariavelBean(StringUtils.trim(variavel), ""));
                  }
               }
            }
            br.close();
         }catch(Exception e) {
            e.printStackTrace();
         }
      }

      return carregaVariaveis(variaveisMap);
   }

   private List<VariavelBean> carregaVariaveis( Map<String, VariavelBean> variaveisMap ) {
      Map<String, VariavelBean> variaveisRetornar = new HashMap<>();

      for(VariavelBean variavelSalva : new VariavelDAO().getVariaveis()) {
         if(variavelSalva == null || variavelSalva.getId() == null || variavelSalva.getId().isEmpty()) {
            continue;
         }

         if(variaveisMap.containsKey(variavelSalva.getId())) {
            variaveisRetornar.put(variavelSalva.getId(), variavelSalva);
         }
      }

      for(Entry<String, VariavelBean> entry : variaveisMap.entrySet()) {
         if(!variaveisRetornar.containsKey(entry.getKey())) {
            variaveisRetornar.put(entry.getKey(), entry.getValue());
         }
      }

      return getVariaveisList(variaveisRetornar);
   }

   private List<VariavelBean> getVariaveisList( Map<String, VariavelBean> variaveisMap ) {
      List<VariavelBean> variaveis = new ArrayList<>();

      for(VariavelBean variavel : variaveisMap.values()) {
         variaveis.add( variavel );
      }

      return variaveis;
   }

   // considera apenas uma variavel por linha
   private String getVariavelFromString(String linha) {
      if(linha == null || linha.isEmpty()) {
         return "";
      }

      int inicio = linha.indexOf( abertura );
      int fim = linha.indexOf( fechamento );

      if(linha.contains( abertura ) && linha.contains( fechamento ) ) {
         return linha.substring( inicio + 2, fim );
      }

      return "";
   }

   private void reescreveArquivos() {
      for(ArquivoBean arquivoBean : this.arquivos) {

         try {
            BufferedReader br = new BufferedReader(new FileReader(arquivoBean.getPath()));
            FileOutputStream bufferSaida = new FileOutputStream(getPathArquivo(arquivoBean.getPath()));

            while(br.ready()){
               montaLinha(bufferSaida, br.readLine());
            }

            br.close();
            bufferSaida.close();
         }catch(Exception e) {
            e.printStackTrace();
         }
      }
   }

   private String getPathArquivo(String path) {
      return path.substring(0, path.length() - 7);
   }

   // considera que sempre a variável estará no final, no padrao chave e valor
   private void montaLinha(FileOutputStream bufferSaida, String linha) throws IOException {


      String variavel = getVariavelFromString(linha);

      if(variavel != null && !variavel.isEmpty()  ) {
         VariavelBean variavelBean = new VariavelDAO().getVariavel( StringUtils.trim( variavel ) );

         String textoAntes = linha.substring(0, linha.indexOf( abertura ));
         String valorVariavel = variavelBean != null ? variavelBean.getValor() : "";

         String novoTexto = textoAntes + valorVariavel + "\n";

         bufferSaida.write(novoTexto.getBytes());
         return;
      }

      bufferSaida.write((linha + "\n").getBytes());
   }
}
