package br.com.tarefas.gui.frame.cadVariaveis.table;

import br.com.tarefas.gui.table.EnumTableColumns;

public enum EnumVariaveisColunas implements EnumTableColumns {
   ID("Variável", 0),
   VALOR("Valor", 1);

   private final String nome;
   private final int posicao;

   EnumVariaveisColunas(String nome, int posicao) {
      this.nome = nome;
      this.posicao = posicao;
   }

   @Override
   public String getNome() {
      return this.nome;
   }

   @Override
   public int getPosicao() {
      return this.posicao;
   }
}
