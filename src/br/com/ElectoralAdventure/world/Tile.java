package br.com.ElectoralAdventure.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.ElectoralAdventure.main.Game;

public class Tile {
	
	public static BufferedImage TILE_GRASS = Game.spriteSheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL = Game.spriteSheet.getSprite(16, 0, 16, 16);
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.getX(), y - Camera.getY(), null);
	}
}
