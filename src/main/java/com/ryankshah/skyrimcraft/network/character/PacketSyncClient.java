package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

/**
 * This class is used for syncing data from all previous packets
 * used in CapabilityHandler for syncing data back to the client
 * at key events, such as login, respawn and changing dimension.
 * This will reduce the load on the network and leave less room
 * for errors to be made and make it easier to find bugs!
 */
public class PacketSyncClient
{
    private static final Logger LOGGER = LogManager.getLogger();

    private float magicka, extraMaxMagicka, extraMaxHealth, extraMaxStamina, magickaRegenModifier;
    private int characterXp;
    private List<ISpell> knownSpells = new ArrayList<>();
    private List<CompassFeature> compassFeatures = new ArrayList<>();
    private Map<Integer, ISpell> selectedSpells = new HashMap<>();
    private Map<Integer, ISkill> skills = new HashMap<>();
    private Map<ISpell, Float> shoutsAndCooldowns = new HashMap<>();
    private LivingEntity targetEntity;
    private Race race;

    public PacketSyncClient(FriendlyByteBuf buf) {
        extraMaxMagicka = buf.readFloat();
        extraMaxHealth = buf.readFloat();
        extraMaxStamina = buf.readFloat();
        magicka = buf.readFloat();
        magickaRegenModifier = buf.readFloat();

        int size = buf.readInt();
        for(int i = 0; i < size; i++)
            knownSpells.add(SpellRegistry.SPELLS_REGISTRY.get().getValue(buf.readResourceLocation()));

        CompoundTag nbt = buf.readNbt();
        String spell0RL = nbt.getString("selected0");
        String spell1RL = nbt.getString("selected1");
        selectedSpells.put(0, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell0RL)));
        selectedSpells.put(1, spell0RL.equals("null") ? null : SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spell1RL)));

        int id = buf.readVarInt();
        if(id != -1) {
            Entity ent = ClientUtil.getClientWorld().getEntity(id);
            targetEntity = ent instanceof LivingEntity ? (LivingEntity) ent : null;
        } else targetEntity = null;

        CompoundTag shoutsAndCooldownsNBT = buf.readNbt();
        for(String s : shoutsAndCooldownsNBT.getAllKeys()) {
            shoutsAndCooldowns.put(SpellRegistry.SPELLS_REGISTRY.get().getValue(new ResourceLocation(s)), shoutsAndCooldownsNBT.getFloat(s));
        }

        int compassFeaturesSize = buf.readInt();
        for(int i = 0; i < compassFeaturesSize; i++) {
            UUID uuid = buf.readUUID();
            ResourceLocation structure = buf.readResourceLocation();
            int x = buf.readInt();
            int y = buf.readInt();
            int z = buf.readInt();
            BlockPos pos = new BlockPos(x, y, z);
            compassFeatures.add(new CompassFeature(uuid, structure, pos));
        }

        characterXp = buf.readInt();
        int skillSize = buf.readInt();
        for(int i = 0; i < skillSize; i++) {
            int skillID = buf.readInt();
            String name = buf.readUtf();
            int level = buf.readInt();
            int totalXp = buf.readInt();
            float xpProgress = buf.readFloat();
            float skillUseMultiplier = buf.readFloat();
            int skillUseOffset = buf.readInt();
            float skillImproveMultiplier = buf.readFloat();
            int skillImproveOffset = buf.readInt();

            skills.put(skillID, new ISkill(skillID, name, level, totalXp, xpProgress, skillUseMultiplier, skillUseOffset, skillImproveMultiplier, skillImproveOffset));
        }
        int raceID = buf.readInt();
        String raceName = buf.readUtf();
        race = new Race(raceID, raceName, skills);
    }

    public PacketSyncClient(float extraMaxMagicka, float extraMaxHealth, float extraMaxStamina,
                            float magicka, float magickaRegenModifier, List<ISpell> knownSpells,
                            Map<Integer, ISpell> selectedSpells, LivingEntity targetEntity,
                            Map<ISpell, Float> shoutsAndCooldowns, List<CompassFeature> compassFeatures,
                            int characterXp, Map<Integer, ISkill> skills, Race race) {
        this.extraMaxMagicka = extraMaxMagicka;
        this.extraMaxHealth = extraMaxHealth;
        this.extraMaxStamina = extraMaxStamina;
        this.magicka = magicka;
        this.magickaRegenModifier = magickaRegenModifier;
        this.knownSpells = knownSpells;
        this.selectedSpells = selectedSpells;
        this.targetEntity = targetEntity;
        this.shoutsAndCooldowns = shoutsAndCooldowns;
        this.compassFeatures = compassFeatures;
        this.characterXp = characterXp;
        this.skills = skills;
        this.race = race;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(extraMaxMagicka);
        buf.writeFloat(extraMaxHealth);
        buf.writeFloat(extraMaxStamina);
        buf.writeFloat(magicka);
        buf.writeFloat(magickaRegenModifier);

        buf.writeInt(knownSpells.size());
        for(ISpell spell : knownSpells)
            buf.writeResourceLocation(SpellRegistry.SPELLS_REGISTRY.get().getKey(spell));

        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<Integer, ISpell> entry : selectedSpells.entrySet()) {
            nbt.putString("selected" + entry.getKey(), entry.getValue() == null ? "null" : entry.getValue().getRegistryName().toString());
        }
        buf.writeNbt(nbt);

        int id = targetEntity != null ? targetEntity.getId() : -1;
        buf.writeVarInt(id);

        CompoundTag shoutsAndCooldownsNBT = new CompoundTag();
        for (Map.Entry<ISpell, Float> entry : shoutsAndCooldowns.entrySet()) {
            shoutsAndCooldownsNBT.put(entry.getKey().getRegistryName().toString(), FloatTag.valueOf(entry.getValue()));
        }
        buf.writeNbt(shoutsAndCooldownsNBT);

        buf.writeInt(compassFeatures.size());
        for(CompassFeature feature : compassFeatures) {
            buf.writeUUID(feature.getId());
            buf.writeResourceLocation(feature.getFeature());
            buf.writeInt(feature.getBlockPos().getX());
            buf.writeInt(feature.getBlockPos().getY());
            buf.writeInt(feature.getBlockPos().getZ());
        }

        buf.writeInt(characterXp);
        buf.writeInt(skills.size());
        for(Map.Entry<Integer, ISkill> skill : skills.entrySet()) {
            buf.writeInt(skill.getKey());
            buf.writeUtf(skill.getValue().getName());
            buf.writeInt(skill.getValue().getLevel());
            buf.writeInt(skill.getValue().getTotalXp());
            buf.writeFloat(skill.getValue().getXpProgress());
            buf.writeFloat(skill.getValue().getSkillUseMultiplier());
            buf.writeInt(skill.getValue().getSkillUseOffset());
            buf.writeFloat(skill.getValue().getSkillImproveMultiplier());
            buf.writeInt(skill.getValue().getSkillImproveOffset());
        }
        buf.writeInt(race.getId());
        buf.writeUtf(race.getName());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketSyncClient received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketSyncClient context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setExtraMaxMagicka(extraMaxMagicka);
                cap.setExtraMaxHealth(extraMaxHealth);
                cap.setExtraMaxStamina(extraMaxStamina);
                cap.setMagicka(magicka);
                cap.setMagickaRegenModifier(magickaRegenModifier);
                cap.setKnownSpells(knownSpells);
                cap.setSelectedSpells(selectedSpells);
                cap.setCurrentTarget(targetEntity);
                cap.setShoutsWithCooldowns(shoutsAndCooldowns);
                cap.setCompassFeatures(compassFeatures);
                cap.setCharacterXp(characterXp);
                cap.setSkills(skills);
                cap.setRace(race);
            });
        });
        return true;
    }
}