package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.item.IPotion;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;

public class AlchemyScreen extends Screen
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

    private Map<IPotion.PotionCategory, List<AlchemyRecipe>> items;
    private List<AlchemyRecipe> itemList;
    private List<Object> categories;
    private boolean categoryChosen;
    private int currentCategory;
    private int currentItem;
    private int categoryStartIndex, itemStartIndex;
    private float spin = 0.0F;
    private AlchemyRecipe currentRecipeObject = null;
    private PlayerEntity player;

    public AlchemyScreen() {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".alchemygui.title"));
        this.player = Minecraft.getInstance().player;
        this.items = new HashMap<>();
        items.put(IPotion.PotionCategory.ALL, new ArrayList<>());
        this.addCategoriesAndRecipes();
        items = items.entrySet().stream().sorted((e1,e2) -> Integer.compare(e1.getKey().getTypeID(), (e2.getKey().getTypeID()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        this.categories = Arrays.asList(this.items.keySet().toArray());
        this.currentCategory = 0;
        this.currentItem = 0;
        this.itemList = new ArrayList<>();
        this.itemList.addAll(this.items.get((IPotion.PotionCategory) this.categories.get(this.currentCategory)));
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

        int MIN_Y = 30;
        int MAX_Y = height / 2 + 14 * 6 - 10;

        for(int i = 0; i < categories.size(); i++) {
            int y = height / 2 + 14 * i - this.currentCategory * 7 - 10;
            if(y <= MIN_Y || y >= MAX_Y)
                continue;

            String categoryName = categories.get(i).toString();
            if (categoryName.length() >= 14) {
                categoryName = categoryName.substring(0, 8) + "..";
            }
            drawString(matrixStack, font, categoryName, 18, y, i == this.currentCategory ? 0x00FFFFFF : 0x00C0C0C0);
        }

        itemList = items.get(categories.get(currentCategory));

        // TODO: Fix the overflowing issue...
        for(int j = 0; j < itemList.size(); j++) {
            AlchemyRecipe recipe = itemList.get(j);
            ITextComponent itemStackHoverName = recipe.itemStack.getHoverName();

            if (j == this.currentItem && this.categoryChosen) {
                this.currentRecipeObject = recipe;
                this.drawItemImage(matrixStack, recipe.getItemStack(), width - 100, height / 2 - 70, this.spin);
                this.drawItemInformation(matrixStack, recipe);
            }

            int y = height / 2 + 14 * j - this.currentItem * 7 - 10;
            if(y >= MAX_Y)
                continue;

            drawString(matrixStack, font, font.width(itemStackHoverName) < 16 ? itemStackHoverName.getString() : itemStackHoverName.getString().substring(0, 16) + "..", 98, y, j == this.currentItem ? 0x00FFFFFF : 0x00C0C0C0);
        }

        fillGradient(matrixStack, 0, this.height * 3 / 4 + 20, this.width, this.height, 0x77000000, 0x77000000);
        fillGradient(matrixStack, 0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xAAFFFFFF, 0xAAFFFFFF);
        drawBorderedGradientRect(matrixStack, 17, this.height - 29, 32 + font.width("Enter"), this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        drawString(matrixStack, font, "Enter", 25, this.height - 25, 0x00FFFFFF);
        drawString(matrixStack, font, "Create Potion", 32 + font.width("Enter") + 6, this.height - 25, 0x00FFFFFF);

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
            if(!this.categoryChosen) {
                if(this.currentCategory < this.categories.size()-1)
                    ++this.currentCategory;
                else
                    this.currentCategory = this.categories.size()-1;
            } else {
                if(this.currentItem >= 0 && this.currentItem < itemList.size()-1)
                    this.currentItem++;
            }
        }

        if(keyCode == GLFW_KEY_UP || keyCode == GLFW_KEY_W) {
            if(!this.categoryChosen) {
                if(this.currentCategory > 0)
                    --this.currentCategory;
                else
                    this.currentCategory = 0;
            } else {
                if(this.currentItem > 0 && this.currentItem < itemList.size())
                    this.currentItem--;
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
                hasAndRemoveItems(player, copy, copy.getCount());
            }

            this.player.addItem(this.currentRecipeObject.getItemStack().copy());
            player.playSound(SoundEvents.BREWING_STAND_BREW, 1.0F, 1.0F);
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

    private void drawItemInformation(MatrixStack matrixStack, AlchemyRecipe recipe) {
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

    private Object[] getCategories(Map<IPotion.PotionCategory, List<AlchemyRecipe>> items) {
        return items.keySet().toArray();
    }

    public static class AlchemyRecipe {
        private ItemStack itemStack;
        private List<ItemStack> recipeItems;

        public AlchemyRecipe(ItemStack itemStack, List<ItemStack> recipeItems) {
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

    private void addCategoriesAndRecipes() {
        List<SkyrimPotion> potions = ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(item -> item instanceof SkyrimPotion).map(item -> (SkyrimPotion)item).collect(Collectors.toList());
        for(SkyrimPotion potion : potions) {
            if(potion.getCategory() == IPotion.PotionCategory.UNIQUE || potion.getIngredients().isEmpty())
                continue;

            if(items.containsKey(potion.getCategory()))
                items.get(potion.getCategory()).add(new AlchemyRecipe(new ItemStack(potion, 1), potion.getIngredients()));
            else {
                List<AlchemyRecipe> temp = new ArrayList<>();
                temp.add(new AlchemyRecipe(new ItemStack(potion, 1), potion.getIngredients()));
                items.put(potion.getCategory(), temp);
            }
            items.get(IPotion.PotionCategory.ALL).add(new AlchemyRecipe(new ItemStack(potion, 1), potion.getIngredients()));
        }
    }

    public static boolean hasItem(PlayerEntity player, ItemStack is, int amount) {
        if (is != null) {
            IItemHandler ih = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new IllegalStateException("skyrim oven gui hasItem"));
            int count = 0;

            for(int i = 0; i < ih.getSlots(); ++i) {
                if(count >= is.getCount())
                    return true;

                ItemStack stack = ih.getStackInSlot(i);
                if(is.sameItem(stack) && ItemStack.tagMatches(is, stack)) {
                    count += stack.getCount();
                }
            }
        }
        return false;
    }

    public static void hasAndRemoveItems(PlayerEntity player, ItemStack is, int amount) {
        if (is != null) {
            IItemHandler ih = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new IllegalStateException("skyrim oven gui hasAndRemoveItems"));
            int count = is.getCount();

            for(int i = 0; i < ih.getSlots(); ++i) {
                ItemStack stack = ih.getStackInSlot(i);
                if(is.sameItem(stack) && ItemStack.tagMatches(is, stack)) {
                    if(stack.getCount() >= count) {
                        ih.extractItem(i, count, false);
                        break;
                    } else {
                        count -= stack.getCount();
                        ih.extractItem(i, stack.getCount(), false);
                    }
                }
            }
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