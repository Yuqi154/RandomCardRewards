package org.hiedacamellia.randomcardrewards.common.menu;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.hiedacamellia.randomcardrewards.core.util.RCRCardManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RCRCardMenuProvider implements MenuProvider {

    private final List<RCRCard> cards;

    public static final RCRCardMenuProvider INSTANCE = new RCRCardMenuProvider(3);

    public RCRCardMenuProvider(int i){
        this.cards = RCRCardManager.getRandomCards(i);
    }
    public RCRCardMenuProvider(List<RCRCard> cards){
        this.cards = cards;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        if(cards!=null) {
            cards.forEach(
                    card -> {
                        RCRCard.encode(card, buf);
                    }
            );
        }else {
            for (int i1 = 0; i1 < RCRCardManager.getRandomCards(3).size(); i1++) {
                RCRCard.encode(RCRCardManager.getRandomCards(3).get(i1), buf);
            }
        }
        return new RCRCardMenu(i,inventory, buf);
    }
}
