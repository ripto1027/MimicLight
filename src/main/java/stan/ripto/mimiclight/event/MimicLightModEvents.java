package stan.ripto.mimiclight.event;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;
import stan.ripto.mimiclight.model.MimicLightBakedModel;

import java.util.Map;

@Mod.EventBusSubscriber(modid = MimicLight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MimicLightModEvents {
    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> models = event.getModels();

        ResourceLocation id = MimicLightBlocks.MIMIC_LIGHT_BLOCK.getId();
        if (id == null) return;

        ModelResourceLocation modelLocation = new ModelResourceLocation(id, "");
        BakedModel originalModel = models.get(modelLocation);
        BakedModel customModel = new MimicLightBakedModel(originalModel);

        event.getModels().put(modelLocation, customModel);
    }
}
