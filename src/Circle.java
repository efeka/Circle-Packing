import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	
	private int x, y, radius;
	private boolean stopped = false;
	
	private CircleList list;
	
	private int collisions = 0;
	
	private int colorMode, colorChange, maxColor;
	private static boolean fillCircles = false;
	
	public Circle(int x, int y, int colorMode, int colorChange, int maxColor, CircleList list) {
		this.x = x;
		this.y = y;
		this.list = list;
		this.colorMode = colorMode;
		this.colorChange = colorChange;
		this.maxColor = maxColor;
	}

	public void tick() {
		if (!stopped)
			grow();
	}
	
	public void render(Graphics g) {
		int R;
		int G;
		int B;
		if (colorMode == 0) {
			R = maxColor;
			G = Math.max(255 - collisions * colorChange, 0);
			B = Math.max(255 - collisions * colorChange, 0);
		}
		else if (colorMode == 1) {
			R = Math.max(255 - collisions * colorChange, 0);
			G = maxColor;
			B = Math.max(255 - collisions * colorChange, 0);
		}
		else {
			R = Math.max(255 - collisions * colorChange, 0);
			G = Math.max(255 - collisions * colorChange, 0);
			B = maxColor;
		}
		g.setColor(new Color(R, G, B));
		if (fillCircles)
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		else
			g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
	}
	
	public void grow() {
		if (x - radius < 0 || x + radius > Main.WIDTH || y - radius < 0 || y + radius > Main.HEIGHT)
			stopped = true;
		for (int i = 0; i < list.getList().size(); i++) {
			if (list.getList().get(i) != this) {
				if (checkCollision(list.getList().get(i))) {
					stopped = true;
					incrementCollisions();
					list.getList().get(i).incrementCollisions();
				}
			}
		}
		if (!stopped)
			radius++;
	}
	
	public void incrementCollisions() {
		collisions++;
	}
	
	public static void toggleFill() {
		fillCircles = !fillCircles;
	}
	
	public boolean checkCollision(Circle c) {
		int x1 = c.getX();
		int y1 = c.getY();
		int distance = (int) Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)); 
		return distance < (radius + c.getRadius());
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
