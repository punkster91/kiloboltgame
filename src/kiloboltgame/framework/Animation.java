package kiloboltgame.framework;

import java.awt.Image;
import java.util.ArrayList;

// SEE animation tutorial at http://www.kilobolt.com/day-8-animations
public class Animation {

	// contain AnimFrame objects that have two values: an image and a duration it is displayed
	private ArrayList frames;
	// refers to the integer value index of the current frame in the ArrayList
	private int currentFrame;
	// keep track of how much time has elapsed since the current image was displayed so we can switch to the next frame
	private long animTime; 
	// amount of time each frame (image) will be displayed for
	private long totalDuration;
	
	// Default constructor
	public Animation() {
		frames = new ArrayList();
		totalDuration = 0;
		
		// animTime and currentFrame will always be called sequentially together
		synchronized(this){
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	// Add an AnimFrame object
	public synchronized void addFrame(Image image, long duration){
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	// @param elapsedTime - is the amount of time that passed since the last update
	public synchronized void update(long elapsedTime){
		if(frames.size() > 1){
			animTime += elapsedTime;
			if(animTime >= totalDuration){
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}
			
			while(animTime > getFrame(currentFrame).endTime){
				currentFrame++;
			}
		}
	}
	
	// Return the image that belongs to the currentFrame
	public synchronized Image getImage(){
		if(frames.size() == 0){
			return null;
		}
		else{
			return getFrame(currentFrame).image;
		}
	}
	
	// Returns the current AnimFrame of the animation sequence
	private AnimFrame getFrame(int i){
		return (AnimFrame) frames.get(i);
	}
	
	// AnimFrame object inside the Animation class
	private class AnimFrame{
		Image image;
		long endTime;
		
		public AnimFrame(Image image, long endTime){
			this.image = image;
			this.endTime = endTime;
		}
	}

}