package com.ryankshah.skyrimcraft.worldgen.ore;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreConfig
{
    public static ForgeConfigSpec.IntValue EBONY_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue EBONY_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue EBONY_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue EBONY_ORE_DEEPSLATE_AMOUNT;

    public static ForgeConfigSpec.IntValue MALACHITE_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue MALACHITE_ORE_DEEPSLATE_AMOUNT;

    public static ForgeConfigSpec.IntValue MOONSTONE_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue MOONSTONE_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue MOONSTONE_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue MOONSTONE_ORE_DEEPSLATE_AMOUNT;

    public static ForgeConfigSpec.IntValue ORICHALCUM_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue ORICHALCUM_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue ORICHALCUM_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue ORICHALCUM_ORE_DEEPSLATE_AMOUNT;

    public static ForgeConfigSpec.IntValue CORUNDUM_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue CORUNDUM_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue CORUNDUM_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue CORUNDUM_ORE_DEEPSLATE_AMOUNT;

    public static ForgeConfigSpec.IntValue QUICKSILVER_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue QUICKSILVER_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue QUICKSILVER_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue QUICKSILVER_ORE_DEEPSLATE_AMOUNT;

    public static ForgeConfigSpec.IntValue SILVER_ORE_OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue SILVER_ORE_OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue SILVER_ORE_DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue SILVER_ORE_DEEPSLATE_AMOUNT;

    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("Settings for ore generation").push("ores");

        EBONY_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("ebonyVeinSize", 5, 1, 5);
        EBONY_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("ebonyAmount", 3, 1, 3);
        EBONY_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("ebonyDeepslateVeinSize", 5, 1, 5);
        EBONY_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("ebonyDeepslateAmount", 3, 1, 3);

        COMMON_BUILDER.pop();
    }
}