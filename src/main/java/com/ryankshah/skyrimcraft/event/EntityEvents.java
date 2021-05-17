package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.goal.UndeadFleeGoal;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketAddToTargetingEntities;
import com.ryankshah.skyrimcraft.network.character.PacketRemoveTargetingEntity;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModItems;
import com.ryankshah.skyrimcraft.util.RandomTradeBuilder;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents
{
    @SubscribeEvent
    public static void entitySetAttackTarget(LivingSetAttackTargetEvent event) {
        if(event.getTarget() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getTarget();
            ISkyrimPlayerData cap = player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("set attack target event"));

            if (event.getEntityLiving() == null || !event.getEntityLiving().isAlive()) {
                Networking.sendToServer(new PacketRemoveTargetingEntity(event.getEntityLiving().getId()));
            } else {
                // Check the entity is not already in the target list
                if(!cap.getTargetingEntities().contains(event.getEntityLiving().getId()) //&& cap.getTargetingEntities().size() <= 10
                        && event.getEntityLiving().isAlive()) {
                    Networking.sendToServer(new PacketAddToTargetingEntities(event.getEntityLiving().getId()));
                }
            }
        }
    }

    // TODO: Check if the player has killed the entity, and if so remove it from the cap targeting entities list.
    @SubscribeEvent
    public static void entityDeath(LivingDeathEvent event) {
    }

    /**
     * Used to add panic AI task for undead mobs (for UndeadFleeEffect to activate)
     */
    @SubscribeEvent
    public static void entityJoin(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof MonsterEntity) {
            MonsterEntity monsterEntity = (MonsterEntity) event.getEntity();
            if(monsterEntity.getMobType() == CreatureAttribute.UNDEAD) {
                monsterEntity.goalSelector.addGoal(0, new UndeadFleeGoal(monsterEntity, 16.0F, 0.8D, 1.33D));
            }
        }
    }

    /**
     * Used to add mod items, etc. to existing villager trades
     */
    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.LIBRARIAN) {
            event.getTrades().get(3).add(new RandomTradeBuilder(1, 10, 0.125F).setEmeraldPrice(18, 30).setForSale(ModItems.FIREBALL_SPELLBOOK.get(), 1, 1).build());
            event.getTrades().get(3).add(new RandomTradeBuilder(1, 10, 0.125F).setEmeraldPrice(18, 30).setForSale(ModItems.TURN_UNDEAD_SPELLBOOK.get(), 1, 1).build());
        }
        if(event.getType() == VillagerProfession.CLERIC) {
            event.getTrades().get(1).add(new RandomTradeBuilder(8, 4, 0.125F).setEmeraldPrice(2, 4).setForSale(ModItems.MINOR_MAGICKA_POTION.get(), 1, 3).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(5, 4, 0.125F).setEmeraldPrice(3, 6).setForSale(ModItems.MAGICKA_POTION.get(), 1, 2).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(3, 6, 0.2F).setEmeraldPrice(4, 7).setForSale(ModItems.PLENTIFUL_MAGICKA_POTION.get(), 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(2, 6, 0.2F).setEmeraldPrice(8, 12).setForSale(ModItems.VIGOROUS_MAGICKA_POTION.get(), 1, 1).build());
            event.getTrades().get(3).add(new RandomTradeBuilder(1, 8, 0.325F).setEmeraldPrice(14, 20).setForSale(ModItems.VIGOROUS_MAGICKA_POTION.get(), 1, 1).build());
            event.getTrades().get(3).add(new RandomTradeBuilder(1, 10, 0.4F).setEmeraldPrice(26, 45).setForSale(ModItems.VIGOROUS_MAGICKA_POTION.get(), 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(1, 10, 0.7F).setEmeraldPrice(22, 45).setForSale(ModItems.PHILTER_OF_THE_PHANTOM_POTION.get(), 1, 1).build());

            // Ingredients
            event.getTrades().get(1).add(new RandomTradeBuilder(3, 4, 0.175F).setEmeraldPrice(5, 11).setForSale(ModItems.CREEP_CLUSTER.get(), 1, 2).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(8, 4, 0.175F).setEmeraldPrice(4, 7).setForSale(ModItems.BRIAR_HEART.get(), 1, 2).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(3, 6, 0.275F).setEmeraldPrice(13, 25).setForSale(ModItems.VAMPIRE_DUST.get(), 1, 2).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(3, 6, 0.275F).setEmeraldPrice(6, 12).setForSale(ModItems.MORA_TAPINELLA.get(), 1, 2).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(3, 6, 0.275F).setEmeraldPrice(11, 25).setForSale(ModItems.DWARVEN_OIL.get(), 1, 2).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(3, 6, 0.275F).setEmeraldPrice(14, 32).setForSale(ModItems.FIRE_SALTS.get(), 1, 2).build());
            event.getTrades().get(3).add(new RandomTradeBuilder(1, 10, 0.675F).setEmeraldPrice(26, 45).setForSale(ModItems.GIANTS_TOE.get(), 1, 1).build());
        }
        if(event.getType() == VillagerProfession.FARMER) {
            event.getTrades().get(1).add(new RandomTradeBuilder(8, 3, 0.125F).setEmeraldPrice(1, 4).setForSale(ModBlocks.TOMATO_SEEDS.get(), 1, 3).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(8, 3, 0.125F).setEmeraldPrice(2, 5).setForSale(ModBlocks.GARLIC.get(), 1, 2).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(4, 5, 0.2F).setEmeraldPrice(3, 6).setForSale(ModItems.GARLIC_BREAD.get(), 1, 1).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(4, 3, 0.2F).setEmeraldPrice(2, 5).setForSale(ModItems.POTATO_BREAD.get(), 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(3, 6, 0.275F).setEmeraldPrice(6, 12).setForSale(ModItems.MORA_TAPINELLA.get(), 1, 2).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(3, 4, 0.175F).setEmeraldPrice(5, 11).setForSale(ModItems.CREEP_CLUSTER.get(), 1, 2).build());
            event.getTrades().get(1).add(new RandomTradeBuilder(8, 4, 0.175F).setEmeraldPrice(4, 7).setForSale(ModItems.BRIAR_HEART.get(), 1, 2).build());
        }
        if(event.getType() == VillagerProfession.LEATHERWORKER) {
            event.getTrades().get(2).add(new RandomTradeBuilder(4, 4, 0.125F).setEmeraldPrice(4, 6).setForSale(ModItems.LEATHER_STRIPS.get(), 1, 2).build());
        }

        // Add seeds (and some foods? i.e. breads?) to farmer
    }
}