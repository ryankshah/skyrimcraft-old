package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.network.*;
import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyrimPlayerData implements ISkyrimPlayerData
{
    private PlayerEntity playerEntity;

    private List<ISpell> knownSpells;
    private Map<Integer, ISpell> selectedSpells;
    private LivingEntity targetEntity;

    private float magicka = 20.0f;
    private final float maxMagicka = 20.0f;

    public SkyrimPlayerData() {
        knownSpells = new ArrayList<>();
        selectedSpells = new HashMap<>();
        targetEntity = null;
    }

    public SkyrimPlayerData(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        knownSpells = new ArrayList<>();
        selectedSpells = new HashMap<>();
        targetEntity = null;
    }

    @Override
    public void addToKnownSpells(ISpell spell) {
        if(!knownSpells.contains(spell)) {
            knownSpells.add(spell);
            setKnownSpells(knownSpells);
        }
    }

    @Override
    public void setSelectedSpell(int pos, ISpell spell) {
        // only allow 2 selected spells at a time.
        if(pos >= 0 && pos < 2) {
            selectedSpells.put(pos, spell);
            setSelectedSpells(selectedSpells);
        }
    }

    @Override
    public void setKnownSpells(List<ISpell> knownSpells) {
        this.knownSpells = knownSpells;

        if(playerEntity instanceof ServerPlayerEntity)
            Networking.sendToClient(new PacketUpdateKnownSpells(this.knownSpells), (ServerPlayerEntity)playerEntity);
    }

    @Override
    public void setKnownSpellsForNBT(List<ISpell> knownSpells) {
        this.knownSpells = knownSpells;
    }

    @Override
    public void setSelectedSpells(Map<Integer, ISpell> selectedSpells) {
        this.selectedSpells = selectedSpells;

        if(playerEntity instanceof ServerPlayerEntity)
            Networking.sendToClient(new PacketUpdateSelectedSpells(this.selectedSpells), (ServerPlayerEntity)playerEntity);
    }

    @Override
    public void setSelectedSpellsForNBT(Map<Integer, ISpell> selectedSpells) {
        this.selectedSpells = selectedSpells;
    }

    @Override
    public List<ISpell> getKnownSpells() {
        return this.knownSpells;
    }

    @Override
    public Map<Integer, ISpell> getSelectedSpells() {
        return this.selectedSpells;
    }

    @Override
    public void consumeMagicka(float amount) {
        setMagicka(magicka - amount);
    }

    @Override
    public void replenishMagicka(float amount) {
        setMagicka(magicka + amount);
    }

    @Override
    public void setMagicka(float amount) {
        this.magicka = amount;

        if(this.magicka <= 0.0f)
            this.magicka = 0.0f;
        if(this.magicka >= maxMagicka)
            this.magicka = maxMagicka;

        if(playerEntity instanceof ServerPlayerEntity)
            Networking.sendToClient(new PacketUpdateMagicka(magicka), (ServerPlayerEntity)playerEntity);
    }

    @Override
    public void setMagickaForNBT(float amount) {
        this.magicka = amount;
    }

    @Override
    public float getMagicka() {
        return this.magicka;
    }

    @Override
    public float getMaxMagicka() {
        return this.maxMagicka;
    }

    @Override
    public void setCurrentTarget(LivingEntity entity) {
        this.targetEntity = entity;

        if(playerEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)playerEntity;
            Networking.sendToClient(new PacketUpdatePlayerTarget(targetEntity), serverPlayerEntity);
        }
    }

    @Override
    public void setCurrentTargetForNBT(LivingEntity entity) {
        this.targetEntity = entity;
    }

    @Override
    public LivingEntity getCurrentTarget() {
        return this.targetEntity;
    }
}