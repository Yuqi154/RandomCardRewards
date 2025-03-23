package org.hiedacamellia.randomcardrewards.core.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
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
                .then(Commands.literal("open_card_menu")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> {
                                    try {
                                        ServerPlayer serverPlayer = EntityArgument.getPlayer(context, "player");
                                        NetworkHooks.openScreen(serverPlayer, RCRCardMenuProvider.INSTANCE);
                                    } catch (Exception e) {
                                        RandomCardRewards.LOGGER.error("Error opening card menu", e);
                                    }
                                    return 0;
                                })
                        ).executes(context -> {
                            ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
                            NetworkHooks.openScreen(serverPlayer, RCRCardMenuProvider.INSTANCE);
                            return 0;
                        }))
                .then(Commands.literal("rewards").then(Commands.argument("pool", StringArgumentType.word())
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> {
                                    try {
                                        ServerPlayer serverPlayer = EntityArgument.getPlayer(context, "player");
                                        String pool = StringArgumentType.getString(context, "pool");
                                        NetworkHooks.openScreen(serverPlayer, new RCRCardMenuProvider(RandomCardRewards.rl(pool)));
                                    } catch (Exception e) {
                                        RandomCardRewards.LOGGER.error("Error opening card menu", e);
                                    }
                                    return 0;
                                })
                        ).executes(context -> {
                            ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
                            String pool = StringArgumentType.getString(context, "pool");
                            NetworkHooks.openScreen(serverPlayer, new RCRCardMenuProvider(RandomCardRewards.rl(pool)));
                            return 0;
                        })))


        );
    }
}
