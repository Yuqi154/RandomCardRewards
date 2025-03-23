package org.hiedacamellia.randomcardrewards.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.CardPoolManager;
import org.hiedacamellia.randomcardrewards.registries.RCRMenu;


public class RCRCardMenu extends AbstractContainerMenu {

    public final Player player;
    public final Level level;
    public final ContainerData data = new SimpleContainerData(1);

    private static int fromNetwork(FriendlyByteBuf buf) {
        if(buf.isReadable()) {
            ResourceLocation resourceLocation = buf.readResourceLocation();
            return CardPoolManager.getCardPoolIndex(resourceLocation);
        }else {
            return -1;
        }
    }

    public RCRCardMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, fromNetwork(buf));
    }

    public RCRCardMenu(int id, Inventory inv, int poolid) {
        super(RCRMenu.CARD_MENU.get(), id);
        this.player = inv.player;
        this.level = inv.player.level();
        if(poolid!=-1)
            data.set(0, poolid);
        this.addDataSlots(data);
        RandomCardRewards.LOGGER.info("Card menu created");
        RandomCardRewards.LOGGER.info("Pool id: "+poolid);
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
