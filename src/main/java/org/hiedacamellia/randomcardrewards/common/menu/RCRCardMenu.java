package org.hiedacamellia.randomcardrewards.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.hiedacamellia.randomcardrewards.core.util.CardContent;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.hiedacamellia.randomcardrewards.registries.RCRMenu;

import java.util.ArrayList;
import java.util.List;


public class RCRCardMenu extends AbstractContainerMenu {

    public final Player player;
    public final Level level;
    public final List<RCRCard> cards;

    public RCRCardMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, new ArrayList<>());
    }

    public RCRCardMenu(int id, Inventory inv, List<RCRCard> cards) {
        super(RCRMenu.CARD_MENU.get(), id);
        this.player = inv.player;
        this.level = inv.player.level();
        this.cards = cards;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
