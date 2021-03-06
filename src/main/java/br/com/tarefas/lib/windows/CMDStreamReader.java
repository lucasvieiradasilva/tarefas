package br.com.tarefas.lib.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CMDStreamReader implements Runnable {
	private Process process;
    private BufferedReader reader;
    private PainelLogavel painel;

    public CMDStreamReader(Process process, InputStream is, PainelLogavel painel) {
    	this.process = process;
        this.reader = new BufferedReader(new InputStreamReader(is));
        this.painel = painel;
    }

    public void run() {
        try {
            String line = reader.readLine();
            while (line != null) {
            	painel.loga(line);
                line = reader.readLine();
            }

            painel.setFinalizado(true);

            reader.close();
        } catch (IOException e) {
        	CMDUtils.mapProcessos.remove(painel.getProcessName());
            e.printStackTrace();
        }
    }

    public void parar() throws IOException {
    	process.destroy();
    	reader.close();
    	this.painel.destroy();
    }
}
