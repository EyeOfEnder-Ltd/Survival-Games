package com.eyeofender.survivalgames.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StatsSaveThread extends Thread {
    protected Connection con = null;
    protected ConcurrentLinkedQueue<Stats> quitQueue = new ConcurrentLinkedQueue<Stats>();
    private StatsSystem plugin;
    private boolean running;

    protected StatsSaveThread(StatsSystem stats) {
        this.plugin = stats;
    }

    public void SQLdisconnect() {
        try {
            System.out.println("[StatsSaveThread] Disconnecting from MySQL database...");
            this.con.close();
        } catch (SQLException ex) {
            System.err.println("[StatsSaveThread] Error while closing the connection...");
        } catch (NullPointerException ex) {
            System.err.println("[StatsSaveThread] Error while closing the connection...");
        }
    }

    public void SQLconnect() {
        try {
            System.out.println("[StatsSaveThread] Connecting to MySQL database...");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String conn = "jdbc:mysql://" + this.plugin.SQL_HOST + "/" + this.plugin.SQL_DATA;
            this.con = DriverManager.getConnection(conn, this.plugin.SQL_USER, this.plugin.SQL_PASS);
        } catch (ClassNotFoundException ex) {
            System.err.println("[StatsSaveThread] No MySQL driver found!");
        } catch (SQLException ex) {
            System.err.println("[StatsSaveThread] Error while fetching MySQL connection!");
        } catch (Exception ex) {
            System.err.println("[StatsSaveThread] Unknown error while fetchting MySQL connection.");
        }
    }

    public void terminate() {
        running = false;
    }

    public void run() {
        System.out.println("Stats Save Thread started");
        SQLconnect();
        running = true;
        while (running) {
            if (this.quitQueue.peek() != null) {
                Stats stats = (Stats) this.quitQueue.poll();
                String savedKit = stats.getSavedKit();
                if (savedKit == null) savedKit = "null";
                String statement = "UPDATE SGStats SET Wins='" + stats.getWins() + "', Passes='" + stats.getMutationPasses() + "', GamesPlayed='" + stats.getTimesPlayed() + "', Kills='" + stats.getKills() + "', HighestKillStreak='" + stats.getHighestKillStreak() + "', SavedKit='" + savedKit + "', TimeLogged='" + stats.getLogged() + "', VIP='" + stats.getVip() +"' WHERE `Name` = '" + stats.getName() + "' ;";
                try {
                    Statement stamt = this.con.createStatement();
                    stamt.executeUpdate(statement);
                    stamt.close();
                } catch (SQLException ex) {
                    System.err.println("[StatsSaveThread] Error while doing statement: " + statement);
                    System.err.println("[StatsSaveThread] MySql error: " + ex.getMessage());
                } catch (NullPointerException ex) {
                    System.err.println("[StatsSaveThread] Error while doing statement: " + statement);
                    System.err.println("[StatsSaveThread] Error while performing a query. (NullPointerException)");
                }
            }
            if (this.quitQueue.peek() == null) try {
                Thread.currentThread();
                Thread.sleep(1000L);
            } catch (InterruptedException localInterruptedException) {
            }
        }
    }
}