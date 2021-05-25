package com.ryankshah.skyrimcraft.network.skill;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import com.ryankshah.skyrimcraft.util.ModEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class PacketHandlePickpocketOnServer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private LivingEntity entity;

    public PacketHandlePickpocketOnServer(PacketBuffer buf) {
        entity = (LivingEntity) ClientUtil.getClientWorld().getEntity(buf.readInt());
    }

    public PacketHandlePickpocketOnServer(LivingEntity entity) {
        this.entity = entity;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(entity.getId());
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PacketHandlePickpocketOnServer received on wrong side:" + sideReceived);
            return false;
        }

        // we know for sure that this handler is only used on the server side, so it is ok to assume
        //  that the ctx handler is a serverhandler, and that ServerPlayerEntity exists
        // Packets received on the client side must be handled differently!  See MessageHandlerOnClient

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("ServerPlayerEntity was null when PacketHandlePickpocketOnServer was received");
            return false;
        }

        context.enqueueWork(() -> {
            sendingPlayer.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                LootTable loottable = sendingPlayer.level.getServer().getLootTables().get(new ResourceLocation(entity.getType().getDefaultLootTable().toString().replace("minecraft", Skyrimcraft.MODID).replace("entities", "pickpocket")));
                LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)sendingPlayer.level)).withParameter(LootParameters.ORIGIN, sendingPlayer.position()).withParameter(LootParameters.THIS_ENTITY, sendingPlayer).withRandom(sendingPlayer.getRandom());
                List<ItemStack> items = loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.SELECTOR));

                // If failed at pickpocketing, slightly hurt and knockback player, otherwise give player items and notify
                if(items.isEmpty()) {
                    sendingPlayer.hurt(DamageSource.mobAttack(entity), 0.5f);
                    sendingPlayer.knockback(0.5F, (double) -MathHelper.sin(sendingPlayer.yRot * ((float)Math.PI / 180F)), (double)(MathHelper.cos(sendingPlayer.yRot * ((float)Math.PI / 180F))));
                    sendingPlayer.displayClientMessage(new TranslationTextComponent("skill.pickpocket.fail", entity.getDisplayName()), false);
                } else {
                    items.forEach(sendingPlayer::addItem);
                    sendingPlayer.displayClientMessage(new TranslationTextComponent("skill.pickpocket.success", entity.getDisplayName()), false);
                    entity.removeTag(ModEntityType.PICKPOCKET_TAG);
                    cap.addXpToSkill(SkillRegistry.PICKPOCKET.getID(), SkillRegistry.BASE_PICKPOCKET_XP, sendingPlayer);
                }
            });
        });
        return true;
    }

    protected LootContext.Builder createLootContext(ServerPlayerEntity sendingPlayer) {
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) sendingPlayer.level)).withRandom(sendingPlayer.getRandom()).withParameter(LootParameters.THIS_ENTITY, entity).withParameter(LootParameters.ORIGIN, sendingPlayer.position());
        return lootcontext$builder;
    }

}