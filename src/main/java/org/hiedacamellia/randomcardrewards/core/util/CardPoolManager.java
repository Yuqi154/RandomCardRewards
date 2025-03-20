package org.hiedacamellia.randomcardrewards.core.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.recipe.CardPoolRecipe;
import org.hiedacamellia.randomcardrewards.core.recipe.CardRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardPoolManager implements ResourceManagerReloadListener {

    private static RecipeManager recipeManager;
    private static final Map<ResourceLocation,CardPool> map = new HashMap<>();

    public static void addCardPool(ResourceLocation id, CardPool pool) {
        map.put(id, pool);
    }

    public static CardPool getCardPool(ResourceLocation id) {
        if(map.get(id)==null){
            RandomCardRewards.LOGGER.error("Card pool "+id+" not found, returning empty");
            return new CardPool(new ArrayList<>());
        }
        return map.get(id);
    }

    public static void onAddResourceReloadListener(AddReloadListenerEvent event){
        event.addListener(new CardPoolManager());
        recipeManager = event.getServerResources().getRecipeManager();
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        map.clear();

        List<CardPoolRecipe> pools = recipeManager.getAllRecipesFor(CardPoolRecipe.TYPE.get());
        List<CardRecipe> cards = recipeManager.getAllRecipesFor(CardRecipe.TYPE.get());
        List<ResourceLocation> cardrls = cards.stream().map(CardRecipe::getId).toList();

        for (int i = 0; i < pools.size(); i++) {
            CardPoolRecipe cardPoolRecipe = pools.get(i);
            ResourceLocation id = cardPoolRecipe.getId();
            List<ResourceLocation> pool = cardPoolRecipe.getPool();
            List<RCRCard> rcrCards = new ArrayList<>();

            for(int j=0;j<pool.size();j++){
                if(!cardrls.contains(pool.get(j))){
                    RandomCardRewards.LOGGER.error("Card "+pool.get(j)+" not found in card pool "+id);
                }else {
                    int index = cardrls.indexOf(pool.get(j));
                    rcrCards.add(cards.get(index).getCard());
                }
            }
            addCardPool(id,new CardPool(rcrCards));
        }
    }
}
