package com.leovanhaaren.brabotools.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;

public class DisplayManager {

    private Brabotools plugin;
    
    public HashMap<Block, DisplayTable> map = new HashMap<Block, DisplayTable>();

    public DisplayManager(Brabotools brabotools) {
        plugin = brabotools;
    }

    public DisplayTable getTableByBlock(Block block) {
        return map.get(block);
    }
    
    public boolean isTable(Block block) {
        return map.containsKey(block);
    }

    public boolean isTableBlock(Block block) {
        for (Integer blockid : plugin.getConfigManager().DISPLAY_TABLE_BLOCKS) {
            if (blockid.equals(block.getTypeId())) return true;
        }
        return false;
    }

    public void createDisplayTable(Player player, Block block) {
        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.AIR) return;

        int itemid = item.getTypeId();
        short itemdata = item.getDurability();
        Map<Enchantment, Integer> enchantment = item.getEnchantments();

        if (enchantment.isEmpty()) {
            DisplayTable table = new DisplayTable(block, player, itemid, itemdata);
            addTable(table);
            plugin.getMysqlDatabase().save(table);

            player.sendMessage(plugin.getConfigManager().TABLE_CREATE_MESSAGE);
        } else {
            player.sendMessage(plugin.getConfigManager().TABLE_ENCHANT_MESSAGE);
        }
    }

    public boolean removeDisplayTable(Player player, Block block) {
        DisplayTable table = map.get(block);
            
        if (table == null || !table.getPlayer().equals(player.getName())) {
            player.sendMessage(plugin.getConfigManager().TABLE_NOT_OWNER_MESSAGE);
            return false;
        }
            
        if (!plugin.getMysqlDatabase().delete(table)) return false;

        table.getItem().setPickupDelay(0);
        player.sendMessage(plugin.getConfigManager().TABLE_REMOVE_MESSAGE);
        map.remove(table.getBlock());
        return true;
    }

    public Collection<DisplayTable> getDisplayTables() {
        return map.values();
    }

    public void addTable(DisplayTable table) {
        map.put(table.getBlock(), table);
    }

    public void removeTable(DisplayTable table) {
        map.remove(table.getBlock());
    }

    public void loadTables() {
        for (World world : plugin.getServer().getWorlds()) {
            List<DisplayTable> tables = plugin.getMysqlDatabase().getTables(world.getName());

            for (DisplayTable table : tables)
                 map.put(table.getBlock(), table);
        }
    }

    public void unloadTables() {
        for (DisplayTable table : map.values())
             table.remove();
    }

    public void reloadTableItems() {
        for (DisplayTable table : map.values()) 
             table.respawn();
    }
}
