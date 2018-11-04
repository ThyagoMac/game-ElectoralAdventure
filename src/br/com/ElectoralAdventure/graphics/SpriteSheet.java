package br.com.ElectoralAdventure.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private BufferedImage spriteSheet;

	public SpriteSheet(String path) {
		try {
			spriteSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
	}
}
