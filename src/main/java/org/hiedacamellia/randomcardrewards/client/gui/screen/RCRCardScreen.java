package org.hiedacamellia.randomcardrewards.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.client.gui.widget.RCRButton;
import org.hiedacamellia.randomcardrewards.client.gui.widget.RewardCardWidget;
import org.hiedacamellia.randomcardrewards.common.menu.RCRCardMenu;
import org.hiedacamellia.randomcardrewards.core.network.RCRCardInvokeC2SMessage;
import org.hiedacamellia.randomcardrewards.core.util.CardPool;
import org.hiedacamellia.randomcardrewards.core.util.CardPoolManager;
import org.hiedacamellia.randomcardrewards.core.util.RCRCard;

import java.util.ArrayList;
import java.util.List;

public class RCRCardScreen extends AbstractContainerScreen<RCRCardMenu> {

    public List<RewardCardWidget> cardWidgets;
    public List<RCRCard> cards;
    private CardPool cardPool;

    private RCRButton left;
    private RCRButton right;
    private RCRButton confirm;

    private int startIndex = 0;

    private int selected = -1;

    private ContainerData data;

    public static final ResourceLocation BACKGROUND = RandomCardRewards.rl("textures/gui/gui.png");

    public RCRCardScreen(RCRCardMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.cards = new ArrayList<>();
        this.imageWidth = 164;
        this.imageHeight = 210;
        this.data = menu.data;
        this.cardPool = CardPoolManager.getCardPool(data.get(0));
        this.cards = cardPool.cards();
    }

    public void setCards(List<RCRCard> cards){
        this.cards = cards;
        reset();
    }

    @Override
    public void init(){
        super.init();
        int startX = leftPos+22;
        int startY = topPos+32;
        cardWidgets=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int finalA = i;
            cardWidgets.add(new RewardCardWidget(startX, startY,(widget)->{
                selected = finalA;
            }));
            startY += 48;
        }
        reset();
        cardWidgets.forEach(this::addRenderableWidget);

        left = new RCRButton(leftPos+12, topPos+212, 20, 20, Component.literal("<"), (button)->{
            if(startIndex>0){
                startIndex--;
                reset();
            }
        });
        right = new RCRButton(leftPos+172-12-20, topPos+212, 20, 20, Component.literal(">"), (button)->{
            if(startIndex+3<cards.size()){
                startIndex++;
                reset();
            }
        });
        confirm = new RCRButton(leftPos+172/2-50, topPos+212, 100, 20, Component.literal("Confirm"), (button)->{
            if(selected!=-1){
                int cardIndex = cardPool.getCardIndex(cardWidgets.get(selected).getCard());
                int poolIndex = CardPoolManager.getCardPoolIndex(cardPool.id());
                RCRCardInvokeC2SMessage.send(poolIndex, cardIndex);
            }
        });
        addRenderableWidget(left);
        addRenderableWidget(right);
        addRenderableWidget(confirm);
    }

    public void reset(){
        for (int i = startIndex; i < cards.size(); i++) {
            if(i-startIndex>=cardWidgets.size()) break;
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
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, 180, this.imageWidth,
                180);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {

    }
}
