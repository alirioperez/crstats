package com.seabware.cr.models;

public class WarlogReportDto
{
	private String player;
	private long battlesPlayed;
	private long wins;
	private double winsRatio;
	private long collectionDayBattlesPlayed;
	private long cardsEarned;
	private double cardsEarnedAverage;

	public String getPlayer()
	{
		return player;
	}

	public void setPlayer(String player)
	{
		this.player = player;
	}

	public long getBattlesPlayed()
	{
		return battlesPlayed;
	}

	public void setBattlesPlayed(long battlesPlayed)
	{
		this.battlesPlayed = battlesPlayed;
	}

	public long getWins()
	{
		return wins;
	}

	public void setWins(long wins)
	{
		this.wins = wins;
	}

	public double getWinsRatio()
	{
		return winsRatio;
	}

	public void setWinsRatio(double winsRatio)
	{
		this.winsRatio = winsRatio;
	}

	public long getCollectionDayBattlesPlayed()
	{
		return collectionDayBattlesPlayed;
	}

	public void setCollectionDayBattlesPlayed(long collectionDayBattlesPlayed)
	{
		this.collectionDayBattlesPlayed = collectionDayBattlesPlayed;
	}

	public long getCardsEarned()
	{
		return cardsEarned;
	}

	public void setCardsEarned(long cardsEarned)
	{
		this.cardsEarned = cardsEarned;
	}

	public double getCardsEarnedAverage()
	{
		return cardsEarnedAverage;
	}

	public void setCardsEarnedAverage(double cardsEarnedAverage)
	{
		this.cardsEarnedAverage = cardsEarnedAverage;
	}
}
