package org.hiedacamellia.randomcardrewards.core.network;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.hiedacamellia.randomcardrewards.client.gui.screen.RCRCardScreen;
import org.hiedacamellia.randomcardrewards.content.card.RCRCard;
import org.hiedacamellia.randomcardrewards.registries.RCRNetWork;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RCRRewardCardS2CMessage {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final int tmpPollId;
    private final List<RCRCard> cardPool;

    public RCRRewardCardS2CMessage(int tmpPollId, List<RCRCard> cardPool) {
        this.tmpPollId = tmpPollId;
        this.cardPool = new ArrayList<>(cardPool);
    }

    public static RCRRewardCardS2CMessage decode(FriendlyByteBuf buffer) {
        int tmpPollId = buffer.readInt();
        int size = buffer.readVarInt();
        List<RCRCard> cardPool = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            RCRCard card = RCRCard.decode(buffer);
            cardPool.add(card);
        }
        return new RCRRewardCardS2CMessage(tmpPollId, cardPool);
    }

    public static void encode(RCRRewardCardS2CMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.tmpPollId);
        buffer.writeVarInt(message.cardPool.size());
        for (RCRCard card : message.cardPool) {
            RCRCard.encode(card, buffer);
        }
    }

    public static void send(ServerPlayer serverPlayer, int tmpPollId, List<RCRCard> cardPool) {
        RCRNetWork.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(()->serverPlayer),new RCRRewardCardS2CMessage(tmpPollId, cardPool));
    }


    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        RCRNetWork.addNetworkMessage(RCRRewardCardS2CMessage.class, RCRRewardCardS2CMessage::encode, RCRRewardCardS2CMessage::decode, RCRRewardCardS2CMessage::handleClient);
    }


    public static void handleClient(RCRRewardCardS2CMessage msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.enqueueWork(() -> {
                Minecraft.getInstance().setScreen(new RCRCardScreen(Component.empty(),msg.cardPool,msg.tmpPollId));
            });
        }
        context.setPacketHandled(true);
    }
}
