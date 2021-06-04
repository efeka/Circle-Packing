import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	
	private int x, y, radius;
	
	public Circle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(x, y, radius, radius);
	}
	
	public void grow() {
		radius++;
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
