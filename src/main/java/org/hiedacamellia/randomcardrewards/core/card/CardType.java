package org.hiedacamellia.randomcardrewards.core.card;

import com.google.gson.JsonObject;

public enum CardType {
    ITEM("item"),
    COMMAND("command"),
    EFFECT("effect"),
    NONE("none")
    ;

    private final String type;
    CardType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static CardType fromString(String type) {
        for (CardType cardType : values()) {
            if (cardType.getType().equals(type)) {
                return cardType;
            }
        }
        return null;
    }

    public static CardType fromJson(JsonObject json){
        return fromString(json.get("type").getAsString());
    }

    public static void toJson(CardType cardType, JsonObject json){
        json.addProperty("type", cardType.getType());
    }
}
