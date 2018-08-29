 import java.awt.Graphics2D;
import java.awt.Image;

public class AnimatedSprite extends Sprite {
	
	protected float frameWidth,frameHeight;
	protected int numFrames;
	protected int[] duration;
	protected int currentFrame,currentFrameCount;
	protected boolean isLooping, hasFinished;
	
	public AnimatedSprite
	(
			String ref, 
			float x, 
			float y, 
			int numFrames,
			int[] duration,
			int id, 
			String name) 
	{
		super(ref, x, y,id, name);
		this.numFrames = numFrames;
		frameWidth = this.w / numFrames;
		frameHeight = this.h;
		currentFrame =0;
		currentFrameCount =0;
		this.duration = duration;
		this.isLooping = true;
		this.hasFinished = false;
		// TODO Auto-generated constructor stub
	}
	
	public AnimatedSprite
	(
			String ref, 
			float x, 
			float y, 
			int numFrames,
			int d,
			int id, 
			String name) 
	{
		super(ref, x, y,id, name);
		this.numFrames = numFrames;
		frameWidth = this.w / numFrames;
		frameHeight = this.h;
		currentFrame =0;
		currentFrameCount =0;
		this.duration = new int[this.numFrames];
	
		for(int i=0; i < this.numFrames; i++)
		{
			this.duration[i] = d/this.numFrames;
		}
		
		this.isLooping = true;
		this.hasFinished = false;
		// TODO Auto-generated constructor stub
	}	
	
	public boolean isLooping() {
		return isLooping;
	}

	public void setLooping(boolean isLooping) {
		this.isLooping = isLooping;
	}

	public boolean isHasFinished() {
		return hasFinished;
	}

	public void setHasFinished(boolean hasFinished) {
		this.hasFinished = hasFinished;
	}

	public float getW() {
		return this.frameWidth;
	}
	
	public float getH() {
		return this.frameHeight;
	}
	
	public void update()
	{
		if(!this.hasFinished)
		{
			currentFrameCount++;
			//System.out.println("CurrentFrameCount: "+currentFrameCount);
			if(duration[currentFrame] == currentFrameCount)
			{//time to switch frames
				
				if(this.currentFrame + 1 == numFrames )
				{//at the last frame
					if(!this.isLooping)
					{//not looping
						this.hasFinished = true;	
					}
				}
				
				currentFrame++;
				
				currentFrameCount = 0;
				currentFrame = currentFrame % numFrames;
				//.println("CurrentFrame >> >> >> >>: "+currentFrame);
			}		
		}
	}
	
	
	@Override
	public void draw(Graphics2D g2d)
	{
		
		g2d
		.drawImage(
				 image, 
				(int)this.drawX, 
				(int)this.drawY, 
				(int)(this.drawX+ this.frameWidth), 
				(int)(this.drawY+ this.frameHeight), 
				(int)(this.frameWidth*currentFrame), 
				0, 
				(int)(this.frameWidth)*(currentFrame+1), 
				(int)this.frameHeight,
				null);
		
		//System.out.println("Animated Draw : x:  " + x+" y: "+y +"frameWidth "+this.frameWidth );
	}

}
