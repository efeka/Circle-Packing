import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {

	private Thread thread;
	private boolean running = false;

	public static int WIDTH = 800, HEIGHT = 600;
	
	private MouseInput mouse;
	private CircleList list;
	
	public Main() {
		requestFocus();
		list = new CircleList();
		mouse = new MouseInput(list);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		new Window(WIDTH, HEIGHT, "Circle Packing", this);
	}
	
	private void tick() {
		int randomX = (int) (Math.random() * WIDTH);
		int randomY = (int) (Math.random() * HEIGHT);
		list.addCircle(randomX, randomY);
		
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
		g.setColor(new Color(70, 70, 70));
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