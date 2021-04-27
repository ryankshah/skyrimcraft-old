package com.ryankshah.skyrimcraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.Skyrimcraft;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.spell.ISpell;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class SkyrimMagicGui extends Screen
{
    protected static final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Skyrimcraft.MODID, "textures/gui/overlay_icons_current.png");
    private static final int PADDING = 7;

    private Map<ISpell.SpellType, ArrayList<ISpell>> spellsAndTypes;
    private List<Object> spellTypes;
    private List<ISpell> spellsListForCurrentSpellType;
    private boolean spellTypeChosen;
    private int currentSpellType;
    private int currentSpell;
    private ISpell currentSpellObject;
    private ISpell.SpellType currentSpellTypeObject;

    private float currentTick, lastTick;
    private int currentSpellFrame;

    public SkyrimMagicGui(List<ISpell> knownSpells) {
        super(new TranslationTextComponent(Skyrimcraft.MODID + ".magicgui.title"));
        this.spellsAndTypes = new HashMap<>();

        for(ISpell spell : knownSpells) {
            if(spellsAndTypes.containsKey(spell.getType()))
                spellsAndTypes.get(spell.getType()).add(spell);
            else {
                ArrayList<ISpell> temp = new ArrayList<>();
                temp.add(spell);
                spellsAndTypes.put(spell.getType(), temp);
            }
        }

        this.currentSpellType = 0;
        this.currentSpell = 0;
        this.currentSpellObject = null;
        this.currentSpellTypeObject = null;
        this.spellTypes = Arrays.asList(spellsAndTypes.keySet().toArray());
        this.spellsListForCurrentSpellType = new ArrayList<>();
        this.currentTick = 0;
        this.lastTick = 0;
        this.currentSpellFrame = 0;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);

        if(this.spellTypeChosen) {
            fillGradient(matrixStack, this.width - 90, 0, this.width - 10, this.height, 0xAA000000, 0xAA000000);
            fillGradient(matrixStack, this.width - 88, 0, this.width - 87, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, this.width - 13, 0, this.width - 12, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, this.width - 190, 0, this.width - 110, this.height, 0xAA222222, 0xAA222222);
            fillGradient(matrixStack, this.width - 188, 0, this.width - 187, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, this.width - 113, 0, this.width - 112, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
        } else {
            fillGradient(matrixStack, this.width - 90, 0, this.width - 10, this.height, 0xAA222222, 0xAA222222);
            fillGradient(matrixStack, this.width - 88, 0, this.width - 87, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, this.width - 13, 0, this.width - 12, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, this.width - 190, 0, this.width - 110, this.height, 0xAA000000, 0xAA000000);
            fillGradient(matrixStack, this.width - 188, 0, this.width - 187, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
            fillGradient(matrixStack, this.width - 113, 0, this.width - 112, this.height, 0xAAFFFFFF, 0xAAFFFFFF);
        }
        fillGradient(matrixStack, 0, this.height * 3 / 4 + 20, this.width, this.height, 0x77000000, 0x77000000);
        fillGradient(matrixStack, 0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xAAFFFFFF, 0xAAFFFFFF);

        // Draw "buttons" for keys for selecting spells
        drawGradientRect(matrixStack, 17, this.height - 29, 32, this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        drawGradientRect(matrixStack, 37, this.height - 29, 52, this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        drawCenteredString(matrixStack, font, "Z", 25, this.height - 25, 0x00FFFFFF);
        drawCenteredString(matrixStack, font, "X", 45, this.height - 25, 0x00FFFFFF);
        drawCenteredString(matrixStack, font, "Equip", 70, this.height - 25, 0x00FFFFFF);

        renderMagicka(matrixStack);

        int i;
        for(i = 0; i < spellTypes.size(); i++)
            drawCenteredString(matrixStack, font, ((ISpell.SpellType)spellTypes.get(i)).toString(), this.width - 50, this.height / 2 + 14 * i - this.currentSpellType * 7, i == this.currentSpellType ? 0x00FFFFFF : 0x00C0C0C0);

        // Get ISpell.SpellType
        currentSpellTypeObject = (ISpell.SpellType) spellTypes.get(currentSpellType);
        // Get player's known spells for chosen spell type
        spellsListForCurrentSpellType = spellsAndTypes.get(currentSpellTypeObject);

        for(int j = 0; j < spellsListForCurrentSpellType.size(); j++) {
            ISpell spell = spellsListForCurrentSpellType.get(j);
            String displayName = spell.getName();

            if (displayName.length() >= 18) {
                displayName = displayName.substring(0, 16) + "..";
            }

            if (j == this.currentSpell && this.spellTypeChosen) {
                this.currentSpellObject = spell;
                drawSpellInformation(matrixStack, currentSpellObject, partialTicks);
                drawCenteredString(matrixStack, font, displayName, this.width - 140, this.height / 2 + 14 * i - this.currentSpell * 7, 0x00FFFFFF);
            } else {
                drawCenteredString(matrixStack, font, displayName, this.width - 140, this.height / 2 + 14 * i - this.currentSpell * 7, 0x00C0C0C0);
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void drawGradientRect(MatrixStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
        matrixStack.push();
        // Draw background
        fillGradient(matrixStack, startX, startY, endX, endY, colorStart, colorEnd);
        // Draw borders
        fill(matrixStack, startX, startY, endX, startY+1, borderColor); // top
        fill(matrixStack, startX, endY-1, endX, endY, borderColor); // bottom
        fill(matrixStack, startX, startY+1, startX+1, endY-1, borderColor); // left
        fill(matrixStack, endX-1, startY+1, endX, endY-1, borderColor); // right
        matrixStack.pop();
    }

    private void drawSpellInformation(MatrixStack matrixStack, ISpell spell, float partialTicks) {
        drawGradientRect(matrixStack, 40, (this.height) / 2, 200, (this.height) / 2 + 60, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
        fillGradient(matrixStack, 50, (this.height) / 2 + 20, 190, (this.height) / 2 + 21, 0xAAFFFFFF, 0xAAFFFFFF); // Line under spell name

        drawCenteredString(matrixStack, font, spell.getName(), 120, (this.height) / 2 + 10, 0x00FFFFFF); // Spell name
        for(int i = 1; i < spell.getDescription().size()+1; i++)
            drawCenteredString(matrixStack, font, spell.getDescription().get(i-1), 120, (this.height) / 2 + 20 + (8 * i), 0x00FFFFFF); // Spell description

        // Draw the spell entity?
        minecraft.getTextureManager().bindTexture(spell.getDisplayAnimation());
        currentSpellFrame = (int)(lastTick + (currentTick - lastTick) * partialTicks) / 64;
        int uOffset = 64 * (currentSpellFrame % 5), vOffset = 0;
        matrixStack.push();
            blit(matrixStack, 88, (this.height / 2) - 84, uOffset, vOffset, 64, 64, 320, 64);
        matrixStack.pop();
        minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }

    private void renderMagicka(MatrixStack matrixStack) {
        minecraft.getTextureManager().bindTexture(OVERLAY_ICONS);
        minecraft.player.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).ifPresent(cap -> {
            float magickaPercentage = cap.getMagicka() / cap.getMaxMagicka();
            float magickaBarWidth = 80.0f * magickaPercentage;
            float magickaBarStartX = (float)(width - 109) + (80.0f - magickaBarWidth);
            this.blit(matrixStack, this.width - 120, this.height - 25, 0, 51, 102, 10);
            this.blit(matrixStack, (int)magickaBarStartX, this.height - 23, 11, 64, (int)magickaBarWidth, 6);
        });
        minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }

    @Override
    public void tick() {
        if(!spellTypeChosen)
            currentTick = 0;
        else {
            currentTick = lastTick;
            lastTick += 32f;
//            if(currentSpellFrame > 14)
//                currentSpellFrame = 0;
        }

        super.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW_KEY_DOWN) {
            if(!this.spellTypeChosen) {
                if(this.currentSpellType < this.spellTypes.size()-1)
                    ++this.currentSpellType;
                else
                    this.currentSpellType = this.spellTypes.size()-1;
            } else {
                if(this.currentSpell > 0 && this.currentSpell < spellsListForCurrentSpellType.size())
                    ++this.currentSpell;
                else
                    this.currentSpell = 0;
            }
        }

        if(keyCode == GLFW_KEY_UP) {
            if(!this.spellTypeChosen) {
                if(this.currentSpellType > 0)
                    --this.currentSpellType;
                else
                    this.currentSpellType = 0;
            } else {
                if(this.currentSpell > 0 && this.currentSpell < spellsListForCurrentSpellType.size())
                    --this.currentSpell;
                else
                    this.currentSpell = 0;
            }
        }

        if(keyCode == GLFW_KEY_RIGHT) {
            if(this.spellTypeChosen) {
                this.spellTypeChosen = false;
                this.currentSpell = 0;
            }
        }

        if(keyCode == GLFW_KEY_LEFT) {
            if(!this.spellTypeChosen) {
                this.spellTypeChosen = true;
                this.currentSpell = 0;
            }
        }

        if(keyCode == GLFW_KEY_Z) {
        }

        if(keyCode == GLFW_KEY_X) {
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
    public void onClose() {
        super.onClose();
    }
}