package com.seabware.cr.models;

import java.util.List;

public class ClanDto
{
	private String tag;
	private List<ClanMemberDto> members;

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public List<ClanMemberDto> getMembers()
	{
		return members;
	}

	public void setMembers(List<ClanMemberDto> members)
	{
		this.members = members;
	}
}
