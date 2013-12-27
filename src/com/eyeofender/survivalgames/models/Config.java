package com.eyeofender.survivalgames.models;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    StatsSystem plugin;

    private FileConfiguration config;
    private File filePath = new File("plugins/SurvivalGames/stats.yml");

    private String username;
    private String password;
    private String host;
    private String database;

    public Config(StatsSystem instance) {
        plugin = instance;
        loadConfig();
    }

    public void loadConfig() {
        config = YamlConfiguration.loadConfiguration(filePath);
        if (!config.contains("username")) {
            InputStream defConfigStream = plugin.getPlugin().getResource("stats.yml");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                config = defConfig;
            }
            try {
                config.save(filePath);
            } catch (IOException e) {
                System.out.println("[AGStats] Something went wrong while trying to copy default config.yml over to plugins directory");
                e.printStackTrace();
            }
        }
        loadNodes();
    }

    public void loadNodes() {
        username = config.getString("username");
        password = config.getString("password");
        host = config.getString("host");
        database = config.getString("database");
    }

    public StatsSystem getPlugin() {
        return plugin;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFilePath() {
        return filePath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public void setPlugin(StatsSystem plugin) {
        this.plugin = plugin;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
