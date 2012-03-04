package com.leovanhaaren.brabotools.util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.leovanhaaren.brabotools.inventory.ItemManager;

public class DisplayTable {
	private int 		id;
	private World 		world;
	private Block 		block;
	private String 		player;
	private int 		itemid;
	private short 		itemdata;
	private Item 		item;
	private boolean 	chunkLoaded = false;
	
	public static final int PICKUP_DELAY = Integer.MAX_VALUE; // 3.4 Years
	public static final int AGE = Integer.MIN_VALUE; // 3.4 Years

	public DisplayTable(Block block, Player player, int itemid, short itemdata) {
		setWorld(block.getWorld().getName());
		setBlock(block);
		setPlayer(player.getName());
		setItemId(itemid);
		setItemData(itemdata);
		setChunkLoaded(getBlock().getWorld().isChunkLoaded(getBlock().getChunk()));
		
		ItemStack item = new ItemStack(itemid, 1, itemdata);
		if (isChunkLoaded()) {
			setItem(getBlock().getWorld().dropItem(getBlock().getLocation(), item));
			getItem().setPickupDelay(PICKUP_DELAY);
			getItem().setTicksLived(AGE);
			updatePosition();
			checkForDupedItem();
		}
		
		ItemManager.Remove(player.getInventory(), item);
	}

	public DisplayTable(int id, String world, int x, int y, int z, String player, int itemid, short itemdata) {	
		setId(id);
		setWorld(world);
		setBlock(x, y, z);
		setPlayer(player);
		setItemId(itemid);
		setItemData(itemdata);
		setChunkLoaded(getBlock().getWorld().isChunkLoaded(getBlock().getChunk()));
		
		if (isChunkLoaded()) {
			ItemStack item = new ItemStack(getItemId(), 1, getItemData());
			setItem(getBlock().getLocation().getWorld().dropItem(getBlock().getLocation(), item));
			getItem().setPickupDelay(PICKUP_DELAY);
			getItem().setTicksLived(AGE);
			updatePosition();
			checkForDupedItem();
		}
	}

	public void remove(){
		item.remove();
	}
	
	public void respawn() {
		if (isChunkLoaded()) {
			if(item != null) item.remove();
			
			ItemStack stack = new ItemStack(getItemId(), 1, getItemData());
			setItem(getBlock().getLocation().getWorld().dropItem(getBlock().getLocation(), stack));
			getItem().setPickupDelay(PICKUP_DELAY);
			getItem().setTicksLived(AGE);
			updatePosition();
		}
	}
	
	public void updatePosition() {
		Vector vec = getBlock().getLocation().toVector();
		
		if (getBlock().getType() == Material.STEP) {
			vec.add(new Vector(0.5, 0.6, 0.5));
		} else {
			vec.add(new Vector(0.5, 1.1, 0.5));
		}
		
		Location newlocation = vec.toLocation(getBlock().getWorld());
		if (item != null) item.teleport(newlocation);
		item.setVelocity(new Vector(0, 0.1, 0));
	}
	
	public void checkForDupedItem() {
		Chunk chunk = getBlock().getChunk();
		for (Entity entity : chunk.getEntities()) {
			if (entity instanceof Item && entity.getLocation().equals(item.getLocation()) && !entity.equals(item)) {
				entity.remove();
			}
		}
	}
	
	public void setChunkLoaded(boolean chunkLoaded) {
		this.chunkLoaded = chunkLoaded;
	}

	public boolean isChunkLoaded() {
		return chunkLoaded;
	}

	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}


	public int getItemId() {
		return itemid;
	}
	
	public void setItemId(int itemid) {
		this.itemid = itemid;
	}

	public short getItemData() {
		return itemdata;
	}

	public void setItemData(short itemdata) {
		this.itemdata = itemdata;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	private void setBlock(int x, int y, int z) {
		this.block = getWorld().getBlockAt(x, y, z);
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = Bukkit.getWorld(world);
	}

}