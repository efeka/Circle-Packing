import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ImageProcessor {
	
	private Main main;
	
	private BufferedImage img;
	private int w, h;
	
	public ImageProcessor(Main main) {
		this.main = main;
	}
	
	public boolean processImage(Image image) {
		try {
			img = (BufferedImage) image;
			img = resizeImage(img, Main.WIDTH, Main.HEIGHT);
			w = img.getWidth();
			h = img.getHeight();
		} catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "ERROR! File not found.","Information", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		ArrayList<Point> points = new ArrayList<Point>();
		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				int pixel = img.getRGB(yy, xx);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				int color = red + green + blue;
				if (color > 255 * 2)
					points.add(new Point(yy, xx));
			}
		}
		
		main.startCirclePacking(points);
		return true;
	}
	
	private BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;      
	}
	
}
