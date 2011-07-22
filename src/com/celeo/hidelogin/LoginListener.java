package com.celeo.hidelogin;

import java.util.ArrayList;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class LoginListener extends PlayerListener {
	
	public final HideLogin plugin;
	
	public LoginListener(HideLogin instance) {
		plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Server server = player.getServer();
		if(Util.hideAll == true)
		{
			event.setJoinMessage(null);
		}
		else
		{
			if(Util.hiddenPlayers != null)
			{
				if(Util.hiddenPlayers.contains(player.getDisplayName()))
				{
					event.setJoinMessage(null);
				}
				else
				{
					event.setJoinMessage(null);
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
						{
							p.sendMessage(Util.cyellow + Util.formatMessage(player.getDisplayName(), "in"));
						}
					}
				}
			}
		}		
	}
	
}