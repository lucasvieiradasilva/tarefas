package br.com.tarefas.gui.panel.painelArquivos.table;

import br.com.tarefas.gui.table.EnumTableColumns;

public enum EnumArquivosColunas implements EnumTableColumns {
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

   @Override
   public String getNome() {
      return this.nome;
   }

   @Override
   public int getPosicao() {
      return this.posicao;
   }
}
