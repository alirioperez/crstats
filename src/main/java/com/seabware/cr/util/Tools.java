package com.seabware.cr.util;

import com.seabware.cr.Constants;

import java.io.PrintStream;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Tools
{
	// -----------------------------------------------------------------------------------------------------------------
	public static String getClanUrl(String clan)
	{
		return Constants.URL_CLAN + clan;
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static String getClanWarlogUrl(String clan)
	{
		return (getClanUrl(clan) + Constants.URI_WARLOG);
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static String milliToStringDate(long milli)
	{
		Instant instance = java.time.Instant.ofEpochMilli(milli * 1000);
		ZonedDateTime zonedDateTime = java.time.ZonedDateTime.ofInstant(instance, java.time.ZoneId.of("America/Toronto"));

		// Format the date

		DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("u-M-d");
		String string = zonedDateTime.format(formatter);
		return (string);
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static void printHeader(PrintStream out, String forClan, String title, long warsCount)
	{
		out.println();
		out.println(String.format("Stats for clan %s from last %d wars", forClan, warsCount));
		out.println(title);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Constants.HEADER_COLUMNS_REPORT_1.length; ++i)
		{
			sb.append(String.format(Constants.HEADER_FORMATS_REPORT_1[i], Constants.HEADER_COLUMNS_REPORT_1[i]));
		}

		printLine(out, sb.length());
		out.println(sb.toString()); // printing the header.
		printLine(out, sb.length());
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static void printRecord(PrintStream out, Object... values)
	{
		assert values.length == Constants.HEADER_COLUMNS_REPORT_1.length;

		for (int i = 0; i < values.length; ++i)
		{
			out.format(Constants.HEADER_FORMATS_REPORT_1[i], values[i]);
		}

		out.println();
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static void printLine(PrintStream out, int length)
	{
		out.println(String.join("", Collections.nCopies(length, "-")));
	}

	// -----------------------------------------------------------------------------------------------------------------
	public static String formatDouble(double value, int numberOfDecimals)
	{
		return String.format("%." + numberOfDecimals + "f", value);
	}
}
