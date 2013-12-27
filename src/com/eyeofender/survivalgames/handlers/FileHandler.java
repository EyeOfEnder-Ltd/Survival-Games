package com.eyeofender.survivalgames.handlers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.eyeofender.survivalgames.SurvivalGames;

public class FileHandler {
	
	private SurvivalGames plugin;

    public FileHandler(SurvivalGames plugin) {
        this.plugin = plugin;
    }

    public void init() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
    
    public void load(){
        getArena().options().copyDefaults(true);
        plugin.getLogger().info(this.getArenaName());
        saveArena();
    }

    private FileConfiguration arena = null;
    private File arenaFile = null;

    public void reloadArena() {
        arenaFile = new File(Bukkit.getWorldContainer(),"worlds" + File.separator + "arena.yml");
        if (!arenaFile.exists()) {
            arenaFile.mkdirs();
            try {
                arenaFile.createNewFile();
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + arenaFile, ex);
            }
        }
        arena = YamlConfiguration.loadConfiguration(arenaFile);
    }

    public FileConfiguration getArena() {
        if (arena == null) {
            this.reloadArena();
        }
        return arena;
    }

    public void saveArena() {
        if (getArenaName() == null) return;
        try {
            arenaFile = new File(plugin.getDataFolder().getParentFile(), "WorldRefresh" + File.separator + "worlds" + File.separator + getArenaName() + File.separator + "arena.yml");
            getArena().save(arenaFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + arenaFile, ex);
        }
    }

    public String getArenaName() {
        return getArena().getString("name");
    }
}
