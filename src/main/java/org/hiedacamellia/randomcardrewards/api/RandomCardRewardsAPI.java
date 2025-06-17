package org.hiedacamellia.randomcardrewards.api;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.hiedacamellia.randomcardrewards.content.card.*;
import org.hiedacamellia.randomcardrewards.core.network.RCRRewardCardS2CMessage;

import java.util.List;

public class RandomCardRewardsAPI {

    public static int createTmpCardPool(List<RCRCard> cards) {
        return TmpCardPoolManager.add(cards);
    }
    public static int createTmpCardPool(List<RCRCard> cards, boolean autoRemove) {
        return TmpCardPoolManager.add(cards,autoRemove);
    }
    public static int createTmpCardPoolFromPool(CardPool pool) {
        return TmpCardPoolManager.add(pool.cards());
    }
    public static int createTmpCardPoolFromPool(CardPool pool, boolean autoRemove) {
        return TmpCardPoolManager.add(pool.cards(),autoRemove);
    }
    public static int createTmpCardPoolFromPoolRandomly(CardPool pool,int count) {
        return TmpCardPoolManager.add(pool.getRandomCards(count));
    }
    public static int createTmpCardPoolFromPoolRandomly(CardPool pool, int count, boolean autoRemove) {
        return TmpCardPoolManager.add(pool.getRandomCards(count),autoRemove);
    }
    public static List<RCRCard> getTmpCardPool(int id) {
        return TmpCardPoolManager.get(id);
    }
    public static void removeTmpCardPool(int id) {
        TmpCardPoolManager.remove(id);
    }
    public static List<CardPool> getAllCardPools() {
        return CardPoolManager.getCardPools();
    }

    public static void rewardPlayerCards(Player player, List<RCRCard> cards){
        if(player instanceof ServerPlayer serverPlayer) {
            int add = TmpCardPoolManager.add(cards);
            RCRRewardCardS2CMessage.send(serverPlayer, add,cards);
        }
    }
    public static void rewardPlayerTmpPool(Player player,int tmpPollId){
        if(player instanceof ServerPlayer serverPlayer) {
            List<RCRCard> pool = TmpCardPoolManager.get(tmpPollId);
            RCRRewardCardS2CMessage.send(serverPlayer, tmpPollId,pool);
        }
    }
}
