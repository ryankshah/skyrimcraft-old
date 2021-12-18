package com.ryankshah.skyrimcraft.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class SkyrimArmorItem extends ArmorItem
{
    private String displayName;
    private boolean isHeavy;

    public SkyrimArmorItem(ArmorMaterial p_i48534_1_, EquipmentSlot p_i48534_2_, Properties p_i48534_3_, String displayName, boolean isHeavy) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
        this.displayName = displayName;
        this.isHeavy = isHeavy;
    }

    public boolean isHeavy() {
        return isHeavy;
    }

    public String getDisplayName() {
        return displayName;
    }
}
