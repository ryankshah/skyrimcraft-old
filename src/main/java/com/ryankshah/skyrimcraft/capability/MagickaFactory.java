package com.ryankshah.skyrimcraft.capability;

import java.util.concurrent.Callable;

public class MagickaFactory implements Callable<IMagicka>
{
    @Override
    public IMagicka call() throws Exception {
        return new Magicka();
    }
}