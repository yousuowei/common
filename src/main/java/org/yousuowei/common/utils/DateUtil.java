package org.yousuowei.common.utils;

import java.text.SimpleDateFormat;

public class DateUtil {

	public static enum DateFormatStyle {
		DATE, TIME_DETAIL
	}

	public static String longToDate(Long timeLong, DateFormatStyle formatStyle) {
		String style;
		switch (formatStyle) {
		case DATE:
			style = "yyyy-MM-dd";
			break;
		case TIME_DETAIL:
			style = "yyyy-MM-dd HH:mm:ss";
			break;
		default:
			style = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(style);
		return format.format(timeLong);
	}

}
