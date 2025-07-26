package stan.ripto.mimiclight.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;

public class MimicLightBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MimicLight.MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<MimicLightBlockEntity>> MIMIC_LIGHT_BLOCK =
            TILES.register("mimic_light_block", () -> BlockEntityType.Builder.of(MimicLightBlockEntity::new, MimicLightBlocks.MIMIC_LIGHT_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        TILES.register(bus);
    }
}
