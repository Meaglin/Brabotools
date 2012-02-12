package com.leovanhaaren.brabotools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.leovanhaaren.brabotools.util.Chat;
import com.leovanhaaren.brabotools.util.Hammer;

public class HammerCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(commandLabel.equalsIgnoreCase("unhammer")) {
			if(args.length < 1) return false;
			if(!(sender instanceof Player)) return false;
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if(target != null) {
				Hammer.unhammerPlayer(target);
				Chat.playerMessage((Player) sender, "Unhammer time!");
			} else {
				Chat.playerMessage((Player) sender, "Player is offline.");
			}
		}
		
		return true;
	}
}