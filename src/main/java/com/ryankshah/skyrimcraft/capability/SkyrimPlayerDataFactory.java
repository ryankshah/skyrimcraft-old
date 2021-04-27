package com.ryankshah.skyrimcraft.capability;

import java.util.concurrent.Callable;

public class SkyrimPlayerDataFactory implements Callable<ISkyrimPlayerData>
{
    @Override
    public ISkyrimPlayerData call() throws Exception {
        return new SkyrimPlayerData();
    }
}