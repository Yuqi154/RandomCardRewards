package org.hiedacamellia.randomcardrewards.core.network;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.hiedacamellia.randomcardrewards.api.event.CardInvokeEvent;
import org.hiedacamellia.randomcardrewards.api.kubejs.RCREventPoster;
import org.hiedacamellia.randomcardrewards.core.card.CardPool;
import org.hiedacamellia.randomcardrewards.core.card.CardPoolManager;
import org.hiedacamellia.randomcardrewards.core.card.RCRCard;
import org.hiedacamellia.randomcardrewards.registries.RCRNetWork;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RCRCardInvokeC2SMessage {
    
    private final int poolid;
    private final int cardid;
    
    public RCRCardInvokeC2SMessage(int poolid, int cardid) {
        this.poolid = poolid;
        this.cardid = cardid;
    }
    
    public static RCRCardInvokeC2SMessage decode(FriendlyByteBuf buffer) {
        int poolid = buffer.readInt();
        int cardid = buffer.readInt();
        return new RCRCardInvokeC2SMessage(poolid, cardid);
    }
    
    public static void encode(RCRCardInvokeC2SMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.poolid);
        buffer.writeInt(message.cardid);
    }

    public static void send(int poolid, int cardid) {
        RCRNetWork.PACKET_HANDLER.sendToServer(new RCRCardInvokeC2SMessage(poolid, cardid));
    }


    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        RCRNetWork.addNetworkMessage(RCRCardInvokeC2SMessage.class, RCRCardInvokeC2SMessage::encode, RCRCardInvokeC2SMessage::decode, RCRCardInvokeC2SMessage::handleServer);
    }


    public static void handleServer(RCRCardInvokeC2SMessage msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if(context.getSender()!=null){
                ServerPlayer serverPlayer = context.getSender();
                CardPool cardPool = CardPoolManager.getCardPool(msg.poolid);
                RCRCard card = cardPool.getCard(msg.cardid);
                CardInvokeEvent.Pre pre = new CardInvokeEvent.Pre(card, serverPlayer);
                MinecraftForge.EVENT_BUS.post(pre);
                RCREventPoster.INSTANCE.post(pre);

                if(pre.isCanceled()) return;

                switch (card.content().type()){
                    case NONE:
                        break;
                    case ITEM: {
                        ResourceLocation resourceLocation = new ResourceLocation(card.content().content());
                        ItemStack itemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(resourceLocation),card.content().i1());
                        serverPlayer.addItem(itemStack);
                        break;
                    }
                    case EFFECT: {
                        ResourceLocation resourceLocation = new ResourceLocation(card.content().content());
                        MobEffect value = ForgeRegistries.MOB_EFFECTS.getValue(resourceLocation);
                        serverPlayer.addEffect(new MobEffectInstance(value,card.content().i2(),card.content().i1()));
                        break;
                    }
                    case COMMAND:{
                        CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack();
                        serverPlayer.server.getCommands().performPrefixedCommand(commandSourceStack, card.content().content());
                    }

                }
                CardInvokeEvent.Post post = new CardInvokeEvent.Post(card, serverPlayer);
                MinecraftForge.EVENT_BUS.post(post);
                RCREventPoster.INSTANCE.post(post);
            }

        });
        context.setPacketHandled(true);
    }
}
