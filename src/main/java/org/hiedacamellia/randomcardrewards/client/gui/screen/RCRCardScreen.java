package org.hiedacamellia.randomcardrewards.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.client.gui.widget.RewardCardWidget;
import org.hiedacamellia.randomcardrewards.common.menu.RCRCardMenu;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;

import java.util.List;

public class RCRCardScreen extends AbstractContainerScreen<RCRCardMenu> {

    public List<RewardCardWidget> cardWidgets;
    public List<RCRCard> cards;

    private int startIndex = 0;

    public static final ResourceLocation BACKGROUND = RandomCardRewards.rl("textures/gui/gui.png");

    public RCRCardScreen(RCRCardMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.cards = menu.cards;
        this.imageWidth = 172;
        this.imageHeight = 208;
    }

    @Override
    public void init(){
        super.init();
        int startX = leftPos+22;
        int startY = topPos+32;
        for (int i = 0; i < 4; i++) {
            RewardCardWidget widget = new RewardCardWidget(startX, startY);
            this.addRenderableWidget(widget);
            startY += 48;
        }
        reset();
        cardWidgets.forEach(this::addRenderableWidget);
    }

    public void reset(){
        for (int i = startIndex; i < cards.size(); i++) {
            cardWidgets.get(i - startIndex).setCard(cards.get(i));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth,
                this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }
}
