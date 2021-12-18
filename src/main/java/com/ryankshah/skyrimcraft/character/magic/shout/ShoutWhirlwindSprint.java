package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShoutWhirlwindSprint extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutWhirlwindSprint(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Whirlwind Sprint";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("The Thu'um rushes forward,");
        desc.add("carrying you in its wake with");
        desc.add("the speed of a tempest");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.BLAZE_SHOOT;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/whirlwind_sprint.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 20.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.SHOUT;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        if(getCaster() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)getCaster();

            Vec3 origin = new Vec3(player.getX(), player.getY(), player.getZ());
            Vec3 normal = player.getForward();
            Set<Vec3> circlePoints = ClientUtil.circle(origin, normal, 2f, 8);
            for(Vec3 point : circlePoints) {
                player.getLevel().sendParticles(ParticleTypes.CLOUD, player.getForward().x + point.x, player.getForward().y + player.getEyeHeight() + point.y, player.getForward().z + point.z,  1, 0D, 0D, 0D, 0.0D);
            }

            Vec3 sprintTo = player.position().add(player.getForward().scale(6));
            player.teleportTo(sprintTo.x, player.position().y, sprintTo.z);
        }
        super.onCast();
    }
}