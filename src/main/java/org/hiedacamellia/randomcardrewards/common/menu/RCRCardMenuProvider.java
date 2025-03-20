package org.hiedacamellia.randomcardrewards.common.menu;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.CardPoolManager;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RCRCardMenuProvider implements MenuProvider {

    private final List<RCRCard> cards;

    public static final RCRCardMenuProvider INSTANCE = new RCRCardMenuProvider(RandomCardRewards.rl("test"));

    public RCRCardMenuProvider(ResourceLocation pool){
        this.cards = CardPoolManager.getCardPool(pool).cards();
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
        }
        return new RCRCardMenu(i,inventory, buf);
    }
}
