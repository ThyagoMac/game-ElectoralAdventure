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
	private boolean hasGun = false;

	public boolean shoot = false;

	public boolean isDamage = false;
	private int damageFrames = 0;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] damagePlayer;
	// private BufferedImage[] stopPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		damagePlayer = new BufferedImage[1];

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

		damagePlayer[0] = Game.spriteSheet.getSprite(0, 16, 16, 16);

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
		this.checkCollisionGun();

		if (isDamage) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamage = false;
			}
		}

		if (shoot && hasGun && ammunition > 0) {
			// cria bala e dispara.
			this.shoot = false;
			int dx = 0, dy = 0, px = 0, py = 0;

			if (direction == right_direction) {
				px = 16;
				py = 7;
				dx = 1;
			} else if (direction == left_direction) {
				px = -4;
				py = 7;
				dx = -1;

			}

			if (direction == up_direction) {
				px = 11;
				py = 4;
				dy = -1;
			} else if (direction == down_direction) {
				px = 1;
				py = 12;
				dy = 1;
			}

			ammunition--;
			Bullet bullet = new Bullet(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
			Game.bullets.add(bullet);
		}

		if (life <= 0) {

			Game.gameStart();
			return;
			// fecha o jogo
			// System.exit(1);
		}

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
			Ammunition currentAmmunition = Game.munitions.get(i);

			if (Entity.isColliding(this, currentAmmunition)) {

				ammunition += 3;
				Game.munitions.remove(currentAmmunition);
				Game.entities.remove(currentAmmunition);
			}
		}
	}

	private void checkCollisionBeer() {
		for (int i = 0; i < Game.beers.size(); i++) {
			Beer currendBeer = Game.beers.get(i);

			if (Entity.isColliding(this, currendBeer)) {
				if (life >= 92) {
					life = 100;
				} else {
					life += 8;
				}
				Game.beers.remove(currendBeer);
				Game.entities.remove(currendBeer);
			}
		}
	}

	private void checkCollisionGun() {
		for (int i = 0; i < Game.guns.size(); i++) {
			Gun currentGun = Game.guns.get(i);

			if (Entity.isColliding(this, currentGun)) {

				hasGun = true;
				Game.guns.remove(currentGun);
				Game.entities.remove(currentGun);
			}
		}
	}

	public void render(Graphics g) {

		if (!isDamage) {
			if (direction == right_direction) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				if (hasGun) {
					// arma direita
					g.drawImage(Entity.GUN_RIGHT, this.getX() - Camera.getX() + 9, this.getY() - Camera.getY() + 2,
							null);
				}

			} else if (direction == left_direction) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				if (hasGun) {
					// arma esquerda
					g.drawImage(Entity.GUN_LEFT, this.getX() - Camera.getX() - 9, this.getY() - Camera.getY() + 2,
							null);
				}

			} else if (direction == up_direction) {
				g.drawImage(upPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				if (hasGun) {
					// arma up
					g.drawImage(Entity.GUN_UP, this.getX() - Camera.getX() + 5, this.getY() - Camera.getY(), null);
				}

			} else if (direction == down_direction) {
				g.drawImage(downPlayer[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
				if (hasGun) {
					// arma down
					g.drawImage(Entity.GUN_DOWN, this.getX() - Camera.getX() - 6, this.getY() - Camera.getY() + 7,
							null);
				}
			}
		} else {
			g.drawImage(damagePlayer[0], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
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
