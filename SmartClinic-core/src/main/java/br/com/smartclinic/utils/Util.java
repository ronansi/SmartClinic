package br.com.smartclinic.utils;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Date;

public class Util implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String removeAcentos(String str){
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static Date addMinutesToDate(int minutes, Date beforeTime){
	    final long ONE_MINUTE_IN_MILLIS = 60000;

	    long curTimeInMs = beforeTime.getTime();
	    Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
	    return afterAddingMins;
	}

}
