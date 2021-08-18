package com.timewars.hungergames.classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class myItem implements ConfigurationSerializable {

    private ItemStack itemStack;

    private int probability;

    public myItem(ItemStack itemStack, int probability) {
        this.itemStack = itemStack;
        this.probability = probability;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("itemStack", itemStack);
        serialized.put("probability", probability);

        return serialized;
    }

    public static myItem deserialize(Map<String, Object> deserialize) {
        return new myItem(
                (ItemStack) deserialize.get("itemStack"),
                (int) deserialize.get("probability")
        );
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }
}
