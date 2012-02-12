package com.leovanhaaren.brabotools.util;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Hammer {

	public static HashMap<Player, Boolean> HammerList = new HashMap<Player, Boolean>();
	
	public static void hammerPlayer(Player p) {
		p.setVelocity(new Vector(0,0,0));
		HammerList.put(p, true);
	}

	public static void unhammerPlayer(Player p) {
		HammerList.remove(p);
	}
}