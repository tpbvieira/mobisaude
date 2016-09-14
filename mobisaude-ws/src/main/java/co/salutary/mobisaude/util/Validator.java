package co.salutary.mobisaude.util;

import java.util.StringTokenizer;

public class Validator {

	public static boolean isValidEmail(String email) {
		boolean result = false;

		try{
			if(email.contains("@") && email.contains(".")){
				StringTokenizer tokens = new StringTokenizer(email,"@");
				tokens.nextToken();
				String domainValue = tokens.nextToken();
				tokens = new StringTokenizer(domainValue,".");
				if(tokens.countTokens() > 1) {
					result = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return result;
	}

	public static boolean isValidName(String name) {
		boolean result = false;

		try{
			result = (name.length() > 1);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return result;
	}

	public static boolean isValidPassword(String password) {
		boolean result = false;

		try{
			result = (password.length() > 4);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return result;
	}

}