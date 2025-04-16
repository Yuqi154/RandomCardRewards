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
import org.hiedacamellia.randomcardrewards.core.card.CardPool;
import org.jetbrains.annotations.Nullable;

public class RCRCardMenuProvider implements MenuProvider {

    private final ResourceLocation resourceLocation;

    public static final RCRCardMenuProvider INSTANCE = new RCRCardMenuProvider(RandomCardRewards.rl("test_pool"));

    public RCRCardMenuProvider(ResourceLocation pool){
        this.resourceLocation = pool;
    }
    public RCRCardMenuProvider(CardPool cardPool){
        this.resourceLocation = cardPool.id();
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeResourceLocation(resourceLocation);
        return new RCRCardMenu(i,inventory, buf);
    }
}
