package com.ryankshah.skyrimcraft.client.gui;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GuiHandler
{
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Skyrimcraft.MODID);

    public static final RegistryObject<ContainerType<MerchantContainer>> SKYRIM_MERCHANT_CONTAINER = CONTAINERS.register("skyrim_merchant_container", () ->
            IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) -> {
                return new MerchantContainer(windowId, inv);
            })
    );
}