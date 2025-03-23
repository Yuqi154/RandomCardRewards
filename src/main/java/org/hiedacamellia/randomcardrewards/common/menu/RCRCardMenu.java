package org.hiedacamellia.randomcardrewards.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.network.RCRCardSyncS2CMessage;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.hiedacamellia.randomcardrewards.registries.RCRMenu;
import org.hiedacamellia.randomcardrewards.registries.RCRNetWork;

import java.util.ArrayList;
import java.util.List;


public class RCRCardMenu extends AbstractContainerMenu {

    public final Player player;
    public final Level level;
    public final List<RCRCard> cards;

    private static  List<RCRCard> fromNetwork(FriendlyByteBuf buf) {
        if(buf==null) return new ArrayList<>();
        List<RCRCard> cards = new ArrayList<>();
        try {
            for (int i = 0; i < 3 && buf.isReadable(); i++) {
                cards.add(RCRCard.decode(buf));
            }
            while(cards.size()<3){
                cards.add(RCRCard.EMPTY);
            }
        }catch (Exception e){
            RandomCardRewards.LOGGER.error("Error decoding card from network",e);
        }
        return cards;
    }

    public RCRCardMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, fromNetwork(buf));
    }

    public RCRCardMenu(int id, Inventory inv, List<RCRCard> cards) {
        super(RCRMenu.CARD_MENU.get(), id);
        this.player = inv.player;
        this.level = inv.player.level();
        this.cards = cards;
        if(this.player instanceof ServerPlayer){
            RCRCardSyncS2CMessage.send(cards,(ServerPlayer) this.player);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
