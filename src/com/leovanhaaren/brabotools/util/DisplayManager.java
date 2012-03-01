package com.leovanhaaren.brabotools.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DisplayManager {
	
    public List<DisplayTable> tables = new ArrayList<DisplayTable>();
	
	public DisplayManager() {}
	
	// Returns the DisplayTable object bound to this block
	public DisplayTable getTableByBlock(Block block) {
		for (DisplayTable displaytable: tables) {
			if(displaytable.getBlock().equals(block)) {
				return displaytable;
			}
		}
		return null;
	}
	
	// Creates a new DisplayTable and binds it to the given block
	public void createDisplayTable(Player player, Block block) {
		DisplayTable displaytable = getTableByBlock(block);
		
		if(displaytable == null) {
			ItemStack item = player.getItemInHand();
			if(item.getType() == Material.AIR) return;
			
			Material material 						= item.getType();
			short data 								= item.getDurability();
			Map<Enchantment, Integer> enchantment 	= item.getEnchantments();
			
			if(enchantment.size() == 0){
				DisplayTable displayTable 	= new DisplayTable(player, block, material, data);
				addTable(displayTable);
				
				player.sendMessage(ChatColor.GOLD + "Display table created!");
			} else {
				player.sendMessage(ChatColor.RED + "Cannot create Display Table with enchanted items!");
			}

		} else {
			if(displaytable.getPlayer().equals(player)) {
				displaytable.getItem().setPickupDelay(0);
				removeTable(displaytable);
				player.sendMessage(ChatColor.GOLD + "Display table removed!");
			} else {
				player.sendMessage(ChatColor.RED + "This table belongs to " + displaytable.getPlayer().getDisplayName() + ChatColor.RED + "!");
			}
		}
	}
	
	// Returns amount of removed DisplayTables, and making the players able to pickup the items
	public void removeDisplayTable(Block block) {
		for (DisplayTable table: tables) {
			if (table.getBlock().equals(block)) {
				table.getItem().setPickupDelay(0);
				removeTable(table);
			}
		}
	}
	
	public void removeDisplayTable(DisplayTable table) {
		removeTable(table);
	}

	// Returns amount of removed DisplayTables, and making the players able to pickup the items
	public int removeAllTables() {
		int i = 0;
		for (DisplayTable table: tables) {
			table.getItem().setPickupDelay(0);
			removeTable(table);
			i++;
		}
		return i;
	}

	public List<DisplayTable> getDisplayTables() {
		return this.tables;
	}
	
	public void addTable(DisplayTable displaytable) {
		tables.add(displaytable);
	}
	
	public void removeTable(DisplayTable displaytable) {
		try {
			tables.remove(displaytable);
		} catch (Exception e) {}
	}

}
