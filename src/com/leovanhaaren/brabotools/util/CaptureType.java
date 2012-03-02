package com.leovanhaaren.brabotools.util;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public enum CaptureType {
    CREEPER(50, EntityType.CREEPER),
    SKELETON(51, EntityType.SKELETON),
    SPIDER(52, EntityType.SPIDER),
    ZOMBIE(54, EntityType.ZOMBIE),
    SLIME(55, EntityType.SLIME),
    GHAST(56, EntityType.GHAST),
    PIG_ZOMBIE(57, EntityType.PIG_ZOMBIE),
    ENDERMAN(58, EntityType.ENDERMAN),
    CAVE_SPIDER(59, EntityType.CAVE_SPIDER),
    SILVERFISH(60, EntityType.SILVERFISH),
    BLAZE(61, EntityType.BLAZE),
    MAGMA_CUBE(62, EntityType.MAGMA_CUBE),
    PIG(90, EntityType.PIG),
    SHEEP(91, EntityType.SHEEP),
    COW(92, EntityType.COW),
    CHICKEN(93, EntityType.CHICKEN),
    SQUID(94, EntityType.SQUID),
    WOLF(95, EntityType.WOLF),
    MUSHROOM_COW(96, EntityType.MUSHROOM_COW),
    SNOWMAN(97, EntityType.SNOWMAN),
    OCELOT(98, EntityType.OCELOT),
    IRONGOLEM(99, EntityType.IRON_GOLEM),
    VILLAGER(120, EntityType.VILLAGER);

     private int id;
     private EntityType entitytype;
     private boolean enabled = false;

     private CaptureType(int id, EntityType entitytype) {
         this.id = id;
         this.entitytype = entitytype;
     }

     public int getId() {
         return id;
     }

     public boolean isInstance(LivingEntity livingEntity) {
         return entitytype.getEntityClass().isInstance(livingEntity);
     }

     public EntityType getEntityType() {
         return entitytype;
     }

     public void setEnabled(boolean e) {
    	 enabled = e;
     }

     public boolean isEnabled() {
    	 return enabled;
     }
}