package com.leovanhaaren.brabotools.util;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.inventory.PlayerItem;

public class CaptureManager {
	
    private Brabotools plugin;

    public CaptureManager(Brabotools brabotools) {
        plugin = brabotools;
    }

    @SuppressWarnings("deprecation")
    public void Catch(Player p, LivingEntity e) {
        for (CaptureType eggType : CaptureType.values()) {
            if (!eggType.isInstance(e)) {
                continue;
            }
            
            if (!eggType.isEnabled()) {
                p.sendMessage(ChatColor.RED + plugin.getConfigManager().MOB_DISABLED_MESSAGE);
                break;
            }
            
            ItemStack cost = new ItemStack(371, 1);
            if (!PlayerItem.Remove(p.getInventory(), cost)) {
                p.sendMessage(ChatColor.RED + plugin.getConfigManager().MOB_COST_MESSAGE);
                break;
            }
            
            if (getSucces()) {
                e.remove();
                ItemStack item = new ItemStack(383, 1, (short) eggType.getId());
                p.getWorld().dropItem(e.getLocation(), item);
                String mob = eggType.getEntityType().getName();
                p.sendMessage(ChatColor.GOLD + mob + plugin.getConfigManager().MOB_CAUGHT_MESSAGE);
                p.updateInventory();
                break;
            }
        }
    }

    public boolean getSucces() {
        Random r = new Random();
        if (r.nextDouble() > (0.5)) {
            return true;
        }
        return false;
    }
}
