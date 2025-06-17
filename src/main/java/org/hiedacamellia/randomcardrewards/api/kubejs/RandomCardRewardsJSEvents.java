package org.hiedacamellia.randomcardrewards.api.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;

public interface RandomCardRewardsJSEvents {

    EventGroup EVENT_GROUP = EventGroup.of("RCREvents");

    EventHandler CARD_INVOKE_PRE = EVENT_GROUP.server("cardInvokePre", () -> CardInvokeEventJS.Pre.class);
    EventHandler CARD_INVOKE_POST = EVENT_GROUP.server("cardInvokePost", () -> CardInvokeEventJS.Post.class);


}
