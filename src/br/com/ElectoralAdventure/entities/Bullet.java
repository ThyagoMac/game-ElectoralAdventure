package br.com.ElectoralAdventure.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.ElectoralAdventure.main.Game;
import br.com.ElectoralAdventure.world.Camera;

public class Bullet extends Entity {

	private int directionX;
	private int directionY;
	private double speed = 4;

	private int currentBulletLife = 0, maxBulletLife = 50;

	public Bullet(int x, int y, int width, int height, BufferedImage sprite, int directionX, int directionY) {
		super(x, y, width, height, sprite);

		this.directionX = directionX;
		this.directionY = directionY;

	}

	public void tick() {
		x += directionX * speed;
		y += directionY * speed;
		currentBulletLife++;
		if(currentBulletLife == maxBulletLife) {
			Game.bullets.remove(this);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, this.getWidth(), this.getHeight());
	}

}
