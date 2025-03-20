package org.hiedacamellia.randomcardrewards.client.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;

public class RewardCardWidget extends AbstractButton {

    private RCRCard card = RCRCard.EMPTY;

    public static final ResourceLocation BACKGROUND_UNCHOOSE = RandomCardRewards.rl("textures/gui/default_background_unchoose.png");
    public static final ResourceLocation BACKGROUND_UNCHOOSED = RandomCardRewards.rl("textures/gui/default_background_unchoosed.png");

    public RewardCardWidget(int x, int y) {
        super(x, y, 128, 48, Component.empty());
    }

    public void setCard(RCRCard card) {
        this.card = card;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.blit(isFocused()?BACKGROUND_UNCHOOSED:BACKGROUND_UNCHOOSE,this.getX(),this.getY(),this.getWidth(),this.getHeight(),0,0,128,48,128,48);


    }

    @Override
    public void onPress() {

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
