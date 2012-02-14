package com.leovanhaaren.brabotools.unused;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.leovanhaaren.brabotools.util.Chat;

public class DisplayTable {
	
    public List<DisplayItem> displayTables 			= new ArrayList<DisplayItem>();
    public static HashMap<Integer, Integer> prefferedItems = new HashMap<Integer, Integer>();
	
	public DisplayTable(Player player, Block clickedBlock) {
		if(addDisplayTable(new DisplayItem(player, clickedBlock, getPrefferedItem(player)))){
			Chat.succesMessage(player, "Display table created!");
		} else {
			Chat.succesMessage(player, "There's already a table there!");
		}
	}
	
	public boolean addDisplayTable(DisplayItem i) {
		if(!displayTables.contains(displayTables)){
			displayTables.add(i);
			return true;
		} else {
			return false;
		}
		

	}

	public static void setPrefferedItem(Player player, int item) {
		if(prefferedItems.containsKey(player.getEntityId())){
			prefferedItems.remove(player.getEntityId());
			prefferedItems.put(player.getEntityId(), item);
			Chat.succesMessage(player, "Table item set to: " + item);
		} else {
			prefferedItems.put(player.getEntityId(), item);
			Chat.succesMessage(player, "Table item set to: " + item);
		}
	}

	public static int getPrefferedItem(Player player) {
		return prefferedItems.get(player.getEntityId());
	}

	public int removeTables() {
		int removedTables = 0;
		
		for (DisplayItem tables: displayTables) {
			tables.remove();
			removedTables++;
		}
		
		return removedTables;
	}

}
