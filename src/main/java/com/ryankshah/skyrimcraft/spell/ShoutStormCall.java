package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.util.RayTraceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoutStormCall extends ISpell implements IForgeRegistryEntry<ISpell> {
    public ShoutStormCall(int identifier) {
        super(identifier);
    }

    @Override
    public String getName() {
        return "Storm Call";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("A shout to the skies, that");
        desc.add("awakens the destructive");
        desc.add("force of Minecraft's lightning");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.LIGHTNING_BOLT_THUNDER;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return new ResourceLocation(Skyrimcraft.MODID, "spells/storm_call.png");
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
        // call lightning storm on target
        World world = getCaster().level;
        Entity rayTracedEntity = RayTraceUtil.rayTrace(world, getCaster(), 20D);
        if(rayTracedEntity instanceof LivingEntity) {
            double x = rayTracedEntity.getX();
            double y = rayTracedEntity.getY();
            double z = rayTracedEntity.getZ();

            //Networking.sendToAllClients(() -> world.getChunkAt(new BlockPos(x, y, z)), new PacketStormCallOnClient(x, y, z));

            // Add lightning
            LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
            //lightning.setVisualOnly(true);
            lightning.setRemainingFireTicks(0);
            lightning.setCause((ServerPlayerEntity) getCaster());
            lightning.moveTo(x, y, z);
            world.addFreshEntity(lightning);

            super.onCast();
        } else {
            getCaster().displayClientMessage(new StringTextComponent("There is nothing there to cast this shout on!"), false);
        }
    }

//    protected static RayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
//        Vector3d vector3d = player.pick
//        Vector3d vector3d1 = player.getViewVector(0.0f);
//        Vector3d vector3d2 = vector3d.add(vector3d1.x * 20D, vector3d1.y * 20D, vector3d1.z * 20D);
//        return player.level.clip(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
//    }
}