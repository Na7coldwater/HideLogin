package com.celeo.hidelogin;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class Core extends JavaPlugin 
{
	
	public static PermissionHandler Permissions;
	
	public PListener playerListener = new PListener(this);
	
	public void onEnable()
	{
		Util.load(this);
                getServer().getPluginManager().registerEvents(playerListener, this);
		Util.log.info("[HideLogin] <enabled>");
	}
	
	public void onDisable()
	{
		Util.saveAll(this);
		Util.log.info("[HideLogin] <disabled>");
	}
	
	public void setupPermissions()
	{
		Plugin test = getServer().getPluginManager().getPlugin("Permissions");
		if (Permissions == null)
		{
			if (test != null)
			{
				getServer().getPluginManager().enablePlugin(test);
				Permissions = ((Permissions)test).getHandler();
			}
			else
				Util.log.info(Util.pre + "uses Permissions, defaulting to OP.");
		}
	}
	
	public void checkAll(Player player)
	{
		if(Util.hideAll == true)
			player.sendMessage(Util.cred + "Note: " + Util.cgreen + "Login messages are turned completely off");
	}
	
	public static boolean canDo(Player player, String node)
	{
		if(Permissions != null)
		{
			if(Permissions.has(player, node))
				return true;
			else
				return false;
		}
		else
		{
			if(player.isOp())
				return true;
			else
				return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		Player player = (Player)sender;
		String playerName = player.getDisplayName();
		if(commandLabel.equalsIgnoreCase("hiddenlogs") && canDo(player, "hidden.show"))
		{
			String list = "";
			for(String str : Util.hiddenPlayers)
				list += str + " ";
			player.sendMessage(ChatColor.AQUA + "Players who hide their logins/outs:");
			player.sendMessage(ChatColor.AQUA + list);
			return true;
		}
		if(commandLabel.equalsIgnoreCase("hideme") && canDo(player, "hidden.hideme"))
		{
			Util.hiddenPlayers.add(playerName);
			player.sendMessage(Util.cgreen + "You have been added to the hidden login/out list");
			checkAll(player);
			return true;
		}
		if(commandLabel.equalsIgnoreCase("showme") && canDo(player, "hidden.hideme"))
		{
			if(Util.hiddenPlayers.contains(playerName))
			{
				Util.hiddenPlayers.remove(playerName);
				player.sendMessage(Util.cgreen + "You have been removed from the hidden login/out list");
				checkAll(player);
			}
			else
			{
				player.sendMessage(Util.cgreen + "Your login was not being hidden");
				checkAll(player);
			}
			return true;
		}
		if(commandLabel.equalsIgnoreCase("togglelogs") && canDo(player, "hidden.toggle"))
		{
			if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("on"))
				{
					Util.hideAll = true;
					player.sendMessage(Util.cgreen + "Login/out messages set to on");
				}
				else if(args[0].equalsIgnoreCase("off"))
				{
					Util.hideAll = false;
					player.sendMessage(Util.cgreen + "Login/out messages set to off");
				}
				else
					return false;
			}
			else
			{
				if(Util.hideAll == true)
				{
					Util.hideAll = false;
					player.sendMessage(Util.cgreen + "Login/out messages set to on");
				}
				else
				{
					Util.hideAll = true;
					player.sendMessage(Util.cgreen + "Login/out messages set to off");
				}
			}
			return true;
		}
		if(commandLabel.equalsIgnoreCase("hidemessages") && canDo(player, "hidden.messages"))
		{
			if(Util.noMessages != null)
			{
				if(Util.noMessages.contains(playerName))
				{
					player.sendMessage(Util.cred + "You are already blocking others' login/out messages");
				}
				else
				{
					Util.noMessages.add(playerName);
					player.sendMessage(Util.cgreen + "You will no longer receive login/out messages");
				}
			}
			return true;
		}
		if(commandLabel.equalsIgnoreCase("showmessages") && canDo(player, "hidden.messages"))
		{
			if(Util.noMessages != null)
			{
				if(Util.noMessages.contains(playerName))
				{
					Util.noMessages.remove(playerName);
					player.sendMessage(Util.cgreen + "You will now receive login/out messages");
				}
				else
					player.sendMessage(Util.cred + "You are already viewing others' login/out messages");
			}
			return true;
		}
		if(commandLabel.equalsIgnoreCase("fakelog"))
		{
			if(canDo(player, "hidden.fakelog"))
			{
				Player[] onlinePlayers = getServer().getOnlinePlayers();
				ArrayList<Player> displayNames = new ArrayList<Player>();
				for(int i = 0; i < onlinePlayers.length; i++)
				{
					if(!onlinePlayers[i].getDisplayName().equalsIgnoreCase(playerName))
						displayNames.add(onlinePlayers[i]);
				}
				String m = ChatColor.YELLOW + playerName + " has left the game.";
				for(Player p : displayNames)
				{
					if(!Util.noMessages.contains(p.getDisplayName()))
						p.sendMessage(Util.cyellow + m);
				}
			}
			else
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}
		return false;
	}

}