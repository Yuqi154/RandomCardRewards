package org.hiedacamellia.randomcardrewards.data.builder;

import com.google.gson.JsonArray;
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
import org.hiedacamellia.randomcardrewards.core.recipe.CardPoolRecipe;
import org.hiedacamellia.randomcardrewards.core.card.RCRCard;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CardPoolRecipeBuilder implements RecipeBuilder {

    private final List<RCRCard> cards;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    protected CardPoolRecipeBuilder(List<RCRCard> cards) {
        this.cards = cards;
    }

    public static CardPoolRecipeBuilder card(List<RCRCard> cards) {
        return new CardPoolRecipeBuilder(cards);
    }
    public static CardPoolRecipeBuilder card(RCRCard... cards) {
        return card(List.of(cards));
    }

    @Override
    public CardPoolRecipeBuilder unlockedBy(String s, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(s, criterionTriggerInstance);
        return this;
    }

    @Override
    public CardPoolRecipeBuilder group(@Nullable String s) {
        return null;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new Result(pRecipeId,cards, this.advancement, pRecipeId.withPrefix("recipes/rcrcardpools/")));
    }

    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final List<RCRCard> cards;

        public Result(ResourceLocation id, List<RCRCard> cards, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.cards = cards;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray pool = new JsonArray();
            for (int i = 0; i < cards.size(); i++) {
                pool.add(cards.get(i).id().toString());
            }
            json.add("pool", pool);
            json.addProperty("id", id.toString());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return CardPoolRecipe.SERIALIZER.get();
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
