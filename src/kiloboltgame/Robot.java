package kiloboltgame;

import java.util.ArrayList;

// methods categorized

// 1 - constantly updated methods that are called on each iteration of the game loop
// 2 - methods that are only called upon player input
// 3 - helper methods that retrieve and change variables

// when in ducked, movement is halted
public class Robot {

	// constants
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;
	final int GROUND = 382;
	
	// robot character's center
	private int centerX = 100;
	private int centerY = GROUND;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	
	// get background instances from StartingClass
	private static Background bg1 = StartingClass.getBg1();
	private static Background bg2 = StartingClass.getBg2();
	
	// rate at which the position changes
	private int speedX = 0;
	private int speedY = 1;
	
	// creating projectiles everytime robot shoots is inefficient so we will maintain a list of projectiles 
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isJumped() {
		return jumped;
	}
	
	public boolean isDucked(){
		return ducked;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}
	
	public void setDucked(boolean ducked){
		this.ducked = ducked;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	// called every frame
	public void update(){
		
		// updates X position	
		// character is moving left
		if(speedX < 0){
			centerX += speedX;
			
		} 
		// character is not moving
		if(speedX == 0 || speedX < 0){
			System.out.println("Do not scroll the background");
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);

		}
		// character is moving rightward
		if(centerX <= 200 && speedX > 0){
			centerX += speedX;
		}
		if(centerX > 200 && speedX > 0){
			// scroll the background to the left to give the illusion the character is moving right
			System.out.println("Scroll background here");
			bg1.setSpeedX(-MOVESPEED);
			bg2.setSpeedX(-MOVESPEED);
		}
		
		// updates Y position
		centerY += speedY;
		// this prevents character from going below the ground
		if(centerY + speedY >= GROUND){
			centerY = GROUND;
		}
		
		// handles jumping
		if(jumped == true){
			// makes the character fall from the jump point
			speedY += 1;
			// character finished the jump
			if(centerY + speedY >= GROUND){
				jumped = false;
				centerY = GROUND;
				speedY = 0;				
			}
		}
		
		// prevents character's left arm from going past the left hand side of the screen
		if(centerX + speedX <= 60){
			centerX = 61;
		}
	}
	
	public void moveRight(){
		if(!ducked){
			speedX = MOVESPEED;
		}
	}
	
	public void moveLeft(){
		if(!ducked){
			speedX = -MOVESPEED;
		}
	}
	
	public void stopRight(){
		setMovingRight(false);
		stop();
	}
	
	public void stopLeft(){
		setMovingLeft(false);
		stop();
	}
	
	public void stop(){
		if(!isMovingRight() && !isMovingLeft()){
			speedX = 0;
		}
		
		if(!isMovingRight() && isMovingLeft()){
			moveLeft();
		}
		
		if(isMovingRight() && !isMovingLeft()){
			moveRight();
		}
	}
	
	public void jump(){
		if(jumped == false){
			jumped = true;
			speedY = JUMPSPEED;			
		}
	}
	
	public void shoot(){
		Projectile p = new Projectile(centerX + 50, centerY - 25);
		projectiles.add(p);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

    
}
