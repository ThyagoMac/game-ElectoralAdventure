package br.com.ElectoralAdventure.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public String[] options = { "Novo Jogo", "Continuar", "Carregar", "Salvar", "Sair" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;

	public void tick() {

		if (up) {
			up = false;
			currentOption--;

			if (currentOption < 0) {
				currentOption = maxOption;
			}
		} else if (down) {
			down = false;
			currentOption++;

			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}

		if (enter) {
			enter = false;
			if (options[currentOption] == "Continuar") {
				Game.gameState = "NORMAL";
			} else if (options[currentOption] == "Novo Jogo") {
				Game.gameStart(1);
				Game.gameState = "NORMAL";
			} else if (options[currentOption] == "Sair") {
				System.exit(1);
			}
		}
	}

	public void render(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(0, 0, 0, 220));
		g.fillRect(0, 0, Game.getWIDTH() * Game.SCALE, Game.getHEIGHT() * Game.SCALE);

		g.setColor(Color.GRAY);
		g.setFont(new Font("arial", Font.BOLD, 32));
		g.drawString(Game.GAME_VERSION, 440, 80);

		// menu options
		g.setColor(Color.WHITE);

		for (int i = 0; i < options.length; i++) {
			g.setFont(new Font("arial", Font.BOLD, 26));
			g.drawString(options[i], 460, 180 + (i * 35));
		}

//		g.setFont(new Font("arial", Font.BOLD, 26));
//		g.drawString("Novo Jogo", 460, 180);
//
//		g.setFont(new Font("arial", Font.BOLD, 26));
//		g.drawString("Carregar", 460, 215);
//
//		g.setFont(new Font("arial", Font.BOLD, 26));
//		g.drawString("Salvar", 460, 250);
//
//		g.setFont(new Font("arial", Font.BOLD, 26));
//		g.drawString("Sair", 460, 285);


		if (options[currentOption] == "Novo Jogo") {
			g.drawString("> ", 435, 180);
		} else if (options[currentOption] == "Continuar") {
			g.drawString("> ", 435, 215);
		} else if (options[currentOption] == "Carregar") {
			g.drawString("> ", 435, 250);
		} else if (options[currentOption] == "Salvar") {
			g.drawString("> ", 435, 285);
		} else if (options[currentOption] == "Sair") {
			g.drawString("> ", 435, 325);
		}

	}
}
