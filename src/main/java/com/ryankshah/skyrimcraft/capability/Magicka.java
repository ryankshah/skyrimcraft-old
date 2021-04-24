package com.ryankshah.skyrimcraft.capability;

public class Magicka implements IMagicka
{
    private float magicka = 20.0f;
    private final float maxMagicka = 20.0f;

    @Override
    public void consume(float amount) {
        this.magicka -= amount;

        if(this.magicka < 0.0f)
            this.magicka = 0.0f;
    }

    @Override
    public void replenish(float amount) {
        this.magicka += amount;

        if(this.magicka > maxMagicka)
            this.magicka = maxMagicka;
    }

    @Override
    public void set(float amount) {
        this.magicka = amount;
    }

    @Override
    public float get() {
        return this.magicka;
    }
}