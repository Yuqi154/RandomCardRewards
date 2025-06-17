package org.hiedacamellia.randomcardrewards;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.hiedacamellia.randomcardrewards.core.config.RCRCommonConfig;
import org.hiedacamellia.randomcardrewards.content.card.CardPoolManager;
import org.hiedacamellia.randomcardrewards.registries.RCRMenu;
import org.hiedacamellia.randomcardrewards.registries.RCRRecipeType;
import org.slf4j.Logger;


@Mod(RandomCardRewards.MODID)
public class RandomCardRewards {


    public static final String MODID = "randomcardrewards";

    public static final Logger LOGGER = LogUtils.getLogger();


    public RandomCardRewards() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RCRRecipeType.RECIPE_TYPES.register(modEventBus);
        RCRRecipeType.SERIALIZERS.register(modEventBus);
        RCRMenu.MENU_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(CardPoolManager::onAddResourceReloadListener);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RCRCommonConfig.SPEC);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MODID, path);
    }

}
