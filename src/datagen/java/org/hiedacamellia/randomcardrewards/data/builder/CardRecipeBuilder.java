package org.hiedacamellia.randomcardrewards.data.builder;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.hiedacamellia.randomcardrewards.core.recipe.CardRecipe;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CardRecipeBuilder implements RecipeBuilder {

    private final RCRCard card;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    protected CardRecipeBuilder(RCRCard card) {
        this.card = card;
    }

    public static CardRecipeBuilder card(RCRCard card) {
        return new CardRecipeBuilder(card);
    }

    @Override
    public CardRecipeBuilder unlockedBy(String s, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public CardRecipeBuilder group(@Nullable String s) {
        return null;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new Result(pRecipeId,card, this.advancement, pRecipeId.withPrefix("recipes/rcrcards/")));
    }

    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RCRCard card;

        public Result(ResourceLocation id, RCRCard card, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.card = card;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            RCRCard.toJson(card,json);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return CardRecipe.SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
