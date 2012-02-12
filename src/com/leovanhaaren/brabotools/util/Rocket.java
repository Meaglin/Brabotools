package com.leovanhaaren.brabotools.util;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Rocket {

	public static void shootPlayer(Player p) {
		p.setVelocity(new Vector(p.getVelocity().getX(), 10, p.getVelocity().getY()));
	}

}