package br.com.tarefas.gui.frame.cadVariaveis.table;

public enum EnumVariaveisColunas {
   ID("Variável", 0),
   VALOR("Valor", 1);

   private final String nome;
   private final int posicao;

   EnumVariaveisColunas(String nome, int posicao) {
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
