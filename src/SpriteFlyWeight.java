import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;


public class SpriteFlyWeight {

	private Map<String,Sprite> sprites;
	
	
	public SpriteFlyWeight()
	{
		sprites = new HashMap<String,Sprite>();
		this.load();
	}
	
	private void load()
	{
		//load sprites and map brick ids
		//could load each from single file,
		//or a strip file and cut it up...whatever...
		
		//ENSURE that ALL images are of equal width
		//and height
		this.loadSpritesFromStrip("images/bricks.png", 10);

		System.out.println("Sprites loaded : "+sprites.size());
	}
	
	public void loadSpritesFromStrip(String ref, int n)
	{
		
		Sprite[] spritesArr = this.loadStrip(ref,n);
		
		System.out.println("Sliced "+spritesArr.length);
		String id;
		for(int i =0; i < spritesArr.length; i++)
		{
			id = ""+i;
			sprites.put(id,spritesArr[i]);	
			System.out.println("Loaded g.png "+i );
		}
	}
	
	//retrieves a sprite from disk
	public Sprite[] loadStrip(String ref,int n) {
		
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
		
		Sprite[] sprites = new Sprite[n];
		BufferedImage image;
		int sx1,sx2,sy1,sy2;
		for(int i=0; i < n; i++)
		{
			image = gc.createCompatibleImage(sourceImage.getWidth()/n,sourceImage.getHeight(),Transparency.BITMASK);
		
			// draw source image into the accelerated image
			
			sx1 =i*image.getWidth();
			sy1 =0;
			sx2 =(i+1)*image.getWidth();
			sy2 =image.getHeight();
			
			System.out.println(sx1+" "+sy1+" "+sx2+" "+sy2);
			image.getGraphics()
			.drawImage(
					sourceImage,
					0,
					0,
					image.getWidth(),
					image.getHeight(),
					sx1,
					sy1,
					sx2,
					sy2,
					null);
			
			sprites[i] = new Sprite();
			sprites[i].image = image;
			sprites[i].h = image.getHeight();
			sprites[i].w = image.getWidth();
		}
		
		return sprites;
	}
	
	public void draw(Graphics2D g2d)
	{
		Set<String> keys = sprites.keySet();
		Sprite s;
		Iterator<String> i = keys.iterator();
		int x = 20;
		while(i.hasNext())
		{
			s = sprites.get(i.next());
			s.setDrawX(x);
			s.setDrawY(10);
			s.draw(g2d);
			x+=s.w +5;
		}
	}
	
	public Sprite getSprite(String id)
	{
		Sprite s;
		s = sprites.get(id);
		//System.out.println("Getting "+id + " "+ s);
		return s;
	}
}
