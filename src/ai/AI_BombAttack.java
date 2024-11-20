package ai;

import java.awt.Point;
import java.util.Random;

import drawables.Card;
import drawables.CardSlot;
import drawables.TicTacToeBox;
import extras.Timing;
import objects.Player;

public abstract class AI_BombAttack{
	protected static final Random random = new Random();

	public AI_BombAttack() {
		
	}
	public boolean doBombAttackChallenge(Player current_challenger, CardSlot card_slot) {
		AI_Move ai_move = onDoBombAttack(current_challenger, card_slot);
		
		if(ai_move != null) {
			new Thread() {
				public void run() {
					new Timing().sleep(1000);
					Card selected_card = card_slot.getSelectedCard();//get the selected card
					
					new Timing().sleep(500);
					selected_card.getPowerUp().setTarget(ai_move.row, ai_move.col);//set the power up target(row, col) to the selected box
					TicTacToeBox box;
					
					box = current_challenger.getTicTacToeBoard().getBox(ai_move.row, ai_move.col);	//bounds(x,y,w,h) of selected TicTacToe Box
					selected_card.setLocation(										//position(x,y) the card at the center of selected box
							box.x + (box.width/2) - (selected_card.width/2),
							box.y + (box.height/2) - (selected_card.height/2)
					);

					new Timing().sleep(500);
					Point points[] = selected_card.getPowerUp().explosionPoints();
					for(Point point: points) {
						box = current_challenger.getTicTacToeBoard().getBox(point.x, point.y);
						if(box != null) {
							box.setHighlighted(true);
						}
					}

					new Timing().sleep(500); 
					card_slot.activateCard();//activate the selected card
					for(Point point: points) {
						box = current_challenger.getTicTacToeBoard().getBox(point.x, point.y);
						if(box != null) {
							box.setHighlighted(false);
						}
					}

					new Timing().sleep(1000); 
				};
			}.start();
			return true;//a bomb attack is made
		}
		return false;//no bomb attack is made
	}
	public abstract AI_Move onDoBombAttack(Player current_challenger, CardSlot card_slot);
}
