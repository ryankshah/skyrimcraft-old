package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.network.character.*;
import com.ryankshah.skyrimcraft.network.spell.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class Networking
{
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Skyrimcraft.MODID, "skyrimcraft_channel"),
                () -> "1.0",
                s -> true,
                s -> true);

        INSTANCE.messageBuilder(PacketUpdateMagicka.class, nextID())
                .encoder(PacketUpdateMagicka::toBytes)
                .decoder(PacketUpdateMagicka::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketUpdateMagicka::handle)
                .add();

        INSTANCE.messageBuilder(PacketConsumeMagicka.class, nextID())
                .encoder(PacketConsumeMagicka::toBytes)
                .decoder(PacketConsumeMagicka::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketConsumeMagicka::handle)
                .add();

        INSTANCE.messageBuilder(PacketReplenishMagicka.class, nextID())
                .encoder(PacketReplenishMagicka::toBytes)
                .decoder(PacketReplenishMagicka::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketReplenishMagicka::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateMagickaRegenModifierOnServer.class, nextID())
                .encoder(PacketUpdateMagickaRegenModifierOnServer::toBytes)
                .decoder(PacketUpdateMagickaRegenModifierOnServer::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketUpdateMagickaRegenModifierOnServer::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateMagickaRegenModifierOnClient.class, nextID())
                .encoder(PacketUpdateMagickaRegenModifierOnClient::toBytes)
                .decoder(PacketUpdateMagickaRegenModifierOnClient::new) // buf -> new PacketUpdateMagicka(buf)
                .consumer(PacketUpdateMagickaRegenModifierOnClient::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateKnownSpells.class, nextID())
                .encoder(PacketUpdateKnownSpells::toBytes)
                .decoder(PacketUpdateKnownSpells::new)
                .consumer(PacketUpdateKnownSpells::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateSelectedSpells.class, nextID())
                .encoder(PacketUpdateSelectedSpells::toBytes)
                .decoder(PacketUpdateSelectedSpells::new)
                .consumer(PacketUpdateSelectedSpells::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateSelectedSpellOnServer.class, nextID())
                .encoder(PacketUpdateSelectedSpellOnServer::toBytes)
                .decoder(PacketUpdateSelectedSpellOnServer::new)
                .consumer(PacketUpdateSelectedSpellOnServer::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdatePlayerTarget.class, nextID())
                .encoder(PacketUpdatePlayerTarget::toBytes)
                .decoder(PacketUpdatePlayerTarget::new)
                .consumer(PacketUpdatePlayerTarget::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdatePlayerTargetOnServer.class, nextID())
                .encoder(PacketUpdatePlayerTargetOnServer::toBytes)
                .decoder(PacketUpdatePlayerTargetOnServer::new)
                .consumer(PacketUpdatePlayerTargetOnServer::handle)
                .add();

        INSTANCE.messageBuilder(PacketCastSpell.class, nextID())
                .encoder(PacketCastSpell::toBytes)
                .decoder(PacketCastSpell::new)
                .consumer(PacketCastSpell::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateShoutCooldowns.class, nextID())
                .encoder(PacketUpdateShoutCooldowns::toBytes)
                .decoder(PacketUpdateShoutCooldowns::new)
                .consumer(PacketUpdateShoutCooldowns::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateShoutCooldownOnServer.class, nextID())
                .encoder(PacketUpdateShoutCooldownOnServer::toBytes)
                .decoder(PacketUpdateShoutCooldownOnServer::new)
                .consumer(PacketUpdateShoutCooldownOnServer::handle)
                .add();

        INSTANCE.messageBuilder(PacketAddToKnownSpells.class, nextID())
                .encoder(PacketAddToKnownSpells::toBytes)
                .decoder(PacketAddToKnownSpells::new)
                .consumer(PacketAddToKnownSpells::handle)
                .add();

        INSTANCE.messageBuilder(PacketRequestCapabilityUpdate.class, nextID())
                .encoder(PacketRequestCapabilityUpdate::toBytes)
                .decoder(PacketRequestCapabilityUpdate::new)
                .consumer(PacketRequestCapabilityUpdate::handle)
                .add();

        INSTANCE.messageBuilder(PacketAddToCompassFeatures.class, nextID())
                .encoder(PacketAddToCompassFeatures::toBytes)
                .decoder(PacketAddToCompassFeatures::new)
                .consumer(PacketAddToCompassFeatures::handle)
                .add();

        INSTANCE.messageBuilder(PacketAddToCompassFeaturesOnClient.class, nextID())
                .encoder(PacketAddToCompassFeaturesOnClient::toBytes)
                .decoder(PacketAddToCompassFeaturesOnClient::new)
                .consumer(PacketAddToCompassFeaturesOnClient::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateCompassFeatures.class, nextID())
                .encoder(PacketUpdateCompassFeatures::toBytes)
                .decoder(PacketUpdateCompassFeatures::new)
                .consumer(PacketUpdateCompassFeatures::handle)
                .add();

        INSTANCE.messageBuilder(PacketAddToTargetingEntities.class, nextID())
                .encoder(PacketAddToTargetingEntities::toBytes)
                .decoder(PacketAddToTargetingEntities::new)
                .consumer(PacketAddToTargetingEntities::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateTargetingEntities.class, nextID())
                .encoder(PacketUpdateTargetingEntities::toBytes)
                .decoder(PacketUpdateTargetingEntities::new)
                .consumer(PacketUpdateTargetingEntities::handle)
                .add();

        INSTANCE.messageBuilder(PacketAddXpToSkillOnServer.class, nextID())
                .encoder(PacketAddXpToSkillOnServer::toBytes)
                .decoder(PacketAddXpToSkillOnServer::new)
                .consumer(PacketAddXpToSkillOnServer::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateCharacter.class, nextID())
                .encoder(PacketUpdateCharacter::toBytes)
                .decoder(PacketUpdateCharacter::new)
                .consumer(PacketUpdateCharacter::handle)
                .add();

        INSTANCE.messageBuilder(PacketAddToLevelUpdates.class, nextID())
                .encoder(PacketAddToLevelUpdates::toBytes)
                .decoder(PacketAddToLevelUpdates::new)
                .consumer(PacketAddToLevelUpdates::handle)
                .add();

        INSTANCE.messageBuilder(PacketOpenCharacterCreationScreen.class, nextID())
                .encoder(PacketOpenCharacterCreationScreen::toBytes)
                .decoder(PacketOpenCharacterCreationScreen::new)
                .consumer(PacketOpenCharacterCreationScreen::handle)
                .add();

        INSTANCE.messageBuilder(PacketCreateCharacterOnServer.class, nextID())
                .encoder(PacketCreateCharacterOnServer::toBytes)
                .decoder(PacketCreateCharacterOnServer::new)
                .consumer(PacketCreateCharacterOnServer::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAllClients(Supplier<Chunk> chunk, Object packet) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(chunk), packet);
    }

//    public static void serverToClient(AbstractMessage message, PlayerEntity player) {
//        INSTANCE.sendTo(message, ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
//    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}