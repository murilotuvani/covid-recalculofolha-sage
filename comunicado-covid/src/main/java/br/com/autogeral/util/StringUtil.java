package br.com.autogeral.util;

public class StringUtil {
	
	public static boolean isNull(String str) {
        return (str == null || str.trim().equals(""));
    }

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
	
	public static String noDeadKeysToUpperCase(String str) {
        if (isNull(str)) {
            return str;
        }
        return str.toUpperCase().replace("Á", "A")
                .replace("À", "A")
                .replace("Ã", "A")
                .replace("Ä", "A")
                .replace("Â", "A")
                .replace("Ç", "C")
                .replace("Ñ", "N")
                .replace("É", "E")
                .replace("Ê", "E")
                .replace("È", "E")
                .replace("Ë", "E")
                .replace("Í", "I")
                .replace("Ì", "I")
                .replace("Î", "I")
                .replace("Ï", "I")
                .replace("Ó", "O")
                .replace("Ò", "O")
                .replace("Ô", "O")
                .replace("Õ", "O")
                .replace("Ö", "O")
                .replace("Ú", "U")
                .replace("Ù", "U")
                .replace("Ü", "U")
                .replace("Û", "U")
                .replace("º", "o")
                .replace("&", "E")
                .replace("ª", "a");
    }

}
