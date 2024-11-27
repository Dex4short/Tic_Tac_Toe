package ai;

import drawables.CardSlot;
import drawables.TicTacToeBox;
import objects.Player;

public class AI_BombAttack_Normal extends AI_BombAttack{
	
	public AI_BombAttack_Normal() {
		
	}
	@Override
	public AI_Move onDoBombAttack(Player current_challenger, CardSlot card_slot) {
		if(!card_slot.hasCurrentCard()) return null;//return null if no power up cards available

		boolean use_powerup = random.nextInt(100) < 50;
		if(!use_powerup) return null; //return null if ai decides not to use a power up

		if(card_slot.hasReservedCard()) {//check reserved card
			if(random.nextBoolean()) {//swap cards or not
				card_slot.swapCards();
			}
		}


		int
				rows = current_challenger.getTicTacToeBoard().getRows(),
				cols = current_challenger.getTicTacToeBoard().getColumns(),
				row = random.nextInt(rows),//random row
				col = random.nextInt(cols);//random column

		if(!current_challenger.getTicTacToeBoard().isBoardEmpty()) {//check board symbols
			TicTacToeBox box = current_challenger.getTicTacToeBoard().getBox(row, col);
			while(box.getSymbol() == null) {//specify symbol
				row = random.nextInt(rows);//random row
				col = random.nextInt(cols);//random column
				box = current_challenger.getTicTacToeBoard().getBox(row, col);
			}
		}
		System.out.println("(normal)target:" + row + ", " + col);

		card_slot.selectCurrentCard();//select current card in the first slot

		return new AI_Move(row, col); //return the target box(row, column) to use a power up
	}
}
