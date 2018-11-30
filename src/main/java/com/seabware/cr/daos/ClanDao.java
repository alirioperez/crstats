package com.seabware.cr.daos;

import com.seabware.cr.models.ClanDto;
import com.seabware.cr.models.ClanMemberDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClanDao extends AbstractBaseDao
{
	public static final String DELETE_ROSTER_WHERE_CLANTAG = "DELETE FROM roster WHERE clantag = ?";
	public static final String INSERT_INTO_ROSTER = "INSERT INTO roster (clantag, player) VALUES (?, ?)";

	public static final String SELECT_FROM_WARLOG_WHERE_WARLOGID = "SELECT warlogid FROM warlog WHERE warlog.clantag = ? AND warlogid = ? ";
	public static final String UPDATE_WARLOG_WHERE_WARLOGID = "UPDATE warlog SET seasonNumber = ? WHERE warlog.clantag = ? AND warlogid = ?";
	public static final String INSERT_INTO_WARLOG_DETAILS = "INSERT INTO warlog_details (clantag, warlogid, battlesPlayed, cardsEarned, collectionDayBattlesPlayed, wins, name, tag) values (?, ?, ?, ?, ?, ?, ?, ?)";

	private String clan;

	// -----------------------------------------------------------------------------------------------------------------
	public ClanDao(String forClan)
	{
		super();
		this.clan = forClan;
	}

	// -----------------------------------------------------------------------------------------------------------------
	public void bulkUpdateClan(ClanDto clanDto)
	{
		try
		{
			PreparedStatement preparedStatement = null;

			preparedStatement = getConnection().prepareStatement(DELETE_ROSTER_WHERE_CLANTAG);
			preparedStatement.setString(1, this.clan); // clan.
			preparedStatement.executeUpdate();

			for(ClanMemberDto clanMemberDto : clanDto.getMembers())
			{
				System.out.println("Inserting member: " + clanMemberDto.getName());
				preparedStatement = getConnection().prepareStatement(INSERT_INTO_ROSTER);
				preparedStatement.setString(1, this.clan); // clan.
				preparedStatement.setString(2, clanMemberDto.getName()); //player.
				preparedStatement.executeUpdate();
			}

			preparedStatement.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
