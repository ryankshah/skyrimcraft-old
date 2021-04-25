package com.ryankshah.skyrimcraft.client.gui;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.lighting.SkyLightStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class NPCShopGui extends Screen
{
    private static final int PADDING = 7;
    private Multimap<String, ArrayList<ItemStack>> itemsAndCategories;
    private Object[] categories;

    private float spin;
    private boolean categoryChosen;
    private int currentCategory;
    private int currentItem;

    public NPCShopGui() {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".shopgui.title"));
        this.itemsAndCategories = ArrayListMultimap.create();
        this.categories = itemsAndCategories.keySet().toArray();
        this.spin = 0.0f;
        this.categoryChosen = false;
        this.currentCategory = 0;
        this.currentItem = 0;

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);

        if(!this.categoryChosen) {
            fillGradient(matrixStack, 10, 0, 80, this.height - 2, 0xAA000000, 0xAA555555);
            fillGradient(matrixStack, 12, 2, 13, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, 77, 2, 78, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, 90, 0, 200, this.height, 0xAA000000, 0xAA000000);
            fillGradient(matrixStack, 197, 2, 198, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, 92, 2, 93, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
        } else {
            fillGradient(matrixStack, 10, 0, 80, this.height - 2, 0xAA000000, 0xAA000000);
            fillGradient(matrixStack, 12, 2, 13, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, 77, 2, 78, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, 90, 0, 200, this.height, 0xAA000000, 0xAA555555);
            fillGradient(matrixStack, 197, 2, 198, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, 92, 2, 93, this.height - 2, 0xAAFFFFFF, 0xAAFFFFFF);
        }

        drawItemInformation(matrixStack);
        drawItemImage(matrixStack, new ItemStack(Items.ELYTRA), this.width - 100, (this.height + 50) / 2, spin);


        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        if(this.spin > 360.0f)
            this.spin -= 360.0f;
        else
            ++this.spin;

        super.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean handleComponentClicked(@Nullable Style style) {
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return true;
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    private void drawItemImage(MatrixStack matrixStack, ItemStack is, int xPos, int yPos, float spin) {
        matrixStack.push();
        matrixStack.rotate(new Quaternion(45.0F, 0.0F, 1.0F, 0.0F));
        matrixStack.rotate(new Quaternion(15.0F, 0.0F, 0.0F, 1.0F));
        matrixStack.rotate(new Quaternion(195.0F, 1.0F, 0.0F, 0.0F));
        matrixStack.rotate(new Quaternion(spin % 360.0F, 0.0F, 1.0F, 0.0F)); // spin++
        matrixStack.scale(0.45F, 0.45f, 0.45f);
        RenderHelper.enableStandardItemLighting();
        minecraft.getItemRenderer().zLevel = 200.0f;
        minecraft.getItemRenderer().renderItem(minecraft.player, is, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, minecraft.getRenderTypeBuffers().getBufferSource(), minecraft.player.getEntityWorld(), 0, 0);
        matrixStack.pop();
        minecraft.getItemRenderer().zLevel = 0.0f;
    }

    private void drawItemInformation(MatrixStack matrixStack) {
        fillGradient(matrixStack, this.width - 180, (this.height + 50) / 2, this.width - 20, (this.height + 50) / 2 + 80, 0xAA000000, 0xAA000000);
    }

    private void categorisePlayerInventory() {
        NonNullList<ItemStack> mainInventory = minecraft.player.inventory.mainInventory;
        NonNullList<ItemStack> offHandInventory = minecraft.player.inventory.offHandInventory;
        NonNullList<ItemStack> armorInventory = minecraft.player.inventory.armorInventory;

        for(ItemStack stack : mainInventory) {
            //this.itemsAndCategories.put(stack.get)
        }
    }

    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new NPCShopGui());
    }
}