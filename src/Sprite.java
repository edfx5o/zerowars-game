import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class Sprite{

	public Image image;
	protected int id;
	protected String name;
	protected float x,y,w,h;
	
	protected float drawX,drawY;
	
	public Sprite()
	{
	
		this.id = 0;
		this.name = "";
		this.x = 0;
		this.y = 0;
		//this.getSprite(ref);
		this.w = 0;
		this.h = 0;
		this.drawX = 0f;
		this.drawY = 0f;
		//System.out.println("Created Sprite");
	
	}

	public Sprite(
			String ref,
			float x,
			float y,
			int id,
			String name)
	{
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.getSprite(ref);
		this.w = image.getWidth(null);
		this.h = image.getHeight(null);
		
	}

    public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	//retrieves a sprite from disk
	public void getSprite(String ref) {
		
     	//load from disk
		BufferedImage sourceImage = null;
		
		try {			
			URL url = this.getClass().getClassLoader().getResource(ref);
			
			if (url == null) {
				System.out.println("Can't find ref: "+ref);
			}			
			sourceImage = ImageIO.read(url);
                        
		} catch (IOException e) {
			System.out.println("Failed to load: "+ref);
		}
		
        //creates an accelerated image
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		
		// draw source image into the accelerated image
		image.getGraphics().drawImage(sourceImage,0,0,null);
		
	}	
	
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(this.image,(int)this.drawX,(int)this.drawY,null);
	}

	public void update() {
		// TODO Auto-generated method stub
		
		
	}

	public float getDrawX() {
		return drawX;
	}

	public void setDrawX(float drawX) {
		this.drawX = drawX;
	}

	public float getDrawY() {
		return drawY;
	}

	public void setDrawY(float drawY) {
		this.drawY = drawY;
	}

}
