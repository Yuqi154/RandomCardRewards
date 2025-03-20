package org.hiedacamellia.randomcardrewards.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.recipe.CardPoolRecipe;
import org.hiedacamellia.randomcardrewards.core.recipe.CardRecipe;

public class RCRRecipeType {

	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, RandomCardRewards.MODID);

	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, RandomCardRewards.MODID);

	static {
		CardRecipe.TYPE = RECIPE_TYPES.register("card", () -> RecipeType.simple(RandomCardRewards.rl("card")));
		CardPoolRecipe.TYPE = RECIPE_TYPES.register("card_pool", () -> RecipeType.simple(RandomCardRewards.rl("card_pool")));
		CardRecipe.SERIALIZER = SERIALIZERS.register("card", () -> CardRecipe.Serializer.INSTANCE);
		CardPoolRecipe.SERIALIZER = SERIALIZERS.register("card_pool", () -> CardPoolRecipe.Serializer.INSTANCE);
	}
}
