package com.eyeofender.survivalgames.models;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StatsLoadThread extends Thread {
    protected Connection con = null;
    protected ConcurrentLinkedQueue<String> joinQueue = new ConcurrentLinkedQueue<String>();
    private StatsSystem plugin;
    private boolean running;

    protected StatsLoadThread(StatsSystem statsSystem) {
        this.plugin = statsSystem;
    }

    public void SQLdisconnect() {
        try {
            System.out.println("[StatsLoadThread] Disconnecting from MySQL database...");
            this.con.close();
        } catch (SQLException ex) {
            System.err.println("[StatsLoadThread] Error while closing the connection...");
        } catch (NullPointerException ex) {
            System.err.println("[StatsLoadThread] Error while closing the connection...");
        }
    }

    public void SQLconnect() {
        try {
            System.out.println("[StatsLoadThread] Connecting to MySQL database...");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String conn = "jdbc:mysql://" + this.plugin.SQL_HOST + "/" + this.plugin.SQL_DATA;
            this.con = DriverManager.getConnection(conn, this.plugin.SQL_USER, this.plugin.SQL_PASS);
        } catch (ClassNotFoundException ex) {
            System.err.println("[StatsLoadThread] No MySQL driver found!");
        } catch (SQLException ex) {
            System.err.println("[StatsLoadThread] Error while fetching MySQL connection!");
        } catch (Exception ex) {
            System.err.println("[StatsLoadThread] Unknown error while fetchting MySQL connection.");
        }

        boolean exists = true;
        try {
            DatabaseMetaData dbm = this.con.getMetaData();

            ResultSet tables = dbm.getTables(null, null, "SGStats", null);
            if (!tables.next()) exists = false;
        } catch (SQLException localSQLException1) {
        } catch (NullPointerException localNullPointerException1) {
        }
        if (!exists) {
            String sta = "CREATE TABLE SGStats (ID int(10) unsigned NOT NULL AUTO_INCREMENT, Name varchar(20) NOT NULL, Wins INT(10) NOT NULL, Passes INT(10) NOT NULL, GamesPlayed INT(10) NOT NULL, Kills INT(10) NOT NULL, HighestKillStreak INT(10) NOT NULL, SavedKit varchar(20) NOT NULL, TimeLogged BIGINT(20) NOT NULL, VIP INT(10) NOT NULL, PRIMARY KEY (`ID`))";
            try {
                Statement st = this.con.createStatement();
                st.executeUpdate(sta);
                st.close();
            } catch (SQLException ex) {
                System.err.println("[Archergames] Error with following query: " + sta);
                System.err.println("[Archergames] MySQL-Error: " + ex.getMessage());
            } catch (NullPointerException ex) {
                System.err.println("[Archergames] Error while performing a query. (NullPointerException)");
            }
        }
    }

    public void terminate() {
        running = false;
    }

    public void run() {
        System.out.println("Stats load thread started");
        SQLconnect();
        running = true;
        while (running) {
            if (this.joinQueue.peek() != null) {
                String playername = (String) this.joinQueue.poll();
                try {
                    Statement stmt = this.con.createStatement();
                    ResultSet r = stmt.executeQuery("SELECT * FROM `SGStats` WHERE `Name` = '" + playername + "' ;");
                    r.last();
                    if (r.getRow() == 0) {
                        stmt.close();
                        r.close();
                        System.out.println(playername + "'s stats do not exist, Creating them");
                        String insert = "INSERT INTO SGStats (Name, Wins, Passes, GamesPlayed, Kills, HighestKillStreak, SavedKit, TimeLogged, VIP) VALUES ('" + playername + "', '0', '0' , '0', '0', '0', 'null', '0', '0')";
                        try {
                            Statement stamt = this.con.createStatement();
                            stamt.executeUpdate(insert);
                            stamt.close();
                            this.joinQueue.add(playername);
                        } catch (SQLException ex) {
                            System.err.println("[StatsLoadThread] MySql error while creating new stats for player " + playername + ", Error: " + ex);
                        } catch (NullPointerException ex) {
                            System.err.println("[StatsLoadThread] MySql error while creating new stats for player " + playername + ", Error: " + ex);
                        }
                    } else {
                        String savedKit = r.getString("SavedKit");
                        if (savedKit.equals("null")) savedKit = null;
                        Stats stats = new Stats(playername, r.getInt("Wins"), r.getInt("Passes"), r.getInt("GamesPlayed"), r.getInt("Kills"), r.getInt("HighestKillStreak"), savedKit, r.getLong("TimeLogged"), r.getInt("VIP"));
                        stats.startTimeLog();
                        this.plugin.getPlayerStats().put(playername, stats);
                        stmt.close();
                        r.close();
                        this.plugin.loadedStats(stats);
                    }
                } catch (SQLException ex) {
                    System.out.println("[StatsLoadThread] Error while fetching " + playername + "'s stats: " + ex);
                } catch (NullPointerException ex) {
                    System.out.println("[StatsLoadThread] Error while fetching " + playername + "'s stats: " + ex);
                }
            }
            if (this.joinQueue.peek() == null) try {
                Thread.currentThread();
                Thread.sleep(1000L);
            } catch (InterruptedException localInterruptedException) {
            }
        }
    }
}