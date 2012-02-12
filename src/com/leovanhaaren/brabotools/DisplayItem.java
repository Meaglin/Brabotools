package com.leovanhaaren.brabotools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DisplayItem {
	private Item item;
	private short data;
	private int itemid;
	private Location location;
	private String player;
	private boolean chunkLoaded;
	private boolean aligned;

	public DisplayItem(Location loc, int itemid, String player) {
		setPlayer(player);
		try{
			if(loc.getBlock().getTypeId()==20){
				loc.getBlock().setType(Material.STEP);
			}
		} catch (Exception e) {

		}
		Block block;
		try{
			block = loc.getBlock();
		} catch (Exception e) {
			block = null;
		}
		setChunkLoaded(block == null ? false:block.getWorld().isChunkLoaded(block.getChunk()));
		if(isChunkLoaded()){
			setLocation(loc);
			setItem(loc.getWorld().dropItem(getLocation(), new ItemStack(itemid, 1, data)));
			getItem().setPickupDelay(2500);
			updatePosition();
		} else {
			location = loc;
			setItem(null);
		}
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	
	public void setData(short data) {
		this.data = data;
	}

	public short getData() {
		return data;
	}

	public void setLocation(Location location) {
		location = location.getBlock().getLocation();
		Vector vec = location.toVector();
		vec.add(new Vector(0.5,0.6,0.5));
		location = vec.toLocation(location.getWorld());
		this.location = location;
		if(item!=null){
			item.teleport(location);
		}
	}

	public Location getLocation() {
		return location;
	}

	public void remove(){
		item.remove();
	}
	
	public void respawn() {
		if(isChunkLoaded()){
			if(item != null){
				item.remove();
			}
			ItemStack stack = new ItemStack(itemid, 1);
			item = getLocation().getWorld().dropItemNaturally(location, stack);
			aligned = false;
		}
	}

	public void updatePosition() {
		if(item != null && (!aligned || item.getLocation().getY() <= getLocation().getBlockY()+0.4)){
			item.teleport(location);
			item.setVelocity(new Vector(0,0.1,0));
			aligned = true;
		}
	}

	public Block getBlock() {
		return location.getBlock();
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getPlayer() {
		return player;
	}

	public void setChunkLoaded(boolean chunkLoaded) {
		this.chunkLoaded = chunkLoaded;
	}

	public boolean isChunkLoaded() {
		return chunkLoaded;
	}

}