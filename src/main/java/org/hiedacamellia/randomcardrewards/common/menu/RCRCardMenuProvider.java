package org.hiedacamellia.randomcardrewards.common.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.RCRCardManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class RCRCardMenuProvider implements MenuProvider {

    public static final RCRCardMenuProvider INSTANCE = new RCRCardMenuProvider();

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        RandomCardRewards.LOGGER.info("Creating RCRCardMenu");
        return new RCRCardMenu(i,inventory, RCRCardManager.getRandomCards(4));
    }
}
