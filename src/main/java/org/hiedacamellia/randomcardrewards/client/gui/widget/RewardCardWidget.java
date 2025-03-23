package org.hiedacamellia.randomcardrewards.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;

import java.util.List;

public class RewardCardWidget extends AbstractButton {

    private RCRCard card = RCRCard.EMPTY;
    protected final OnPress onPress;

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(RewardCardWidget var1);
    }

    public static final ResourceLocation BACKGROUND_UNCHOOSE = RandomCardRewards.rl("textures/gui/default_background_unchoose.png");
    public static final ResourceLocation BACKGROUND_UNCHOOSED = RandomCardRewards.rl("textures/gui/default_background_unchoosed.png");

    public RewardCardWidget(int x, int y,OnPress onPress) {
        super(x, y, 128, 48, Component.empty());
        this.onPress = onPress;
    }

    public void setCard(RCRCard card) {
        this.card = card;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.blit(isFocused()?BACKGROUND_UNCHOOSED:BACKGROUND_UNCHOOSE,this.getX(),this.getY(),this.getWidth(),this.getHeight(),0,0,128,48,128,48);
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawString(font, card.getName(), this.getX() + 4, this.getY() + 4, 0xFFFFFF);
        Component description = card.getDescription();
        if(isHovered())
            guiGraphics.renderComponentTooltip(font,List.of(description),mouseX,mouseY);


    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
