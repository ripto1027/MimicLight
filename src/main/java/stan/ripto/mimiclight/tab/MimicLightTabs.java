package stan.ripto.mimiclight.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;

public class MimicLightTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MimicLight.MOD_ID);

    public static void register(IEventBus bus) {
        TABS.register(
                "mimic_light_tab",
                () -> CreativeModeTab.builder()
                        .title(Component.literal("Mimic Light"))
                        .icon(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get().asItem()::getDefaultInstance)
                        .displayItems(
                                (param, output) ->
                                        output.accept(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get())
                        )
                        .build()
        );
        TABS.register(bus);
    }
}
