package org.hiedacamellia.randomcardrewards.core.command;

import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.common.menu.RCRCardMenuProvider;

@Mod.EventBusSubscriber
public class RCRCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal(RandomCardRewards.MODID)
                .then(Commands.literal("open_card_menu").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    NetworkHooks.openScreen(player, new RCRCardMenuProvider(),player.getOnPos());
                    return 0;
                }))


        );
    }
}
