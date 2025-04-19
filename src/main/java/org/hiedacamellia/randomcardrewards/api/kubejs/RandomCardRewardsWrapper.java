package org.hiedacamellia.randomcardrewards.api.kubejs;

import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.randomcardrewards.core.card.CardPool;
import org.hiedacamellia.randomcardrewards.core.card.CardPoolManager;

public class RandomCardRewardsWrapper {

    public static final RandomCardRewardsWrapper INSTANCE = new RandomCardRewardsWrapper();

    public static void addCardPool(ResourceLocation id, CardPool pool) {
        CardPoolManager.addCardPool(id, pool);
    }
    public static int getCardPoolIndex(ResourceLocation id){
        return CardPoolManager.getCardPoolIndex(id);
    }
    public static CardPool getCardPool(ResourceLocation id) {
        return CardPoolManager.getCardPool(id);
    }
    public static CardPool getCardPool(int index) {
        return CardPoolManager.getCardPool(index);
    }
}
