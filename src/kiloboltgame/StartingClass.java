package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

// Applet class - init, start, stop, destroy
// Runnable class - run
// KeyListener class - keyPressed, keyReleased, keyTyped
public class StartingClass extends Applet implements Runnable, KeyListener {

	// character resources
	private Robot robot;
	private Image image, character, background, currentSprite, characterDown, characterJumped;
	
	// enemy resources
	private Heliboy hb, hb2;
	private Image heliboy;
	
	private Graphics graphics;
	// location containing our local files
	private URL base;
	
	private static Background bg1, bg2;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);

		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");

		// add user input handling here
		addKeyListener(this);
		
		try{
			base = getDocumentBase();
		}catch(Exception e){
			
		}
		
		// image setups
		character = getImage(base, "data/character.png");
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		
		// set default character image
		currentSprite = character;
		
		// enemy setup
		// dimensions are 96x96
		heliboy = getImage(base, "data/Heliboy.png");
		
		background = getImage(base, "data/background.png");
	}

	@Override
	public void start() {
		// background drawn first
		bg1 = new Background(0, 0);
		bg2 = new Background(2160,0);
		
		// enemy drawn on top of the background
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		
		// character drawn on top of the background
		robot = new Robot();
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			robot.update();
			if(robot.isJumped()){
				currentSprite = characterJumped;
			}else if(!robot.isJumped() && !robot.isDucked()){
				currentSprite = character;
			}
			
			// draw projectiles
			ArrayList<Projectile> projectiles = robot.getProjectiles();
			for(int i =0; i< projectiles.size(); i++){
				Projectile p = (Projectile) projectiles.get(i);
				if(p.isVisible()){
					p.update();
				}else{
					projectiles.remove(i);
				}
			}
						
			hb.update();
			hb2.update();
			
			bg1.update();
			bg2.update();
			repaint();
			try {
				// sleep for 17 milliseconds to simulate 60 frames per second
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// images are painted in the order they appear. Character should appear above background
	@Override
	public void paint(Graphics g) {	
		// draw background
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		
		// draw the projectiles
		ArrayList<Projectile> projectiles = robot.getProjectiles();
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		
		
		// draw the enemy
		g.drawImage(heliboy, hb.getCenterX()-48, hb.getCenterY()-48, this);
		g.drawImage(heliboy, hb2.getCenterX()-48, hb2.getCenterY()-48, this);
		
		// draw character
		g.drawImage(currentSprite, robot.getCenterX()-61, robot.getCenterY()-63, this);
	}
	
	// called implicitly and loop over again
	// using double buffering method to prevent screen tearing and flickering
	@Override
	public void update(Graphics g) {
		if(image == null){
			image = createImage(this.getWidth(), this.getHeight());
			graphics = image.getGraphics();
		}
	
		graphics.setColor(getBackground());
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		graphics.setColor(getForeground());
		paint(graphics);
		
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;
		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if(!robot.isJumped()){
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;
		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;
		case KeyEvent.VK_SPACE:
			robot.jump();
			break;
			
		case KeyEvent.VK_CONTROL:
			//if(!robot.isDucked() && !robot.isJumped() ){
			//	robot.shoot();
			//}
			robot.shoot();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("Stop moving down");
			currentSprite = character;
			robot.setDucked(false);
			break;
		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;
		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("Stop Jumping");
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static Background getBg1(){
		return bg1;
	}
	
	public static Background getBg2(){
		return bg2;
	}
}
