package br.com.tarefas.data.variaveis;

public class VariavelBean {
   private String id;
   private String valor;

   public VariavelBean() {}

   public VariavelBean(String id, String valor) {
      this.id = id;
      this.valor = valor;
   }

   public String getId() {
      return id;
   }

   public void setId( String id ) {
      this.id = id;
   }

   public String getValor() {
      return valor;
   }

   public void setValor( String valor ) {
      this.valor = valor;
   }
}
