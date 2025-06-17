package org.hiedacamellia.randomcardrewards.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.content.card.CardType;
import org.hiedacamellia.randomcardrewards.content.card.RCRCard;

import java.util.List;
import java.util.Objects;

public class RewardCardWidget extends RCRButton {

    private RCRCard card = RCRCard.EMPTY;

    public static final ResourceLocation BACKGROUND = RandomCardRewards.rl("textures/gui/card_newbg.png");
    public static final ResourceLocation BACKGROUND_CHOOSED = RandomCardRewards.rl("textures/gui/card_newbg_choosed.png");

    public RewardCardWidget(int x, int y,OnPress onPress) {
        super(x, y, 96, 128, Component.empty(),onPress);
    }

    public void setCard(RCRCard card) {
        this.card = card;
    }

    public RCRCard getCard() {
        return card;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.blit(isFocused()?BACKGROUND_CHOOSED:BACKGROUND,this.getX(),this.getY(),this.getWidth(),this.getHeight(),0,0,96,128,96,128);
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawCenteredString(font, card.getName(), this.getX() + 48, this.getY() + 51, 0xFFFFFF);
        Component description = card.getDescription();
        if(isHovered())
            guiGraphics.renderComponentTooltip(font,List.of(description),mouseX,mouseY);

        if (Objects.requireNonNull(card.content().type()) == CardType.ITEM) {
            ResourceLocation resourceLocation = new ResourceLocation(card.content().content());
            ItemStack itemStack = ForgeRegistries.ITEMS.getValue(resourceLocation).getDefaultInstance();
            guiGraphics.renderItem(itemStack, this.getX() + 40, this.getY() + 26);
        } else {
            guiGraphics.blit(card.texture(), this.getX() + 40, this.getY() + 26, 16, 16, 0, 0, 16, 16, 16, 16);
        }

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
