package org.hiedacamellia.randomcardrewards.api.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import org.hiedacamellia.randomcardrewards.api.RandomCardRewardsAPI;
import org.hiedacamellia.randomcardrewards.content.card.CardPool;
import org.hiedacamellia.randomcardrewards.content.card.RCRCard;

public class RandomCardRewardsPlugin extends KubeJSPlugin {

    @Override
    public void init() {

    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("RCRCardAPI", RandomCardRewardsAPI.class);
        event.add("RCRCard", RCRCard.class);
        event.add("RCRCardPool", CardPool.class);
    }

    @Override
    public void registerEvents() {
        RandomCardRewardsJSEvents.EVENT_GROUP.register();
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        typeWrappers.registerSimple(RCRCard.class, RCRCard::of);
        typeWrappers.registerSimple(CardPool.class, CardPool::of);
    }

}
