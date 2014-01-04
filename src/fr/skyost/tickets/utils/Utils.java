package fr.skyost.tickets.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static String date() {
		String dat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
		return dat;
	}

}
