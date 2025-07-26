package stan.ripto.mimiclight.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.item.MimicLightItems;

public class MimicLightBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MimicLight.MOD_ID);

    public static final RegistryObject<Block> MIMIC_LIGHT_BLOCK = register();

    private static RegistryObject<Block> register() {
        RegistryObject<Block> reg = BLOCKS.register("mimic_light_block", MimicLightBlock::new);
        MimicLightItems.ITEMS
                .register("mimic_light_block", () -> new BlockItem(reg.get(), new Item.Properties()));
        return reg;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
