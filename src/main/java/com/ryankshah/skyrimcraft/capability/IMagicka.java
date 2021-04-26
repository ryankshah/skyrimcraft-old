package com.ryankshah.skyrimcraft.capability;

public interface IMagicka {
    public void consume(float amount);
    public void replenish(float amount);
    public void set(float amount);

    public float getMagicka();
    public float getMaxMagicka();
}