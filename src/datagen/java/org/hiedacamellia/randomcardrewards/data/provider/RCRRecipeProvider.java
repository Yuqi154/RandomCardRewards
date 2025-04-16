package org.hiedacamellia.randomcardrewards.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.card.CardContent;
import org.hiedacamellia.randomcardrewards.core.card.RCRCard;
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
                CardContent.effect(MobEffects.DAMAGE_BOOST,10,1000)
        );
        RCRCard rcrCard1 = new RCRCard(
                RandomCardRewards.rl("test1"),
                "test1",
                "test",
                RandomCardRewards.rl("test1"),
                CardContent.command("time set day")
        );
        RCRCard rcrCard2 = new RCRCard(
                RandomCardRewards.rl("test2"),
                "test2",
                "descriptionKey",
                RandomCardRewards.rl("test2"),
                CardContent.item(Items.IRON_INGOT,2)
        );
        RCRCard rcrCard3 = new RCRCard(
                RandomCardRewards.rl("test3"),
                "test3",
                "descriptionKey",
                RandomCardRewards.rl("test3"),
                CardContent.item(Items.DIAMOND,12)
        );

        CardRecipeBuilder.card(rcrCard).save(consumer);
        CardRecipeBuilder.card(rcrCard1).save(consumer);
        CardRecipeBuilder.card(rcrCard2).save(consumer);
        CardRecipeBuilder.card(rcrCard3).save(consumer);

        CardPoolRecipeBuilder.card(List.of(rcrCard,rcrCard1,rcrCard2,rcrCard3))
                .save(consumer,RandomCardRewards.rl("test_pool"));

    }

}
