package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.client.gui.SkyrimIngameGui;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class PacketAddToLevelUpdates
{
    private static final Logger LOGGER = LogManager.getLogger();
    private LevelUpdate levelUpdate;

    public PacketAddToLevelUpdates(FriendlyByteBuf buf) {
        String updateName = buf.readUtf();
        int level = buf.readInt();
        int levelUpRenderTime = buf.readInt();
        levelUpdate = new LevelUpdate(updateName, level, levelUpRenderTime);
    }

    public PacketAddToLevelUpdates(LevelUpdate levelUpdate) {
        this.levelUpdate = levelUpdate;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(levelUpdate.getUpdateName());
        buf.writeInt(levelUpdate.getLevel());
        buf.writeInt(levelUpdate.getLevelUpRenderTime());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketAddToLevelUpdates received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketAddToLevelUpdates context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(ISkyrimPlayerData::incrementLevelUpdates);
            Minecraft.getInstance().player.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
            SkyrimIngameGui.LEVEL_UPDATES.add(levelUpdate);
        });

        return true;
    }
}