package com.leovanhaaren.brabotools.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.leovanhaaren.brabotools.inventory.PlayerItem;

public class DisplayTable {
	private Item item;
	private short data;
	private Block block;
	private Player player;
	private Location location;
	private Material material;
	private boolean chunkLoaded = false;

	public DisplayTable(Player player, Block block, Material material, short data) {
		setPlayer(player);
		setBlock(block);
		setMaterial(material);
		setData(data);
		setChunkLoaded(block.getWorld().isChunkLoaded(block.getChunk()));
		
		ItemStack item = new ItemStack(material, 1, getData());
		Location location = block.getLocation();
		
		if (isChunkLoaded()) {
			setLocation(location);
			setItem(location.getWorld().dropItem(getLocation(), item));
			getItem().setPickupDelay(2500);
			updatePosition();
		} else {
			this.location = location;
		}
		
		PlayerItem.Remove(player.getInventory(), item);
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
			
			ItemStack stack = new ItemStack(getMaterial(), 1, getData());
			setItem(getBlock().getLocation().getWorld().dropItem(getBlock().getLocation(), stack));
			getItem().setPickupDelay(2500);
			updatePosition();
		}
	}

	public Block getBlock() {
		return block;
	}
	
	private void setBlock(Block block) {
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

	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}

}