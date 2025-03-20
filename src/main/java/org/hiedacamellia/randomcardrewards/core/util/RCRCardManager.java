package org.hiedacamellia.randomcardrewards.core.util;

import net.minecraft.util.RandomSource;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RCRCardManager {

    private static final RandomSource randomSource = RandomSource.create();

    protected static List<RCRCard> cards = new ArrayList<>();

    public static void addCard(RCRCard card){
        cards.add(card);
    }

    public static RCRCard getCard(int index){
        return cards.get(index);
    }

    public static void removeCard(RCRCard card){
        cards.remove(card);
    }

    public static RCRCard getRandomCard(){
        return cards.get(randomSource.nextInt(cards.size()));
    }

    public static List<RCRCard> getRandomCards(int n){
        //不重复获取
        RandomCardRewards.LOGGER.info("Getting "+n+" random cards");
        RandomCardRewards.LOGGER.info("Total cards: "+cards.size());

        if(n>cards.size()) {
            RandomCardRewards.LOGGER.warn("Not enough cards, returning empty");
            List<RCRCard> empty = new ArrayList<>();
            for(int i=0;i<n;i++){
                empty.add(RCRCard.EMPTY);
            }
            return empty;
        }
        Set<RCRCard> cardSet = new HashSet<>();
        while(cardSet.size()<n){
            cardSet.add(getRandomCard());
        }
        return new ArrayList<>(cardSet);
    }


    @SubscribeEvent
    public void onSetup(FMLCommonSetupEvent event) {
        RandomCardRewards.LOGGER.info("Setting up RCRCardManager");
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
    }

}
