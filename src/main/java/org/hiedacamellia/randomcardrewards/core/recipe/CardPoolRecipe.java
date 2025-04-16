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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CardPoolRecipe implements Recipe<Inventory> {

    private final ResourceLocation id;
    private final List<ResourceLocation> pool;

    public CardPoolRecipe(ResourceLocation id, List<ResourceLocation> pool) {
        this.id = id;
        this.pool = pool;
    }

    public static RegistryObject<RecipeSerializer<CardPoolRecipe>> SERIALIZER;
    public static RegistryObject<RecipeType<CardPoolRecipe>> TYPE;

    public static class Serializer implements RecipeSerializer<CardPoolRecipe> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public CardPoolRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "id"));
            JsonArray pool1 = json.get("pool").getAsJsonArray();
            List<ResourceLocation> rcrCard = new ArrayList<>();
            for (int i = 0; i < pool1.asList().size(); i++) {
                rcrCard.add(new ResourceLocation(pool1.get(i).getAsString()));
            }
            return new CardPoolRecipe(id, rcrCard);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CardPoolRecipe recipe) {
            buffer.writeResourceLocation(recipe.id);
            buffer.writeVarInt(recipe.pool.size());
            recipe.pool.forEach(buffer::writeResourceLocation);
        }

        @Nullable
        @Override
        public CardPoolRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf byteBuf) {
            ResourceLocation id = byteBuf.readResourceLocation();
            int size = byteBuf.readVarInt();
            List<ResourceLocation> pool = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                pool.add(byteBuf.readResourceLocation());
            }
            return new CardPoolRecipe(id, pool);
        }

    }


    public List<ResourceLocation> getPool() {
        return pool;
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

    public static CardPoolRecipe getRecipe(RecipeManager recipeManager, ResourceLocation id) {
        if (recipeManager != null) {
	        for (CardPoolRecipe recipe : recipeManager.getAllRecipesFor(TYPE.get())) {
	            if (recipe.getId().equals(id)) {
	                return recipe;
	            }
	        }
        }
        return null;
    }

}
