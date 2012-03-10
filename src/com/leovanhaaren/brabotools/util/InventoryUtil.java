package com.leovanhaaren.brabotools.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static final boolean Remove(Inventory inventory, ItemStack item) {
        ItemStack[] stacks = inventory.getContents();

        for (int i = 0; i < stacks.length; i++) {
            ItemStack s = stacks[i];

            if (s == null)
                continue;
            if (s.getTypeId() == item.getTypeId()
                    && s.getDurability() == item.getDurability()) {
                if (s.getAmount() >= item.getAmount()) {
                    s.setAmount(s.getAmount() - item.getAmount());
                    if (s.getAmount() <= 0)
                        inventory.setItem(i, null);
                    else
                        inventory.setItem(i, s);
                    return true;
                } else {
                    item.setAmount(item.getAmount() - s.getAmount());
                    s.setAmount(0);
                    inventory.setItem(i, null);
                    return false;
                }
            }
        }

        return false;
    }

    public static final boolean has(Inventory inventory, ItemStack item) {
        ItemStack itemClone = clone(item);
        ItemStack[] items = inventory.getContents();
        for (ItemStack t : items) {
            if (t == null)
                continue;
            if (t.getTypeId() == itemClone.getTypeId()
                    && itemClone.getDurability() == -1) {
                itemClone.setDurability(t.getDurability());
                item.setDurability(t.getDurability());
            }
            if (t.getTypeId() == itemClone.getTypeId()
                    && t.getDurability() == itemClone.getDurability()
                    && t.getEnchantments().size() == 0) {
                if (t.getAmount() >= itemClone.getAmount())
                    return true;
                else
                    itemClone.setAmount(itemClone.getAmount() - t.getAmount());
            }
        }
        if (itemClone.getAmount() <= 0)
            return true;

        return false;
    }

    public static final ItemStack clone(ItemStack item) {
        return new ItemStack(item.getTypeId(), item.getAmount(),
                item.getDurability());
    }

}
