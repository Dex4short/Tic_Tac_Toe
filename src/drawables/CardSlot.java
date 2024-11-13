package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

import enums.BombPowerUp;
import extras.Metrics;
import extras.RGBA;
import interfaces.Drawable;
import res.Resource;

public abstract class CardSlot extends Rectangle implements Drawable, AWTEventListener{
	private static final long serialVersionUID = 6184563525362625381L;
	private Card deck[][], selected_card, temp_card;
	private BasicStroke stroke, default_stroke;
	private Color deck_bgcolor;
	private RoundRectangle2D card_slot[][];
	private float card_scale;
	private int card_w, card_h, next_deck=-1, next_slot=-1;

	public CardSlot() {
		stroke = new BasicStroke(10);
		default_stroke = new BasicStroke(1);
		deck_bgcolor = RGBA.setAlpha(Resource.main_color[1].darker().darker(), 224);
		card_slot = new RoundRectangle2D[2][2];
		for(int i=0; i<2; i++) {
			for(int ii=0; ii<2; ii++) {
				card_slot[i][ii] = new RoundRectangle2D.Float();
			}
		}
		
		deck = new Card[2][2];
		Random r = new Random();
		deck[0][0] = new Card(BombPowerUp.values()[r.nextInt(8)]);
		deck[0][1] = new Card(BombPowerUp.values()[r.nextInt(8)]);
		deck[1][0] = new Card(BombPowerUp.values()[r.nextInt(8)]);
		deck[1][1] = new Card(BombPowerUp.values()[r.nextInt(8)]);
	}
	private int d,s,card_x,card_y;
	@Override
	public void draw(Graphics2D g2d) {
		card_scale = Metrics.rectLength(width, height) / 1000f;
		card_w = Math.round( 70 * card_scale);
		card_h = Math.round(106 * card_scale);

		g2d.setColor(deck_bgcolor);
		//cards
		for(d=0; d<2; d++) {//deck
			for(s=0; s<2; s++) {//slots
				card_x = x + (width/(8-s)) + ((width/(8-s))*((6-s)*d)) - (card_w/2);
				card_y = y + (height/(5-s)) - (card_h/2);
				
				if(d==next_deck && s==next_slot && selected_card==null) {
					g2d.setStroke(stroke);
					g2d.drawRoundRect(card_x, card_y, card_w, card_h, 10, 10);
				}

				card_slot[d][s].setRoundRect(card_x, card_y, card_w, card_h, 10, 10);
				g2d.fill(card_slot[d][s]);
				if(deck[d][s] != null) {
					if(deck[d][s] != selected_card) {
						deck[d][s].setBounds(card_x, card_y, card_w, card_h);
					}
					g2d.drawImage(
							deck[d][s].getBomb_card().img, 
							deck[d][s].x, 
							deck[d][s].y,
							deck[d][s].width, 
							deck[d][s].height,
							null
					);
				}
				
				if(d==next_deck && s==next_slot) {
					g2d.setStroke(default_stroke);
				}
			}
		}
	}
	private int nd,ns;
	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)event;
			
			if(e.getID() == MouseEvent.MOUSE_PRESSED) {
				if(next_slot==1) {
					selectCard(deck[next_deck][1]);
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_MOVED) {
				if(selected_card == null) {
					next_deck = -1;
					next_slot = -1;
					for(nd=0; nd<2; nd++) {//deck
						for(ns=0; ns<2; ns++) {//slots
							if(deck[nd][ns].contains(e.getPoint())) {
								next_deck = nd;
								next_slot = ns;
							}
						}
					}
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_RELEASED) {
				for(nd=0; nd<2; nd++) {//deck
					for(ns=0; ns<2; ns++) {//slots
						if(card_slot[nd][ns].contains(e.getPoint()) && next_deck==nd && next_slot==ns) {
							selectCard(null);
							return;
						}
					}
				}
				if(!getTicTacToeBoard().contains(e.getPoint())) {
					selectCard(null);
				}
				else{
					placeCard();
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_CLICKED) {
				if(next_deck!=-1 && next_slot==0) {
					swapCard();
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_DRAGGED) {
				if(selected_card != null) {
					selected_card.setLocation(e.getX()-(card_w/2), e.getY()-(card_h/2));
				}
			}
		}
	}
	public Card getSelectedBombCard() {
		return selected_card;
	}
	public void selectCard(Card card) {
		selected_card = card;
	}
	public void swapCard() {
		temp_card = deck[next_deck][0];
		deck[next_deck][0] = deck[next_deck][1];
		deck[next_deck][1] = temp_card;
	}
	public void placeCard() {
		
	}
	
	public abstract TicTacToeBoard getTicTacToeBoard();
	
}
