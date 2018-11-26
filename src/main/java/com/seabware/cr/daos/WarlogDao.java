package com.seabware.cr.daos;

import com.seabware.cr.models.WarlogDto;
import com.seabware.cr.models.WarlogParticipantDto;
import com.seabware.cr.models.WarlogReportDto;
import com.seabware.cr.util.Tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WarlogDao extends AbstractBaseDao
{
	public static final String SELECT_PLAYERS_BY_PERFORMANCE = "SELECT " +
			"warlog_details.name name, " +
			"sum(warlog_details.battlesPlayed) battlesPlayed, " +
			"sum(warlog_details.wins) wins, " +
			"(sum(warlog_details.wins) / sum(warlog_details.battlesPlayed)) winsRatio, " +
			"sum(warlog_details.collectionDayBattlesPlayed) collectionDayBattlesPlayed, " +
			"sum(warlog_details.cardsEarned) cardsEarned, " +
			"avg(warlog_details.cardsEarned) cardsEarnedAvg " +
			"FROM " +
			"warlog inner join warlog_details on (warlog_details.warlogid = warlog.warlogid) " +
			"WHERE " +
			"warlog_details.clantag = ? AND " +
			"warlog_details.name NOT IN (select name from blocked_players) " +
			"GROUP BY " +
			"warlog_details.name " +
			"ORDER BY " +
			"winsRatio %s " +
			"LIMIT ?";

	public static final String SELECT_FROM_WARLOG_WHERE_WARLOGID = "SELECT warlogid FROM warlog WHERE warlog.clantag = ? AND warlogid = ? ";
	public static final String INSERT_INTO_WARLOG = "INSERT INTO warlog (clantag, warlogid, createdDate, seasonNumber) VALUES (?, ?, ?, ?)";
	public static final String UPDATE_WARLOG_WHERE_WARLOGID = "UPDATE warlog SET seasonNumber = ? WHERE warlog.clantag = ? AND warlogid = ?";
	public static final String DELETE_FROM_WARLOG_DETAILS_WHERE_WARLOGID = "DELETE FROM warlog_details WHERE warlog_details.clantag = ? AND warlogid = ?";
	public static final String INSERT_INTO_WARLOG_DETAILS = "INSERT INTO warlog_details (clantag, warlogid, battlesPlayed, cardsEarned, collectionDayBattlesPlayed, wins, name, tag) values (?, ?, ?, ?, ?, ?, ?, ?)";

	private String clan;

	// -----------------------------------------------------------------------------------------------------------------
	public WarlogDao(String forClan)
	{
		super();
		this.clan = forClan;
	}

	// -----------------------------------------------------------------------------------------------------------------
	public void upsertWar(String forClan, WarlogDto warlogEntry)
	{
		try
		{
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			preparedStatement = getConnection().prepareStatement(SELECT_FROM_WARLOG_WHERE_WARLOGID);
			preparedStatement.setString(1, this.clan); // clan.
			preparedStatement.setLong(2, warlogEntry.getCreatedDate());
			resultSet = preparedStatement.executeQuery();
			resultSet.last();
			boolean isNew = resultSet.getRow() == 0;

			if (isNew)
			{
				System.out.println("Inserting new War: " + Tools.milliToStringDate(warlogEntry.getCreatedDate()));

				preparedStatement = getConnection().prepareStatement(INSERT_INTO_WARLOG);
				preparedStatement.setString(1, this.clan); // clan.
				preparedStatement.setLong(2, warlogEntry.getCreatedDate());
				preparedStatement.setString(3, Tools.milliToStringDate(warlogEntry.getCreatedDate()));
				preparedStatement.setLong(4, warlogEntry.getSeasonNumber());
				preparedStatement.executeUpdate();
			}
			else
			{
				System.out.println("Updating war: " + Tools.milliToStringDate(warlogEntry.getCreatedDate()));

				preparedStatement = getConnection().prepareStatement(UPDATE_WARLOG_WHERE_WARLOGID);
				preparedStatement.setLong(1, warlogEntry.getSeasonNumber());
				preparedStatement.setString(2, this.clan); // clan.
				preparedStatement.setLong(3, warlogEntry.getCreatedDate());
				preparedStatement.executeUpdate();
			}

			preparedStatement = getConnection().prepareStatement(DELETE_FROM_WARLOG_DETAILS_WHERE_WARLOGID);
			preparedStatement.setString(1, this.clan); // clan.
			preparedStatement.setLong(2, warlogEntry.getCreatedDate());
			preparedStatement.executeUpdate();

			for (WarlogParticipantDto participant : warlogEntry.getParticipants())
			{
				preparedStatement = getConnection().prepareStatement(INSERT_INTO_WARLOG_DETAILS);

				preparedStatement.setString(1, this.clan); // clan.
				preparedStatement.setLong(2, warlogEntry.getCreatedDate());
				preparedStatement.setLong(3, participant.getBattlesPlayed());
				preparedStatement.setLong(4, participant.getCardsEarned());
				preparedStatement.setLong(5, participant.getCollectionDayBattlesPlayed());
				preparedStatement.setLong(6, participant.getWins());
				preparedStatement.setString(7, participant.getName());
				preparedStatement.setString(8, participant.getTag());

				preparedStatement.executeUpdate();
			}

			resultSet.close();
			preparedStatement.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	public List<WarlogReportDto> listPlayersByPerformance(String forClan, int limit, boolean bestPlayers)
	{
		try
		{
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			String sql = String.format(SELECT_PLAYERS_BY_PERFORMANCE, (bestPlayers ? "DESC" : "ASC"));
			preparedStatement = getConnection().prepareStatement(sql);

			preparedStatement.setString(1, this.clan); // clan.
			preparedStatement.setLong(2, limit); // limit n clause.


			resultSet = preparedStatement.executeQuery();

			List<WarlogReportDto> result = new ArrayList<>();

			while (resultSet.next())
			{
				WarlogReportDto dto = new WarlogReportDto();

				dto.setPlayer(resultSet.getString("name"));
				dto.setBattlesPlayed(resultSet.getLong("battlesPlayed"));
				dto.setWins(resultSet.getLong("wins"));
				dto.setWinsRatio(resultSet.getDouble("winsRatio"));
				dto.setCollectionDayBattlesPlayed(resultSet.getLong("collectionDayBattlesPlayed"));
				dto.setCardsEarned(resultSet.getLong("cardsEarned"));
				dto.setCardsEarnedAverage(resultSet.getDouble("cardsEarnedAvg"));

				result.add(dto);
			}

			resultSet.close();
			preparedStatement.close();

			return result;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}

	// -----------------------------------------------------------------------------------------------------------------
	public long getHistoricalWarsCount(String forClan)
	{
		try
		{
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			preparedStatement = getConnection().prepareStatement("SELECT COUNT(warlogid) AS warsCount FROM warlog WHERE clantag = ?");

			preparedStatement.setString(1, this.clan); // clan.

			resultSet = preparedStatement.executeQuery();

			long warsCount = 0;

			while (resultSet.next())
			{
				warsCount = resultSet.getLong("warsCount");
			}

			resultSet.close();
			preparedStatement.close();

			return warsCount;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return -1;
	}
}
