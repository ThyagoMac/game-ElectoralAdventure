package br.com.ElectoralAdventure.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.ElectoralAdventure.main.Game;
import br.com.ElectoralAdventure.world.Camera;
import br.com.ElectoralAdventure.world.World;

public class Player extends Entity {

	public boolean right, left, up, down;
	private double speed = 1;
	public int right_direction = 0, left_direction = 1, up_direction = 2, down_direction = 3;
	public int direction = down_direction;
	private double life = 100, maxLife = 100;
	private int ammunition = 0;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	// private BufferedImage[] stopPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		// stopPlayer = new BufferedImage[4];

		for (int i = 0; i < rightPlayer.length; i++) {
			rightPlayer[i] = Game.spriteSheet.getSprite(64 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < leftPlayer.length; i++) {
			leftPlayer[i] = Game.spriteSheet.getSprite(112 - (i * 16), 16, 16, 16);
		}

		upPlayer[0] = Game.spriteSheet.getSprite(48, 0, 16, 16);
		upPlayer[1] = Game.spriteSheet.getSprite(32, 16, 16, 16);
		upPlayer[2] = Game.spriteSheet.getSprite(48, 16, 16, 16);
		upPlayer[3] = Game.spriteSheet.getSprite(144, 0, 16, 16);

		for (int i = 0; i < downPlayer.length; i++) {
			downPlayer[i] = Game.spriteSheet.getSprite(80 + (i * 16), 0, 16, 16);
		}

//		stopPlayer[0] = Game.spriteSheet.getSprite(32, 0, 16, 16);
//		stopPlayer[1] = Game.spriteSheet.getSprite(48, 0, 16, 16);
//		stopPlayer[2] = Game.spriteSheet.getSprite(64, 0, 16, 16);
//		stopPlayer[3] = Game.spriteSheet.getSprite(112, 16, 16, 16);
	}

	public void tick() {
		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			direction = right_direction;
			x += speed; // x += speed;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			direction = left_direction;
			x -= speed;
		}

		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			direction = up_direction;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			direction = down_direction;
			moved = true;
			y += speed;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		this.checkCollisionBeer();
		this.checkCollisionAmmunition();

		Camera.x = Camera.clamp(this.getX() - (Game.getWIDTH() / 2), 0, (World.WIDTH * 16) - Game.getWIDTH());
		Camera.y = Camera.clamp(this.getY() - (Game.getHEIGHT() / 2), 0, (World.HEIGHT * 16) - Game.getHEIGHT());
//		
//		Camera.setX((Camera.clamp( this.getX() - (Game.getWIDTH()/2), 0, (World.WIDTH*16) - Game.getWIDTH())));
//		Camera.setX((Camera.clamp( this.getY() - (Game.getHEIGHT()/2), 0, (World.HEIGHT*16) - Game.getHEIGHT())));

//		Camera.setX( (this.getX() - (Game.getWIDTH()/2)));
//		Camera.setY( (this.getY()- (Game.getHEIGHT()/2)));
	}

	private void checkCollisionAmmunition() {
		for (int i = 0; i < Game.munitions.size(); i++) {
			Ammunition ammunitionAtual = Game.munitions.get(i);

			if (Entity.isColliding(this, ammunitionAtual)) {

				ammunition += 3;
				System.out.println(ammunition);
				Game.munitions.remove(ammunitionAtual);
				Game.entities.remove(ammunitionAtual);
			}
		}
	}

	private void checkCollisionBeer() {
		for (int i = 0; i < Game.beers.size(); i++) {
			Beer beerAtual = Game.beers.get(i);
			// if (e instanceof Beer) { <---<< foi posto em uma lista só de beers...

			if (Entity.isColliding(this, beerAtual)) {
				if (life >= 92) {
					life = 100;
				} else {
					life += 8;
				}
				Game.beers.remove(beerAtual);
				Game.entities.remove(beerAtual);
			}
			// }

		}

	}

	public void render(Graphics g) {

		if (direction == right_direction) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);

		} else if (direction == left_direction) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);

		} else if (direction == up_direction) {
			g.drawImage(upPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);

		} else if (direction == down_direction) {
			g.drawImage(downPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
	}

	public boolean isRight() {
		return right;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public double getSpeed() {
		return speed;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public double getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public double getMaxLife() {
		return maxLife;
	}

	public int getAmmunition() {
		return ammunition;
	}

	public void setAmmunition(int ammunition) {
		this.ammunition = ammunition;
	}

}
