package org.hiedacamellia.randomcardrewards.api.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class RandomCardRewardsPlugin extends KubeJSPlugin {

    @Override
    public void init() {

    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("RCRCardPoolManager",RandomCardRewardsWrapper.class);
    }

    @Override
    public void registerEvents() {
        RandomCardRewardsJSEvents.EVENT_GROUP.register();
    }


}
