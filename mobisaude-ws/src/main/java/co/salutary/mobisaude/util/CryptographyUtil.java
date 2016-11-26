package co.salutary.mobisaude.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que implementa servicos referentes a criptografia
 *
 */
public final class CryptographyUtil {
	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(CryptographyUtil.class);
	/**
	 * Algoritmo MD5
	 */
	private static final String ALGORITHM_MD5 = "MD5";
	/**
	 * Algoritmo SHA
	 */
	private static final String ALGORITHM_SHA = "SHA";
	
	/**
	 * Construtor
	 */
	private CryptographyUtil() {
	}
	
	/**
	 * Gerar codificacao MD5
	 * @param message
	 * @return
	 */
	public static byte[] encodeToMD5(String message) {
		return encode(message, ALGORITHM_MD5);
	}

	/**
	 * Gerar codificacao SHA
	 * @param message
	 * @return
	 */
	public static byte[] encodeToSHA(String message) {
		return encode(message, ALGORITHM_SHA);
	}

	/**
	 * Codificar mensagen usando algoritmo
	 * @param message
	 * @param algorithm
	 * @return
	 */
	public static byte[] encode(String message, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(message.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}
	}
	
	/**
	 * Converer bytes em string hexa
	 * @param bytes
	 * @return
	 */
	public static String convertStringHexa(byte[] bytes) {
	      StringBuilder s = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	          int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
	          int parteBaixa = bytes[i] & 0xf;
	          if (parteAlta == 0) s.append('0');
	          s.append(Integer.toHexString(parteAlta | parteBaixa));
	      }
	      return s.toString();
	  }
	
	/**
	 * Converter String Base64 em array de bytes
	 * @param strBase64
	 * @return
	 */
	public static byte[] convertoToByteArray(String strBase64) {
        byte[] b = Base64.decodeBase64(strBase64);
        return b;
	}
       
    /**  
     * Criptografar/descriptografar uma String.  
     * @param dir true para criptografar, false para descriptografar  
     * @param password a senha deve ter no minimo 8 caracteres  
     * @param text o texto a criptografar/descriptografar  
     * @return o texto descriptografado/criptografado  
     */  
    private static byte[] crypto(boolean dir, byte[] password, byte[] text) {   
        try {
        	DESKeySpec keySpec = new DESKeySpec(password);   
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");   
            Cipher cipher = Cipher.getInstance("DES");   
            cipher.init(dir ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,    
                        factory.generateSecret(keySpec));   
            return cipher.doFinal(text);           	
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return null;
    }   
       
    /**
     * Criptografar
     * @param text
     * @return
     */
    public static byte[] encrypt(String text) {   
        return crypto(true, "G4t3w7y0".getBytes(), text.getBytes());   
    }   
       
    /**
     * Descriptografar
     * @param text
     * @return
     */
    public static String decrypt(byte[] text) {   
        return new String(crypto(false, "G4t3w7y0".getBytes(), text));   
    } 
    
    /**
     * Clonar
     */
    public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
    
    /**
     * Obter fatores primos
     * @param numbers
     * @return
     */
    public static List<Integer> primeFactors(int numbers) {
        int n = numbers;
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n / i; i++) {
          while (n % i == 0) {
            factors.add(i);
            n /= i;
          }
        }
        if (n > 1) {
          factors.add(n);
        }
        return factors;
      }
    
	public static String convertToStringBase64(byte[] bytes) {
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(bytes));
	}
	
}