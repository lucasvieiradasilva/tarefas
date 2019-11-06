package br.com.tarefas.gui.panel.painelArquivos.table;

public enum EnumArquivosColunas {
   SELECT("", 0),
   ID("Id", 1),
   DESCRICAO("Descrição", 2),
   PATH("Arquivo", 3);

   private final String nome;
   private final int posicao;

   EnumArquivosColunas(String nome, int posicao) {
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
