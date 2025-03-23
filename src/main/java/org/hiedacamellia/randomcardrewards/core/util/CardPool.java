package org.hiedacamellia.randomcardrewards.core.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;

import java.util.ArrayList;
import java.util.List;

public record CardPool(List<RCRCard> cards, ResourceLocation id) {

    public static final CardPool EMPTY = new CardPool(new ArrayList<>(), RandomCardRewards.rl("empty"));

    private static final RandomSource randomSource = RandomSource.create();

    public RCRCard getCard(int index){
        return cards.get(index);
    }

    public RCRCard getRandomCard(){
        return cards.get(randomSource.nextInt(cards.size()));
    }

    public List<RCRCard> getRandomCards(int n){
        //不重复获取
        RandomCardRewards.LOGGER.info("Getting "+n+" random cards");
        RandomCardRewards.LOGGER.info("Total cards: "+cards.size());

        if(n>cards.size()) {
            RandomCardRewards.LOGGER.warn("Not enough cards, returning with empty");
            List<RCRCard> empty = new ArrayList<>(cards);
            for(int i=0;i<n;i++){
                empty.add(RCRCard.EMPTY);
            }
            return empty;
        }
        ArrayList<RCRCard> cardSet = new ArrayList<>();

        ArrayList<RCRCard> rcrCards = new ArrayList<>(cards);
        for(int i=0;i<n;i++){
            RCRCard card = rcrCards.get(randomSource.nextInt(rcrCards.size()));
            cardSet.add(card);
            rcrCards.remove(card);
        }
        return new ArrayList<>(cardSet);
    }
}
