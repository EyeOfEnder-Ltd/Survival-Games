package com.eyeofender.survivalgames.handlers;

import com.eyeofender.survivalgames.SurvivalGames;
import com.eyeofender.survivalgames.commands.CmdBounty;
import com.eyeofender.survivalgames.commands.CmdBuyKit;
import com.eyeofender.survivalgames.commands.CmdCreate;
import com.eyeofender.survivalgames.commands.CmdGoTo;
import com.eyeofender.survivalgames.commands.CmdKit;
import com.eyeofender.survivalgames.commands.CmdKitInfo;
import com.eyeofender.survivalgames.commands.CmdKits;
import com.eyeofender.survivalgames.commands.CmdLoadMap;
import com.eyeofender.survivalgames.commands.CmdPlayers;
import com.eyeofender.survivalgames.commands.CmdPlugins;
import com.eyeofender.survivalgames.commands.CmdSponsor;
import com.eyeofender.survivalgames.commands.CmdStart;
import com.eyeofender.survivalgames.commands.CmdStartNow;
import com.eyeofender.survivalgames.commands.CmdStats;
import com.eyeofender.survivalgames.commands.CmdTop;
import com.eyeofender.survivalgames.commands.CmdUpdate;
import com.eyeofender.survivalgames.commands.CmdVote;

public class CommandHandler {

	SurvivalGames plugin;
	
	public CommandHandler ( SurvivalGames plugin ){
		this.plugin = plugin;
	}
	
	public void init(){
        plugin.getCommand("create").setExecutor(new CmdCreate(plugin));
        plugin.getCommand("update").setExecutor(new CmdUpdate(plugin));
        plugin.getCommand("vote").setExecutor(new CmdVote(plugin));
        plugin.getCommand("startNow").setExecutor(new CmdStartNow(plugin));
        plugin.getCommand("kitInfo").setExecutor(new CmdKitInfo(plugin));
        plugin.getCommand("kit").setExecutor(new CmdKit(plugin));
        plugin.getCommand("kits").setExecutor(new CmdKits(plugin));
        plugin.getCommand("buyKit").setExecutor(new CmdBuyKit(plugin));
        plugin.getCommand("stats").setExecutor(new CmdStats(plugin));
        plugin.getCommand("goto").setExecutor(new CmdGoTo(plugin));
        plugin.getCommand("loadMap").setExecutor(new CmdLoadMap(plugin));
        
        plugin.getCommand("top").setExecutor(new CmdTop(plugin));
        plugin.getCommand("start").setExecutor(new CmdStart(plugin));
        //plugin.getCommand("plugins").setExecutor(new CmdPlugins(plugin));
        plugin.getCommand("players").setExecutor(new CmdPlayers(plugin));
        plugin.getCommand("list").setExecutor(new CmdPlayers(plugin));
        plugin.getCommand("bounty").setExecutor(new CmdBounty(plugin));
        plugin.getCommand("sponsor").setExecutor(new CmdSponsor(plugin));
	}
}
