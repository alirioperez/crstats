package com.seabware.cr.app;

import com.seabware.cr.Constants;
import com.seabware.cr.services.CRFacade;

public class App
{
	public static void main(String[] args)
	{
		String forClan = Constants.THE_MONSTERS_CA_CLAN_TAG;

		if (args[0].equals("collect"))
			CRFacade.collectStats(forClan);
		else
			if (args[0].equals("reports"))
			{
				CRFacade.printHistoricalPlayersByPerformance(forClan, 10, false);
				CRFacade.printHistoricalPlayersByPerformance(forClan, 10, true);
				CRFacade.printHistoricalPlayersByPerformance(forClan, 50, true);
			}
	}
}
