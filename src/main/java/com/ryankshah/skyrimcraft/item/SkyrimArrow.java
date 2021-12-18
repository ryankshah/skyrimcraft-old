package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.client.entity.arrow.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SkyrimArrow extends ArrowItem {
    private String displayName;

    public SkyrimArrow(Properties p_i48531_1_, String displayName) {
        super(p_i48531_1_);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
        int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
        return enchant > 0 && this.getClass() == this.getClass();
    }

    // Arrows

    public static class AncientNordArrow extends SkyrimArrow {
        public AncientNordArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            AncientNordArrowEntity arrowentity = new AncientNordArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class GlassArrow extends SkyrimArrow {
        public GlassArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            GlassArrowEntity arrowentity = new GlassArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class DaedricArrow extends SkyrimArrow {
        public DaedricArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            DaedricArrowEntity arrowentity = new DaedricArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class IronArrow extends SkyrimArrow {
        public IronArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            IronArrowEntity arrowentity = new IronArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class SteelArrow extends SkyrimArrow {
        public SteelArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            SteelArrowEntity arrowentity = new SteelArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class DragonboneArrow extends SkyrimArrow {
        public DragonboneArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            DragonboneArrowEntity arrowentity = new DragonboneArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class DwarvenArrow extends SkyrimArrow {
        public DwarvenArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            DwarvenArrowEntity arrowentity = new DwarvenArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class EbonyArrow extends SkyrimArrow {
        public EbonyArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            EbonyArrowEntity arrowentity = new EbonyArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class ElvenArrow extends SkyrimArrow {
        public ElvenArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            ElvenArrowEntity arrowentity = new ElvenArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class OrcishArrow extends SkyrimArrow {
        public OrcishArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            OrcishArrowEntity arrowentity = new OrcishArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
    public static class FalmerArrow extends SkyrimArrow {
        public FalmerArrow(Properties p_i48531_1_, String displayName) {
            super(p_i48531_1_, displayName);
        }

        @Override
        public AbstractArrow createArrow(Level p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
            FalmerArrowEntity arrowentity = new FalmerArrowEntity(p_200887_1_, p_200887_3_);
            return arrowentity;
        }
    }
}