package com.seabware.cr.models;

import java.util.List;

public class WarlogDto
{
	private long createdDate;
	private long seasonNumber;
	private List<WarlogParticipantDto> participants;

	public List<WarlogParticipantDto> getParticipants()
	{
		return participants;
	}

	public void setParticipants(List<WarlogParticipantDto> participants)
	{
		this.participants = participants;
	}

	public long getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(long createdDate)
	{
		this.createdDate = createdDate;
	}

	public long getSeasonNumber()
	{
		return seasonNumber;
	}

	public void setSeasonNumber(long seasonNumber)
	{
		this.seasonNumber = seasonNumber;
	}
}
