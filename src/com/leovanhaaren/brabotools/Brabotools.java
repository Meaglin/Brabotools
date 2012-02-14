package com.leovanhaaren.brabotools;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.leovanhaaren.brabotools.commands.ExplodeCommand;
import com.leovanhaaren.brabotools.commands.HammerCommand;
import com.leovanhaaren.brabotools.commands.SlapCommand;
import com.leovanhaaren.brabotools.commands.UnHammerCommand;
import com.leovanhaaren.brabotools.listener.EntityListener;


public class Brabotools extends JavaPlugin {
	
	public static Brabotools			 plugin  		= null;
    protected static final Logger        logger         = Logger.getLogger("Minecraft");

   	public void onDisable(){
   		plugin = null;
   		PluginDescriptionFile pdfFile = this.getDescription();
   		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now disabled.");
   	}   
   	
    public void onEnable() {
    	plugin = this;
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
		
		registerEvents();
		registerCommands();
    }
    
	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EntityListener(), plugin);
	}
    
    public void registerCommands() {
        getCommand("hammer").setExecutor(new HammerCommand());
        getCommand("unhammer").setExecutor(new UnHammerCommand());
        getCommand("explode").setExecutor(new ExplodeCommand());
        getCommand("slap").setExecutor(new SlapCommand());
    }
    
    public static Brabotools getInstance(){
        return plugin;
    }
	
}
