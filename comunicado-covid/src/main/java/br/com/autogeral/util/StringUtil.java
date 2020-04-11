package br.com.autogeral.util;

public class StringUtil {

	public static String soPrimeiraMaiuscula(String astring) {
		if (astring == null) {
			return null;
		}
		String palavras[] = astring.split(" ");
		StringBuilder sb = new StringBuilder();
		boolean primeira = true;
		for (String palavra : palavras) {
			if (primeira) {
				primeira = false;
			} else {
				sb.append(" ");
			}
			String tratada = soPrimeiraMaiusculaPalavra(palavra);
			sb.append(tratada);
		}
		return sb.toString();
	}

	public static String soPrimeiraMaiusculaPalavra(String astring) {
		if (astring == null) {
			return null;
		}
		String string = astring.toLowerCase();
		if (string.length() == 1) {
			return string;
		}
		if (string.equals("da") || string.equals("de") || string.equals("do")) {
			return string;
		}
		return (string.length() > 2 ? string.substring(0, 1).toUpperCase() + string.substring(1) : string);
	}

}
