package br.com.tarefas.data.arquivo;

public class ArquivoBean {
   private int id;
   private boolean selected;
   private String descricao;
   private String path;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public boolean isSelected() {
      return selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path;
   }
}
