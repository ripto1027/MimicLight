package stan.ripto.mimiclight.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stan.ripto.mimiclight.MimicLight;

public class MimicLightItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MimicLight.MOD_ID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
