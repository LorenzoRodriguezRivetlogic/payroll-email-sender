package com.rivetlogic.emailsender.util;

import com.rivetlogic.emailsender.model.FileColumn;

import java.util.List;

public class Utils {
	public static String formatColumnName(String columnName) {
		columnName = columnName.toLowerCase();
		columnName = columnName.replace(" ", "_");
		
		return "${" + columnName + "}";
	}
	
	public static String generateHtmlTable(List<FileColumn> columns) {
		String tableStart = "<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px;\">"+
								"<tbody>"+
									"<tr>";
		String columnsName = "";
		for (FileColumn fileColumn : columns) {
			columnsName += "<td>"+ fileColumn.getName() +"</td>";
		}
		
		String rowChange = "</tr><tr>";
		
		String columnsData = "";
		for (FileColumn fileColumn : columns) {
			columnsData += "<td>"+ formatColumnName(fileColumn.getName()) +"</td>";
		}
		
		String tableEnd = "</tr></tbody></table>";
		
		return tableStart + columnsName + rowChange + columnsData + tableEnd;
	}
}
