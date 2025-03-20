package org.hiedacamellia.randomcardrewards.core.util;

import net.minecraft.util.RandomSource;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RCRCardManager {

    private static final RandomSource randomSource = RandomSource.create();

    protected static List<RCRCard> cards;

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
        if(n>cards.size()) return new ArrayList<>(cards);
        Set<RCRCard> cardSet = new HashSet<>();
        while(cardSet.size()<n){
            cardSet.add(getRandomCard());
        }
        return new ArrayList<>(cardSet);
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
        addCard(RCRCard.EMPTY);
    }

}
