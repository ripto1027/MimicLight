package stan.ripto.mimiclight.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.datagen.client.blockstate.MimicLightBlockStateProvider;
import stan.ripto.mimiclight.datagen.client.lang.JapaneseLanguageProvider;
import stan.ripto.mimiclight.datagen.client.lang.USLanguageProvider;
import stan.ripto.mimiclight.datagen.server.loot.MimicLightLootTableProvider;
import stan.ripto.mimiclight.datagen.server.recipe.MimicLightRecipeProvider;

@Mod.EventBusSubscriber(modid = MimicLight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MimicLightDataGenerator {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new MimicLightBlockStateProvider(output, efh));
        gen.addProvider(event.includeClient(), new USLanguageProvider(output));
        gen.addProvider(event.includeClient(), new JapaneseLanguageProvider(output));

        gen.addProvider(event.includeServer(), new MimicLightLootTableProvider(output));
        gen.addProvider(event.includeServer(), new MimicLightRecipeProvider(output));
    }
}
