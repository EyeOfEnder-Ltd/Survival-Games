package com.eyeofender.survivalgames.handlers;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import world.refresh.FileUtilities;

import com.eyeofender.survivalgames.SurvivalGames;

public class WorldHandler {

	  private final File folder = new File("plugins/WorldRefresh/worlds/");
	  private final Random rand = new Random();
	  SurvivalGames plugin;
	public WorldHandler (SurvivalGames plugin){  
		if (!this.folder.exists()) {
		      this.folder.mkdirs();
		}
		
		this.plugin = plugin;
	}
	
	public void refreshWorld(String world, String load)
	  {

	    File w = new File(world + "/");
	    clear(w);

	    if (this.folder.listFiles().length != 0) {
	      File[] files = this.folder.listFiles();
	      File r = null;
	      r = files[this.rand.nextInt(files.length)];
	      if (load != null) {
	        plugin.getConfig().set("load", null);
	        plugin.saveConfig();
	        for (int n = 0; n < files.length; n++) {
	          if (files[n].getName().toLowerCase().equals(load.toLowerCase())) {
	            r = files[n];
	            break;
	          }
	        }
	      }

	      copy(r, new File(world + "/"));
	    } 
	  }
	
	 public static void clear(File file) {
		    if (!file.isDirectory()) {
		      System.err.println("Can't clear something that isn't a directory!");
		      return;
		    }
		    File[] files = file.listFiles();
		    for (File f : files)
		      if (f.isDirectory()) {
		        clear(f);
		        f.delete();
		      } else {
		        f.delete();
		      }
		  }
	 
	  public static void copy(File from, File to)
	  {
	    try {
	      if (!to.exists())
	        to.mkdirs();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    File[] files = from.listFiles();
	    for (File file : files)
	      try {
	        if (file.getName().equals("uid.dat"))
	          System.err.println("Cannot copy uid.dat!");
	        else
	          FileUtilities.copy(file, new File(to, file.getName()));
	      }
	      catch (IOException e) {
	        e.printStackTrace();
	      }
	  }
}
