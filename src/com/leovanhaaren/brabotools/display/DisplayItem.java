package com.leovanhaaren.brabotools.display;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DisplayItem {
	private Item item;
	private Location location;
	private Block block;
	private Player player;
	private boolean chunkLoaded;
	private boolean aligned;

	public DisplayItem(Player player, Block clickedBlock, int itemId) {
		setPlayer(player);
		setBlock(block);
		setLocation(clickedBlock.getLocation());
		
		//setChunkLoaded(block.getWorld().isChunkLoaded(block.getChunk()));
		
		//if(isChunkLoaded()){

		setItem(clickedBlock.getLocation().getWorld().dropItem(getLocation(), new ItemStack(itemId, 1)));
		getItem().setPickupDelay(2500);
		updatePosition();
		/*} else {
			location = clickedBlock.getLocation();
			setItem(null);
		}*/
	}

	public Location getLocation() {
		return location;
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

	public void remove(){
		item.remove();
	}
	
	public void respawn() {
		if(isChunkLoaded()){
			if(item != null){
				item.remove();
			}
			//ItemStack stack = new ItemStack(itemId, 1);
			//item = getLocation().getWorld().dropItemNaturally(location, stack);
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
		return block;
	}
	
	private void setBlock(Block block) {
		this.block = block;
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

	public boolean isChunkLoaded() {
		return chunkLoaded;
	}
	
	public void setChunkLoaded(boolean chunkLoaded) {
		this.chunkLoaded = chunkLoaded;
	}


}