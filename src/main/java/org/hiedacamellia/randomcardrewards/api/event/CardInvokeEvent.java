package org.hiedacamellia.randomcardrewards.api.event;


import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.hiedacamellia.randomcardrewards.core.card.RCRCard;

public abstract class CardInvokeEvent extends Event {

    private final RCRCard card;
    private final Player player;

    public CardInvokeEvent(RCRCard card, Player player) {
        this.card = card;
        this.player = player;
    }

    public RCRCard getCard() {
        return card;
    }

    public Player getPlayer() {
        return player;
    }

    @Cancelable
    public static class Pre extends CardInvokeEvent {
        public Pre(RCRCard card, Player player) {
            super(card,player);
        }
    }

    public static class Post extends CardInvokeEvent {
        public Post(RCRCard card,Player player) {
            super(card,player);
        }
    }
}
