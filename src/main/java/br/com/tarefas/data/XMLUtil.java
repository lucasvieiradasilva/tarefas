package br.com.tarefas.data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLUtil {
	private static XStream stream;

	public static XStream getInstance() {
		if(stream == null) {
			stream = new XStream(new DomDriver());
		}

		return stream;
	}

	public static String toXml(Object obj) {
		return getInstance().toXML(obj);
	}
}
