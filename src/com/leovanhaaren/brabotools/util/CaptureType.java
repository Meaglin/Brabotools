package com.leovanhaaren.brabotools.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.CreatureType;

public enum CaptureType {
    CREEPER(50, CreatureType.CREEPER),
    SKELETON(51, CreatureType.SKELETON),
    SPIDER(52, CreatureType.SPIDER),
    ZOMBIE(54, CreatureType.ZOMBIE),
    SLIME(55, CreatureType.SLIME),
    GHAST(56, CreatureType.GHAST),
    PIG_ZOMBIE(57, CreatureType.PIG_ZOMBIE),
    ENDERMAN(58, CreatureType.ENDERMAN),
    CAVE_SPIDER(59, CreatureType.CAVE_SPIDER),
    SILVERFISH(60, CreatureType.SILVERFISH),
    BLAZE(61, CreatureType.BLAZE),
    MAGMA_CUBE(62, CreatureType.MAGMA_CUBE),
    PIG(90, CreatureType.PIG),
    SHEEP(91, CreatureType.SHEEP),
    COW(92, CreatureType.COW),
    CHICKEN(93, CreatureType.CHICKEN),
    SQUID(94, CreatureType.SQUID),
    WOLF(95, CreatureType.WOLF),
    MUSHROOM_COW(96, CreatureType.MUSHROOM_COW),
    SNOWMAN(97, CreatureType.SNOWMAN),
    VILLAGER(120, CreatureType.VILLAGER);

     private int id;
     private CreatureType creatureType;
     private boolean enabled = false;

     private CaptureType(int id, CreatureType creatureType) {
         this.id = id;
         this.creatureType = creatureType;
     }

     public int getId() {
         return id;
     }

     public boolean isInstance(LivingEntity livingEntity) {
         return creatureType.getEntityClass().isInstance(livingEntity);
     }

     public CreatureType getCreatureType() {
         return creatureType;
     }

     public void setEnabled(boolean e) {
    	 enabled = e;
     }

     public boolean isEnabled() {
    	 return enabled;
     }
}