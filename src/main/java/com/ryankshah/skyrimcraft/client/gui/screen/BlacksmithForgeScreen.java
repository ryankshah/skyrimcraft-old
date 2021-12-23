package com.ryankshah.skyrimcraft.client.gui.screen;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.character.skill.ISkill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.client.gui.TextureDrawer;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.skill.PacketAddXpToSkillOnServer;
import com.ryankshah.skyrimcraft.util.ForgeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class BlacksmithForgeScreen extends Screen
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons.png");

    private Multimap<String, ForgeRecipe> items;
    private List<ForgeRecipe> itemList;
    private Object[] categories;
    private boolean categoryChosen;
    private int currentCategory;
    private int currentItem;
    private float spin = 0.0F;
    private ForgeRecipe currentRecipeObject = null;
    private Player player;

    public BlacksmithForgeScreen(List<ForgeRecipe> recipes) {
        super(new TranslatableComponent(Skyrimcraft.MODID + ".blacksmithscreen.title"));
        this.player = Minecraft.getInstance().player;
        this.items = ArrayListMultimap.create();
        recipes.stream().forEach(recipe -> items.put(recipe.getCategory(), recipe));
        this.categories = this.items.keySet().toArray();
        this.currentCategory = 0;
        this.currentItem = 0;
        this.itemList = new ArrayList<>();
        this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
        this.itemList.sort((i1, i2) -> i1.getResult().getItem().getRegistryName().compareTo(i2.getResult().getItem().getRegistryName()));
        categoryChosen = false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);
        //this.fillGradient(matrixStack, 0, 0, this.width, this.height, -1072689136, -804253680);
        if(!this.categoryChosen) {
            fillGradient(matrixStack, 10, 0, 80, this.height - 2, 0xAA000000, 0xAA555555);
            fillGradient(matrixStack, 12, 2, 13, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
            fillGradient(matrixStack, 77, 2, 78, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
            fillGradient(matrixStack, 90, 0, 200, this.height, 0xAA000000, 0xAA000000);
            fillGradient(matrixStack, 197, 2, 198, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
            fillGradient(matrixStack, 92, 2, 93, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
        } else {
            fillGradient(matrixStack, 10, 0, 80, this.height - 2, 0xAA000000, 0xAA000000);
            fillGradient(matrixStack, 12, 2, 13, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
            fillGradient(matrixStack, 77, 2, 78, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
            fillGradient(matrixStack, 90, 0, 200, this.height, 0xAA000000, 0xAA555555);
            fillGradient(matrixStack, 197, 2, 198, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
            fillGradient(matrixStack, 92, 2, 93, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
        }

        int MIN_Y = 20;
        int MAX_Y = height / 2 + 14 * 6 - 10;

        if (!this.items.isEmpty()) {
            Object[] categories = this.getCategories(this.items);

            for(int i = 0; i < categories.length; i++) {
                String categoryName = ((String)categories[i]).toUpperCase();

                if (categoryName.length() >= 10)
                    categoryName = categoryName.substring(0, 8) + "..";

                drawString(matrixStack, font, categoryName, 18, height / 2 + (i * 14) - this.currentCategory * font.lineHeight, i == currentCategory ? 16777215 : 12632256);
            }

            if (this.itemList != null) {
                for(int i = 0; i < itemList.size(); i++) {
                    ForgeRecipe recipe = this.itemList.get(i);

                    if (i == this.currentItem) {
                        this.currentRecipeObject = recipe;
                        this.drawItemImage(matrixStack, recipe.getResult(), width - 100, height / 2 - 70, this.spin);
                        this.drawItemInformation(matrixStack, recipe);
                    }

//                    int y = this.height / 2 + 14 * i - this.currentItem * 6;
//                    if(y <= MIN_Y || y >= MAX_Y)
//                        continue;

                    String name = recipe.getResult().getHoverName().getString();
                    if (name.length() >= 16)
                        name = name.substring(0, 14) + "..";

                    drawString(matrixStack, font, name, 98, height / 2 + (i * 14) - this.currentItem * font.lineHeight, i == currentItem ? 16777215 : 12632256);
                }
            }
        }

        fillGradient(matrixStack, 0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        fillGradient(matrixStack, 0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF6E6B64, 0xFF6E6B64);
        drawBorderedGradientRect(matrixStack, 17, this.height - 29, 32 + font.width("Enter"), this.height - 14, 0xAA000000, 0xAA000000, 0xFF6E6B64);
        drawString(matrixStack, font, "Enter", 25, this.height - 25, 0x00FFFFFF);
        drawString(matrixStack, font, "Create", 32 + font.width("Enter") + 6, this.height - 25, 0x00FFFFFF);


        renderLevel(matrixStack);


        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.spin >= 360.0f)
            this.spin = 0.0f;
        else
            ++this.spin;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        if(scrollDelta > 0) {
            if (!this.categoryChosen) {
                if (this.currentCategory < this.categories.length - 1)
                    ++this.currentCategory;

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else {
                if (this.currentItem < this.itemList.size() - 1)
                    ++this.currentItem;
            }
        } else if(scrollDelta < 0) {
            if (!this.categoryChosen) {
                if(this.currentCategory > 0)
                    --this.currentCategory;

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else {
                if (this.currentItem > 0)
                    --this.currentItem;
            }
        }
        return true;
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
                this.player.displayClientMessage(new TextComponent("[Skyrimcraft] - You don't have the required items!"), false);
                return false;
            }

            for(ItemStack is : currentRecipeObject.getRecipeItems()) {
                ItemStack copy = is.copy();
                hasAndRemoveItems(player, copy, copy.getCount());
            }

            this.player.addItem(this.currentRecipeObject.getResult().copy());
            player.playSound(SoundEvents.BLASTFURNACE_FIRE_CRACKLE, 1.0F, 1.0F);
            // TODO: do level check
            Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.SMITHING.getID(), this.currentRecipeObject.getXpGained()));
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

    private void drawItemImage(PoseStack matrixStack, ItemStack is, int xPos, int yPos, float spin) {
        matrixStack.pushPose();
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        minecraft.getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS).setFilter(false, false);
        //RenderSystem.enableRescaleNormal();
        //RenderSystem.enableAlphaTest();
        //RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.translate(xPos, yPos,100F); //300.0F
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(spin++ % 360));
        matrixStack.scale(60F, 60F, 60F);
        MultiBufferSource.BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        this.itemRenderer.render(is, ItemTransforms.TransformType.GUI, false, matrixStack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, this.itemRenderer.getModel(is, null, null, 0));
        irendertypebuffer$impl.endBatch();
        //RenderSystem.disableAlphaTest();
        //RenderSystem.disableRescaleNormal();
        matrixStack.popPose();
    }

    private void drawItemInformation(PoseStack matrixStack, ForgeRecipe recipe) {
        //fillGradient(matrixStack, this.width - 180, (this.height + 50) / 2 - 20, this.width - 20, (this.height + 50) / 2 + 80, 0xAA000000, 0xAA000000);
        drawBorderedGradientRect(matrixStack, this.width - 180, this.height / 2 - 20, this.width - 20, this.height / 2 + 20 + (10 * recipe.getRecipeItems().size()), 0xAA000000, 0xAA000000, 0xFF6E6B64);
        fillGradient(matrixStack, this.width - 160, (this.height) / 2, this.width - 40, (this.height) / 2 + 1, 0xFF6E6B64, 0xFF6E6B64); // Line under recipe item name

        drawCenteredString(matrixStack, font, recipe.getResult().getHoverName().getString() + (recipe.getResult().getCount() > 1 ? " (" + recipe.getResult().getCount() + ")" : ""), width - 100, height / 2 - 10, 0xFFFFFF);
        //this.func_73730_a(this.field_146294_l - 170, this.field_146294_l - 30, (this.field_146295_m + 50) / 2 + 20, -1);
        //drawCenteredString(matrixStack, font, "Required Items: ", width - 100, height / 2 + 10, 0xFFFFFF);

        for(int i = 0; i < recipe.getRecipeItems().size(); i++) {
            ItemStack is = recipe.getRecipeItems().get(i);
            boolean hasItem = hasItem(player, is, is.getCount());

            drawCenteredString(matrixStack, font, is.getHoverName().plainCopy().append(new TextComponent(" (" + is.getCount() + ")")), width - 100, height / 2 + 10 + (10 * i+1), !hasItem ? 0xFF0000 : 0x228B22);
        }
    }

    private Object[] getCategories(Multimap<String, ForgeRecipe> items) {
        return items.keySet().toArray();
    }

    public static boolean hasItem(Player player, ItemStack is, int amount) {
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

    public static void hasAndRemoveItems(Player player, ItemStack is, int amount) {
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


    private void renderLevel(PoseStack matrixStack) {
        RenderSystem.setShaderTexture(0, OVERLAY_ICONS);

        ISkyrimPlayerData cap = minecraft.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalStateException("Skyrimcraft BlacksmithForgeScreen renderLevel"));
        ISkill smithing = cap.getSkills().get(SkillRegistry.SMITHING.getID());
        String curLevel = ""+(smithing.getLevel());
        String nextLevel = ""+(smithing.getLevel()+1);
        float skillProgress = smithing.getXpProgress();
        float skillBarWidth = 80.0f * skillProgress;
        TextureDrawer.drawGuiTexture(matrixStack, this.width - 140, this.height - 25, 0, 51, 102, 10);
        TextureDrawer.drawGuiTexture(matrixStack, width - 129, this.height - 23, 95 + ((80 - skillBarWidth) / 2.0f), 64, 80 * skillProgress, 6);
        drawString(matrixStack, font, curLevel, width - 140 - 6 - font.width(curLevel), height - 23, 0x00FFFFFF);
        drawString(matrixStack, font, nextLevel, width - 18 - font.width(nextLevel), height - 23, 0x00FFFFFF);

        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
    }

    private void drawBorderedGradientRect(PoseStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
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