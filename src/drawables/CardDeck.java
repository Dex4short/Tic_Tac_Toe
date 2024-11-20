package drawables;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import enums.BombPowerUp;
import interfaces.Drawable;
import res.Resource;

public abstract class CardDeck extends Rectangle implements Drawable{
	private static final long serialVersionUID = 5822644005124182324L;
	private Card top_card, bottom_card, next_card;
	private Random r;
	private String str_cardDeck;
	private int nextCard_count, card_yTranslate=0, card_idleTime;
	
	public CardDeck() {
		r = new Random();
		
		top_card = new_card();
		bottom_card = new_card();
		
		str_cardDeck = "Next Card in ";
		nextCard_count = 4;
	}
	@Override
	public void draw(Graphics2D g2d) {
		Card.cardScale(width, height);
		
		g2d.setColor(Resource.main_color[2]);
		g2d.setFont(Resource.font[0]);
		g2d.drawString(
				str_cardDeck + nextCard_count, 
				x + (width/2) - (g2d.getFontMetrics().stringWidth(str_cardDeck + nextCard_count)/2),
				y + height - (Card.card_h/2) - 10
		);

		bottom_card.setBounds(x + (width/2) - (Card.card_w/2), y + height - (Card.card_h/2), Card.card_w, Card.card_h);
		bottom_card.draw(g2d);
		
		if(next_card == null) {
			top_card.setBounds(x + (width/2) - (Card.card_w/2), y + height - (Card.card_h/2), Card.card_w, Card.card_h);
			top_card.draw(g2d);
		}
		else {
			next_card.setBounds(x + (width/2) - (Card.card_w/2), y + height - Card.card_h - card_yTranslate, Card.card_w, Card.card_h);
			next_card.draw(g2d);
			
			if(card_yTranslate >= (height/4)) {
				next_card.flipFront();
				if(next_card.isFacedFront()) {
					card_idleTime++;
					if(card_idleTime == 30) {
						transferCard(next_card);
						card_yTranslate = 0;
						card_idleTime = 0;
					}
				}
			}
			else {
				card_yTranslate += 10;
			}
		}
	}
	public void nextCard() {
		if(nextCard_count == 1) {
			next_card = top_card;
			top_card = bottom_card;
			bottom_card = new_card();
		}
		nextCard_count--;
	}
	public void transferCard(Card card) {
		onTransferCard(card);
		next_card = null;
		nextCard_count=3;
	}
	public abstract void onTransferCard(Card card);
	
	private Card new_card() {
		Card card = new Card(BombPowerUp.values()[r.nextInt(8)]);
		card.faceBack();
		return card;
	}
}
