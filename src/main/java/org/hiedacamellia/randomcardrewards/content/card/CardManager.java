package org.hiedacamellia.randomcardrewards.content.card;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeManager;
import org.hiedacamellia.randomcardrewards.core.recipe.CardRecipe;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManager {

    private static final Map<ResourceLocation, RCRCard> map = new HashMap<>();

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void addCard(ResourceLocation id, RCRCard card) {
        if (map.containsKey(id)) {
            LOGGER.warn("Card with id {} already exists, replacing it", id);
        }
        map.put(id, card);
    }

    public static void load(RecipeManager recipeManager){
        map.clear();
        long startTime = System.currentTimeMillis();
        LOGGER.info("Loading cards...");
        List<CardRecipe> cards = recipeManager.getAllRecipesFor(CardRecipe.TYPE.get());
        cards.forEach(
            cardRecipe -> {
                RCRCard card = cardRecipe.getCard();
                if (card != null) {
                    map.put(card.id(), card);
                }
            }
        );
        long endTime = System.currentTimeMillis();
        LOGGER.info("Loaded {} cards in {} ms", map.size(), endTime - startTime);
    }

    public static RCRCard getCard(String id) {
        return getCard(new ResourceLocation(id));
    }
    public static RCRCard getCard(ResourceLocation id) {
        return map.getOrDefault(id, RCRCard.EMPTY);
    }

    public static boolean hasCard(ResourceLocation id) {
        return map.containsKey(id);
    }
    public static boolean hasCard(String id) {
        return hasCard(new ResourceLocation(id));
    }
}
