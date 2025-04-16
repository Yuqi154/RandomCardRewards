package org.hiedacamellia.randomcardrewards.core.card;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public record CardContent(CardType type, String content,int i1,int i2) {

    public static final CardContent EMPTY = new CardContent(CardType.NONE, "",1,0);

    public static CardContent of(CardType type, String content) {
        return of(type, content,1);
    }
    public static CardContent of(CardType type, String content,int i1) {
        return of(type, content,i1,0);
    }
    public static CardContent of(CardType type, String content,int i1,int i2) {
        return new CardContent(type, content,i1,i2);
    }

    public static CardContent item(Item item) {
        return item(item,1);
    }
    public static CardContent item(Item item,int count) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
        return item(key.toString(),count);
    }
    public static CardContent item(String content) {
        return item(content,1);
    }
    public static CardContent item(String content,int count) {
        return of(CardType.ITEM, content,count);
    }
    public static CardContent effect(MobEffect mobEffect) {
        return effect(mobEffect,0);
    }
    public static CardContent effect(MobEffect mobEffect,int amplifier) {
        return effect(mobEffect,amplifier,15);
    }
    public static CardContent effect(MobEffect mobEffect,int amplifier,int duration) {
        ResourceLocation key = ForgeRegistries.MOB_EFFECTS.getKey(mobEffect);
        return effect(key.toString(),amplifier,duration);
    }
    public static CardContent effect(String content) {
        return effect(content,0);
    }
    public static CardContent effect(String content,int amplifier) {
        return effect(content,amplifier,15);
    }
    public static CardContent effect(String content,int amplifier,int duration) {
        return CardContent.of(CardType.EFFECT, content,amplifier,duration);
    }
    public static CardContent command(String content) {
        return CardContent.of(CardType.COMMAND, content);
    }

    public static void encode(CardContent cardContent, FriendlyByteBuf buffer) {
        buffer.writeEnum(cardContent.type);
        buffer.writeUtf(cardContent.content);
        buffer.writeInt(cardContent.i1);
        buffer.writeInt(cardContent.i2);
    }

    public static CardContent decode(FriendlyByteBuf buffer) {
        CardType cardType = buffer.readEnum(CardType.class);
        String content = buffer.readUtf();
        int i1 = buffer.readInt();
        int i2 = buffer.readInt();
        return CardContent.of(cardType, content,i1,i2);
    }

    public static CardContent fromJson(JsonObject json){
        CardType type = CardType.fromJson(json);
        String content = json.get("content").getAsString();
        int i1 = json.get("i1").getAsInt();
        int i2 = json.get("i2").getAsInt();
        return CardContent.of(type, content,i1,i2);
    }

    public static void toJson(CardContent cardContent, JsonObject json){
        CardType.toJson(cardContent.type, json);
        json.addProperty("content", cardContent.content);
        json.addProperty("i1", cardContent.i1);
        json.addProperty("i2", cardContent.i2);
    }
}
