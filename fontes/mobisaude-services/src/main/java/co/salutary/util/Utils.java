package co.salutary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe utilitaria 
 */
public class Utils {
	private static final SimpleDateFormat sdfAnoMes = new SimpleDateFormat("MM/yyyy");
	
	public static final boolean isAnoMesCorrente(String anoMes) {
		String anoMesAtual = sdfAnoMes.format(new Date());		

		return anoMesAtual.equals(anoMes);
	}
}
