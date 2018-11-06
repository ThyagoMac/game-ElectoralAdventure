package br.com.ElectoralAdventure.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.com.ElectoralAdventure.main.Game;
import br.com.ElectoralAdventure.world.Camera;

public class Entity {

	public static BufferedImage BEER_EN = Game.spriteSheet.getSprite(128, 32, 16, 16);
	public static BufferedImage BONSOMINION_EN = Game.spriteSheet.getSprite(32, 32, 16, 16);
	public static BufferedImage AMMUNITION_EN = Game.spriteSheet.getSprite(128, 16, 16, 16);
	public static BufferedImage GUN_EN = Game.spriteSheet.getSprite(16, 16, 16, 16);
	public static BufferedImage GUN_RIGHT =Game.spriteSheet.getSprite(144, 16, 16, 16);
	public static BufferedImage GUN_LEFT =Game.spriteSheet.getSprite(144, 32, 16, 16);
	public static BufferedImage GUN_UP =Game.spriteSheet.getSprite(144, 48, 16, 16);
	public static BufferedImage GUN_DOWN =Game.spriteSheet.getSprite(128, 48, 16, 16);

	protected double x, y;
	private int width, height;

	private BufferedImage sprite;
	private int maskX, maskY, maskW, maskH;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskX = 0;
		this.maskY = 0;
		this.maskW = width;
		this.maskH = height;
	}

	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}


	public void tick() {
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.maskW, e1.maskH);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.maskW, e2.maskH);

		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		//reveal entities masks
		//g.setColor(Color.RED);
		//g.fillRect(this.getX() + maskX - Camera.getX(), this.getY() + maskY - Camera.getY(), maskW, maskH);
	}


	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
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
