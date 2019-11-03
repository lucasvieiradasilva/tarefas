package br.com.tarefas.data.tarefa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.tarefas.data.XMLUtil;

public class TarefaDAO {
	public void salvar(TarefaBean tarefa) {
		List<TarefaBean> tarefas = getTarefas();

		for(int i = 0; i < tarefas.size(); i++) {
			if(tarefas.get(i).getId() == tarefa.getId()) {
				tarefas.remove(i);
				break;
			}
		}

		tarefas.add(tarefa);

		salvar(tarefas);
	}

	public void salvar(List<TarefaBean> tarefas) {
	    salvar(XMLUtil.getInstance().toXML(tarefas));
	}

	public void remover(int id) {
		List<TarefaBean> tarefas = getTarefas();

		for(int i = 0; i < tarefas.size(); i++) {
			if(tarefas.get(i).getId() == id ) {
				tarefas.remove(i);
				break;
			}
		}

		salvar(tarefas);
	}

	public TarefaBean getTarefa(int id) {
		List<TarefaBean> tarefas = getTarefas();

		for(int i = 0; i < tarefas.size(); i++) {
			if(tarefas.get(i).getId() == id) {
				return tarefas.get(i);
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<TarefaBean> getTarefas() {
		try {
			return (List<TarefaBean>) XMLUtil.getInstance().fromXML(getFile());
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		return new ArrayList<TarefaBean>();
	}

	public int getNovoId() {
		List<TarefaBean> tarefas = getTarefas();

		int idMaior = 0;

		for(int i = 0; i < tarefas.size(); i++) {
			if(tarefas.get(i).getId() > idMaior) {
				idMaior = tarefas.get(i).getId();
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
		File file = new File("tarefas_tarefa.xml");

		if(!file.exists()) {
			file.createNewFile();
		}

		return file;
	}
}
