package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import extras.RGBA;
import interfaces.Drawable;
import res.Resource;

public abstract class CardSlot extends Rectangle implements Drawable, AWTEventListener{
	private static final long serialVersionUID = 6184563525362625381L;
	private Card cards[][], selected_card, temp_card;
	private BasicStroke stroke, default_stroke;
	private Color deck_bgcolor;
	private RoundRectangle2D card_slot[][];
	private int next_set=-1, next_slot=-1, next_user=1;

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
		
		cards = new Card[2][2];
		//Random r = new Random();
		cards[0][0] = null; //new Card(BombPowerUp.values()[r.nextInt(8)])
		cards[0][1] = null;
		cards[1][0] = null;
		cards[1][1] = null;
		
		//cards[0][0].faceBack();
		//cards[0][1].faceBack();
	}
	private int st,sl,card_x,card_y;
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(deck_bgcolor);
		//cards
		for(st=0; st<2; st++) {//sets
			for(sl=0; sl<2; sl++) {//slots
				card_x = x + (width/(8-sl)) + ((width/(8-sl))*((6-sl)*st)) - (Card.card_w/2);
				card_y = y + (height/(5-sl)) - (Card.card_h/2);
				
				if(selected_card==null) {
					if(st==next_set && sl==next_slot) {
						g2d.setStroke(stroke);
						g2d.drawRoundRect(card_x, card_y, Card.card_w, Card.card_h, 10, 10);
					}
				}
				else {
					if(selected_card.isActivated()) {
						popCard();
					}
				}

				card_slot[st][sl].setRoundRect(card_x, card_y, Card.card_w, Card.card_h, 10, 10);
				g2d.fill(card_slot[st][sl]);
				if(cards[st][sl] != null) {
					if(cards[st][sl] != selected_card) {
						cards[st][sl].setBounds(card_x, card_y, Card.card_w, Card.card_h);
					}
					cards[st][sl].draw(g2d);
				}
				
				if(st==next_set && sl==next_slot) {
					g2d.setStroke(default_stroke);
				}
			}
		}
	}
	private int n_st,n_sl;
	@Override
	public void eventDispatched(AWTEvent event) {		
		if(event instanceof MouseEvent) {			
			MouseEvent e = (MouseEvent)event;
			
			if(e.getID() == MouseEvent.MOUSE_MOVED) {
				if(selected_card == null) {
					next_set = next_user;
					next_slot = -1;
					for(n_st=0; n_st<2; n_st++) {//next_set
						if(n_st != next_user) {
							continue;
						}
						for(n_sl=0; n_sl<2; n_sl++) {//next_slot
							if(card_slot[n_st][n_sl].contains(e.getPoint())) {
								next_set = n_st;
								next_slot = n_sl;
							}
						}
					}
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_PRESSED) {
				if(next_set==next_user) {
					if(next_slot==1) {
						select_card(cards[next_set][1]);
					}
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_DRAGGED) {
				if(selected_card != null) {
					selected_card.setLocation(e.getX()-(Card.card_w/2), e.getY()-(Card.card_h/2));
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_RELEASED) {
				if(selected_card != null) {
					if(TicTacToeBoard.preferedBoardRect().contains(e.getPoint())) {
						activateCard(selected_card);
					}
					else{
						select_card(null);
					}
				}
				else if(next_set == next_user && next_slot!=-1) {
					if(card_slot[next_set][next_slot].contains(e.getPoint())) {
						select_card(null);
						return;
					}
				}
			}
			else if(e.getID() == MouseEvent.MOUSE_CLICKED) {
				if(next_set == next_user) {
					if(next_slot==0) {
						swapCards(next_set);
					}
				}
			}
		}
	}
	public void flipCards() {
		for(Card card_set[]: cards) {
			for(Card card: card_set) {
				if(card!=null) {
					if(card.isFacedFront()) {
						card.flipBack();
					}
					else{
						card.flipFront();
					}
				}
			}
		}
		next_user = 1 - next_user;
		onFlipCards();
	}
	public Card getSelectedBombCard() {
		return selected_card;
	}
	public void activateCard(Card card) {
		if(card != null) {
			card.activate();
			onActivateCard(card);
		}
	}
	public void swapCards(int next_set) {
		temp_card = cards[next_set][0];
		cards[next_set][0] = cards[next_set][1];
		cards[next_set][1] = temp_card;
	}
	public void popCard() {
		selected_card = null;
		cards[1-next_user][1] = null;
		swapCards(1-next_user);
	}
	public void pushCard(Card card) {
		swapCards(next_user);
		cards[next_user][1] = card;
	}
	
	public abstract void onFlipCards();
	public abstract void onActivateCard(Card card);
	
	private void select_card(Card card) {
		selected_card = card;
	}
}
