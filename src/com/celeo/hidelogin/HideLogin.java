package com.celeo.hidelogin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class HideLogin extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public LoginListener loginListener = new LoginListener(this);
	
	@Override
	public void onDisable() {
		Util.saveAll();
		log.info("[HideLogin] <disabled>");
	}

	@Override
	public void onEnable() {
		Util.load(this);
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_JOIN, this.loginListener, Event.Priority.Normal, this);
		log.info("[HideLogin] <enabled>");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player)sender;
		if(commandLabel.equalsIgnoreCase("hiddenlogins") && sender.isOp())
		{
			String list = "";
			for(String str : Util.hiddenPlayers)
			{
				list += str + " ";
			}
			player.sendMessage(ChatColor.AQUA + "Players who hide their logins:");
			player.sendMessage(ChatColor.AQUA + list);
			return true;
		}
		if(commandLabel.equalsIgnoreCase("hideme") && sender.isOp())
		{
			Util.hiddenPlayers.add(player.getDisplayName());
			player.sendMessage("You have been added to the hidden login list");
			return true;
		}
		if(commandLabel.equalsIgnoreCase("showme") && sender.isOp())
		{
			if(Util.hiddenPlayers.contains(player.getDisplayName()))
			{
				Util.hiddenPlayers.remove(player.getDisplayName());
				player.sendMessage("You have been removed from the hidden login list");
			}
			return true;
		}
		return true;
	}

}