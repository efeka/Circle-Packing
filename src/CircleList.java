import java.util.ArrayList;

public class CircleList {
	
	private ArrayList<Circle> circles;
	
	private int colorMode, colorChange, maxColor;
	
	public CircleList() {
		circles = new ArrayList<Circle>();
		reconfigureColors();
	}
	
	public void reconfigureColors() {
		colorMode = (int) (Math.random() * 3);
		colorChange = (int) (Math.random() * 100);
		maxColor = (int) (Math.random() * 200) + 55;
	}
	
	public boolean addCircle(int x, int y) {
		Circle circle = new Circle(x, y, colorMode, colorChange, maxColor, this);
		boolean valid = true;
		
		for (int i = 0; i < circles.size(); i++)
			if (circles.get(i).checkCollision(circle))
				valid = false;
		
		if (valid)
			circles.add(circle);
		return valid;
	}
	
	public ArrayList<Circle> getList() {
		return circles;
	}
	
	public void clear() {
		circles.clear();
	}
}
