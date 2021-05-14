package com.ryankshah.skyrimcraft.client.gui;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.util.ModBlocks;
import com.ryankshah.skyrimcraft.util.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class SkyrimAlchemyGui extends Screen
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

    private Multimap<String, OvenRecipe> items;
    private ArrayList<OvenRecipe> itemList;
    private Object[] categories;
    private boolean categoryChosen;
    private int currentCategory;
    private int currentItem;
    private float spin = 0.0F;
    private OvenRecipe currentRecipeObject = null;
    private PlayerEntity player;

    public SkyrimAlchemyGui() {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".alchemygui.title"));
        this.player = Minecraft.getInstance().player;
        this.items = ArrayListMultimap.create();
        this.addItems();
        this.categories = this.items.keySet().toArray();
        this.currentCategory = 0;
        this.currentItem = 0;
        this.itemList = new ArrayList();
        this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
        this.itemList.sort((i1, i2) -> i1.getItemStack().getItem().getRegistryName().compareTo(i2.getItemStack().getItem().getRegistryName()));
        categoryChosen = true;
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

        if (!this.items.isEmpty()) {
            Object[] categories = this.getCategories(this.items);

            int i;
            for(i = Math.max(this.currentCategory - 6, 0); i < (Math.min(this.currentCategory + 6, categories.length)); ++i) {
                if (i == this.currentCategory) {
                    drawString(matrixStack, font, ((String)categories[i]).toUpperCase(), 18, height / 2 + 14 * i - this.currentCategory * 7, 16777215);
                } else {
                    drawString(matrixStack, font, ((String)categories[i]).toUpperCase(), 18, height / 2 + 14 * i - this.currentCategory * 7, 12632256);
                }
            }

            if (this.itemList != null) {
                for(i = Math.max(this.currentItem - 6, 0); i < (Math.min(this.currentItem + 6, this.itemList.size())); ++i) {
                    OvenRecipe recipe = (OvenRecipe)this.itemList.get(i);

                    if (i == this.currentItem) {
                        this.currentRecipeObject = recipe;
                        drawString(matrixStack, font, recipe.getItemStack().getHoverName(), 98, height / 2 + 14 * i - this.currentItem * 7, 16777215);
                        this.drawItemImage(matrixStack, recipe.getItemStack(), width - 100, height / 2 - 70, this.spin);
                        this.drawItemInformation(matrixStack, recipe);
                    } else {
                        drawString(matrixStack, font, recipe.getItemStack().getHoverName(), 98, height / 2 + 14 * i - this.currentItem * 7, 12632256);
                    }
                }
            }
        }

        fillGradient(matrixStack, 0, this.height * 3 / 4 + 20, this.width, this.height, 0x77000000, 0x77000000);
        fillGradient(matrixStack, 0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xAAFFFFFF, 0xAAFFFFFF);
//        drawBorderedGradientRect(matrixStack, 17, this.height - 29, 32, this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
//        drawCenteredString(matrixStack, font, "Enter", 25, this.height - 25, 0x0000FF00);
//        drawCenteredString(matrixStack, font, "Select", 70, this.height - 25, 0x00FFFFFF);

        renderHealth(matrixStack);


        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.spin > 360.0f)
            this.spin -= 360.0f;
        else
            this.spin += 2;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW_KEY_DOWN || keyCode == GLFW_KEY_S) {
            if (!this.categoryChosen) {
                if (this.currentCategory < this.categories.length - 1) {
                    ++this.currentCategory;
                } else {
                    this.currentCategory = this.categories.length - 1;
                }

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else if (this.currentItem < this.itemList.size() - 1) {
                ++this.currentItem;
            } else {
                this.currentItem = this.itemList.size() - 1;
            }
        }

        if(keyCode == GLFW_KEY_UP || keyCode == GLFW_KEY_W) {
            if (!this.categoryChosen) {
                if (this.currentCategory > 0) {
                    --this.currentCategory;
                } else {
                    this.currentCategory = 0;
                }

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else if (this.currentItem > 0) {
                --this.currentItem;
            } else {
                this.currentItem = 0;
            }
        }

        if(keyCode == GLFW_KEY_LEFT || keyCode == GLFW_KEY_A) {
            if(this.categoryChosen) {
                this.categoryChosen = false;
                this.currentItem = 0;
            }
        }

        if(keyCode == GLFW_KEY_RIGHT || keyCode == GLFW_KEY_D) {
            if(!this.categoryChosen) {
                this.categoryChosen = true;
                this.currentItem = 0;
            }
        }

        if(keyCode == GLFW_KEY_ENTER) {
            if (!this.categoryChosen) {
                return false;
            }

            List<ItemStack> recipe = this.currentRecipeObject.getRecipeItems();
            boolean hasAllItems = recipe.stream().allMatch(itemstack -> hasItem(player, itemstack, itemstack.getCount()));

            if(!hasAllItems) {
                this.player.displayClientMessage(new StringTextComponent("[Skyrimcraft] - You don't have the required items!"), false);
                return false;
            }

            for(ItemStack is : currentRecipeObject.getRecipeItems()) {
                ItemStack copy = is.copy();
                removeItem(player, copy, copy.getCount());
            }

            this.player.addItem(this.currentRecipeObject.getItemStack().copy());
            this.player.giveExperiencePoints(2);
        }

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
    public void removed() {
        super.removed();
    }

    private void drawItemImage(MatrixStack matrixStack, ItemStack is, int xPos, int yPos, float spin) {
        matrixStack.pushPose();
        minecraft.getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        minecraft.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.translate(xPos, yPos,100F); //300.0F
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(spin++ % 360));
        matrixStack.scale(60F, 60F, 60F);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        this.itemRenderer.render(is, ItemCameraTransforms.TransformType.GUI, false, matrixStack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, this.itemRenderer.getModel(is, null, null));
        irendertypebuffer$impl.endBatch();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        matrixStack.popPose();
    }

    private void drawItemInformation(MatrixStack matrixStack, OvenRecipe recipe) {
        //fillGradient(matrixStack, this.width - 180, (this.height + 50) / 2 - 20, this.width - 20, (this.height + 50) / 2 + 80, 0xAA000000, 0xAA000000);
        drawBorderedGradientRect(matrixStack, this.width - 180, this.height / 2 - 20, this.width - 20, this.height / 2 + 20 + (10 * recipe.getRecipeItems().size()), 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        fillGradient(matrixStack, this.width - 160, (this.height) / 2, this.width - 40, (this.height) / 2 + 1, 0xAAFFFFFF, 0xAAFFFFFF); // Line under recipe item name

        drawCenteredString(matrixStack, font, recipe.getItemStack().getHoverName(), width - 100, height / 2 - 10, 0xFFFFFF);
        //this.func_73730_a(this.field_146294_l - 170, this.field_146294_l - 30, (this.field_146295_m + 50) / 2 + 20, -1);
        //drawCenteredString(matrixStack, font, "Required Items: ", width - 100, height / 2 + 10, 0xFFFFFF);

        for(int i = 0; i < recipe.getRecipeItems().size(); i++) {
            ItemStack is = recipe.getRecipeItems().get(i);
            boolean hasItem = hasItem(player, is, is.getCount());

            drawCenteredString(matrixStack, font, is.getHoverName().plainCopy().append(new StringTextComponent(" (" + is.getCount() + ")")), width - 100, height / 2 + 10 + (10 * i+1), !hasItem ? 0xFF0000 : 0x228B22);
        }
    }

    private Object[] getCategories(Multimap<String, OvenRecipe> items) {
        return items.keySet().toArray();
    }

    public static class OvenRecipe {
        private ItemStack itemStack;
        private List<ItemStack> recipeItems;

        public OvenRecipe(ItemStack itemStack, List<ItemStack> recipeItems) {
            this.itemStack = itemStack;
            this.recipeItems = recipeItems;
        }

        public ItemStack getItemStack() {
            return this.itemStack;
        }

        public List<ItemStack> getRecipeItems() {
            return this.recipeItems;
        }
    }

    public static OvenRecipe createRecipe(ItemStack itemStack, ItemStack... items) {
        return new OvenRecipe(itemStack, Arrays.asList(items));
    }

    private void addItems() {
//        NonNullList<ItemStack> inventory = this.player.inventory.items;
//
//        for (ItemStack itemStack : inventory) {
//            this.items.put("Inventory", new OvenRecipe(itemStack, null));
//        }

        // Add oven recipes
        this.items.put("Food", createRecipe(new ItemStack(ModItems.SWEET_ROLL.get(), 1),
                new ItemStack(ModItems.BUTTER.get(), 1), new ItemStack(Items.EGG, 1), new ItemStack(ModItems.SALT_PILE.get(), 1),
                new ItemStack(ModItems.FLOUR.get(), 1), new ItemStack(Items.MILK_BUCKET, 1)));
        this.items.put("Food", createRecipe(new ItemStack(ModItems.GARLIC_BREAD.get(), 1),
                new ItemStack(ModBlocks.GARLIC.get(), 1), new ItemStack(ModItems.BUTTER.get(), 1), new ItemStack(ModItems.FLOUR.get(), 1)));
        this.items.put("Food", createRecipe(new ItemStack(ModItems.APPLE_PIE.get(), 1),
                new ItemStack(ModItems.FLOUR.get(), 1), new ItemStack(ModItems.SALT_PILE.get(), 2), new ItemStack(ModItems.BUTTER.get(), 1),
                new ItemStack(Items.EGG, 1), new ItemStack(Items.APPLE, 2)));
        this.items.put("Food", createRecipe(new ItemStack(ModItems.POTATO_BREAD.get(), 1),
                new ItemStack(ModItems.SALT_PILE.get(), 1), new ItemStack(Items.EGG, 1), new ItemStack(ModItems.FLOUR.get(), 1),
                new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.POTATO, 1)));
        //this.items.put("Food", createRecipe(new ItemStack(ModItems.SWEET_ROLL.get(), 1), new ItemStack(ModItems.BUTTER.get(), 1), new ItemStack(Items.EGG, 1), new ItemStack(ModItems.SALT_PILE.get(), 1), new ItemStack(ModItems.FLOUR.get(), 1), new ItemStack(Items.MILK_BUCKET, 1)));
    }

    public static boolean hasItem(PlayerEntity player, ItemStack is, int amount) {
        if (is != null) {
            IItemHandler ih = (IItemHandler)player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("skyrim oven gui hasItem"));

            for(int i = 0; i < ih.getSlots(); ++i) {
                ItemStack stack = ih.getStackInSlot(i);
                if (areItemStacksEqual(is, stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeItem(PlayerEntity player, ItemStack is, int amount) {
        if (is != null) {
            IItemHandler ih = (IItemHandler)player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("skyrim oven gui removeItem"));

            for(int i = 0; i < ih.getSlots(); ++i) {
                ItemStack stack = ih.getStackInSlot(i);
                if (areItemStacksEqual(is, stack)) {
                    ih.extractItem(i, amount, false);
                }
            }

        }
    }

    public static boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2) {
        if (stack1 != null && stack2 != null) {
            return stack1.sameItem(stack2) && ItemStack.tagMatches(stack1, stack2) && (stack2.getCount() >= stack1.getCount());
        } else {
            return stack1 == stack2;
        }
    }

    private void renderHealth(MatrixStack matrixStack) {
        minecraft.getTextureManager().bind(OVERLAY_ICONS);
        float healthPercentage = minecraft.player.getHealth() / minecraft.player.getMaxHealth();
        float healthBarWidth = 80.0f * healthPercentage;
        float healthBarStartX = (float)(width - 109) + (80.0f - healthBarWidth);
        this.blit(matrixStack, this.width - 120, this.height - 25, 0, 51, 102, 10);
        this.blit(matrixStack, (int)healthBarStartX, this.height - 23, 11, 64, (int)healthBarWidth, 6);
        minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }

    private void drawBorderedGradientRect(MatrixStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
        matrixStack.pushPose();
        // Draw background
        fillGradient(matrixStack, startX, startY, endX, endY, colorStart, colorEnd);
        // Draw borders
        fill(matrixStack, startX, startY, endX, startY+1, borderColor); // top
        fill(matrixStack, startX, endY-1, endX, endY, borderColor); // bottom
        fill(matrixStack, startX, startY+1, startX+1, endY-1, borderColor); // left
        fill(matrixStack, endX-1, startY+1, endX, endY-1, borderColor); // right
        matrixStack.popPose();
    }
}