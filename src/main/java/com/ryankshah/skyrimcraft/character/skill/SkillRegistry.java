package com.ryankshah.skyrimcraft.character.skill;

import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class SkillRegistry
{
    public static final DeferredRegister<ISkill> SKILLS = DeferredRegister.create(ISkill.class, Skyrimcraft.MODID);
    public static final Supplier<IForgeRegistry<ISkill>> SKILLS_REGISTRY = SKILLS.makeRegistry(Skyrimcraft.MODID + "skills", RegistryBuilder::new);

    private static int id = 0;

    public static final int BASE_ARCHERY_XP = 4;
    public static final int BASE_BLOCK_XP = 4;

    public static RegistryObject<ISkill> ALTERATION = SKILLS.register("alteration", () -> new ISkill(id++, "Alteration", 15, 3f, 0, 2f, 0));
    public static RegistryObject<ISkill> CONJURATION = SKILLS.register("conjuration", () -> new ISkill(id++, "Conjuration", 15, 2.1f, 0, 2f, 0));
    public static RegistryObject<ISkill> DESTRUCTION = SKILLS.register("destruction", () -> new ISkill(id++, "Destruction", 15, 1.35f, 0, 2f, 0));
    public static RegistryObject<ISkill> ILLUSION = SKILLS.register("illusion", () -> new ISkill(id++, "Illusion", 15, 4.6f, 0, 2f, 0));
    public static RegistryObject<ISkill> RESTORATION = SKILLS.register("restoration", () -> new ISkill(id++, "Restoration", 15, 2.1f, 0, 2f, 0));
    public static RegistryObject<ISkill> ENCHANTING = SKILLS.register("enchanting", () -> new ISkill(id++, "Enchanting", 15, 900f, 0, 1f, 170));
    public static RegistryObject<ISkill> ONE_HANDED = SKILLS.register("one_handed", () -> new ISkill(id++, "One-Handed", 15, 6.3f, 0, 2f, 0));
    public static RegistryObject<ISkill> TWO_HANDED = SKILLS.register("two_handed", () -> new ISkill(id++, "Two-Handed", 15, 5.95f, 0, 2f, 0));
    public static RegistryObject<ISkill> ARCHERY = SKILLS.register("archery", () -> new ISkill(id++, "Archery", 15, 9.3f, 0, 2f, 0));
    public static RegistryObject<ISkill> BLOCK = SKILLS.register("block", () -> new ISkill(id++, "Block", 15, 8.1f, 0, 2f, 0));
    public static RegistryObject<ISkill> SMITHING = SKILLS.register("smithing", () -> new ISkill(id++, "Smithing", 15, 1f, 0, 0.25f, 300));
    public static RegistryObject<ISkill> HEAVY_ARMOR = SKILLS.register("heavy_armor", () -> new ISkill(id++, "Heavy Armor", 15, 3.8f, 0, 2f, 0));
    public static RegistryObject<ISkill> LIGHT_ARMOR = SKILLS.register("light_armor", () -> new ISkill(id++, "Light Armor", 15, 4f, 0, 2f, 0));
    public static RegistryObject<ISkill> PICKPOCKET = SKILLS.register("pickpocket", () -> new ISkill(id++, "Pickpocket", 15, 8.1f, 0, 0.25f, 250));
    public static RegistryObject<ISkill> LOCKPICKING = SKILLS.register("lockpicking", () -> new ISkill(id++, "Lockpicking", 15, 45f, 10, 0.25f, 300));
    public static RegistryObject<ISkill> SNEAK = SKILLS.register("sneak", () -> new ISkill(id++, "Sneak", 15, 11.25f, 0, 0.5f, 120));
    public static RegistryObject<ISkill> ALCHEMY = SKILLS.register("alchemy", () -> new ISkill(id++, "Alchemy", 15, 0.75f, 0, 1.6f, 65));
    public static RegistryObject<ISkill> SPEECH = SKILLS.register("speech", () -> new ISkill(id++, "Speech", 15, 0.36f, 0, 2f, 0));
}