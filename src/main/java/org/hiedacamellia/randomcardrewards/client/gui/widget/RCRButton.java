package org.hiedacamellia.randomcardrewards.client.gui.widget;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;

public class RCRButton extends AbstractButton {


    protected final OnPress onPress;

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(RCRButton var1);
    }

    public RCRButton(int x, int y, int w, int h, Component component, OnPress onPress) {
        super(x, y, w, h, component);
        this.onPress = onPress;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.renderWidget(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }
}
