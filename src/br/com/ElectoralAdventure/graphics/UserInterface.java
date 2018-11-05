package br.com.ElectoralAdventure.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import br.com.ElectoralAdventure.main.Game;

public class UserInterface {
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(5, 3, 50, 5);
		g.setColor(Color.GREEN);
		g.fillRect(5, 3, (int)((Game.player.getLife()/Game.player.getMaxLife())*50), 5);
		g.setColor(Color.WHITE);
		g.setFont(new Font ("arial", Font.BOLD, 8));
		g.drawString("Life: " + (int)Game.player.getLife() +" / "+ (int)Game.player.getMaxLife() , 5, 16);
	}
	
}
