package br.com.tarefas.data.variaveis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.tarefas.data.XMLUtil;

public class VariavelDAO {
   public void salvar(VariavelBean variavel) {
      List<VariavelBean> variaveis = getVariaveis();

      for(int i = 0; i < variaveis.size(); i++) {
         if(variaveis.get(i).getId() == variavel.getId()) {
            variaveis.remove(i);
            break;
         }
      }

      variaveis.add(variavel);

      salvar(variaveis);
   }

   public void salvar(List<VariavelBean> variaveis) {
       salvar(XMLUtil.getInstance().toXML(variaveis));
   }

   public void remover(String id) {
      List<VariavelBean> variaveis = getVariaveis();

      for(int i = 0; i < variaveis.size(); i++) {
         if(variaveis.get(i).getId().equals( id ) ) {
            variaveis.remove(i);
            break;
         }
      }

      salvar(variaveis);
   }

   public VariavelBean getVariavel(String id) {
      List<VariavelBean> variaveis = getVariaveis();

      for(int i = 0; i < variaveis.size(); i++) {
         if(variaveis.get(i).getId().equals( id )) {
            return variaveis.get(i);
         }
      }
      return null;
   }

   @SuppressWarnings("unchecked")
   public List<VariavelBean> getVariaveis() {
      try {
         return (List<VariavelBean>) XMLUtil.getInstance().fromXML(getFile());
      }
      catch(Throwable e) {
         e.printStackTrace();
      }
      return new ArrayList<VariavelBean>();
   }

   private void salvar(String xml) {
      if(xml.isEmpty()) {
         return;
      }

      try {
         FileOutputStream buffer = new FileOutputStream(getFile());
         buffer.write(xml.getBytes());
         buffer.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private File getFile() throws IOException {
      File file = new File("tarefas_variavel.xml");

      if(!file.exists()) {
         file.createNewFile();
      }

      return file;
   }
}
