package com.celeo.hidelogin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Util
{
	
	public final Core plugin;
		
	public Util(Core instance) 
	{
		plugin = instance;
	}
	
	public static void load(Core plugin)
	{
		config = (YamlConfiguration) plugin.getConfig();
		try
		{
			hideAll = config.getBoolean("hideall", hideAll);
			logMessage = config.getString("logmessage");
			if(logMessage == null || logMessage == "")
				logMessage = "A has logged B.";
			hiddenPlayers = (ArrayList<String>)config.getConfigurationSection("hiddenplayers").getKeys(false);
			if(hiddenPlayers == null)
			{
				hiddenPlayers = new ArrayList<String>();
				log.info(pre + "null hiddenplayers");
			}
			noMessages = (ArrayList<String>) config.getConfigurationSection("nomessages").getKeys(false);
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
		saveAll(plugin);
	}
	
	public static void saveAll(Core plugin)
	{
		config.options().header(
				"#	=== Config for HideLogin === #\n"
				+ "#	hiddenplayers are the players who are hiding their messages\n"
				+ "#	nomessages are people who don't want to see messages\n"
				+ "#	hideall is if you want to stop messages altogether\n"
				+ "#	log message is the message that gets displayed to people on login/logout\n"
				+ "##		A will be replaced by the name of the player\n"
				+ "##		B will be replaced by the 'direction' of the log\n"
				);
		config.set("hiddenplayers", hiddenPlayers);
		config.set("nomessages", noMessages);
		config.set("hideall", hideAll);
		config.set("logmessage", logMessage);
		plugin.saveConfig();
		log.info(pre + "settings saved");
	}
	
	public static String formatMessage(String playerName, String mode)
	{
		String m = logMessage;
		m = m.replaceAll("A", playerName);
		m = m.replaceAll("B", mode);
		return m;
	}
	
	public static final Logger log = Logger.getLogger("Minecraft");
	public static ArrayList<String> hiddenPlayers = new ArrayList<String>();
	public static ArrayList<String> noMessages = new ArrayList<String>();
	public static String pre = "[HideLogin] ";	
	public static YamlConfiguration config;
	public static ChatColor cyellow = ChatColor.YELLOW;
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cred = ChatColor.RED;
	public static boolean hideAll = false;
	public static String logMessage = "[A] has logged [B].";
	
}