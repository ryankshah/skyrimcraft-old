package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketUpdateMagicka;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class Magicka implements IMagicka
{
    private float magicka = 20.0f;
    private final float maxMagicka = 20.0f;
    private PlayerEntity playerEntity;

    public Magicka() {}

    public Magicka(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    @Override
    public void consume(float amount) {
        set(magicka - amount);
//        this.magicka -= amount;
//
//        if(this.magicka <= 0.0f)
//            this.magicka = 0.0f;
    }

    @Override
    public void replenish(float amount) {
        set(magicka + amount);
//        this.magicka += amount;
//
//        if(this.magicka >= maxMagicka)
//            this.magicka = maxMagicka;
    }

    @Override
    public void set(float amount) {
        this.magicka = amount;

        if(this.magicka <= 0.0f)
            this.magicka = 0.0f;
        if(this.magicka >= maxMagicka)
            this.magicka = maxMagicka;

        if(playerEntity instanceof ServerPlayerEntity)
            Networking.sendToClient(new PacketUpdateMagicka(magicka), (ServerPlayerEntity)playerEntity);
    }

    @Override
    public float getMagicka() {
        return this.magicka;
    }

    @Override
    public float getMaxMagicka() {
        return this.maxMagicka;
    }
}