package com.modelsystem.util;

public class UrlUtil {

	public static String getExtension(String uri) {
		int start = uri.indexOf(".");
		start = start == -1 ? 0 : start;
		int end = uri.indexOf("?");
		end = end == -1 ? uri.length() : end;
		return (String) uri.subSequence(start, end);
	}
}
