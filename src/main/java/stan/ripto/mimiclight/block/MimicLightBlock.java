package stan.ripto.mimiclight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.mimiclight.blockentity.MimicLightBlockEntity;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class MimicLightBlock extends BaseEntityBlock {
    protected MimicLightBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).lightLevel(state -> 15).noOcclusion());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MimicLightBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof MimicLightBlockEntity mimic) {
            if (mimic.getHasCamo() && mimic.getCopiedState().getBlock() != MimicLightBlocks.MIMIC_LIGHT_BLOCK.get()) {
                mimic.setHasCamo(false);
                popCamoItem(mimic.getCopiedState(), level, pos);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof MimicLightBlockEntity mimic)) return InteractionResult.PASS;

        ItemStack heldItem = player.getMainHandItem();
        if (mimic.getHasCamo()) {
            if (player.isCrouching() && heldItem.isEmpty()) {
                popCamoItem(mimic.getCopiedState(), level, pos);
                mimic.setCopiedState(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get().defaultBlockState());
                mimic.setHasCamo(false);
            }
            return InteractionResult.PASS;
        }

        if (!(heldItem.getItem() instanceof BlockItem)) return InteractionResult.PASS;

        Block heldBlock = Block.byItem(heldItem.getItem());
        if (heldBlock == Blocks.AIR || heldBlock == MimicLightBlocks.MIMIC_LIGHT_BLOCK.get()) {
            return InteractionResult.PASS;
        }

        BlockState stateToCopy = heldBlock.defaultBlockState();
        if (!stateToCopy.getProperties().isEmpty()) return InteractionResult.PASS;

        mimic.setCopiedState(stateToCopy);
        mimic.setHasCamo(true);
        heldItem.shrink(1);

        return InteractionResult.SUCCESS;
    }

    private void popCamoItem(BlockState state, Level level, BlockPos pos) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        stack.setCount(1);
        ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(), stack);
        level.addFreshEntity(item);
    }
}
