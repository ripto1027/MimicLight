package stan.ripto.mimiclight;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import stan.ripto.mimiclight.block.MimicLightBlocks;
import stan.ripto.mimiclight.blockentity.MimicLightBlockEntities;
import stan.ripto.mimiclight.item.MimicLightItems;
import stan.ripto.mimiclight.tab.MimicLightTabs;

@Mod(MimicLight.MOD_ID)
public class MimicLight {
    public static final String MOD_ID = "mimiclight";

    public MimicLight(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        MimicLightBlocks.register(bus);
        MimicLightItems.register(bus);
        MimicLightBlockEntities.register(bus);
        MimicLightTabs.register(bus);
    }
}
