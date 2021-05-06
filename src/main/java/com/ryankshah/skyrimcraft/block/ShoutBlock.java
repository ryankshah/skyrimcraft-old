package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerData;
import com.ryankshah.skyrimcraft.capability.ISkyrimPlayerDataProvider;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.network.PacketAddToKnownSpells;
import com.ryankshah.skyrimcraft.spell.ISpell;
import com.ryankshah.skyrimcraft.spell.SpellRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
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
            // Check if the shout is given (if blockstate shout given property is true)
            if(!p_225533_1_.getValue(SHOUT_GIVEN)) {
                // Check that no blocks in nearbyShoutBlocks has the property set to true
                for(BlockState state : nearbyShoutBlocks) {
                    if(state.hasProperty(SHOUT_GIVEN) && state.getValue(SHOUT_GIVEN)) {
                        playerEntity.displayClientMessage(new StringTextComponent("The power which once resonated within this wall has since departed"), false);
                        return ActionResultType.FAIL;
                    }
                }

                // Now that no blocks in nearbyShoutBlocks has the property set to true
                // we can now add the shout to the player

                // TODO: Later on we will make use of dragon souls to unlock shouts...
                ISkyrimPlayerData cap = playerEntity.getCapability(ISkyrimPlayerDataProvider.SKYRIM_PLAYER_DATA_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("shout block use"));

                // we'll have unrelenting force always be the first shout they learn,
                // otherwise add a random shout from the spell registry.
                if (cap.getKnownSpells().contains(SpellRegistry.UNRELENTING_FORCE.get())) {
                    List<ISpell> shouts = SpellRegistry.SPELLS.getEntries().stream().filter(spell -> spell.get().getType() == ISpell.SpellType.SHOUT && spell != SpellRegistry.UNRELENTING_FORCE && !cap.getKnownSpells().contains(spell.get())).map(RegistryObject::get).collect(Collectors.toList());

                    if (shouts.size() > 0) {
                        ISpell shout = shouts.get(random.nextInt(shouts.size()));
                        Networking.sendToServer(new PacketAddToKnownSpells(shout));
                        // TODO: do we need to replace this with a shout learned sound of our own
                        //p_225533_2_.playLocalSound(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1f, 1f, false);
                    } else
                        playerEntity.displayClientMessage(new StringTextComponent("You have no more shouts to learn!"), false);
                } else {
                    Networking.sendToServer(new PacketAddToKnownSpells(SpellRegistry.UNRELENTING_FORCE.get()));
                }

                // Now set the state value to true, as well as for other block states in the area.
                p_225533_2_.setBlockAndUpdate(p_225533_3_, p_225533_1_.setValue(SHOUT_GIVEN, true));
                for(BlockState state : nearbyShoutBlocks) {
                    if(state.hasProperty(SHOUT_GIVEN) && !state.getValue(SHOUT_GIVEN)) {
                        state.setValue(SHOUT_GIVEN, true);
                    }
                }
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