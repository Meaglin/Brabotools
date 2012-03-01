package com.leovanhaaren.brabotools;

import org.bukkit.configuration.file.FileConfiguration;
import com.leovanhaaren.brabotools.util.CaptureType;

public class Config {
	
	private Brabotools brabotools = null;
	
	public static boolean 	TNT_SNOWBALL_ENABLED 	= false;
	public static int		TNT_SNOWBALL_RANGE 		= 0;

	public Config(Brabotools bt) {
		brabotools = bt;
	}
	
	public void loadConfig() {
		FileConfiguration config = brabotools.getConfig();
		
		config.options().copyDefaults(true);
		brabotools.saveConfig();
		
        for (CaptureType eggType : CaptureType.values()) {
                String creatureName = eggType.getCreatureType().getName();
                boolean enabled = config.getBoolean("mobs." + creatureName);
	            eggType.setEnabled(enabled);
        }
        
    	TNT_SNOWBALL_ENABLED = config.getBoolean("tntSnowball.enabled");
    	TNT_SNOWBALL_RANGE 	 = config.getInt("tntSnowball.range");
	}

}
