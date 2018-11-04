package br.com.ElectoralAdventure.world;

public class Camera {

	public static int x = 0;
	public static int y = 0;

	public static int clamp(int xyAtual, int xyMin, int xyMax) {

		if (xyAtual < xyMin) {
			xyAtual = xyMin;
		}

		if (xyAtual > xyMax) {
			xyAtual = xyMax;
		}

		return xyAtual;
	}

	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Camera.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Camera.y = y;
	}

}
