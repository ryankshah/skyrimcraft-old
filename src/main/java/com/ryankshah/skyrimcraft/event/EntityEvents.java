package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.goal.UndeadFleeGoal;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyrimcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents
{
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
}