package stan.ripto.mimiclight.datagen.server.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import stan.ripto.mimiclight.block.MimicLightBlocks;

import java.util.List;
import java.util.Set;

public class MimicLightLootTableProvider extends LootTableProvider {
    public MimicLightLootTableProvider(PackOutput pOutput) {
        super(pOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(MimicLightSubProvider::new, LootContextParamSets.BLOCK)));
    }

    private static class MimicLightSubProvider extends BlockLootSubProvider {
        protected MimicLightSubProvider() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropSelf(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get());

        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return MimicLightBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
        }
    }
}
