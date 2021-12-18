package com.ryankshah.skyrimcraft.client.entity.arrow;

import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.item.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class FalmerArrowEntity extends AbstractArrow
{
    public FalmerArrowEntity(EntityType<? extends FalmerArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public FalmerArrowEntity(Level world, double p_i46757_2_, double p_i46757_4_, double p_i46757_6_) {
        super(ModEntityType.FALMER_ARROW_ENTITY.get(), p_i46757_2_, p_i46757_4_, p_i46757_6_, world);
    }

    public FalmerArrowEntity(Level world, LivingEntity livingEntity) {
        super(ModEntityType.FALMER_ARROW_ENTITY.get(), livingEntity, world);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.FALMER_ARROW.get());
    }
}