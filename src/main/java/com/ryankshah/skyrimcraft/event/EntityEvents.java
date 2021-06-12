package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.block.ModBlocks;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.client.entity.ModEntityType;
import com.ryankshah.skyrimcraft.data.PickpocketLootTables;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.goal.UndeadFleeGoal;
import com.ryankshah.skyrimcraft.item.ModItems;
import com.ryankshah.skyrimcraft.item.SkyrimArmorItem;
import com.ryankshah.skyrimcraft.item.SkyrimTwoHandedWeapon;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.character.PacketAddToTargetingEntities;
import com.ryankshah.skyrimcraft.network.character.PacketUpdatePlayerTargetOnServer;
import com.ryankshah.skyrimcraft.network.skill.PacketAddXpToSkillOnServer;
import com.ryankshah.skyrimcraft.util.RandomTradeBuilder;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents
{
    private static List<EntityType<?>> pickPocketableEntities = StreamSupport.stream(PickpocketLootTables.getPickpocketableEntities().spliterator(), false).collect(Collectors.toList());

    @SubscribeEvent
    public static void onEntityHit(LivingHurtEvent event) {
        if(event.getSource().getEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getEntity();

            if (event.getEntityLiving() != null) {
                if (playerEntity.hasEffect(ModEffects.ETHEREAL.get()))
                    playerEntity.removeEffect(ModEffects.ETHEREAL.get());

                if (playerEntity.getMainHandItem().getItem() instanceof ShootableItem) {
                    Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.ARCHERY.getID(), (int)event.getAmount()));
                } else if(playerEntity.getMainHandItem().getItem() instanceof SwordItem) {
                    Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.ONE_HANDED.getID(), (int)event.getAmount()));
                } else if(playerEntity.getMainHandItem().getItem() instanceof SkyrimTwoHandedWeapon) {
                    Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.TWO_HANDED.getID(), (int)event.getAmount()));
                }

                playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent((cap) -> {
                    if (event.getEntityLiving().isAlive()) {
                        Networking.sendToServer(new PacketUpdatePlayerTargetOnServer(event.getEntityLiving()));
                    } else {
                        Networking.sendToServer(new PacketUpdatePlayerTargetOnServer((LivingEntity) null));
                    }
                });

            }
        } else if(event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if(playerEntity.isDamageSourceBlocked(event.getSource())) {
                Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.BLOCK.getID(), SkillRegistry.BASE_BLOCK_XP));
            }

            if(playerEntity.getArmorValue() > 0) {
                // minecraft armors will default to light armor for now.
                // TODO: Check all the slots and check the type of armor (perhaps leather, iron and gold will be
                //       classed as "light armor" with diamond and netherite as the heavy armors for default mc.
                //       All other mod armors outside of skyrim will be classed as light armor. Perhaps instead,
                //       there may be a different way we can define these...
                AtomicInteger heavySlots = new AtomicInteger();
                for (Iterator<ItemStack> it = playerEntity.getArmorSlots().iterator(); it.hasNext(); ) {
                    ItemStack itemStack = it.next();
                    if(itemStack.getItem() instanceof SkyrimArmorItem && ((SkyrimArmorItem)itemStack.getItem()).isHeavy())
                        heavySlots.set(heavySlots.get() + 1);
                }

                if(heavySlots.get() >= 3)
                    Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.HEAVY_ARMOR.getID(), (int)(playerEntity.getArmorValue() * playerEntity.getArmorCoverPercentage())));
                else
                    Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.LIGHT_ARMOR.getID(), (int)(playerEntity.getArmorValue() * playerEntity.getArmorCoverPercentage())));
            }
        }
    }

    @SubscribeEvent
    public static void entitySetAttackTarget(LivingSetAttackTargetEvent event) {
        if(event.getTarget() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getTarget();
            ISkyrimPlayerData cap = player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("set attack target event"));

            if(!cap.getTargetingEntities().contains(event.getEntityLiving().getId()) //&& cap.getTargetingEntities().size() <= 10
                    && event.getEntityLiving().isAlive()) {
                Networking.sendToServer(new PacketAddToTargetingEntities(event.getEntityLiving().getId()));
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
        if(pickPocketableEntities.contains(event.getEntity().getType())) {
            event.getEntity().addTag(ModEntityType.PICKPOCKET_TAG);
        }
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
    }
}