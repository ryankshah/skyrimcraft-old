package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.util.RayTraceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutDragonrend extends ISpell implements IForgeRegistryEntry<ISpell>
{
    public ShoutDragonrend(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Dragonrend";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Your voice lashes out at a");
        desc.add("dragon's very soul, forcing");
        desc.add("the beast to land");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.ENDER_DRAGON_GROWL;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/dragonrend.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 60.0f;
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
        World world = getCaster().level;
        Entity rayTracedEntity = RayTraceUtil.rayTrace(world, getCaster(), 200D);
        if(rayTracedEntity instanceof EnderDragonEntity) {
            EnderDragonEntity dragon = (EnderDragonEntity) rayTracedEntity;

            dragon.getPhaseManager().setPhase(PhaseType.LANDING);

            super.onCast();
        } else {
            getCaster().displayClientMessage(new StringTextComponent("There is nothing there to cast this shout on!"), false);
        }
    }
}