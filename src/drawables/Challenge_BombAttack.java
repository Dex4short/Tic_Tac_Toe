package drawables;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import objects.Player;

public abstract class Challenge_BombAttack extends Challenge{
	private static final long serialVersionUID = 2220243368717946881L;
	private CardSlot card_slot;
	private Player current_challenger;

	public Challenge_BombAttack(Player challenger1, Player challenger2) {
		super(challenger1, challenger2);
		
		card_slot = new CardSlot() {
			private static final long serialVersionUID = 8605064682722839055L;
			@Override
			public TicTacToeBoard getTicTacToeBoard() {
				return current_challenger.getTicTacToeBoard();
			}
		};
	}
	@Override
	public void draw(Graphics2D g2d) {
		card_slot.setBounds(x, y, width, height);
		card_slot.draw(g2d);
	}
	private int row,col,target_row=-1, target_col=-1, lastTarget_row=-1, lastTarget_col=-1;
	@Override
	public void eventDispatched(AWTEvent event) {
		card_slot.eventDispatched(event);

		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)event;
			
			if(e.getID() == MouseEvent.MOUSE_DRAGGED) {
				if(card_slot.getSelectedBombCard() != null) {
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
	public void onChallengeAccepted(Player player) {
		current_challenger = player;
	}
	@Override
	public void onApplyChallenge(TicTacToeBoard board) {
		detonateBomb(card_slot.getSelectedBombCard());
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
	public void detonateBomb(Card card) {
		onDetonateBomb(card);
	}
	
	public abstract void onDetonateBomb(Card card);
	
	private void highlight_explosion_points(int target_row, int target_col, boolean highlight) {
		card_slot.getSelectedBombCard().getBomb_card().setTarget(target_row, target_col);
		for(Point point: card_slot.getSelectedBombCard().getBomb_card().explosionPoints()) {
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
}
