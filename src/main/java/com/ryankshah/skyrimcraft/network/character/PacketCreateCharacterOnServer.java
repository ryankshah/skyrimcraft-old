package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.network.Networking;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketCreateCharacterOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private Race race;

    private int id;
    private String name;
    private Map<Integer, ISkill> startingSkills = new HashMap<>();

    public PacketCreateCharacterOnServer(PacketBuffer buf) {
        int raceID = buf.readInt();
        String raceName = buf.readUtf();
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

            startingSkills.put(id, new ISkill(id, name, level, totalXp, xpProgress, skillUseMultiplier, skillUseOffset, skillImproveMultiplier, skillImproveOffset));
        }

        race = new Race(raceID, raceName, startingSkills);
    }

    public PacketCreateCharacterOnServer(Race race) {
        this.race = race;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(race.getId());
        buf.writeUtf(race.getName());
        buf.writeInt(race.getStartingSkills().size());
        for(Map.Entry<Integer, ISkill> skill : race.getStartingSkills().entrySet()) {
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
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketCreateCharacterOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("Server Player was null when PacketCreateCharacterOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                cap.setRace(race);
                cap.setSkills(race.getStartingSkills());
                //TriggerManager.TRIGGERS.get(spell).trigger(sendingPlayer);
                Networking.sendToClient(new PacketUpdateCharacter(cap.getCharacterXp(), cap.getSkills(), cap.getRace()), sendingPlayer);
            });
        });
        return true;
    }
}