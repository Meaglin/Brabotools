package com.leovanhaaren.brabotools.util;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.inventory.ItemManager;

public class CaptureManager {
	
    private Brabotools plugin;

    public CaptureManager(Brabotools brabotools) {
        plugin = brabotools;
    }

    @SuppressWarnings("deprecation")
    public void Catch(Player player, LivingEntity livingentity) {
        for (CaptureType eggType : CaptureType.values()) {
            if (!eggType.isInstance(livingentity)) {
                continue;
            }
            
            if (!eggType.isEnabled()) {
                player.sendMessage(plugin.getConfigManager().MOB_DISABLED_MESSAGE);
                break;
            }
            
            if (livingentity instanceof Tameable) {
                if (((Tameable)livingentity).isTamed()) {
                	player.sendMessage(plugin.getConfigManager().MOB_TAMED_MESSAGE);
                	break;
                }
	        }

	        if (livingentity instanceof Animals) {
	            if (!((Animals)livingentity).isAdult()) {
	            	player.sendMessage(plugin.getConfigManager().MOB_BABY_MESSAGE);
	                break;
	            }
	        }
            
            ItemStack cost = new ItemStack(plugin.getConfigManager().MOBCATCH_COST, 1);
            if (!ItemManager.Remove(player.getInventory(), cost)) {
            	player.sendMessage(plugin.getConfigManager().MOB_COST_MESSAGE);
                break;
            }
            
            Random random = new Random();
            if (random.nextInt(100) < plugin.getConfigManager().MOBCATCH_CHANCE) {
            	World world 	  = player.getWorld();
            	Location location = livingentity.getLocation();
            	
            	livingentity.remove();
            		
                ItemStack item = new ItemStack(383, 1, (short) eggType.getId());
                world.dropItem(location, item);
                
                String mob = eggType.getEntityType().getName();
                player.sendMessage(ChatColor.GOLD + mob + plugin.getConfigManager().MOB_CAUGHT_MESSAGE);
                
                player.updateInventory();
                break;
            }
        }
    }

}
