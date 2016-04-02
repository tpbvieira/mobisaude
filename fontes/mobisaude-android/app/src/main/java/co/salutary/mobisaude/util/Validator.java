package co.salutary.mobisaude.util;

import android.util.Log;

import java.util.StringTokenizer;

public class Validator {

    private static final String TAG = Validator.class.getSimpleName();

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
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static boolean isValidName(String name) {
        boolean result = false;

        try{
            result = (name.length() > 1);
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static boolean isValidPassword(String password) {
        return password.length() > 4;
    }

}
