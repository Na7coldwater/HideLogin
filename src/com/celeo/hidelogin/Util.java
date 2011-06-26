package com.celeo.hidelogin;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

import java.util.ArrayList;

public class Util {
	
	public final HideLogin plugin;
		
	public Util(HideLogin instance) {
		plugin = instance;
	}
	
	public static void load(HideLogin plugin) {
		config = plugin.getConfiguration();
		try
		{
			hideAll = config.getBoolean("hideall", hideAll);
			hiddenPlayers = (ArrayList<String>) config.getProperty("hiddenplayers");
			if(hiddenPlayers == null)
			{
				hiddenPlayers = new ArrayList<String>();
				HideLogin.log.info(pre + "null hiddenplayers");
			}
			noMessages = (ArrayList<String>) config.getProperty("nomessages");
			if(noMessages == null)
			{
				noMessages = new ArrayList<String>();
				HideLogin.log.info(pre + "null nomessages");
			}
		}
		catch(Exception ex)
		{
			HideLogin.log.info(pre + "Error with loading of settings");
		}
		HideLogin.log.info(pre + "settings loaded");
		saveAll();
	}
	
	public static void saveAll() {
		config.setProperty("hiddenplayers", hiddenPlayers);
		config.setProperty("nomessages", noMessages);
		config.setProperty("hideall", hideAll);
		config.save();
		HideLogin.log.info(pre + "settings saved");
	}
	
	public static ArrayList<String> hiddenPlayers = new ArrayList<String>();
	public static ArrayList<String> noMessages = new ArrayList<String>();
	public static String pre = "[HideLogin] ";	
	public static Configuration config;
	public static ChatColor cyellow = ChatColor.YELLOW;
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cred = ChatColor.RED;
	public static boolean hideAll = false;
	
}