package com.seabware.cr.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seabware.cr.Constants;
import com.seabware.cr.daos.ClanDao;
import com.seabware.cr.daos.WarlogDao;
import com.seabware.cr.models.ClanDto;
import com.seabware.cr.models.WarlogDto;
import com.seabware.cr.models.WarlogReportDto;
import com.seabware.cr.util.Tools;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.PrintStream;
import java.util.List;

public class CRFacade
{
	/**
	 * Fetches stats from the public CR's API and upsert them into the database.
	 */
	// -----------------------------------------------------------------------------------------------------------------
	public static void collectStats(String forClan)
	{
		WarlogDto[] warlog;

		OkHttpClient httpclient = new OkHttpClient();

		String url = Tools.getClanWarlogUrl(forClan);
		System.out.println("Trying to connect to: " + url);

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("auth", Constants.CR_API_KEY)
				.build();

		try (Response response = httpclient.newCall(request).execute())
		{
			if (response.code() != 200)
				throw new Exception("API is offline: " + response.body().string());

			WarlogDao warlogDao = new WarlogDao(forClan);

			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			warlog = mapper.readValue(response.body().bytes(), WarlogDto[].class);

			// Insert or update wars data.

			for (WarlogDto warlogEntry : warlog)
			{
				warlogDao.upsertWar(warlogEntry);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes/Updates the clan's roster.
	 */
	// -----------------------------------------------------------------------------------------------------------------
	public static void bulkUpdateClan(String forClan)
	{
		ClanDto clanDto;

		OkHttpClient httpclient = new OkHttpClient();

		String url = Tools.getClanUrl(forClan);
		System.out.println("Trying to connect to: " + url);

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("auth", Constants.CR_API_KEY)
				.build();

		try (Response response = httpclient.newCall(request).execute())
		{
			if (response.code() != 200)
				throw new Exception("API is offline: " + response.body().string());

			ClanDao clanDao = new ClanDao(forClan);

			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			clanDto = mapper.readValue(response.body().bytes(), ClanDto.class);

			// Updates the clan's roster.

			clanDao.bulkUpdateClan(clanDto);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Print on the standard output the worst 10 players.
	 */
	// -----------------------------------------------------------------------------------------------------------------
	public static void printHistoricalPlayersByPerformance(String forClan, int numberOfRecords, boolean best)
	{
		PrintStream out = System.out;

		WarlogDao warlogDao = new WarlogDao(forClan);

		long warsCount = warlogDao.getHistoricalWarsCount(forClan);

		if (warsCount <= 0)
			out.println("No data to show!");
		else
		{
			String title = (best ? "BEST" : "WORST") + " PLAYERS";

			Tools.printHeader(out, forClan, title, warsCount);

			List<WarlogReportDto> list = warlogDao.listPlayersByPerformance(forClan, numberOfRecords, best);

			int rowNb = 1;
			for (WarlogReportDto item : list)
			{
				Tools.printRecord(out,
						String.format("%s. " + item.getPlayer(), rowNb),
						item.getBattlesPlayed(),
						item.getWins(),
						Tools.formatDouble(item.getWinsRatio(), 4),
						item.getCollectionDayBattlesPlayed(),
						item.getCardsEarned(),
						Tools.formatDouble(item.getCardsEarnedAverage(), 2));

				rowNb++;
			}

			out.println();
		}
	}
}
