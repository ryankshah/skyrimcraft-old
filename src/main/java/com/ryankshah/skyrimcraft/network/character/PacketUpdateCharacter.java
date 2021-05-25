package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class PacketUpdateCharacter
{
    private int characterXp;
    private Map<Integer, ISkill> skills = new HashMap<>();
    private Race race;
    private static final Logger LOGGER = LogManager.getLogger();

    public PacketUpdateCharacter(PacketBuffer buf) {
        characterXp = buf.readInt();
        int size = buf.readInt();
        for(int i = 0; i < size; i++) {
            int id = buf.readInt();
            String name = buf.readUtf();
            int level = buf.readInt();
            int totalXp = buf.readInt();
            float xpProgress = buf.readFloat();
            float skillUseMultiplier = buf.readFloat();
            int skillUseOffset = buf.readInt();
            float skillImproveMultiplier = buf.readFloat();
            int skillImproveOffset = buf.readInt();

            skills.put(id, new ISkill(id, name, level, totalXp, xpProgress, skillUseMultiplier, skillUseOffset, skillImproveMultiplier, skillImproveOffset));
        }
        int raceID = buf.readInt();
        String raceName = buf.readUtf();
        race = new Race(raceID, raceName, skills);
    }

    public PacketUpdateCharacter(int characterXp, Map<Integer, ISkill> skills, Race race) {
        this.characterXp = characterXp;
        this.skills = skills;
        this.race = race;
    }

    public void toBytes(PacketBuffer buf) {
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
            LOGGER.warn("PacketUpdateCharacter received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateCharacter context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setCharacterXp(characterXp);
                cap.setSkills(skills);
                cap.setRace(race);
            });
        });
        return true;
    }
}