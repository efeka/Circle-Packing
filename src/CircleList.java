import java.util.ArrayList;

public class CircleList {
	
	private ArrayList<Circle> circles;
	
	public CircleList() {
		circles = new ArrayList<Circle>();
	}
	
	public void addCircle(int x, int y) {
		Circle circle = new Circle(x, y, this);
		boolean valid = true;
		
		for (int i = 0; i < circles.size(); i++)
			if (circles.get(i).checkCollision(circle))
				valid = false;
		
		if (valid)
			circles.add(circle);
	}
	
	public ArrayList<Circle> getList() {
		return circles;
	}
}
