package com.seabware.cr.models;

public class WarlogParticipantDto
{
	private String name;
	private String tag;
	private long wins;
	private long battlesPlayed;
	private long cardsEarned;
	private long collectionDayBattlesPlayed;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public long getWins()
	{
		return wins;
	}

	public void setWins(long wins)
	{
		this.wins = wins;
	}

	public long getBattlesPlayed()
	{
		return battlesPlayed;
	}

	public void setBattlesPlayed(long battlesPlayed)
	{
		this.battlesPlayed = battlesPlayed;
	}

	public long getCardsEarned()
	{
		return cardsEarned;
	}

	public void setCardsEarned(long cardsEarned)
	{
		this.cardsEarned = cardsEarned;
	}

	public long getCollectionDayBattlesPlayed()
	{
		return collectionDayBattlesPlayed;
	}

	public void setCollectionDayBattlesPlayed(long collectionDayBattlesPlayed)
	{
		this.collectionDayBattlesPlayed = collectionDayBattlesPlayed;
	}
}
