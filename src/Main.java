import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {

	private Thread thread;
	private boolean running = false;

	public static int WIDTH = 800, HEIGHT = 600;

	private ControlsWindow controlsWindow;
	private MouseInput mouse;
	private CircleList list;

	private ArrayList<Point> points;
	private boolean startCirclePacking = false;
	private int circleCount = 0, circleMax = 20;
	private int circleAddingAttempts = 0;
	public boolean completed = false;
	
	public Color background = new Color(70, 70, 70);

	public Main() {
		requestFocus();
		list = new CircleList();
		mouse = new MouseInput(list);

		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		Window window = new Window(WIDTH, HEIGHT, "Circle Packing", this);
		controlsWindow = new ControlsWindow(this, list, window.getX(), window.getY(), window.getWidth(), window.getHeight());
	}

	public void startCirclePacking(ArrayList<Point> points) {
		this.points = points;
		startCirclePacking = true;
	}
	
	public void reset() {
		list.clear();
		circleCount = 0;
		completed = false;
		controlsWindow.setInfoIndex(0);
		startCirclePacking = false;
		list.reconfigureColors();
		circleAddingAttempts = 0;
	}

	private void tick() {
		if (startCirclePacking) {
			while (circleCount++ < circleMax) {
				if (points.size() == 0) {
					reset();
					JOptionPane.showMessageDialog(null, "ERROR! Chosen file does not have bright enough pixels.","ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				}
					
				int randomPoint = (int) (Math.random() * points.size());
				Point point = points.get(randomPoint);
				boolean added = list.addCircle(point.x, point.y);
				if (!added)
					circleAddingAttempts++;
				if (circleAddingAttempts > 1000) {
					startCirclePacking = false;
					completed = true;
					controlsWindow.setInfoIndex(2);
					break;
				}
			}
			circleCount = 0;
		}

		for (int i = 0; i < list.getList().size(); i++)
			list.getList().get(i).tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		//background
		g.setColor(background);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		//circles
		for (int i = 0; i < list.getList().size(); i++)
			list.getList().get(i).render(g);

		g.dispose();
		bs.show();
	}

	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >=1) {
				tick();
				delta--;
			}
			if(running) 
				render();

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(InterruptedException e) {}
	}

	public static void main(String[] args) {
		new Main();	
	}

}