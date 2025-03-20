package org.hiedacamellia.randomcardrewards.core.util;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;

public record CardContent(CardType type, String content) {

    public static CardContent of(CardType type, String content) {
        return new CardContent(type, content);
    }

    public static void encode(CardContent cardContent, FriendlyByteBuf buffer) {
        CardType.encode(cardContent.type, buffer);
        buffer.writeUtf(cardContent.content);
    }

    public static CardContent decode(FriendlyByteBuf buffer) {
        CardType type = CardType.decode(buffer);
        String content = buffer.readUtf();
        return CardContent.of(type, content);
    }

    public static CardContent fromJson(JsonObject json){
        CardType type = CardType.fromJson(json);
        String content = json.get("content").getAsString();
        return CardContent.of(type, content);
    }

    public static void toJson(CardContent cardContent, JsonObject json){
        CardType.toJson(cardContent.type, json);
        json.addProperty("content", cardContent.content);
    }
}
