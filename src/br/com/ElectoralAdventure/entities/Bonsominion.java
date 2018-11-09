package br.com.ElectoralAdventure.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.com.ElectoralAdventure.main.Game;
import br.com.ElectoralAdventure.main.Sound;
import br.com.ElectoralAdventure.world.Camera;
import br.com.ElectoralAdventure.world.World;

public class Bonsominion extends Entity {

	private double speed = 1.3;

	private int maskX = 3;
	private int maskY = 3;
	private int maskW = 10;
	private int maskH = 12;

	private boolean isDamage = false;

	private int life = 3;

	// animation part
	private BufferedImage[] rightBonsominion;
	private BufferedImage[] leftBonsominion;
	private BufferedImage[] upBonsominion;
	private BufferedImage[] downBonsominion;
	private BufferedImage[] damageBonsominion;
	private boolean moved = false;
	private int frames = 0, maxFrames = 2, index = 0, maxIndex = 3;

	public int right_direction = 0, left_direction = 1, up_direction = 2, down_direction = 3;
	public int direction = down_direction;
	////

	private int damageFrames = 0;

	public Bonsominion(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightBonsominion = new BufferedImage[4];
		leftBonsominion = new BufferedImage[4];
		upBonsominion = new BufferedImage[4];
		downBonsominion = new BufferedImage[4];
		damageBonsominion = new BufferedImage[1];

		for (int i = 0; i < rightBonsominion.length; i++) {
			rightBonsominion[i] = Game.spriteSheet.getSprite(112 - (i * 16), 32, 16, 16);
		}

		for (int i = 0; i < leftBonsominion.length; i++) {
			leftBonsominion[i] = Game.spriteSheet.getSprite(64 + (i * 16), 48, 16, 16);
		}

		for (int i = 0; i < upBonsominion.length; i++) {
			upBonsominion[i] = Game.spriteSheet.getSprite(0 + (i * 16), 48, 16, 16);
		}

		for (int i = 0; i < downBonsominion.length; i++) {
			downBonsominion[i] = Game.spriteSheet.getSprite(0 + (i * 16), 32, 16, 16);
		}

		damageBonsominion[0] = Game.spriteSheet.getSprite(0, 64, 16, 16);

	}

	public void tick() {
		moved = false;
		// mthod follow3
		if (Game.random.nextInt(100) < 30) {

			if (this.isCollidingPlayer() == false) {

				if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
						&& !isColliding((int) (x + speed), this.getY())) {
					moved = true;
					direction = right_direction;
					x += speed;
				} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
						&& !isColliding((int) (x - speed), this.getY())) {
					moved = true;
					direction = left_direction;
					x -= speed;
				}

				if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
						&& !isColliding(this.getX(), (int) (y + speed))) {
					moved = true;
					direction = up_direction;
					y += speed;
				} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
						&& !isColliding(this.getX(), (int) (y - speed))) {
					moved = true;
					direction = down_direction;
					y -= speed;
				}
			} else {
				// hit check
				if (Game.random.nextInt(100) > 30) {
					
					Sound.hurtEffect.play();

					Game.player.setLife((int) Game.player.getLife() - 1);
					Game.player.isDamage = true;
					//System.out.println("tiche.. Tiche... TICHE!!!" + " Player life: " + Game.player.getLife());

				}
//				else {
//					System.out.println("miss.. Miss... MISS!!!" + " Player life: " + Game.player.getLife());
//				}
			}
		}
		// full follow + zombie % method
//		if (Game.random.nextInt(100) < 30) {
//
//			if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())) {
//				x += speed;
//			} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())) {
//				x -= speed;
//			}
//
//			if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))) {
//				y += speed;
//			} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))) {
//				y -= speed;
//			}
//		}
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

		if (isDamage) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamage = false;
			}
		}

		collidionBullet();
	}

	private boolean collidionBullet() {

		for (int i = 0; i < Game.bullets.size(); i++) {
			Bullet b = Game.bullets.get(i);

			if (Entity.isColliding(this, b)) {
				life--;
				isDamage = true;
				Game.bullets.remove(b);
				if (life == 0) {
					Game.entities.remove(this);
					Game.bonsominions.remove(this);
				}
			}
		}
		return false;
	}

	public void render(Graphics g) {

		if (!isDamage) {

			if (direction == right_direction) {
				g.drawImage(rightBonsominion[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);

			} else if (direction == left_direction) {
				g.drawImage(leftBonsominion[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);

			} else if (direction == up_direction) {
				g.drawImage(upBonsominion[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);

			} else if (direction == down_direction) {
				g.drawImage(downBonsominion[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			}
		} else {
			g.drawImage(damageBonsominion[0], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
	}

	public boolean isCollidingPlayer() {

		Rectangle bonsoCurrent = new Rectangle(this.getX() + maskX, this.getY() + maskY, maskW, maskH);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return bonsoCurrent.intersects(player);
	}

	// mthod follow3 colliding check
	public boolean isColliding(int xNext, int yNext) {
		Rectangle bonsoCurrent = new Rectangle(xNext + maskX, yNext + maskY, maskW, maskH);
		for (int i = 0; i < Game.bonsominions.size(); i++) {
			Bonsominion bonso = Game.bonsominions.get(i);
			if (bonso == this) {
				continue;
			}

			Rectangle targetBonso = new Rectangle(bonso.getX() + maskX, bonso.getY() + maskY, maskW, maskH);
			if (bonsoCurrent.intersects(targetBonso)) {
				return true;
			}
		}
		return false;
	}
	// add mask (hit box)
//	public void render(Graphics g) {
//		super.render(g);
//		g.setColor(Color.RED);
//		g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y, maskW, maskH);
//	}

}
