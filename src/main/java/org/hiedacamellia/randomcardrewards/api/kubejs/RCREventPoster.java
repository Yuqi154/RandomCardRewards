package org.hiedacamellia.randomcardrewards.api.kubejs;

import net.minecraftforge.fml.ModList;
import org.hiedacamellia.randomcardrewards.api.event.CardInvokeEvent;

public class RCREventPoster {

    public static final RCREventPoster INSTANCE = new RCREventPoster();

    public void post(CardInvokeEvent.Pre event){
        if(kubeJsLoaded) {
            post(new CardInvokeEventJS.Pre(event));
        }
    }
    public void post(CardInvokeEvent.Post event){
        if(kubeJsLoaded) {
            post(new CardInvokeEventJS.Post(event));
        }
    }

    public void post(CardInvokeEventJS.Pre event) {
        RandomCardRewardsJSEvents.CARD_INVOKE_PRE.post(event);
    }

    public void post(CardInvokeEventJS.Post event) {
        RandomCardRewardsJSEvents.CARD_INVOKE_POST.post(event);
    }

    private static final boolean kubeJsLoaded = ModList.get().isLoaded("kubejs");
}
