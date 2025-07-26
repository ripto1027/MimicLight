package stan.ripto.mimiclight.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.mimiclight.block.MimicLightBlocks;

public class MimicLightBlockEntity extends BlockEntity {
    public static final ModelProperty<BlockState> COPIED_STATE_PROPERTY = new ModelProperty<>();
    private BlockState copiedState = MimicLightBlocks.MIMIC_LIGHT_BLOCK.get().defaultBlockState();
    private boolean hasCamo;
    private CompoundTag pendingStateNbt = null;
    public static final String COPIED_STATE_KEY = "copied_state";
    public static final String HAS_CAMO_KEY = "has_como";

    public MimicLightBlockEntity(BlockPos pos, BlockState state) {
        super(MimicLightBlockEntities.MIMIC_LIGHT_BLOCK.get(), pos, state);
    }

    private void resolvePendingState() {
        if (pendingStateNbt != null && this.level != null) {
            this.copiedState = NbtUtils.readBlockState(
                    this.level.holderLookup(ForgeRegistries.Keys.BLOCKS),
                    this.pendingStateNbt
            );
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            this.pendingStateNbt = null;
        }
    }

    public void setCopiedState(BlockState state) {
        if (level != null) {
            this.copiedState = state;
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            requestModelDataUpdate();
        }
    }

    public BlockState getCopiedState() {
        resolvePendingState();
        return this.copiedState;
    }

    public void setHasCamo(boolean hasCamo) {
        this.hasCamo = hasCamo;
        setChanged();
    }

    public boolean getHasCamo() {
        return this.hasCamo;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        resolvePendingState();
        super.saveAdditional(nbt);
        nbt.put(COPIED_STATE_KEY, NbtUtils.writeBlockState(this.copiedState));
        nbt.putBoolean(HAS_CAMO_KEY, this.hasCamo);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains(COPIED_STATE_KEY)) {
            this.pendingStateNbt = nbt.getCompound(COPIED_STATE_KEY);
        }
        if (nbt.contains(HAS_CAMO_KEY)) {
            this.hasCamo = nbt.getBoolean(HAS_CAMO_KEY);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull ModelData getModelData() {
        resolvePendingState();
        return ModelData.builder().with(COPIED_STATE_PROPERTY, this.copiedState).build();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        BlockState oldState = getCopiedState();
        super.onDataPacket(net, pkt);
        if (!getCopiedState().equals(oldState)) {
            requestModelDataUpdate();
            if (level != null && level.isClientSide()) {
                level.setBlock(getBlockPos(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        requestModelDataUpdate();
    }
}
