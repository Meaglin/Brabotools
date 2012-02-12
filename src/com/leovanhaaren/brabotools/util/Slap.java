package com.leovanhaaren.brabotools.util;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Slap {

	public static void slapPlayer(Player p) {
		p.setVelocity(new Vector(randomDouble(10), randomDouble(10), randomDouble(5)));
	}
	
	public static double randomDouble(int i) {
		Random randomGenerator = new Random();
		
		int randomInt 	= randomGenerator.nextInt(i);
		double random		= randomInt - (i/2);
		
		return random;
	}

}