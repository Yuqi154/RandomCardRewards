package org.hiedacamellia.randomcardrewards.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.randomcardrewards.RandomCardRewards;
import org.hiedacamellia.randomcardrewards.client.gui.widget.RCRButton;
import org.hiedacamellia.randomcardrewards.client.gui.widget.RewardCardWidget;
import org.hiedacamellia.randomcardrewards.core.network.RCRCardInvokeC2SMessage;
import org.hiedacamellia.randomcardrewards.content.card.RCRCard;

import java.util.ArrayList;
import java.util.List;

public class RCRCardScreen extends Screen {

    public List<RewardCardWidget> cardWidgets;
    public List<RCRCard> cards;

    private RCRButton left;
    private RCRButton right;
    private RCRButton confirm;

    private int startIndex = 0;

    private int selected = -1;

    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;
    private final int tmpPollId;

    public RCRCardScreen(Component component,List<RCRCard> cards,int tmpPollId) {
        super(component);
        this.imageWidth = 328;
        this.imageHeight = 168;
        this.cards = new ArrayList<>(cards);
        this.tmpPollId = tmpPollId;
    }

    @Override
    public void init(){
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        int startX = leftPos;
        int startY = topPos;
        cardWidgets=new ArrayList<>();
        int min = Math.min(cards.size(), 3);
        if(min<3){
            startX+=(96+20)*(3-min)/2;
        }
        for (int i = 0; i < min; i++) {
            int finalA = i;
            cardWidgets.add(new RewardCardWidget(startX, startY,(widget)->{
                selected = finalA;
            }));
            startX += 96+20;
        }
        reset();
        cardWidgets.forEach(this::addRenderableWidget);

        left = new RCRButton(leftPos+72, topPos+imageHeight-25, 20, 20, Component.literal("<"), (button)->{
            if(startIndex>0){
                startIndex--;
                reset();
            }
        });
        right = new RCRButton(leftPos+imageWidth-72-20, topPos+imageHeight-25, 20, 20, Component.literal(">"), (button)->{
            if(startIndex+3<cards.size()){
                startIndex++;
                reset();
            }
        });
        confirm = new RCRButton(leftPos+imageWidth/2-40, topPos+imageHeight-25, 80, 20, Component.literal("Confirm"), (button)->{
            if(selected!=-1){
                RCRCardInvokeC2SMessage.send(tmpPollId, cards.get(startIndex + selected).id());
                onClose();
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
    }

}
