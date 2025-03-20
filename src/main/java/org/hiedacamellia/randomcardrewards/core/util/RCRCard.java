package org.hiedacamellia.randomcardrewards.core.util;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;

public record RCRCard(ResourceLocation id,String nameKey,String descriptionKey,ResourceLocation texture,CardContent content) {

    public static final RCRCard EMPTY = new RCRCard(RandomCardRewards.rl("empty"), "empty", "empty", RandomCardRewards.rl("empty"), CardContent.EMPTY);

    public Component getName(){
        return Component.translatable(nameKey);
    }

    public Component getDescription(){
        return Component.translatable(descriptionKey);
    }

    public static void encode(RCRCard card, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(card.id);
        buffer.writeUtf(card.nameKey);
        buffer.writeUtf(card.descriptionKey);
        buffer.writeResourceLocation(card.texture);
        CardContent.encode(card.content, buffer);
    }

    public static RCRCard decode(FriendlyByteBuf buffer) {
        ResourceLocation id = buffer.readResourceLocation();
        String nameKey = buffer.readUtf();
        String descriptionKey = buffer.readUtf();
        ResourceLocation texture = buffer.readResourceLocation();
        CardContent content = CardContent.decode(buffer);
        return new RCRCard(id, nameKey, descriptionKey, texture, content);
    }

    public static RCRCard fromJson(JsonObject object){
        JsonObject meta = object.getAsJsonObject("meta");
        JsonObject content = object.getAsJsonObject("content");

        return new RCRCard(
                new ResourceLocation(meta.get("id").getAsString()),
                meta.get("nameKey").getAsString(),
                meta.get("descriptionKey").getAsString(),
                new ResourceLocation(meta.get("texture").getAsString()),
                CardContent.fromJson(content)
        );
    }

    public static void toJson(RCRCard card, JsonObject object){
        JsonObject meta = new JsonObject();
        meta.addProperty("id", card.id.toString());
        meta.addProperty("nameKey", card.nameKey);
        meta.addProperty("descriptionKey", card.descriptionKey);
        meta.addProperty("texture", card.texture.toString());
        object.add("meta", meta);
        JsonObject content = new JsonObject();
        CardContent.toJson(card.content, content);
        object.add("content", content);
    }
}
