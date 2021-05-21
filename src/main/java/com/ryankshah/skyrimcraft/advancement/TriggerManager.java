package com.ryankshah.skyrimcraft.advancement;

import com.ryankshah.skyrimcraft.character.magic.ISpell;
import net.minecraft.advancements.CriteriaTriggers;

import java.util.HashMap;
import java.util.Map;

public class TriggerManager
{
    //public static final BaseTrigger SPELL_LEARNED = new BaseTrigger("spell_learned");

    public static final Map<ISpell, BaseTrigger> TRIGGERS = new HashMap<>();

    public static void init() {
        for(BaseTrigger trigger : TRIGGERS.values())
            CriteriaTriggers.register(trigger);
    }
}