package com.leovanhaaren.brabotools;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.entity.Item;

public class DisplayTable {
	
    public ArrayList<DisplayItem> displayTables = new ArrayList<DisplayItem>();
    public int tableItem		= 0;
    
    public DisplayTable(){
    	
    }

	public DisplayItem getItemByBlock(Block b) {
		for (DisplayItem item: displayTables) {
			if (b.equals(item.getBlock())) {
				return item;
			}
		}
		return null;
	}
	
	public DisplayItem getItemByDrop(Item i) {
		for (DisplayItem item: displayTables) {
			if (item.getItem().equals(i)) {
				return item;
			}
		}
		return null;
	}
	
	public int removeTables() {
		int removedTables = 0;
		
		for (DisplayItem item: displayTables) {
			item.remove();
			removedTables++;
		}
		
		return removedTables;
	}

}
