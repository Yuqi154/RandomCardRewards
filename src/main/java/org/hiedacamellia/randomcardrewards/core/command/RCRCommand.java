package org.hiedacamellia.randomcardrewards.core.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.api.RandomCardRewardsAPI;
import org.hiedacamellia.randomcardrewards.content.card.CardPool;

@Mod.EventBusSubscriber
public class RCRCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal(RandomCardRewards.MODID)
                .then(Commands.literal("rewards").then(Commands.argument("pool", StringArgumentType.word())
                        .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> {
                                            ServerPlayer serverPlayer = EntityArgument.getPlayer(context, "player");
                                            String pool = StringArgumentType.getString(context, "pool");
                                            int tmpPollId = RandomCardRewardsAPI.createTmpCardPoolFromPoolRandomly(CardPool.of(pool), IntegerArgumentType.getInteger(context, "count"));
                                            RandomCardRewardsAPI.rewardPlayerTmpPool(serverPlayer, tmpPollId);
                                            return 0;
                                        })
                                ).executes(context -> {
                                    ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
                                    String pool = StringArgumentType.getString(context, "pool");
                                    int tmpPollId = RandomCardRewardsAPI.createTmpCardPoolFromPoolRandomly(CardPool.of(pool), IntegerArgumentType.getInteger(context, "count"));
                                    RandomCardRewardsAPI.rewardPlayerTmpPool(serverPlayer, tmpPollId);
                                    return 0;
                                }))))
        );
    }
}
