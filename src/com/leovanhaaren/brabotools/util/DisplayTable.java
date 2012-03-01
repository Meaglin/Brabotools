package com.leovanhaaren.brabotools.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.leovanhaaren.brabotools.inventory.PlayerItem;

public class DisplayTable {
	private int id;
	private Item item;
	private int itemid;
	private short itemdata;
	private Block block;
	private Player player;
	private Location location;
	private boolean chunkLoaded = false;

	public DisplayTable(Player player, Block block, int itemid, short itemdata) {
		setItemId(itemid);
		setItemData(itemdata);
		setBlock(block);
		setPlayer(player);
		setLocation(block.getLocation());
		setChunkLoaded(block.getWorld().isChunkLoaded(block.getChunk()));
		
		ItemStack item = new ItemStack(itemid, 1, itemdata);
		
		if (isChunkLoaded()) {
			setItem(location.getWorld().dropItem(getLocation(), item));
			getItem().setPickupDelay(2500);
			updatePosition();
		}
		
		PlayerItem.Remove(player.getInventory(), item);
	}

	public DisplayTable(int id, String w, double x, double y, double z, String player, int itemid, short itemdata) {
		World world = Bukkit.getWorld(w);
		Location location = new Location(world, x, y, z);
		setId(id);
		setItemId(itemid);
		setItemData(itemdata);
		setBlock(location.getBlock());
		setPlayer(Bukkit.getPlayer(player));
		setLocation(location);
		setChunkLoaded(block.getWorld().isChunkLoaded(block.getChunk()));
		
		ItemStack item = new ItemStack(itemid, 1, itemdata);
		
		if (isChunkLoaded()) {
			setItem(location.getWorld().dropItem(getLocation(), item));
			getItem().setPickupDelay(2500);
			updatePosition();
		}
	}

	public void remove(){
		item.remove();
	}

	public void updatePosition() {
		item.teleport(location);
		item.setVelocity(new Vector(0, 0.1, 0));
	}
	
	public void respawn() {
		if (isChunkLoaded()) {
			if(item != null) item.remove();
			
			ItemStack stack = new ItemStack(getItemId(), 1, getItemData());
			setItem(getBlock().getLocation().getWorld().dropItem(getBlock().getLocation(), stack));
			getItem().setPickupDelay(2500);
			updatePosition();
		}
	}

	public Block getBlock() {
		return block;
	}
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	public void setLocation(Location location) {
		location = location.getBlock().getLocation();
		Vector vec = location.toVector();
		
		if(getBlock().getType() == Material.STEP) {
			vec.add(new Vector(0.5, 0.6, 0.5));
		} else {
			vec.add(new Vector(0.5, 1.1, 0.5));
		}
		
		location = vec.toLocation(location.getWorld());
		this.location = location;
		
		if (item != null) item.teleport(location);
	}

	public Location getLocation() {
		return location;
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

	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
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

}