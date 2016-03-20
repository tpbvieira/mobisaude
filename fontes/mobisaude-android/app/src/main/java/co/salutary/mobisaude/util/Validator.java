package co.salutary.mobisaude.util;

import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by thiago on 09/02/16.
 */
public class Validator {

    private static final String TAG = Validator.class.getSimpleName();

    public static boolean isEmailValid(String email) {
        boolean result = false;

        try{
            if(email.contains("@") && email.contains(".")){
                StringTokenizer tokens = new StringTokenizer(email,"@");
                tokens.nextToken();
                String domainValue = tokens.nextToken();
                tokens = new StringTokenizer(domainValue,".");
                int size = tokens.countTokens();
                if(tokens.countTokens() > 1) {
                    result = true;
                }
            }
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}
