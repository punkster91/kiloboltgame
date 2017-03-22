package kiloboltgame;


// infinitely scrolling background consist of 2 long images that will loop like this: 1,2,1,2,1...
public class Background {
	private int bgX, bgY, speedX;
	
	Background(int x, int y){
		bgX = x;
		bgY = y;
		speedX = 0;
	}
	
	// update the position
	// if background is no longer visible, it should either destroy itself or be recycled by moving to a visible (or soon to be visible location)
	public void update(){
		//dimensions 2160x480
		// image moves to the left
		bgX += speedX;
		System.out.println(bgX);
		// when the character goes back left?
		if(bgX <= -2160){
			bgX += 4320;
		}
		
	}

	public int getBgX() {
		return bgX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
}
