package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Set;

public class SpectralEffect extends Effect implements IForgeRegistryEntry<Effect>
{
    public SpectralEffect() {
        super(EffectType.BENEFICIAL, 0xA5F2F3);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent("Spectral");
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity.level instanceof ServerWorld) {
            ServerWorld world = (ServerWorld)livingEntity.level;
            float radius = 0.5f;
            // Get origins
            Vector3d origin = Vector3d.ZERO;
            Vector3d normal = new Vector3d(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());

            Set<Vector3d> circlePoints = ClientUtil.circle(origin, normal, radius, 8);
            for(Vector3d point : circlePoints) {
                world.sendParticles(ParticleTypes.ASH, livingEntity.getX() + point.x, livingEntity.getY() + 0.2f, livingEntity.getZ() + point.z, 1, point.x, 0D, point.z, 0); // set amount to 0 so particles don't fly off and stays in place
            }
        }
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}