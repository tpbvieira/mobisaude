//package co.salutary.mobisaude.adapters;
//
//import java.util.List;
//import java.util.Locale;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.ImageSpan;
//import android.text.style.StyleSpan;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.TextView.BufferType;
//import br.com.mais2x.anatelsm.R;
//import br.com.mais2x.anatelsm.controller.db.entidade.Problema;
//import br.com.mais2x.anatelsm.util.LocalPreferences;
//
//public class ProblemAdapter extends PagerAdapter {
//	Context context;
//	List<Problema> problemas;
//
//	JSONArray operadoras, tiposProblemas, tiposAmbientes, tiposSistema;
//
//	public ProblemAdapter(Context mContext, List<Problema> mProblemas) {
//		context = mContext;
//		problemas = mProblemas;
//		try {
//			LocalPreferences pref = new LocalPreferences(context);
//			operadoras = new JSONArray(pref.getPreferenceValueR(LocalPreferences.operadoras));
//			tiposProblemas = new JSONArray(pref.getPreferenceValueR(LocalPreferences.tiposProblema));
//			tiposAmbientes = new JSONArray(pref.getPreferenceValueR(LocalPreferences.tiposAmbiente));
//			tiposSistema = new JSONArray(pref.getPreferenceValueR(LocalPreferences.TIPOS_SISTEMA_OPERACIONAL));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return problemas.size();
//	}
//
//	@Override
//	public boolean isViewFromObject(View arg0, Object arg1) {
//		return arg0 == arg1;
//	}
//	
//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		// TODO Auto-generated method stub
//		//super.destroyItem(container, position, object);
//	}
//
//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		View view = inflater.inflate(R.layout.infowindow_for_problem_item, container, false);
//
//		TextView textView = (TextView)view.findViewById(R.id.textView);
//
//		JSONObject problema = problemas.get(position).getProblema();
//
//		 SpannableString text = null;
//	        try {
//	        	String problemaText = null;
//	        	for (int i = 0; i < tiposProblemas.length(); i++) {
//	        		if(tiposProblemas.getJSONObject(i).getInt("id") == problema.getInt("tipoProblema")) {
//	        			problemaText = tiposProblemas.getJSONObject(i).getString("descricao");
//	        			break;
//	        		}
//				}
//
//	        	String ambienteText = null;
//	        	for (int i = 0; i < tiposAmbientes.length(); i++) {
//	        		if(tiposAmbientes.getJSONObject(i).getInt("id") == problema.getInt("tipoAmbiente")) {
//	        			ambienteText = tiposAmbientes.getJSONObject(i).getString("descricao");
//	        			break;
//	        		}
//				}
//
//	        	String osText = null;
//	        	for (int i = 0; i < tiposSistema.length(); i++) {
//	        		if(tiposSistema.getJSONObject(i).getInt("id") == problema.getInt("tipoSistemaOperacional")) {
//	        			osText = tiposSistema.getJSONObject(i).getString("descricao");
//	        			break;
//	        		}
//				}
//
//	        	String operadoraImage = null;
//	        	for (int i = 0; i < operadoras.length(); i++) {
//	        		if(operadoras.getJSONObject(i).getInt("id") == problema.getInt("operadora")) {
//	        			operadoraImage = operadoras.getJSONObject(i).getString("codigo").toLowerCase();
//	        			break;
//	        		}
//				}
//
//	        	String dataHora = problema.getString("dataHora");
//
//	        	if(true) {
//		        	String textStr = "Evento: " + problemaText + "\n"
//		        			+ "Tipo de Local: " + ambienteText + "\n" + "Data: " + dataHora + "\n"
//		        			+ "#image" + "\n" + "Sistema operacional: " + osText;
//		        	text = new SpannableString(textStr);
//		        	StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
//		        	int start = textStr.indexOf("Evento");
//		        	int end = start + "Evento".length();
//		        	text.setSpan(bss, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//		        	StyleSpan bss1 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
//		        	start = textStr.indexOf("Tipo de Local");
//		        	end = start + "Tipo de Local".length();
//		        	text.setSpan(bss1, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//		        	StyleSpan bss2 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
//		        	start = textStr.indexOf("Data");
//		        	end = start + "Data".length();
//		        	text.setSpan(bss2, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//		        	StyleSpan bss3 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
//		        	start = textStr.indexOf("Sistema operacional");
//		        	end = start + "Sistema operacional".length();
//		        	text.setSpan(bss3, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//
//		        	Drawable operadoraDrawable = getLogo(context, operadoraImage);
//		        	int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//		                    (float) 40.0, context.getResources().getDisplayMetrics());
//
//		        	operadoraDrawable.setBounds(0, 0, value, value);
//		        	ImageSpan operadora = new ImageSpan(operadoraDrawable, ImageSpan.ALIGN_BASELINE);
//		        	start = textStr.indexOf("#image");
//		        	end = start + "#image".length();
//		        	text.setSpan(operadora, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//		        }
//			} catch (Exception e) {
//				e.printStackTrace();
//				// TODO: handle exception
//			}
//	        textView.setText(text, BufferType.SPANNABLE);
//	        container.addView(view);
//	        //((ViewPager) container).addView(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//		return view;
//	}
//
//	public static String getProblemaOperadoraCodigo(Context context, JSONObject problema) {
//		try {
//			LocalPreferences pref = new LocalPreferences(context);
//			JSONArray operadoras = new JSONArray(pref.getPreferenceValueR(LocalPreferences.operadoras));
//			String operadoraImage = null;
//	    	for (int i = 0; i < operadoras.length(); i++) {
//	    		if(operadoras.getJSONObject(i).getInt("id") == problema.getInt("operadora")) {
//	    			operadoraImage = operadoras.getJSONObject(i).getString("codigo").toLowerCase();
//	    			break;
//	    		}
//			}
//	    	return operadoraImage;
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}
//
//	public static int getLogoResourceId(Context context, String nomeFantasia) {
//		if(nomeFantasia != null){
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("claro"))
//				return R.drawable.logo_claro;
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("oi"))
//				return R.drawable.logo_oi;
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("tim"))
//				return R.drawable.logo_tim;
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("vivo"))
//				return R.drawable.logo_vivo;
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("nextel"))
//				return R.drawable.logo_nextel;
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("sercomtel"))
//				return R.drawable.logo_sercomtel;
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("ctbc"))
//				return R.drawable.logo_ctbc;
//			return -1;
//		}
//		else {
//			return -1;
//		}
//	}
//
//	public static Drawable getLogo(Context context, String nomeFantasia) {
//		if(nomeFantasia != null){
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("claro"))
//				return context.getResources().getDrawable(R.drawable.logo_claro);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("oi"))
//				return context.getResources().getDrawable(R.drawable.logo_oi);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("tim"))
//				return context.getResources().getDrawable(R.drawable.logo_tim);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("vivo"))
//				return context.getResources().getDrawable(R.drawable.logo_vivo);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("nextel"))
//				return context.getResources().getDrawable(R.drawable.logo_nextel);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("sercomtel"))
//				return context.getResources().getDrawable(R.drawable.logo_sercomtel);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("ctbc"))
//				return context.getResources().getDrawable(R.drawable.logo_ctbc);
//			return null;
//		}
//		else {
//			return null;
//		}
//	}
//
//	public static Bitmap getLogoBitmap(Context context, String nomeFantasia) {
//		if(nomeFantasia != null){
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("claro"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_claro);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("oi"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_oi);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("tim"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_tim);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("vivo"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_vivo);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("nextel"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_nextel);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("sercomtel"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_sercomtel);
//			if(nomeFantasia.toLowerCase(Locale.getDefault()).equals("ctbc"))
//				return BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_ctbc);
//			return null;
//		}
//		else {
//			return null;
//		}
//	}
//
//}