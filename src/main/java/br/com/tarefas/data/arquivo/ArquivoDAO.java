package br.com.tarefas.data.arquivo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.tarefas.data.XMLUtil;

public class ArquivoDAO {
   public void salvar(ArquivoBean arquivo) {
      List<ArquivoBean> arquivos = getArquivos();

      for(int i = 0; i < arquivos.size(); i++) {
         if(arquivos.get(i).getId() == arquivo.getId()) {
            arquivos.remove(i);
            break;
         }
      }

      arquivos.add(arquivo);

      salvar(arquivos);
   }

   public void salvar(List<ArquivoBean> arquvios) {
       salvar(XMLUtil.getInstance().toXML(arquvios));
   }

   public void remover(int id) {
      List<ArquivoBean> arquivos = getArquivos();

      for(int i = 0; i < arquivos.size(); i++) {
         if(arquivos.get(i).getId() == id ) {
            arquivos.remove(i);
            break;
         }
      }

      salvar(arquivos);
   }

   public ArquivoBean getArquivo(int id) {
      List<ArquivoBean> arquivos = getArquivos();

      for(int i = 0; i < arquivos.size(); i++) {
         if(arquivos.get(i).getId() == id) {
            return arquivos.get(i);
         }
      }

      return null;
   }

   @SuppressWarnings("unchecked")
   public List<ArquivoBean> getArquivos() {
      try {
         return (List<ArquivoBean>) XMLUtil.getInstance().fromXML(getFile());
      }
      catch(Throwable e) {
         e.printStackTrace();
      }
      return new ArrayList<ArquivoBean>();
   }

   public int getNovoId() {
      List<ArquivoBean> arquivos = getArquivos();

      int idMaior = 0;

      for(int i = 0; i < arquivos.size(); i++) {
         if(arquivos.get(i).getId() > idMaior) {
            idMaior = arquivos.get(i).getId();
         }
      }

      return ++idMaior;
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
      File file = new File("tarefas_arquivo.xml");

      if(!file.exists()) {
         file.createNewFile();
      }

      return file;
   }
}
