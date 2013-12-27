package com.eyeofender.survivalgames.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;

@Entity
@Table(name="Arena")
public class Arena 
{
	@Id
	protected int id;
	
	@Column
	protected String name;
	
	@Column
	protected String description;
	
	@Column
	protected int amountMaxPlayers = 0;
	
	@OneToMany(mappedBy="arena", targetEntity=Statistic.class)
	protected List<Statistic> statistics;
	
	public Arena() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmountMaxPlayers() {
		return amountMaxPlayers;
	}

	public void setAmountMaxPlayers(int amountMaxPlayers) {
		this.amountMaxPlayers = amountMaxPlayers;
	}

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}
}
