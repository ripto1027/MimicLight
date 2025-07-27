package stan.ripto.mimiclight.event;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;
import stan.ripto.mimiclight.blockentity.MimicLightBlockEntity;
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
        BakedModel currentModel = models.get(modelLocation);
        BakedModel customModel = new MimicLightBakedModel(currentModel);

        event.getModels().put(modelLocation, customModel);
    }

    @SubscribeEvent
    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();

        event.register((state, level, pos, tintIndex) -> {
            if (level == null || pos == null) return 0xFFFFFF;

            BlockEntity tile = level.getBlockEntity(pos);
            if (!(tile instanceof MimicLightBlockEntity mimic)) return 0xFFFFFF;

            BlockState copiedState = mimic.getCopiedState();
            if (!mimic.hasCopiedState()) return 0xFFFFFF;

            return blockColors.getColor(copiedState, level, pos, tintIndex);

        }, MimicLightBlocks.MIMIC_LIGHT_BLOCK.get());
    }

    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            event.accept(MimicLightBlocks.MIMIC_LIGHT_BLOCK.get());
        }
    }
}
