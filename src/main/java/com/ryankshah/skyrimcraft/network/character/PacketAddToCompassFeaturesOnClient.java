package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class PacketAddToCompassFeaturesOnClient
{
    private static final Logger LOGGER = LogManager.getLogger();
    private CompassFeature compassFeature;

    public PacketAddToCompassFeaturesOnClient(FriendlyByteBuf buf) {
        UUID id = buf.readUUID();
        ResourceLocation structure = buf.readResourceLocation();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        BlockPos pos = new BlockPos(x, y, z);
        compassFeature = new CompassFeature(id, structure, pos);
    }

    public PacketAddToCompassFeaturesOnClient(CompassFeature compassFeature) {
        this.compassFeature = compassFeature;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(compassFeature.getId());
        buf.writeResourceLocation(compassFeature.getFeature());
        buf.writeInt(compassFeature.getBlockPos().getX());
        buf.writeInt(compassFeature.getBlockPos().getY());
        buf.writeInt(compassFeature.getBlockPos().getZ());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("PacketUpdateMapFeatures received on wrong side:" + context.getDirection().getReceptionSide());
            return false;
        }
        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("PacketUpdateMapFeatures context could not provide a ClientWorld.");
            return false;
        }

        ctx.get().enqueueWork(() -> {
            Networking.sendToServer(new PacketAddToCompassFeatures(compassFeature));
        });

        return true;
    }
}