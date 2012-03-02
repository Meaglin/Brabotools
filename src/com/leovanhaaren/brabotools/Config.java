package com.leovanhaaren.brabotools;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.leovanhaaren.brabotools.util.CaptureType;

public class Config {

	private Brabotools plugin;
	
	public static boolean 		TNT_SNOWBALL_ENABLED 	= false;
	public static int			TNT_SNOWBALL_RANGE 		= 0;
	
	public static boolean 		MOBCATCH_ENABLED 		= false;
	public static boolean 		DISPLAY_TABLE_ENABLED 	= false;
	public static List<Integer> DISPLAY_TABLE_BLOCKS 	= null;
	
	public static final String TABLE_CREATE_MESSAGE 	= 	"Display table created!";
	public static final String TABLE_ENCHANT_MESSAGE 	= 	"Cannot create Display Table with enchanted items!";

	public Config(Brabotools brabotools) {
		plugin = brabotools;
	}
	
	public void loadConfig() {
		FileConfiguration config = plugin.getConfig();
		
		config.options().copyDefaults(true);
		plugin.saveConfig();
		
        for (CaptureType eggType : CaptureType.values()) {
                String creatureName = eggType.getEntityType().getName();
                boolean enabled = config.getBoolean("mobCatch.mobs." + creatureName);
	            eggType.setEnabled(enabled);
        }
        
    	TNT_SNOWBALL_ENABLED 	= config.getBoolean("tntSnowball.enabled");
    	TNT_SNOWBALL_RANGE 	 	= config.getInt("tntSnowball.range");
    	
    	MOBCATCH_ENABLED 	 	= config.getBoolean("mobCatch.enabled");
    	
    	DISPLAY_TABLE_ENABLED 	= config.getBoolean("displayTable.enabled");
        DISPLAY_TABLE_BLOCKS 	= config.getIntegerList("displayTable.tableblocks");
	}

}
