package org.hiedacamellia.randomcardrewards.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.client.gui.screen.RCRCardScreen;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.hiedacamellia.randomcardrewards.registries.RCRNetWork;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RCRCardSyncS2CMessage {

    private final List<RCRCard> cards;

    private RCRCardSyncS2CMessage(List<RCRCard> cards) {
        this.cards = cards;
    }

    public static void send(List<RCRCard> cards, ServerPlayer player) {
        RCRNetWork.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(()->player),new RCRCardSyncS2CMessage(cards));
    }

    public static RCRCardSyncS2CMessage decode(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        List<RCRCard> cards = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            cards.add(RCRCard.decode(buffer));
        }
        return new RCRCardSyncS2CMessage(cards);
    }

    public static void encode(RCRCardSyncS2CMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.cards.size());
        message.cards.forEach(card -> RCRCard.encode(card,buffer));
    }



    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        RCRNetWork.addNetworkMessage(RCRCardSyncS2CMessage.class, RCRCardSyncS2CMessage::encode, RCRCardSyncS2CMessage::decode, RCRCardSyncS2CMessage::handleClient);
    }

    public static void handleClient(RCRCardSyncS2CMessage msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {

            if(Minecraft.getInstance().screen!=null){
                if(Minecraft.getInstance().screen instanceof RCRCardScreen){
                    ((RCRCardScreen) Minecraft.getInstance().screen).setCards(msg.cards);
                }

            }else {
                RandomCardRewards.LOGGER.error("Screen is null");
            }

        });
        context.setPacketHandled(true);
    }
}
