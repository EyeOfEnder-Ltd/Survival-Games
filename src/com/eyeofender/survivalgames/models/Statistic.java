package com.eyeofender.survivalgames.models;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Entity
@Table(name="Statistic")
public class Statistic 
{
	@Id
	protected String name;
	
	@ManyToOne(targetEntity=Arena.class)
	protected Arena arena;
	
	@Column
	protected int kills = 0;
	
	@Column
	protected int deaths = 0;
	
	@Column
	protected int gamesPlayed = 0;
	
	@Column
	protected int timePlayed = 0;
	
	@Column
	protected int bestKillStreak = 0;
	
	@Column
	protected int wins = 0;
	
	@Column
	protected String savedKit;
	
	@Column
	protected int mutationPassesUsed = 0;
	
	@Column
	protected Calendar lastLogin;
	
	public Statistic()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getTimePlayed() {
		return timePlayed;
	}

	public void setTimePlayed(int timePlayed) {
		this.timePlayed = timePlayed;
	}

	public int getBestKillStreak() {
		return bestKillStreak;
	}

	public void setBestKillStreak(int bestKillStreak) {
		this.bestKillStreak = bestKillStreak;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public String getSavedKit() {
		return savedKit;
	}

	public void setSavedKit(String savedKit) {
		this.savedKit = savedKit;
	}

	public int getMutationPassesUsed() {
		return mutationPassesUsed;
	}

	public void setMutationPassesUsed(int mutationPassesUsed) {
		this.mutationPassesUsed = mutationPassesUsed;
	}

	public Calendar getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}
}
