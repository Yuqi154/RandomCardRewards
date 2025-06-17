package org.hiedacamellia.randomcardrewards.api.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.entity.player.Player;
import org.hiedacamellia.randomcardrewards.api.event.CardInvokeEvent;
import org.hiedacamellia.randomcardrewards.content.card.RCRCard;

public abstract class CardInvokeEventJS extends EventJS {

    public final CardInvokeEvent event;

    public CardInvokeEventJS(CardInvokeEvent event) {
        this.event = event;
    }

    public RCRCard getCard() {
        return event.getCard();
    }

    public Player getPlayer() {
        return event.getPlayer();
    }

    public static class Pre extends CardInvokeEventJS {

        public Pre(CardInvokeEvent.Pre event) {
            super(event);
        }

        public void setCanceled(boolean cancel) {
            event.setCanceled(cancel);
        }
    }

    public static class Post extends CardInvokeEventJS {

        public Post(CardInvokeEvent.Post event) {
            super(event);
        }
    }
}
