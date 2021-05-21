package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.character.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.character.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.spell.PacketAddToKnownSpells;
import com.ryankshah.skyrimcraft.character.magic.ISpell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShoutBlock extends SkyrimBlock
{
    private Random random = new Random();

    public static final BooleanProperty SHOUT_GIVEN;

    public ShoutBlock(String displayName) {
        super(AbstractBlock.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).randomTicks().noDrops(), displayName);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SHOUT_GIVEN, false));
    }

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(SHOUT_GIVEN, false);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(SHOUT_GIVEN);
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity playerEntity, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (!p_225533_2_.isClientSide) {
            List<BlockState> nearbyShoutBlocks = p_225533_2_.getBlockStates(new AxisAlignedBB(p_225533_3_.getX() - 5, p_225533_3_.getY() - 5, p_225533_3_.getZ() - 5,
                    p_225533_3_.getX() + 5, p_225533_3_.getY() + 5, p_225533_3_.getZ() + 5)).collect(Collectors.toList());
            if(!p_225533_1_.getValue(SHOUT_GIVEN)) {
                for(BlockState state : nearbyShoutBlocks) {
                    if(state.hasProperty(SHOUT_GIVEN) && state.getValue(SHOUT_GIVEN)) {
                        playerEntity.displayClientMessage(new TranslationTextComponent("shoutblock.used"), false);
                        return ActionResultType.FAIL;
                    }
                }

                // TODO: Later on we will make use of dragon souls to unlock shouts...
                ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("shout block use"));

                if (cap.getKnownSpells().contains(SpellRegistry.UNRELENTING_FORCE.get())) {
                    List<ISpell> shouts = SpellRegistry.SPELLS.getEntries().stream().filter(spell -> spell.get().getType() == ISpell.SpellType.SHOUT && spell != SpellRegistry.UNRELENTING_FORCE && !cap.getKnownSpells().contains(spell.get())).map(RegistryObject::get).collect(Collectors.toList());

                    if (shouts.size() > 0) {
                        ISpell shout = shouts.get(random.nextInt(shouts.size()));
                        Networking.sendToServer(new PacketAddToKnownSpells(shout));

                        p_225533_2_.setBlockAndUpdate(p_225533_3_, p_225533_1_.setValue(SHOUT_GIVEN, true));
                        for(BlockState state : nearbyShoutBlocks) {
                            if(state.hasProperty(SHOUT_GIVEN) && !state.getValue(SHOUT_GIVEN)) {
                                state.setValue(SHOUT_GIVEN, true);
                            }
                        }

                    } else
                        playerEntity.displayClientMessage(new TranslationTextComponent("shoutblock.allshoutsknown"), false);
                } else {
                    Networking.sendToServer(new PacketAddToKnownSpells(SpellRegistry.UNRELENTING_FORCE.get()));
                }
            } else {
                playerEntity.displayClientMessage(new TranslationTextComponent("shoutblock.used"), false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        // TODO: Check if player is near and then spawn particles
        super.animateTick(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);
    }

    static {
        SHOUT_GIVEN = BooleanProperty.create("shout_given");
    }
}