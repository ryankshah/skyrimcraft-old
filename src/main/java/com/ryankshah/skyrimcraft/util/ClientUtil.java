package com.ryankshah.skyrimcraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientUtil
{
    public static World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}