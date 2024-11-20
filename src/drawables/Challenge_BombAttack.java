package drawables;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import interfaces.Drawable;
import objects.AI;
import objects.Player;

public abstract class Challenge_BombAttack extends Challenge{
	private static final long serialVersionUID = 2220243368717946881L;
	private CardSlot card_slot;
	private CardDeck card_deck;
	private Player current_challenger;
	private ExplosionEffex explosion_effex;

	public Challenge_BombAttack(Player challenger1, Player challenger2) {
		super(challenger1, challenger2);
		
		card_slot = new CardSlot() {
			private static final long serialVersionUID = 8605064682722839055L;
			@Override
			public void onFlipCards() {
				card_deck.nextCard();
			}
			@Override
			public void onActivateCard(Card card) {
				System.out.println("card active");
				detonateBomb(card);
			}
		};
		
		card_deck = new CardDeck() {
			private static final long serialVersionUID = -9187386451136237846L;
			@Override
			public void onTransferCard(Card card) {
				card_slot.pushCard(card);
			}
		};
	}
	@Override
	public void draw(Graphics2D g2d) { 
		if(explosion_effex != null) {
			explosion_effex.draw(g2d);
			
			if(explosion_effex.isCleared()) {
				explosion_effex = null;
			}
		}
		
		card_slot.setBounds(x, y, width, height);
		card_slot.draw(g2d);
		
		card_deck.setBounds(x, y, width, height);
		card_deck.draw(g2d);
		
	}
	private int row,col,target_row=-1, target_col=-1, lastTarget_row=-1, lastTarget_col=-1;
	@Override
	public void eventDispatched(AWTEvent event) {
		if(current_challenger instanceof AI) return;
		
		card_slot.eventDispatched(event);
		
		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)event;
			
			if(e.getID() == MouseEvent.MOUSE_DRAGGED) {
				if(card_slot.getSelectedCard() != null) {
					if(current_challenger.getTicTacToeBoard().contains(e.getPoint())){					
						find_target_box(e.getPoint());
						if(target_row!=lastTarget_row || target_col!=lastTarget_col) {
							highlight_explosion_points(lastTarget_row, lastTarget_col, false);
							highlight_explosion_points(target_row, target_col, true);
						}
					}
					else {
						highlight_explosion_points(lastTarget_row, lastTarget_col, false);
						target_row = -1;
						target_col = -1;
					}
					lastTarget_row = target_row;
					lastTarget_col = target_col;
				}
			}
		}
	}
	@Override
	public void onAction() {
		boolean powerUp_available = ai_powerup.doBombAttackChallenge(current_challenger, card_slot);
		if(!powerUp_available) {
			((AI)current_challenger).getDefaultAction().action();
		}
	}
	@Override
	public void onChallengeAccepted(Player player) {
		current_challenger = player;
		card_slot.flipCards();
		
		if(player instanceof AI) {
			((AI)player).setAction(this);
		}
	}
	@Override
	public void onGameStart() {}
	@Override
	public void onPauseGame() {}
	@Override
	public void onResumeGame() {}
	@Override
	public void onSuspendGame() {}
	@Override
	public void onGameOver() {}
	public Player getCurrentChallenger() {
		return current_challenger;
	}
	public CardSlot getCardSlot() {
		return card_slot;
	}
	public void detonateBomb(Card card) {
		System.out.println("detonating bomb");
		explosion_effex = new ExplosionEffex(card.getPowerUp().explosionPoints());
		onDetonateBomb(card);
	}
	
	public abstract void onDetonateBomb(Card card);
	
	private void highlight_explosion_points(int target_row, int target_col, boolean highlight) {
		card_slot.getSelectedCard().getPowerUp().setTarget(target_row, target_col);
		for(Point point: card_slot.getSelectedCard().getPowerUp().explosionPoints()) {
			if(current_challenger.getTicTacToeBoard().getBox(point.x, point.y) != null) {
				current_challenger.getTicTacToeBoard().getBox(point.x, point.y).setHighlighted(highlight);
			}
		}
	}
	private void find_target_box(Point point) {
		for(row=0; row<current_challenger.getTicTacToeBoard().getRows(); row++) {
			for(col=0; col<current_challenger.getTicTacToeBoard().getColumns(); col++) {								
				if(current_challenger.getTicTacToeBoard().getBox(row, col).contains(point)) {
					target_row = row;
					target_col = col;
					break;
				}
			}
		}
	}
	
	private class ExplosionEffex implements Drawable{
		private Point points[];
		private Explosion explosions[];
		private boolean isClear=false;
		
		public ExplosionEffex(Point points[]) {
			this.points = points;
			
			explosions = new Explosion[points.length];
			TicTacToeBox box;
			for(int n=0; n<explosions.length; n++) {
				box = current_challenger.getTicTacToeBoard().getBox(points[n].x, points[n].y);
				
				if(box != null) {
					explosions[n] = new Explosion();
					explosions[n].setBounds(box.getBounds());
				}
			}
		}
		private int n;
		@Override
		public void draw(Graphics2D g2d) {			
			for(n=0; n<points.length; n++) {
				if(explosions[n] == null) continue;
				explosions[n].draw(g2d);
			}
			
			for(n=0; n<points.length; n++) {
				if(explosions[n] == null) continue;
				if(!explosions[n].isCleared()) {
					return;
				}
			}
			isClear = true;
		}
		public boolean isCleared() {
			return isClear;
		}
	}
}
