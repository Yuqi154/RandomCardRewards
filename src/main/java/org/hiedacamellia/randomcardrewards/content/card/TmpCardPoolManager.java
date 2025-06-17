package org.hiedacamellia.randomcardrewards.content.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TmpCardPoolManager {

    private static final Map<Integer,List<RCRCard>> cardPools = new HashMap<>();
    private static final Map<Integer,Boolean> shouldRemoves = new HashMap<>();

    private static int id = 0;

    public static int add(List<RCRCard> cardPool){
        return add(cardPool, false);
    }
    public static int add(List<RCRCard> cardPool,boolean shouldRemove){
        if(cardPool == null || cardPool.isEmpty()){
            throw new IllegalArgumentException("Card pool cannot be null or empty");
        }
        id++;
        cardPools.put(id,cardPool);
        shouldRemoves.put(id,shouldRemove);
        return id;
    }

    public static List<RCRCard> get(int id){
        return cardPools.get(id);
    }
    public static boolean shouldRemove(int id){
        return shouldRemoves.getOrDefault(id, false);
    }

    public static void remove(int id){
        cardPools.remove(id);
        shouldRemoves.remove(id);
    }

    public static void clear(){
        cardPools.clear();
        shouldRemoves.clear();
        id = 0;
    }
}
