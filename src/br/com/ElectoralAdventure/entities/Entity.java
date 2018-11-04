package br.com.ElectoralAdventure.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.ElectoralAdventure.main.Game;
import br.com.ElectoralAdventure.world.Camera;

public class Entity {
	
	public static BufferedImage BEER_EN = Game.spriteSheet.getSprite(128, 32, 16, 16);
	public static BufferedImage BONSOMINION_EN = Game.spriteSheet.getSprite(32, 32, 16, 16);
	public static BufferedImage AMMUNITION_EN = Game.spriteSheet.getSprite(128, 16, 16, 16);
	public static BufferedImage GUN_EN = Game.spriteSheet.getSprite(144, 16, 16, 16);
	
	protected int x, y;
	private int width, height;

	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
	}
	
	public void tick() {
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
