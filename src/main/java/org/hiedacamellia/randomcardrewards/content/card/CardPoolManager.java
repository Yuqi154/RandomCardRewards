package org.hiedacamellia.randomcardrewards.content.card;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.recipe.CardPoolRecipe;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardPoolManager implements ResourceManagerReloadListener {

    private static RecipeManager recipeManager;
    private static final Map<ResourceLocation, CardPool> map = new HashMap<>();

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void addCardPool(ResourceLocation id, CardPool pool) {
        map.put(id, pool);
    }

    public static CardPool getCardPool(String id) {
        return getCardPool(new ResourceLocation(id));
    }
    public static CardPool getCardPool(ResourceLocation id) {
        return map.getOrDefault(id, CardPool.EMPTY);
    }

    public static void onAddResourceReloadListener(AddReloadListenerEvent event){
        event.addListener(new CardPoolManager());
        recipeManager = event.getServerResources().getRecipeManager();
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        CardManager.load(recipeManager);
        map.clear();
        long startTime = System.currentTimeMillis();
        LOGGER.info("Loading card pools");


        List<CardPoolRecipe> pools = recipeManager.getAllRecipesFor(CardPoolRecipe.TYPE.get());

        for (int i = 0; i < pools.size(); i++) {
            CardPoolRecipe cardPoolRecipe = pools.get(i);
            ResourceLocation id = cardPoolRecipe.getId();
            List<ResourceLocation> pool = cardPoolRecipe.getPool();
            List<RCRCard> rcrCards = new ArrayList<>();

            for(int j=0;j<pool.size();j++){
                if(!CardManager.hasCard(pool.get(j))){
                    LOGGER.error("Card "+pool.get(j)+" not found in card pool "+id);
                }else {
                    rcrCards.add(CardManager.getCard(pool.get(j)));
                }
            }
            addCardPool(id,new CardPool(rcrCards,id));
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("Loaded {} card pools in {} ms", map.size(), endTime - startTime);
    }
}
