package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.client.gui.SkyrimIngameGui;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class PacketAddToLevelUpdates
{
    private static final Logger LOGGER = LogManager.getLogger();
    private LevelUpdate levelUpdate;

    public PacketAddToLevelUpdates(PacketBuffer buf) {
        String updateName = buf.readUtf();
        int level = buf.readInt();
        int levelUpRenderTime = buf.readInt();
        levelUpdate = new LevelUpdate(updateName, level, levelUpRenderTime);
    }

    public PacketAddToLevelUpdates(LevelUpdate levelUpdate) {
        this.levelUpdate = levelUpdate;
    }

    public void toBytes(PacketBuffer buf) {
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
        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketAddToLevelUpdates context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().player.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
            SkyrimIngameGui.LEVEL_UPDATES.add(levelUpdate);
        });

        return true;
    }
}