/*
 * Copyright (c) 2024 TeamMoeg
 *
 * This file is part of Frosted Heart.
 *
 * Frosted Heart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Frosted Heart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frosted Heart. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package org.hiedacamellia.randomcardrewards.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;
import org.jetbrains.annotations.Nullable;

public class CardRecipe implements Recipe<Inventory> {

    private final RCRCard card;
    private final ResourceLocation id;

    public CardRecipe(ResourceLocation id, RCRCard card) {
        this.id = id;
        this.card = card;
    }

    public static RegistryObject<RecipeSerializer<CardRecipe>> SERIALIZER;
    public static RegistryObject<RecipeType<CardRecipe>> TYPE;

    public static class Serializer implements RecipeSerializer<CardRecipe> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public CardRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "id"));
            RCRCard rcrCard = RCRCard.fromJson(json);
            return new CardRecipe(id, rcrCard);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CardRecipe recipe) {
            buffer.writeResourceLocation(recipe.id);
            RCRCard.encode(recipe.card, buffer);
        }

        @Nullable
        @Override
        public CardRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf byteBuf) {
            ResourceLocation id = byteBuf.readResourceLocation();
            RCRCard card = RCRCard.decode(byteBuf);
            return new CardRecipe(id, card);
        }

    }

    public RCRCard getCard() {
        return card;
    }

    @Override
    public boolean matches(Inventory iInventory, Level world) {
        return false;
    }

    @Override
    public ItemStack assemble(Inventory inventory, RegistryAccess registryAccess) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE.get();
    }

    public static CardRecipe getRecipe(Level level, ResourceLocation id) {
        if (level != null) {
	        for (CardRecipe recipe : level.getRecipeManager().getAllRecipesFor(TYPE.get())) {
	            if (recipe.getId().equals(id)) {
	                return recipe;
	            }
	        }
        }
        return null;
    }

}
