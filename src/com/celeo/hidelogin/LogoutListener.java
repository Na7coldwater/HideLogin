package com.celeo.hidelogin;

import java.util.ArrayList;

import org.bukkit.Server;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

public class LogoutListener extends PlayerListener {
	
	public final HideLogin plugin;
	
	public LogoutListener(HideLogin instance) {
		plugin = instance;
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Server server = player.getServer();
		if(Util.hideAll == true)
		{
			event.setQuitMessage(null);
		}
		else
		{
			if(Util.hiddenPlayers != null)
			{
				if(Util.hiddenPlayers.contains(player.getDisplayName()))
				{
					event.setQuitMessage(null);
				}
				else
				{
					event.setQuitMessage(null);
					Player[] onlinePlayers = server.getOnlinePlayers();
					ArrayList<Player> displayNames = new ArrayList<Player>();
					for(int i = 0; i < onlinePlayers.length; i++)
					{
						if(onlinePlayers[i] != player)
						{
							displayNames.add(onlinePlayers[i]);
						}
					}
					for(Player p : displayNames)
					{
						if(!Util.noMessages.contains(p.getDisplayName()))
							p.sendMessage(Util.cyellow + player.getDisplayName() + " has logged out.");
					}
				}
			}
		}	
	}
	
}