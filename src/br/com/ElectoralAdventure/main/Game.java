package br.com.ElectoralAdventure.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import br.com.ElectoralAdventure.entities.Ammunition;
import br.com.ElectoralAdventure.entities.Beer;
import br.com.ElectoralAdventure.entities.Bonsominion;
import br.com.ElectoralAdventure.entities.Bullet;
import br.com.ElectoralAdventure.entities.Entity;
import br.com.ElectoralAdventure.entities.Gun;
import br.com.ElectoralAdventure.entities.Player;
import br.com.ElectoralAdventure.graphics.SpriteSheet;
import br.com.ElectoralAdventure.graphics.UserInterface;
import br.com.ElectoralAdventure.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private final String GAME_VERSION = "Electoral Adventure v0.3.13";
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final static int WIDTH = 260;
	private final static int HEIGHT = 140;
	private final int SCALE = 5;
	private boolean restartGame = false;

	public static int curLevel = 1;

	private int maxLevel = 3;

	private BufferedImage image;

	// mthod follow3
	public static List<Bonsominion> bonsominions;
	public static List<Gun> guns;
	public static List<Beer> beers;
	public static List<Ammunition> munitions;
	public static List<Bullet> bullets;
	public static List<Entity> entities;
	public static SpriteSheet spriteSheet;

	public static World world;
	public static Player player;
	public static Random random;

	public UserInterface ui;

	public static String gameState = "NORMAL";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;

	public Game() {
		random = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();

		// init entities
		ui = new UserInterface();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		// start game
		gameStart(curLevel);
	}

	public static void gameStart(int level) {
		entities = new ArrayList<Entity>();
		// mthod follow3
		bonsominions = new ArrayList<Bonsominion>();
		guns = new ArrayList<Gun>();
		beers = new ArrayList<Beer>();
		munitions = new ArrayList<Ammunition>();
		bullets = new ArrayList<Bullet>();
		spriteSheet = new SpriteSheet("/spritesheet.png");
		player = new Player(25, 25, 16, 16, spriteSheet.getSprite(32, 0, 16, 16));
		world = new World("/map0" + level + ".png");
		entities.add(player);

	}

	private void initFrame() {
		frame = new JFrame(GAME_VERSION);
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		if (gameState == "NORMAL") {
			// tickando todos as entities
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}

			nextLevelCheck();
		}else if (gameState == "GAME_OVER") {
			this.framesGameOver++;
			if (this.framesGameOver == 30) {
				this.framesGameOver=0;
				if(showMessageGameOver)
					this.showMessageGameOver = false;
				else
					this.showMessageGameOver = true;
			}
		}else if (gameState == "WIN") {
			this.framesGameOver++;
			if (this.framesGameOver == 30) {
				this.framesGameOver=0;
				if(showMessageGameOver)
					this.showMessageGameOver = false;
				else
					this.showMessageGameOver = true;
			}
		}
	}

	public void nextLevelCheck() {
		if (bonsominions.size() == 0) {
			curLevel++;
			if (curLevel > maxLevel) {
				curLevel = 1;
				gameState = "WIN";
				// gameStart();
			}
			gameStart(curLevel);
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Graphics2d g2 = (Graphics2d) g;
		// renderizando todas as entities
		world.render(g);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}

		ui.render(g);

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString((int) Game.player.getLife() + " / " + (int) Game.player.getMaxLife(), 100, 37);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Ammo: " + player.getAmmunition(), 26, 65);

		if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 35));
			g.drawString("PARECE QUE BONSONARO VENCEU AS ELEIÇÕES NÃO É MESMO?!?!?!", 50, 310);
			g.setFont(new Font("arial", Font.BOLD, 35));
			if (showMessageGameOver)
				g.drawString(">Pressione 'ENTER' para tentar novamente.<", 260, 510);

			if (restartGame) {
				gameStart(curLevel);
				gameState = "NORMAL";
			}

		} else if (gameState == "WIN") {

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 32));
			g.drawString("PARECE QUE BONSONARO VENCEU AS ELEIÇÕES DO MESMO JEITO NÃO É?!?!?!", 17, 310);
			g.setFont(new Font("arial", Font.BOLD, 35));
			if (showMessageGameOver)
				g.drawString(">Pressione 'ENTER' para tentar reiniciar.<", 260, 410);
			
			if (restartGame) {
				curLevel=1;
				gameStart(curLevel);
				gameState = "NORMAL";
			}

		}
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double nanoSeconds = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSeconds;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		//System.out.println(e.getKeyCode());
//		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
//			System.out.println("direita");
//		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
//			System.out.println("esquerda");
//		}
//
//		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
//			System.out.println("cima");
//		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
//			System.out.println("baixo");
//		}

		if (e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			player.setRight(true);
		} else if (e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			player.setLeft(true);
		}

		if (e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			player.setUp(true);
		} else if (e.getKeyCode() == 83 || e.getKeyCode() == 40) {
			player.setDown(true);
		}

		if (e.getKeyCode() == 88 || e.getKeyCode() == 17) {
			player.shoot = true;
		}

		if (e.getKeyCode() == 10) {
			restartGame = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			player.setRight(false);
		} else if (e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			player.setLeft(false);
		}

		if (e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			player.setUp(false);
		} else if (e.getKeyCode() == 83 || e.getKeyCode() == 40) {
			player.setDown(false);
		}

		if (e.getKeyCode() == 88 || e.getKeyCode() == 17) {
			player.shoot = false;
		}
		if (e.getKeyCode() == 10) {
			restartGame = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX() / SCALE);
		player.my = (e.getY() / SCALE);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		player.mouseShoot = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

}
