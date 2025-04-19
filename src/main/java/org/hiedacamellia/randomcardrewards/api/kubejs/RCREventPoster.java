package org.hiedacamellia.randomcardrewards.api.kubejs;

import net.minecraftforge.fml.ModList;
import org.hiedacamellia.randomcardrewards.api.event.CardInvokeEvent;

public class RCREventPoster {

    public static void post(CardInvokeEvent.Pre event){
        if(kubeJsLoaded) {
            RandomCardRewardsJSEvents.CARD_INVOKE_PRE.post(new CardInvokeEventJS.Pre(event));
        }
    }
    public static void post(CardInvokeEvent.Post event){
        if(kubeJsLoaded) {
            RandomCardRewardsJSEvents.CARD_INVOKE_POST.post(new CardInvokeEventJS.Post(event));
        }
    }

    private static final boolean kubeJsLoaded = ModList.get().isLoaded("kubejs");
}
