package com.celeo.hidelogin;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

import java.util.ArrayList;
import java.util.logging.Logger;

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
			logMessage = config.getString("logmessage");
			if(logMessage == null || logMessage == "")
			{
				logMessage = "[A] has logged [B].";
			}
			hiddenPlayers = (ArrayList<String>) config.getKeys("hiddenplayers");
			if(hiddenPlayers == null)
			{
				hiddenPlayers = new ArrayList<String>();
				log.info(pre + "null hiddenplayers");
			}
			noMessages = (ArrayList<String>) config.getKeys("nomessages");
			if(noMessages == null)
			{
				noMessages = new ArrayList<String>();
				log.info(pre + "null nomessages");
			}
		}
		catch(Exception ex)
		{
			log.info(pre + "Error with loading of settings");
		}
		log.info(pre + "settings loaded");
		saveAll();
	}
	
	public static void saveAll() {
		config.setHeader("#	=== Config for HideLogin === #\n"
				+ "#	hiddenplayers are the players who are hiding their messages\n"
				+ "#	nomessages are people who don't want to see messages\n"
				+ "#	hideall is if you want to stop messages altogether\n"
				+ "#	log message is the message that gets displayed to people on login/logout\n"
				+ "##		<White><Yellow><Red><Green> will be replaced with the respective color\n"
				+ "##		[A] will be replaced by the name of the player\n"
				+ "##		[B] will be replaced by the 'direction' of the log\n"
				);
		config.setProperty("hiddenplayers", hiddenPlayers);
		config.setProperty("nomessages", noMessages);
		config.setProperty("hideall", hideAll);
		config.setProperty("logmessage", logMessage);
		config.save();
		log.info(pre + "settings saved");
	}
	
	public static String formatMessage(String player, String mode) {
		String m = logMessage;
		if(m.contains("[A]"))
		{
			m.replace("[A]", player);
		}
		if(m.contains("[B]"))
		{
			m.replace("[B]", mode);
		}
		return m;
	}
	
	public static final Logger log = Logger.getLogger("Minecraft");
	public static ArrayList<String> hiddenPlayers = new ArrayList<String>();
	public static ArrayList<String> noMessages = new ArrayList<String>();
	public static String pre = "[HideLogin] ";	
	public static Configuration config;
	public static ChatColor cyellow = ChatColor.YELLOW;
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cred = ChatColor.RED;
	public static boolean hideAll = false;
	public static String logMessage = "[A] has logged [B].";
	
}