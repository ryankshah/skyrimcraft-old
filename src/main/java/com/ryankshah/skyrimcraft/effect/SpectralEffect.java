package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Set;

public class SpectralEffect extends MobEffect implements IForgeRegistryEntry<MobEffect>
{
    public SpectralEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xA5F2F3);
    }

    public Component getDisplayName() {
        return new TextComponent("Spectral");
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity.level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel)livingEntity.level;
            float radius = 0.5f;
            // Get origins
            Vec3 origin = Vec3.ZERO;
            Vec3 normal = new Vec3(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());

            Set<Vec3> circlePoints = ClientUtil.circle(origin, normal, radius, 8);
            for(Vec3 point : circlePoints) {
                world.sendParticles(ParticleTypes.ASH, livingEntity.getX() + point.x, livingEntity.getY() + 0.2f, livingEntity.getZ() + point.z, 1, point.x, 0D, point.z, 0); // set amount to 0 so particles don't fly off and stays in place
            }
        }
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}