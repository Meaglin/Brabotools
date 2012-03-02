package com.leovanhaaren.brabotools;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.leovanhaaren.brabotools.util.CaptureType;

public class ConfigManager {

	private Brabotools plugin;
	
	public boolean 		TNT_SNOWBALL_ENABLED 					= 	false;
	public int			TNT_SNOWBALL_RANGE 						= 	0;
	
	public boolean 		MOBCATCH_ENABLED 						= 	false;
	public boolean 		DISPLAY_TABLE_ENABLED 					= 	false;
	public List<Integer> DISPLAY_TABLE_BLOCKS 					= 	null;
	
	public final String TABLE_CREATE_MESSAGE 					= 	"Display Table created!";
	public final String TABLE_REMOVE_MESSAGE 					= 	"Display Table removed!";
	public final String TABLE_PRESENT_MESSAGE 					= 	"There's already a Display Table there!";
	public final String TABLE_ENCHANT_MESSAGE 					= 	"Cannot create Display Table with enchanted items!";
	public final String TABLE_PLACE_BLOCK_MESSAGE 				= 	"Cannot place block on a Display Table!";
	public final String TABLE_CANNOT_DESTROY_BLOCK_MESSAGE 		= 	"Cannot destroy a Display Table block!";
	public final String TABLE_NOT_OWNER_MESSAGE 				= 	"This Display Table doesn't belong to you!";

	public ConfigManager(Brabotools brabotools) {
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
