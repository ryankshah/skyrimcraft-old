package com.ryankshah.skyrimcraft.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class SkyrimArmorItem extends ArmorItem
{
    private String displayName;
    private boolean isHeavy;

    public SkyrimArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_, String displayName, boolean isHeavy) {
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
