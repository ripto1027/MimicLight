package stan.ripto.mimiclight.datagen.client.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;

public class USLanguageProvider extends LanguageProvider {
    public USLanguageProvider(PackOutput output) {
        super(output, MimicLight.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addBlock(MimicLightBlocks.MIMIC_LIGHT_BLOCK, "Mimic Light Block");
    }
}
