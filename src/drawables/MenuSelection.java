package drawables;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import enums.Challenge;
import enums.Difficulty;
import enums.GameMode;
import enums.GridType;
import interfaces.Action;
import interfaces.ButtonModel;
import interfaces.DrawableClip;
import objects.GamePlay;
import res.Resource;
import sound.Sound;

public abstract class MenuSelection implements DrawableClip, AWTEventListener{
	private int selection_x, selection_y,selection_w, selection_h, selection_arc=20, s;
	private Selection selections[];
	private BasicStroke stroke;
	private Font font;
	private GamePlay game_play;
	
	public MenuSelection() {
		stroke = new BasicStroke(5);
		
		selections = new Selection[]{
			new ButtonPlay(),
			new ButtonDifficulty(),
			new ButtonGrid(),
			new ButtonMode(),
			new ButtonChallenge(),
			new ButtonAbout()
		};
		selections[1].disable(true);
		
		font = new Font("Calibri", Font.BOLD, 20);
		game_play = new GamePlay();
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		selection_w = w/3;
		selection_h = h/3;
		selection_x = x + (w/2) - (selection_w/2);
		selection_y = y + (int)(h*0.66) - (selection_h/2);
		
		g2d.setColor(Resource.main_color[2]);
		g2d.fillRoundRect(selection_x, selection_y, selection_w, selection_h, selection_arc, selection_arc);
		
		g2d.setColor(Resource.main_color[3]);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(selection_x, selection_y, selection_w, selection_h, selection_arc, selection_arc);
		
		drawSelections(g2d, selection_x, selection_y, selection_w, selection_h);
	}
	@Override
	public void eventDispatched(AWTEvent event) {		
		for(Selection selection: selections) {
			selection.eventDispatched(event);
		}
	}
	public abstract void onPlay(GamePlay game_play);
	public abstract void onAbout();
	
	private void drawSelections(Graphics2D g2d, int x, int y, int w, int h) {
		for(s=0; s<selections.length; s++) {
			selections[s].drawClip(
					g2d, 
					x + 5, 
					y + 5 + (s*((h - 10) / selections.length)),
					w - 10, 
					(h/selections.length) - 5
			);
		}
	}
	
	public abstract class Selection extends Rectangle implements DrawableClip,AWTEventListener,ButtonModel,Action{
		private static final long serialVersionUID = -7267045785208769956L;
		private int arc=20;
		private AlphaComposite alpha_composite, normal_composite;
		private String text;
		private boolean hover,pressed,disable;

		public Selection(String text) {
			this.text = text;
			
			disable = false;
			alpha_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			normal_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
			setBounds(x, y, w, h);
			
			if(isDisabled()) {
				g2d.setComposite(alpha_composite);
			}

			if(pressed) {
				g2d.setColor(Resource.main_color[0].darker());
			}
			else if(hover) {
				g2d.setColor(Resource.main_color[0].brighter());
			}
			else {
				g2d.setColor(Resource.main_color[0]);
			}
			g2d.fillRoundRect(x, y, w, h, arc, arc);
			
			g2d.setFont(font);
			g2d.setColor(Resource.main_color[2]);
			g2d.drawString(
					text,
					x + (w/2) - (g2d.getFontMetrics().stringWidth(text)/2),
					y + (h/2) + (g2d.getFontMetrics().getAscent()/2)
			);
			
			if(isDisabled()) {
				g2d.setComposite(normal_composite);
			}
		}
		@Override
		public void eventDispatched(AWTEvent event) {
			if(isDisabled()) return;
			
			if(event instanceof MouseEvent) {
				MouseEvent e = (MouseEvent)event;
				
				switch(e.getID()) {
				case MouseEvent.MOUSE_PRESSED:
					onPress(e);
					break;
				case MouseEvent.MOUSE_RELEASED:
					onRelease(e);
					break;
				case MouseEvent.MOUSE_CLICKED:
					onClicked(e);
					break;
				case MouseEvent.MOUSE_MOVED:
					onHover(e);
					break;
				}
			}
		}
		private int sound_flag=0;
		@Override
		public void onHover(MouseEvent e) {
			if(getBounds().contains(e.getPoint())) {
				if(sound_flag==1) {
					Sound.playOnHoverButton();
					sound_flag = 0;
				}
				hover = true;
			}
			else {
				if(sound_flag==0) {
					sound_flag = 1;
				}
				hover = false;
			}
		}
		@Override
		public void onPress(MouseEvent e) {
			if(getBounds().contains(e.getPoint())) {
				pressed = true;
			}
		}
		@Override
		public void onRelease(MouseEvent e) {
			if(getBounds().contains(e.getPoint())) {
				pressed = false;
			}
		}
		@Override
		public void onClicked(MouseEvent e) {
			if(getBounds().contains(e.getPoint()))	{
				Sound.playOnClickButton();
				onAction();
			}
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public void disable(boolean disable) {
			this.disable = disable;
		}
		public boolean isDisabled() {
			return disable;
		}
	}
	
	private class ButtonPlay extends Selection{
		private static final long serialVersionUID = -4550683785373592173L;
		
		public ButtonPlay() {
			super("Play");
		}
		@Override
		public void onAction() {
			onPlay(game_play);
		}
		
	}
	private class SwappableButton extends Selection{
		private static final long serialVersionUID = 8941139149007209602L;
		private String text,swaps[];
		private int selected;

		public SwappableButton(String text, String swaps[]) {
			super(text + " : " + swaps[0]);
			this.text = text;
			this.swaps = swaps;
			selected = 0;
		}
		@Override
		public void onAction() {
			selected++;
			if(selected == swaps.length) {
				selected=0;
			}
			setText(text + " : " + swaps[selected]);
		}
		public String getSelected() {
			return swaps[selected];
		}
	}
	private class ButtonMode extends SwappableButton{
		private static final long serialVersionUID = -4550683785373592173L;
		
		public ButtonMode() {
			super("Mode", new String[] {"PvP","PvCom"});
		}
		@Override
		public void onAction() {
			super.onAction();
			if(getSelected().equals("PvP")) {
				game_play.setGame_mode(GameMode.PvP);
				selections[1].disable(true);
			}
			else if(getSelected().equals("PvCom")) {
				game_play.setGame_mode(GameMode.PvCom);
				selections[1].disable(false);
			}
		}
	}
	private class ButtonDifficulty extends SwappableButton{
		private static final long serialVersionUID = 4249264716971655694L;

		public ButtonDifficulty() {
			super("Difficulty", new String[] {"Easy", "Normal", "Hard" });
		}
		@Override
		public void onAction() {
			super.onAction();
			if(getSelected().equals("Easy")) {
				game_play.setDifficulty(Difficulty.Easy);
			}
			else if(getSelected().equals("Normal")) {
				game_play.setDifficulty(Difficulty.Normal);
			}
			else if(getSelected().equals("Hard")) {
				game_play.setDifficulty(Difficulty.Hard);
			}
		}
	}
	private class ButtonGrid extends SwappableButton{
		private static final long serialVersionUID = 4249264716971655694L;

		public ButtonGrid() {
			super("Grid", new String[] {"3x3", "6x6", "9x9" });
		}
		@Override
		public void onAction() {
			super.onAction();
			if(getSelected().equals("3x3")) {
				game_play.setGrid_type(GridType.Grid_3x3);
			}
			else if(getSelected().equals("6x6")) {
				game_play.setGrid_type(GridType.Grid_6x6);
			}
			else if(getSelected().equals("9x9")) {
				game_play.setGrid_type(GridType.Grid_9x9);
			}
		}
	}
	private class ButtonChallenge extends SwappableButton{
		private static final long serialVersionUID = 1528737073575125893L;
		
		public ButtonChallenge() {
			super("Challenge", new String[] {"None", "Fast Play", "Bomb Attack"});
		}
		@Override
		public void onAction() {
			super.onAction();
			if(getSelected().equals("None")) {
				game_play.setChallenge(Challenge.None);
			}
			else if(getSelected().equals("Fast Play")) {
				game_play.setChallenge(Challenge.FastPLay);
			}
			else if(getSelected().equals("Bomb Attack")) {
				game_play.setChallenge(Challenge.BombAttack);
			}
		}
	}
	private class ButtonAbout extends Selection{
		private static final long serialVersionUID = -7117907692472680449L;

		public ButtonAbout() {
			super("About");
		}

		@Override
		public void onAction() {
			onAbout();
		}
		
	}
}
