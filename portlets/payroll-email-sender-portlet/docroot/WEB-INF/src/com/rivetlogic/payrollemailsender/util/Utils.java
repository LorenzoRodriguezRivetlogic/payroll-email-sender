package com.rivetlogic.payrollemailsender.util;

public class Utils {
	public static String formatColumnName(String columnName) {
		columnName = columnName.toLowerCase();
		columnName = columnName.replace(" ", "_");
		
		return "$" + columnName;
	}
}
