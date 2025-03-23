package org.hiedacamellia.randomcardrewards.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.CardContent;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.hiedacamellia.randomcardrewards.data.builder.CardPoolRecipeBuilder;
import org.hiedacamellia.randomcardrewards.data.builder.CardRecipeBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RCRRecipeProvider extends RecipeProvider {
    public RCRRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        RCRCard rcrCard = new RCRCard(
                RandomCardRewards.rl("test"),
                "test",
                "test",
                RandomCardRewards.rl("test"),
                CardContent.EMPTY
        );

        CardRecipeBuilder.card(rcrCard).save(consumer);

        CardPoolRecipeBuilder.card(List.of(rcrCard))
                .save(consumer,RandomCardRewards.rl("test_pool"));

    }

}
