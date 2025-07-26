package stan.ripto.mimiclight.datagen.client.blockstate;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;

public class MimicLightBlockStateProvider extends BlockStateProvider {
    public MimicLightBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MimicLight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockModelBuilder model = models().cubeAll("mimic_light_block_default", modLoc("block/mimic_light_block")).renderType("translucent");
        simpleBlockWithItem(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get(), model);
    }
}
