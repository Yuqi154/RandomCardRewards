package org.hiedacamellia.randomcardrewards.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.common.menu.RCRCardMenu;

public class RCRMenu {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, RandomCardRewards.MODID);
	public static final RegistryObject<MenuType<RCRCardMenu>> CARD_MENU = MENU_TYPES.register("card_menu", () -> IForgeMenuType.create(RCRCardMenu::new));

}
