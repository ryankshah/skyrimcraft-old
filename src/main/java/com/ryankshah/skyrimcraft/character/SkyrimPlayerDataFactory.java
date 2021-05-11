package com.ryankshah.skyrimcraft.character;

import java.util.concurrent.Callable;

public class SkyrimPlayerDataFactory implements Callable<ISkyrimPlayerData>
{
    @Override
    public ISkyrimPlayerData call() throws Exception {
        return new SkyrimPlayerData();
    }
}