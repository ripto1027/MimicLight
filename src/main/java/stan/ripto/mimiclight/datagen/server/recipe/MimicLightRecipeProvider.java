package stan.ripto.mimiclight.datagen.server.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import stan.ripto.mimiclight.MimicLight;
import stan.ripto.mimiclight.block.MimicLightBlocks;

import java.util.function.Consumer;

public class MimicLightRecipeProvider extends RecipeProvider {
    public MimicLightRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, MimicLightBlocks.MIMIC_LIGHT_BLOCK.get())
                .requires(Items.GLOWSTONE, 2)
                .group(MimicLight.MOD_ID)
                .unlockedBy(getHasName(Items.GLOWSTONE), has(Items.GLOWSTONE))
                .save(pWriter, "mimic_light_block");
    }
}
