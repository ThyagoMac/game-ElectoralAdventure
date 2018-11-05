package br.com.ElectoralAdventure.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.ElectoralAdventure.entities.Ammunition;
import br.com.ElectoralAdventure.entities.Beer;
import br.com.ElectoralAdventure.entities.Bonsominion;
import br.com.ElectoralAdventure.entities.Entity;
import br.com.ElectoralAdventure.entities.Gun;
import br.com.ElectoralAdventure.main.Game;

public class World {

	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {

			// carrega o mapa na variavel
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			// multiplica altura e largura do map para saber quandos pixels
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH * HEIGHT];
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);

			for (int xx = 0; xx < WIDTH; xx++) {
				for (int yy = 0; yy < HEIGHT; yy++) {
					int currentPixel = pixels[xx + (yy * WIDTH)];

					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_GRASS);
					if (currentPixel == 0xFF000000) {
						// GRASS
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_GRASS);
					} else if (currentPixel == 0xFFFFFFFF) {
						// WALL
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					} else if (currentPixel == 0xFF00FF00) {
						// BEER
						Beer beer = new Beer(xx * 16, yy * 16, 16, 16, Entity.BEER_EN);
						beer.setMask(6, 5, 7, 7);
						Game.entities.add(beer);
						Game.beers.add(beer);
					} else if (currentPixel == 0xFFFFFF00) {
						// AMMUNITION
						Game.entities.add(new Ammunition(xx * 16, yy * 16, 16, 16, Entity.AMMUNITION_EN));
					} else if (currentPixel == 0xFF00FFFF) {
						// GUN
						Game.entities.add(new Gun(xx * 16, yy * 16, 16, 16, Entity.GUN_EN));
					} else if (currentPixel == 0xFF0000FF) {
						// PLAYER
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					} else if (currentPixel == 0xFFFF0000) {
						// BONSOMINION
						//mthod follow3
						Bonsominion bon = new Bonsominion(xx * 16, yy * 16, 16, 16, Entity.BONSOMINION_EN);
						Game.entities.add(bon);
						Game.bonsominions.add(bon);
						
//						Game.entities.add(new Bonsominion(xx * 16, yy * 16, 16, 16, Entity.BONSOMINION_EN));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}

	public void render(Graphics g) {
		int xstart = Camera.getX() >> 4;
		int ystart = Camera.getY() >> 4;

		int xfinal = xstart + (Game.getWIDTH() >> 4);
		int yfinal = ystart + (Game.getHEIGHT() >> 4);

		for (int xx = xstart; xx <= xfinal+1; xx++) {
			for (int yy = ystart; yy <= yfinal+1; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
