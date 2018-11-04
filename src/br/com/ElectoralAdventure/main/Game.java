package br.com.ElectoralAdventure.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import br.com.ElectoralAdventure.entities.Entity;
import br.com.ElectoralAdventure.entities.Player;
import br.com.ElectoralAdventure.graphics.SpriteSheet;
import br.com.ElectoralAdventure.world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	private static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final static int WIDTH = 260;
	private final static int HEIGHT = 140;
	private final int SCALE = 4;

	private BufferedImage image;

	public static List<Entity> entities;
	public static SpriteSheet spriteSheet;
	
	private static World world;
	
	public static Player player;

	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();

		// init entities
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		spriteSheet = new SpriteSheet("/spritesheet.png");
		player = new Player(25, 25, 16, 16, spriteSheet.getSprite(32, 0, 16, 16)); 
		world = new World("/map01.png");
		entities.add(player);
	}

	private void initFrame() {
		frame = new JFrame("Electoral Adventure v0.2.5");
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
		// tickando todos as entities
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
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

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
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

//		System.out.println(e.getKeyCode());
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
