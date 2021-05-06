package com.ryankshah.skyrimcraft.spell;

import com.ryankshah.skyrimcraft.advancement.TriggerManager;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.event.ForgeClientEvents;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketConsumeMagicka;
import com.ryankshah.skyrimcraft.network.PacketUpdateShoutCooldownOnServer;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * TODO:
 *   - Implement spell "stages" - specifically for shouts so that each
 *     individual part of the shout is learnable
 */
public abstract class ISpell extends ForgeRegistryEntry<ISpell>
{
    private int identifier;
    private PlayerEntity caster;

    public ISpell(int identifier) {
        this.identifier = identifier;
    }

    /**
     * Get the ID of the spell
     *
     * @return spell ID
     */
    public int getID() {
        return identifier;
    }

    public ISpell getSpell() {
        return this;
    }

    /**
     * Get the name of the spell
     *
     * @return name
     */
    public String getName() {
        return "";
    }

    /**
     * Get the spell's description
     * @return description
     */
    public List<String> getDescription() {
        return null;
    }

    public void setCaster(PlayerEntity playerEntity) {
        this.caster = playerEntity;
    }

    /**
     * Get the player entity who casted the spell
     * @return {@link PlayerEntity}
     */
    public PlayerEntity getCaster() {
        return this.caster;
    }

    public ResourceLocation getDisplayAnimation() { return null; }

    /**
     * Get the magicka cost of the spell
     *
     * @return magicka cost
     */
    public float getCost() {
        return 0;
    }

    /**
     * Get the cooldown (seconds) of the spell
     *
     * @return cooldown
     */
    public float getCooldown() {
        return 0;
    }

    /**
     * Get the type of spell {@link SpellType}
     *
     * @return {@link SpellType}
     */
    public SpellType getType() {
        return SpellType.DESTRUCTION;
    }

    /**
     * Gets the sound to play before the spell is cast
     * @return {@link SoundEvent}
     */
    public SoundEvent getSound() {
        return null;
    }

    /**
     * Returns the length of the sound
     * @return sound length
     */
    public float getSoundLength() { return 0f; }

    /**
     * Get the spell difficulty {@link SpellDifficulty}
     *
     * @return {@link SpellDifficulty}
     */
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    private CastResult canCast() {
        ISkyrimPlayerData cap = caster.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("spell onCast"));
        if(getType() == SpellType.SHOUT) {
            return cap.getShoutCooldown(this) <= 0f ? CastResult.SUCCESS : CastResult.COOLDOWN;
        } else {
            return (cap.getMagicka() >= getCost() || getCost() == 0f) ? CastResult.SUCCESS : CastResult.MAGICKA;
        }
    }

    public void cast() {
        if(canCast() == CastResult.SUCCESS) {
            onCast();
        }  else {
            getCaster().displayClientMessage(new StringTextComponent(TextFormatting.RED + (canCast() == CastResult.MAGICKA ? "Not enough magicka!" : "This shout is still on cooldown!") + TextFormatting.RESET), false);
        }
    }

    /**
     * Specifies what happens on spell cast
     */
    public void onCast() {
        ISkyrimPlayerData cap = caster.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("spell onCast"));
        if(!caster.isCreative()) {
            if (getType() == SpellType.SHOUT)
                Networking.sendToServer(new PacketUpdateShoutCooldownOnServer(this, getCooldown()));
            else
                Networking.sendToServer(new PacketConsumeMagicka(getCost()));
        }
        // TODO: Create a var in cap to track cast time (sound length of current cast) on use packets to sync with player tick
        caster.getCommandSenderWorld().playSound(null, caster.getX(), caster.getY(), caster.getZ(), getSound(), SoundCategory.PLAYERS, 1f, 1f);
    }

    public enum SpellType {
        ALL(-1, "ALL"),
        ALTERATION(0, "ALTERATION"),
        DESTRUCTION(1, "DESTRUCTION"),
        RESTORATION(2, "RESTORATION"),
        SHOUT(3, "SHOUTS"),
        POWERS(4, "POWERS"),
        ILLUSION(5, "ILLUSION"),
        CONJURATION(6, "CONJURATION");

        private int typeID;
        private String displayName;

        SpellType(int typeID, String displayName) {
            this.typeID = typeID;
            this.displayName = displayName;
        }

        public int getTypeID() {
            return this.typeID;
        }


        @Override
        public String toString() {
            return this.displayName;
        }
    }
    public enum SpellDifficulty {
        NA(0),
        NOVICE(1),
        APPRENTICE(2),
        ADEPT(3),
        EXPERT(4),
        MASTER(5);

        private int difficulty;

        SpellDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getDifficulty() {
            return this.difficulty;
        }
    }

    private enum CastResult {
        SUCCESS(0),
        COOLDOWN(1),
        MAGICKA(2);

        private int id;

        CastResult(int id) { this.id = id; }

        public int getId() { return this.id; }
    }

//    @Override
//    public int hashCode() {
//        return getID();
//    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ISpell && getID() == ((ISpell) obj).getID();
    }
}