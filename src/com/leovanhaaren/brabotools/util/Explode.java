package com.leovanhaaren.brabotools.util;

import org.bukkit.entity.Player;

public class Explode {

	public static void explodePlayer(Player p) {
		p.getWorld().createExplosion(p.getLocation(), 5);
	}

}