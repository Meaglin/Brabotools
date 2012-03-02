package com.leovanhaaren.brabotools.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.Config;

public class DisplayManager {

    private Brabotools plugin;
    public List<DisplayTable> tables = new ArrayList<DisplayTable>();

    public DisplayManager(Brabotools brabotools) {
        plugin = brabotools;
    }

    public DisplayTable getTableByBlock(Block block) {
        for (DisplayTable table : tables) {
            if (table.getBlock().equals(block)) {
                return table;
            }
        }
        return null;
    }

    public boolean isTableBlock(Block block) {
        for (Integer blockid : Config.DISPLAY_TABLE_BLOCKS) {
            if (blockid.equals(block.getTypeId())) {
                return true;
            }
        }
        return false;
    }

    public void createDisplayTable(Player player, Block block) {
        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.AIR) {
            return;
        }

        int itemid = item.getTypeId();
        short itemdata = item.getDurability();
        Map<Enchantment, Integer> enchantment = item.getEnchantments();

        if (enchantment.isEmpty()) {
            DisplayTable table = new DisplayTable(block, player, itemid, itemdata);
            addTable(table);
            plugin.getMysqlDatabase().save(table);

            player.sendMessage(ChatColor.GOLD + Config.TABLE_CREATE_MESSAGE);
        } else {
            player.sendMessage(ChatColor.RED + Config.TABLE_ENCHANT_MESSAGE);
        }
    }

    public void removeDisplayTable(Player player, Block block) {
        synchronized (tables) {
            Iterator<DisplayTable> tableiter = tables.iterator();
            while (tableiter.hasNext()) {
                DisplayTable table = tableiter.next();
                if (!table.getBlock().equals(block)) {
                    continue;
                }
                if (!table.getPlayer().equals(player)) {
                    player.sendMessage(ChatColor.RED + "This table belongs to " + table.getPlayer().getDisplayName() + ChatColor.RED + "!");
                    continue;
                }
                if (!plugin.getMysqlDatabase().delete(table)) {
                    return;
                }

                table.getItem().setPickupDelay(0);
                tableiter.remove();
                player.sendMessage(ChatColor.GOLD + "Display Table removed!");
            }
        }
    }

    public int removeAllTables() {
        int i = 0;
        for (DisplayTable table : tables) {
            table.getItem().setPickupDelay(0);
            removeTable(table);
            i++;
        }
        return i;
    }

    public List<DisplayTable> getDisplayTables() {
        return tables;
    }

    public void addTable(DisplayTable table) {
        tables.add(table);
    }

    public void removeTable(DisplayTable table) {
        try {
            tables.remove(table);
        } catch (Exception e) {
        }
    }

    public void loadTables() {
        for (World world : plugin.getServer().getWorlds()) {
            List<DisplayTable> tables = plugin.getMysqlDatabase().getTables(world.getName());

            for (DisplayTable table : tables) {
                this.tables.add(table);
            }
        }
    }

    public void unloadTables() {
        for (DisplayTable table : getDisplayTables()) {
            table.remove();
        }
    }

    public void reloadTableItems() {
        for (DisplayTable table : getDisplayTables()) {
            table.respawn();
        }
    }
}
