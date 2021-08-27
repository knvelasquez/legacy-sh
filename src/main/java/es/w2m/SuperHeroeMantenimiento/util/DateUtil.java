/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Kevin Velásquez
 *
 */
public class DateUtil {

	private static final String format1 = "yyyy/MM/dd HH:mm:ss";

	public static String getCurrentDate() {
		return DateTimeFormatter.ofPattern(format1).format(LocalDateTime.now());
	}

	public static long getCurrentLongDate() {
		try {
			return new SimpleDateFormat(format1, Locale.ENGLISH).parse(DateUtil.getCurrentDate()).getTime();
		} catch (ParseException exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static long getDifferenceFromNowinSeconds(long time) {
		try {
			//
			long diffInMillies = Math.abs(new SimpleDateFormat(format1, Locale.ENGLISH)
					.parse(DateUtil.getCurrentDate()).getTime()-time);
			//
			return TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		} catch (ParseException exception) {
			exception.printStackTrace();
		}
		return 0;
	}
}
