package br.com.tarefas.lib;

public class StringUtils {

	public static boolean isEmpty(String value) {
		if(value == null) {
			return true;
		}

		if(trim(value).length() == 0) {
			return true;
		}
		return false;
	}

	public static String trim(String value) {
		if(value == null) {
			return "";
		}
		return value.trim();
	}

	public static String letfTrim(String value) {
		if(isEmpty(value)) {
			return "";
		}

		int count = 0;
		for(int i = 0; i < value.length(); i++ ) {
			if(!value.subSequence(i, i + 1).equals(" ")) {
				break;
			}
			count++;
		}

		if(count == 0) {
			return value;
		}

		return value.substring(count, value.length());
	}

	public static String rightTrim(String value) {
		if(isEmpty(value)) {
			return "";
		}

		int count = 0;
		for(int i = value.length(); i > 0; i-- ) {
			if(!value.subSequence(i -1, i).equals(" ")) {
				break;
			}
			count++;
		}

		if(count == 0) {
			return value;
		}
		return value.substring(0, value.length() - count);
	}

	public static boolean equals(String value1, String value2) {
		return trim(value1).equals(trim(value2));
	}
}
