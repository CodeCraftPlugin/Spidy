package me.codecraft.datagen;

import me.codecraft.items.Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class RecipeGeneration extends FabricRecipeProvider {


    public RecipeGeneration(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {

        RecipeProvider recipeProvider = new RecipeProvider(provider,recipeOutput) {
            @Override
            public void buildRecipes() {

                shaped(RecipeCategory.TOOLS, Items.SPIDER_SHOTER)
                        .define('d',()-> net.minecraft.world.item.Items.DIAMOND)
                        .define('r',() -> net.minecraft.world.item.Items.REDSTONE)
                        .define('c',() -> net.minecraft.world.item.Items.COBWEB)
                        .pattern("rdr")
                        .pattern("dcd")
                        .pattern("rdr")
                        .unlockedBy("has_diamond",has(net.minecraft.world.item.Items.DIAMOND))
                        .save(recipeOutput);


            }
        };


        return recipeProvider;
    }

    @Override
    public String getName() {
        return "Recipe Generator";
    }
}
