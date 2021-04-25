package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketUpdateMagicka;
import net.minecraft.client.Minecraft;

public class Magicka implements IMagicka
{
    private float magicka = 20.0f;
    private final float maxMagicka = 20.0f;

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

        Networking.sendToClient(new PacketUpdateMagicka(magicka), Minecraft.getInstance().player);
    }

    @Override
    public float get() {
        return this.magicka;
    }

    @Override
    public float getMaxMagicka() {
        return this.maxMagicka;
    }
}