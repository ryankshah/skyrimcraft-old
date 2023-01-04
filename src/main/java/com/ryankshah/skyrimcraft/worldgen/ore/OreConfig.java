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

        MALACHITE_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("malachiteVeinSize", 5, 1, 5);
        MALACHITE_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("malachiteAmount", 3, 1, 3);
        MALACHITE_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("malachiteDeepslateVeinSize", 5, 1, 5);
        MALACHITE_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("malachiteDeepslateAmount", 3, 1, 3);

        MOONSTONE_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("moonstoneVeinSize", 5, 1, 5);
        MOONSTONE_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("moonstoneAmount", 3, 1, 3);
        MOONSTONE_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("moonstoneDeepslateVeinSize", 5, 1, 5);
        MOONSTONE_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("moonstoneDeepslateAmount", 3, 1, 3);

        ORICHALCUM_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("orichalcumVeinSize", 5, 1, 5);
        ORICHALCUM_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("orichalcumAmount", 3, 1, 3);
        ORICHALCUM_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("orichalcumDeepslateVeinSize", 5, 1, 5);
        ORICHALCUM_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("orichalcumDeepslateAmount", 3, 1, 3);

        CORUNDUM_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("corundumVeinSize", 5, 1, 5);
        CORUNDUM_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("corundumAmount", 3, 1, 3);
        CORUNDUM_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("corundumDeepslateVeinSize", 5, 1, 5);
        CORUNDUM_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("corundumDeepslateAmount", 3, 1, 3);

        QUICKSILVER_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("quicksilverVeinSize", 5, 1, 5);
        QUICKSILVER_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("quicksilverAmount", 3, 1, 3);
        QUICKSILVER_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("quicksilverDeepslateVeinSize", 5, 1, 5);
        QUICKSILVER_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("quicksilverDeepslateAmount", 3, 1, 3);

        SILVER_ORE_OVERWORLD_VEINSIZE = COMMON_BUILDER.defineInRange("silverVeinSize", 5, 1, 5);
        SILVER_ORE_OVERWORLD_AMOUNT = COMMON_BUILDER.defineInRange("silverAmount", 3, 1, 3);
        SILVER_ORE_DEEPSLATE_VEINSIZE = COMMON_BUILDER.defineInRange("silverDeepslateVeinSize", 5, 1, 5);
        SILVER_ORE_DEEPSLATE_AMOUNT = COMMON_BUILDER.defineInRange("silverDeepslateAmount", 3, 1, 3);

        COMMON_BUILDER.pop();
    }
}