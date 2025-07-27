package stan.ripto.mimiclight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
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
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof MimicLightBlockEntity mimic && mimic.hasCopiedState() && !player.isCreative()) {
            popCopiedStateBlockItem(mimic.getCopiedState(), level, pos);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof MimicLightBlockEntity mimic)) return InteractionResult.PASS;

        ItemStack heldItem = player.getMainHandItem();
        if (mimic.hasCopiedState()) {
            if (player.isCrouching() && heldItem.isEmpty()) {
                if (!player.isCreative()) {
                    popCopiedStateBlockItem(mimic.getCopiedState(), level, pos);
                }
                mimic.setCopiedState(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get().defaultBlockState());
            }
            return InteractionResult.PASS;
        }

        if (!(heldItem.getItem() instanceof BlockItem)) return InteractionResult.PASS;

        Block heldBlock = Block.byItem(heldItem.getItem());
        if (heldBlock instanceof EntityBlock) return InteractionResult.PASS;

        BlockPlaceContext context = new BlockPlaceContext(level, player, hand, heldItem, hit);
        BlockState stateToCopy = heldBlock.getStateForPlacement(context);
        if (stateToCopy == null) return InteractionResult.PASS;

        VoxelShape shape = stateToCopy.getShape(level, pos);
        if (!Block.isShapeFullBlock(shape)) return InteractionResult.PASS;

        mimic.setCopiedState(stateToCopy);
        if (!player.isCreative()) heldItem.shrink(1);

        return InteractionResult.SUCCESS;
    }

    private void popCopiedStateBlockItem(BlockState state, Level level, BlockPos pos) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        stack.setCount(1);
        ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        level.addFreshEntity(item);
    }
}
