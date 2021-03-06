package enginex;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.openal.AL;

public class EngineX implements Runnable {
	public JFrame					window;
	public JFrame					window2;
	private JPanel				renderingEngine;
	private BufferedImage	image;
	private Graphics2D		g;
	public StateMachine		stateMachine;
	public SoundMachine		soundMachine;
	public boolean				autoAdjust				= true;

	public int						width							= 800;
	public int						height						= 600;
	public float					scale							= 1;
	public String					gameName					= "EngineX";
	public int						windowTitleHeight	= 0;

	private int						fps								= 60;
	private int						targetTime				= 1000 / fps;

	double								gameScale					= 100;

	private boolean				running						= true;

	private boolean				visible						= false;

	public Point					mousePosition;

	protected EngineX() {
		this.gameName = "EngineX App";

		construct();
	}

	protected EngineX(String gameName) {
		this.gameName = gameName;

		construct();
	}

	protected EngineX(String gameName, int w, int h) {
		this.gameName = gameName;
		this.width = w;
		this.height = h;
		visible = true;

		construct();
	}

	protected EngineX(String gameName, int w, int h, boolean sizeable, boolean autoAdjust) {
		this.gameName = gameName;
		this.width = w;
		this.height = h;

		construct();

		window.setResizable(sizeable);

		this.autoAdjust = autoAdjust;
	}

	protected EngineX(String gameName, int w, int h, boolean sizeable) {
		this.gameName = gameName;
		this.width = w;
		this.height = h;

		construct();

		window.setResizable(sizeable);
		window.setVisible(true);
	}
	
	protected EngineX(String gameName, ArrayList<String> config) {
		this.gameName = gameName;

		construct();
		
		try {
			for(String v:config) {
				// Maximized
				if(v=="maximized:true") {
					maximizeWindow();
					window.setVisible(true);
					this.width = window.getSize().width;
					this.height = window.getSize().height;
				}
				
				// Sizable
				if(v=="sizable:true") {
					window.setResizable(true);
				}
				else if(v=="sizeable:false") {
					window.setResizable(false);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			exit();
		}
	}

	void construct() {
		stateMachine = new StateMachine(this);
		soundMachine = new SoundMachine(this);

		setSize(width, height, (int)scale);
	}

	public void setSize(int width, int height, int scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;

		try {
			window.dispose();
		}
		catch(Exception e) {
		}

		window = new JFrame();
		renderingEngine = new JPanel();
		renderingEngine.setPreferredSize(new Dimension(width * scale, height * scale));
		image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();

		window.addKeyListener(stateMachine);
		window.addMouseListener(stateMachine);
		window.addMouseWheelListener(stateMachine);

		window.setContentPane(renderingEngine);

		window.setTitle(gameName);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.BLACK);
		window.setResizable(false);
		window.pack();
		window.setVisible(visible);
		window.setLocationRelativeTo(null);
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	public void setSize(int width, int height, int scale, boolean visible) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.visible = visible;

		window.dispose();

		window = new JFrame();
		renderingEngine = new JPanel();
		renderingEngine.setPreferredSize(new Dimension(width * scale, height * scale));
		image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();

		window.addKeyListener(stateMachine);
		window.addMouseListener(stateMachine);
		window.addMouseWheelListener(stateMachine);

		window.setContentPane(renderingEngine);

		window.setTitle(gameName);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.BLACK);
		window.setResizable(false);
		window.pack();
		window.setVisible(visible);
		window.setLocationRelativeTo(null);
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}
	
	void maximizeWindow() {
		window.setExtendedState(window.getExtendedState()|JFrame.MAXIMIZED_BOTH);
	}

	void updateWindow() {
		if(window.getWidth() != width || window.getHeight() != height) {
			if(autoAdjust) {
				width = window.getWidth();
				height = window.getHeight();
			}
		}
		image = new BufferedImage(width * (int)scale, height * (int)scale, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
	}

	public void init() {
		// menuState = new MenuState(this);
		// stateMachine.pushState(menuState);
		// stateMachine.getCurrentState().init();
		run();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void run() {
		long startTime;
		long urdTime;
		long waitTime;

		while(running) {
			startTime = System.nanoTime();
			updateWindow();

			update();
			render();
			draw();

			urdTime = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - urdTime;

			sleep((int)waitTime);
		}
	}

	void update() {
		mousePosition = renderingEngine.getMousePosition();
		stateMachine.update();
	}

	void render() {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		clearScreen(g);
		stateMachine.render(g);
	}

	private void draw() {
		Graphics g = renderingEngine.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}

	public void clearScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
	}

	public Graphics2D getGraphics2D() {
		return g;
	}

	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch(Exception e) {
		}
	}

	public Point getMousePosition() {
		return renderingEngine.getMousePosition();
	}

	public void hideDefaultCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		renderingEngine.setCursor(blankCursor);
	}

	public void exit(String msg) {
		System.out.println(msg);
		exit();
	}

	public void exit() {
		// Release All Sound Resources
		AL.destroy();

		// Exit app...
		System.exit(0);
	}

	public void adjustScale(double d) {
		g.scale(d, d);
	}

	public void positionWindow(int x, int y) {
		window.setLocation(new Point(x, y));
	}

	public static double round(double value, int places) {
		if(places < 0)
			throw new IllegalArgumentException();

		long factor = (long)Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double)tmp / factor;
	}

	public void setState(int stateIndex) {
		stateMachine.setState(stateIndex);
	}
}