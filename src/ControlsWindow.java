import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ControlsWindow {

	private Image image;
	private ImageProcessor imageProcessor;
	private FileChooser fileChooser;

	private JFrame frame;
	//x: [8,341]
	private final int width = 350, height = 250;

	private JTextField txtPath;
	private JLabel lblPath, lblImageOptions, lblInfoMessage;
	private JButton btnBrowse, btnProcess, btnBackground, btnChangeFill;

	private String[] infoMessages = {"Status: Waiting for image file", "Status: Packing circles...", "Status: Process complete. Press again to restart."};
	private Color[] infoColors = {new Color(100, 100, 100), new Color(0, 0, 0), new Color(0, 180, 0)};

	@SuppressWarnings("unchecked")
	public ControlsWindow(Main main, CircleList list, int mainX, int mainY, int mainWidth, int mainHeight) {
		imageProcessor = new ImageProcessor(main);

		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.setTitle("Control Panel");
		frame.setResizable(false);
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocation(mainX + mainWidth + 5, mainY + (mainHeight - height) / 2);

		Font labelFont = new Font("Myriad Web Pro", Font.BOLD, 15);
		@SuppressWarnings("rawtypes")
		Map attributes = labelFont.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		int textX = 12;
		int textY = 40;
		int textWidth = width - 125;
		int textHeight = 25;
		txtPath = new JTextField("Path to your image file");
		txtPath.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
		txtPath.setVisible(true);
		txtPath.setEditable(false);
		txtPath.setBackground(Color.white);
		txtPath.setForeground(Color.gray);
		txtPath.setHorizontalAlignment(SwingConstants.LEFT);
		txtPath.setBounds(textX, textY, textWidth, textHeight);
		frame.add(txtPath);

		btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
		btnBrowse.setVisible(true);
		btnBrowse.setBounds(textX + textWidth + 2, textY + 1, 85, textHeight - 2); 
		btnBrowse.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fileChooser = new FileChooser();
				image = fileChooser.getImage();
				if (fileChooser.getImagePath() != null && !fileChooser.getImagePath().equals("")) {
					txtPath.setForeground(Color.black);
					txtPath.setToolTipText(fileChooser.getImagePath());
					txtPath.setText(fileChooser.getImagePath());
				}
			}
		});
		frame.add(btnBrowse);

		int centerX = (341 - 8) / 2;
		lblPath = new JLabel("Choose image file");

		lblPath.setHorizontalAlignment(JLabel.CENTER);
		lblPath.setBounds(centerX - 100, 5, 200, 30);
		lblPath.setVisible(true);
		lblPath.setFont(labelFont.deriveFont(attributes));
		frame.add(lblPath);

		lblImageOptions = new JLabel("Image Options");
		lblImageOptions.setHorizontalAlignment(JLabel.CENTER);
		lblImageOptions.setBounds(centerX - 100, textY + 40, 200, 30);
		lblImageOptions.setVisible(true);
		lblImageOptions.setFont(labelFont.deriveFont(attributes));
		frame.add(lblImageOptions);

		btnProcess = new JButton("Process Image!");
		btnProcess.setFont(new Font("Myriad Web Pro", Font.BOLD, 14));
		btnProcess.setVisible(true);
		btnProcess.setBounds(2 * (width - 300) / 3 + 150 - 8 + 10, lblImageOptions.getY() + 35, 130, 58); 
		btnProcess.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				main.reset();
				boolean fileFound = imageProcessor.processImage(image);
				if (fileFound) {
					lblInfoMessage.setText(infoMessages[1]);
					lblInfoMessage.setForeground(infoColors[1]);
				}
			}			
		});
		frame.add(btnProcess);
		
		btnBackground = new JButton("Choose Background");
		btnBackground.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
		btnBackground.setVisible(true);
		btnBackground.setBounds((width - 300) / 3 - 8, lblImageOptions.getY() + 35, 160, 23); 
		btnBackground.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				main.background = newColor;
			}
		});
		frame.add(btnBackground);
		
		
		btnChangeFill = new JButton("Toggle Fill");
		btnChangeFill.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
		btnChangeFill.setVisible(true);
		btnChangeFill.setBounds((width - 300) / 3 - 8, btnBackground.getY() + 35, 160, 23);
		btnChangeFill.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Circle.toggleFill();
			}			
		});
		frame.add(btnChangeFill);

		lblInfoMessage = new JLabel();
		lblInfoMessage.setText(infoMessages[0]);
		lblInfoMessage.setForeground(infoColors[0]);
		lblInfoMessage.setHorizontalAlignment(JLabel.CENTER);
		lblInfoMessage.setBounds(centerX - 250, 180, 500, 30);
		lblInfoMessage.setVisible(true);
		lblInfoMessage.setFont(new Font("Myriad Web Pro", Font.PLAIN, 15));
		frame.add(lblInfoMessage);

		frame.setVisible(true);
	}

	public void setInfoIndex(int index) {
		lblInfoMessage.setText(infoMessages[index]);
		lblInfoMessage.setForeground(infoColors[index]);
	}

}
