package stan.ripto.mimiclight.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stan.ripto.mimiclight.block.MimicLightBlocks;
import stan.ripto.mimiclight.blockentity.MimicLightBlockEntity;

import java.util.List;

public class MimicLightBakedModel extends BakedModelWrapper<BakedModel> {
    public MimicLightBakedModel(BakedModel defaultModel) {
        super(defaultModel);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        BlockState copiedState = extraData.get(MimicLightBlockEntity.COPIED_STATE_PROPERTY);

        if (copiedState != null && copiedState != MimicLightBlocks.MIMIC_LIGHT_BLOCK.get().defaultBlockState()) {
            BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(copiedState);

            if (renderType == null || model.getRenderTypes(copiedState, rand, extraData).contains(renderType)) {
                return model.getQuads(copiedState, side, rand, extraData, renderType);
            }
        }

        return super.getQuads(state, side, rand, ModelData.EMPTY, renderType);
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        BlockState copiedState = data.get(MimicLightBlockEntity.COPIED_STATE_PROPERTY);

        if (copiedState != null && copiedState != MimicLightBlocks.MIMIC_LIGHT_BLOCK.get().defaultBlockState()) {
            BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(copiedState);
            return model.getRenderTypes(copiedState, rand, data);
        }

        return super.getRenderTypes(state, rand, ModelData.EMPTY);
    }
}
