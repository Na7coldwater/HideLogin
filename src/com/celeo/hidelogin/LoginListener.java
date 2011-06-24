package com.celeo.hidelogin;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class LoginListener extends PlayerListener {
	
	public final HideLogin plugin;
	
	public LoginListener(HideLogin instance) {
		plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(Util.hiddenPlayers != null)
		{
			if(Util.hiddenPlayers.contains(event.getPlayer().getDisplayName()))
			{
				event.setJoinMessage(null);
			}
			else
			{
				event.setJoinMessage(null);
				Player[] onlinePlayers = event.getPlayer().getServer().getOnlinePlayers();
				ArrayList<Player> displayNames = new ArrayList<Player>();
				for(int i = 0; i < onlinePlayers.length; i++)
				{
					if(onlinePlayers[i] != event.getPlayer())
					{
						displayNames.add(onlinePlayers[i]);
					}
				}
				for(Player p : displayNames)
				{
					p.sendMessage(Util.cyellow + event.getPlayer().getDisplayName() + " has logged in.");
				}
			}
		}
	}
	
}