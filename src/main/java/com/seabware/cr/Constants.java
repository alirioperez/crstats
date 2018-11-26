package com.seabware.cr;

public interface Constants
{
	// Credentials

	public static final String CR_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjAzMywiaWRlbiI6IjUxNDgzNDY1Mzc0ODc4OTI2OSIsIm1kIjp7fSwidHMiOjE1NDI4MTcwMzM3NTl9.F9ACKdYPe-q7_UpfM-6ZijMEj-vsAXe8BI8x62Gk5tk";

	// Clans

	public static final String THE_MONSTERS_CA_CLAN_TAG = "8QVQ9LG";
	public static final String THE_MONSTERS_CA_II_CLAN_TAG = "XXXXXX";

	// URLs

	public static final String URL_CLAN = "https://api.royaleapi.com/clan/";
	public static final String URI_WARLOG = "/warlog";

	// Reports

	public static final String[] HEADER_FORMATS_REPORT_1 = {"%-21s", "%13s", "%6s", "%10s", "%24s", "%12s", "%15s"};
	public static final String[] HEADER_COLUMNS_REPORT_1 = {"Player", "BattlesPlayed", "Wins", "WinsRatio", "CollectionBattlesPlayed", "CardsEarned", "CardsEarnedAVG"};

}
