package co.salutary.mobisaude.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Utils {
	private static final SimpleDateFormat sdfMY = new SimpleDateFormat("MM/yyyy");
	private static final SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final boolean isAnoMesCorrente(String anoMes) {
		String anoMesAtual = sdfMY.format(new Date());		

		return anoMesAtual.equals(anoMes);
	}
	
	public static String dateTo01DDYY(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		Date newdate = calendar.getTime();
		return sdfDMY.format(newdate);
	}
}
