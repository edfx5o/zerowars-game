import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;


public class Ball extends Entity {

	protected Rectangle2D.Double rect;
	protected float xStart,yStart;
	protected Random rand;

	protected Sprite s;
	protected Sprite normalImage;
	protected AnimatedSprite expl;
	
	protected boolean explShowing;
	
	public Ball
	(
			String ref,
			float x,
			float y,
			float dx,
			float dy,
			int id,
			String name
	)
	{
		super();
		this.x = x;
		this.y = y;
	
		this.normalImage = new Sprite(ref,x,y,id,"Sprite: "+name);
		
		this.expl = new AnimatedSprite("images/expl1.png",x,y,8,100,id,"Sprite: "+name);
		this.explShowing = false;
		
		this.s = normalImage;
		
		this.w = s.getW();
		this.h = s.getH();
		this.dx = dx;
		this.dy = dy;
		this.id = id;
		this.name = name;
		
		this.rect = new Rectangle2D.Double(x,y,w,h);
		this.xStart = x;
		this.yStart = y;
		this.rand = new Random();
	}
	
		
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		s.draw(g2d);
	}

	@Override
	public Rectangle2D getRectangle() {
		// TODO Auto-generated method stub
		return rect;
	}

	@Override
	public boolean hasCollide(Entity e) {
		// TODO Auto-generated method stub
		return rect.intersects(e.getRectangle());
	}

	public void reset()
	{
		this.explShowing = true;
		expl.setHasFinished(false);
		expl.setLooping(false);
		s = this.expl;
		
		MainGame.getInstance().getSoundMgr().playClip(0, false);
	
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if(!this.explShowing)
		{
			x+= dx;
			y+= dy;
	
			if(x < 0)
			{
				x = 0;
				dx = dx *-1;
			}
			else
			if(x + w > MainGame.getInstance().X_WORLD_END)
			{
				x = MainGame.getInstance().X_WORLD_END - w ;
				dx = dx *-1;	
			}
	
			if(y < 0)
			{
				this.reset();
			}
			else
			if(y + h > MainGame.getInstance().Y_WORLD_END)
			{
				
				this.reset();
			}
		}
		
		s.update();
	
		if(this.explShowing)
		{
			if(expl.isHasFinished())
			{
				explShowing = false;
				s = this.normalImage;
				this.x = this.xStart;
				this.y = this.yStart;
				s.setX(this.xStart);
				s.setY(this.yStart);
			}
		}
		
		//s.setX(x);
		//s.setY(y);
		
		this.rect.setFrame(getX(),getY(),getW(),getH());	
	}


	@Override
	public void draw(Graphics2D g2d, int cx1, int cy1, int cx2, int cy2) {
		// TODO Auto-generated method stub
		this.drawX = this.x - cx1;
		this.drawY = this.y - cy1;
		
		//System.out.println(" x: "+x+" y: "+y+" drawx : "+this.drawX +" drawY: "+this.drawY);
		s.setDrawX(drawX);
		s.setDrawY(drawY);
		
		s.draw(g2d);
		
	}

}
