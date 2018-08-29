import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Skull extends Entity{

private Queue<KeyEvent> keyQueue;
	
	private Sprite skull;
	
	private Sprite FlyingLeft;
	private Sprite FlyingRight;		
	
	private Sprite currentSprite;

	private Rectangle2D rect;
	private Rectangle2D hp;
	
	private Camera c;
	private BrickMap bm;
	private HiddenBrickMap hm;
	
	private int para1;
	private int para2;
	
	private boolean movingLeft;
	private boolean movingRight;
	private boolean movingUp;
	private boolean movingDown;
	private boolean isRising;
	
	private boolean facingLeft;
	private boolean facingRight;
	
	private boolean fire;
	private List<Bullet> bullets;
	private List<Projectiles> projectiles;
	
	private int LIFE = 100;
	
	public Skull
	(
			float x,
			float y,
			float dx,
			float dy,
			int id,
			String name, 
			String ref,
			Camera c,
			BrickMap bm,
			HiddenBrickMap hm
	)
	{
		super();
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.id = id;
		this.name = name;
		this.para1 = (int)this.x - 30;
		this.para2 = (int)this.x + 250;
		
		skull = new Sprite(ref,x,y,id,"Sprite: "+name);						
		
		FlyingRight = new ReverseAnimation("images/skullR.gif",x,y,8,200,id,"Sprite: "+name);
		FlyingLeft = new AnimatedSprite("images/skullL.gif",x,y,8,200,id,"Sprite: "+name);						
		
		this.h = skull.getH()/2;
		this.w = skull.getW()/8;
		this.c = c;
		this.bm = bm;
		this.hm = hm;
		this.currentSprite = this.skull;
		this.rect = new Rectangle2D.Double();
		rect.setFrame(x,y,w,h);
		
		keyQueue = new LinkedList<KeyEvent>();
		
		this.facingLeft = true;
		this.facingRight = false;
		
		this.fire = false;
		this.bullets = new LinkedList<Bullet>();
		this.projectiles = new LinkedList<Projectiles>();
		//this.stop();
	}
	
	public void stop()
	{
		this.movingDown = this.movingUp 
	  = this.movingLeft = this.movingRight 
	  = this.isRising = false;
		
	}
	public boolean isMoving()
	{
		return this.movingDown||this.movingUp||this.movingLeft
		||this.movingRight;
	}
	
				
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g2d, int cx1, int cy1, int cx2, int cy2) {
		// TODO Auto-generated method stub
	
		this.drawX = this.x - cx1;
		this.drawY = this.y - cy1;
		
		
		currentSprite.setDrawX(drawX);
		currentSprite.setDrawY(drawY);
		
		currentSprite.draw(g2d);
		
		hp = new Rectangle2D.Double();
		hp.setFrame((int)x - c.getX1(), (int)(y - c.getY1())-10,(int) this.LIFE,(int) 5);
		//g2d.drawRect((int)x - c.getX1(), (int)(y - c.getY1())-10,(int) this.LIFE,(int) 5);
		g2d.fill(hp);
		
		//g2d.setColor(Color.RED);
		//g2d.drawString("Player x: "+x+" y: "+y+" drawx : "+this.drawX +" drawY: "+this.drawY + " Dx : " + this.dx + " Dy : " + this.dy, this.drawX, this.drawY + this.h+  10);
		
		for(int i=0; i < this.bullets.size(); i++)
		{
			this.bullets.get(i).draw(g2d, cx1, cy1, cx2, cy2);
		}
		
		for(int i=0; i < this.projectiles.size(); i++)
		{
			this.projectiles.get(i).draw(g2d, cx1, cy1, cx2, cy2);
		}
	}

	@Override
	public Rectangle2D getRectangle() {
		// TODO Auto-generated method stub
		return rect;
	}

	@Override
	public boolean hasCollide(Entity e) {
		// TODO Auto-generated method stub
		return rect.intersects((e.getRectangle()));
	}	
	
	public boolean getDirection()
	{
		if(this.facingRight)
			return true;
		
		else 
			return false;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		Bullet b1;
		bullets = MainGame.getInstance().getPlayer().getBullets();
		
		if (hasCollide(MainGame.getInstance().getPlayer()))
			MainGame.getInstance().getPlayer().minusLIFE(1);
		
		for (int i = 0; i < bullets.size(); i++)
		{
			b1 = bullets.get(i);
			
			if(hasCollide(b1))
			{
				if (b1.getType() == 0)
				{
//					this.LIFE = this.LIFE - 1;
					bullets.remove(b1);
				}
				else
				if (b1.getType() == 1)
				{
//					this.LIFE = this.LIFE - 2;
					bullets.remove(b1);
				}
				else
				if (b1.getType() == 2)
				{
					this.LIFE = this.LIFE - 5;
					bullets.remove(b1);
				}
				else
				if (b1.getType() == 3)
				{
					this.LIFE = this.LIFE/2;
				}
				
				
				if (this.LIFE <= 0)
					MainGame.getInstance().getEnemies().remove(this);
			}
				
				//System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEAAAAAAAAHHHH");
				
		}
		
		Projectiles p1;
		projectiles = MainGame.getInstance().getPlayer().getProjectiles();
		
		for (int i = 0; i < projectiles.size(); i++)
		{
			p1 = projectiles.get(i);
			
			if(hasCollide(p1))
			{
				if (p1.getType() == 0)
				{
//					this.LIFE = this.LIFE - 1;
					projectiles.remove(p1);
				}
				else
				if (p1.getType() == 1)
				{
//					this.LIFE = this.LIFE - 2;
					projectiles.remove(p1);
				}
				else
				if (p1.getType() == 2)
				{
					this.LIFE = this.LIFE - 5;
					projectiles.remove(p1);
				}
				else
				if (p1.getType() == 3)
				{
					this.LIFE = this.LIFE/2;
				}
				
				
				if (this.LIFE <= 0)
					MainGame.getInstance().getEnemies().remove(this);
			}
				
				//System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEAAAAAAAAHHHH");
				
		}
		
		keyQueue.poll();
		
		Brick b;
		Brick hb;
		//positive amount to adjust by
		float xDiff = dx;
		if (this.facingRight) 
		{			
			this.facingLeft = false;
			this.currentSprite = this.FlyingRight;
			this.x += 1;	
			
			if(this.x == this.para2)
				this.facingRight = false;
		}
		else
		if (!this.facingRight) 
		{			
			this.facingLeft = true;
			this.currentSprite = this.FlyingLeft;
			this.x -= 1;
			
			
			if(this.x <= this.para1)
			{
				this.facingRight = true;				
			}
				
		}
			
		
		//.println(this.isDiving + " " + this.isRising);
								
		if(!this.isRising)
		{//check for floor
			b = this.bm.checkHorizontalCollisionsWith
	    	(
	    			(int)(this.x), 
	    			(int)(this.y + this.h + 1),
	    			(int)this.w,
	    			4
	    	);
			
			
			if(b == null)
			{
			//	this.isDiving = true;
			}
		}
												
		
		if(this.movingLeft)
		{
			//move left
			
			//check for collision and adjust if needed.
			//assuming dx < brickWidth
	    	b = this.bm.checkVerticalCollisionWith
	    	(
	    			(int)(this.x - dx), 
	    			(int)this.y,
	    			(int)this.h,
	    			4
	    	);
	    	
	    	hb = this.hm.checkVerticalCollisionWith
	    	    	(
	    	    			(int)(this.x - dx), 
	    	    			(int)this.y,
	    	    			(int)this.h,
	    	    			4
	    	    	);
	    	
	    	if( (b==null && hb == null) ||(b==null && hb!=null))
	    	{//no bricks colliding shift by dx
	    		//System.out.println("Did not collied");
	    		this.facingLeft = true;
				this.facingRight = false;
	    	
	    	}
	    	else
	    	{
	    		//System.out.println("Collied with "+ b.toString());
	    		
	    		xDiff = x - (b.mapCol+1)*bm.brickWidth;
	    	}
	    	
	    	this.x -=xDiff;
			this.c.shiftHorizontal((int)(-1*xDiff));
			this.currentSprite = this.FlyingLeft;	
			
			//this.movingLeft = false;
		}
		
		
		if(this.movingRight)
		{
			//move right
			
			
			//check for collision and adjust if needed.
			//assuming dx < brickWidth
	    	b = this.bm.checkVerticalCollisionWith
	    	(
	    			(int)(this.x +this.w + dx), 
	    			(int)this.y,
	    			(int)this.h,
	    			4
	    	);
	    	
	    	hb = this.hm.checkVerticalCollisionWith
	    	    	(
	    	    			(int)(this.x +this.w + dx), 
	    	    			(int)this.y,
	    	    			(int)this.h,
	    	    			4
	    	    	);
	    	
	    	if( (b==null && hb == null) ||(b==null && hb!=null))
	    	{//no bricks colliding shift by dx
	    		//System.out.println("Did not collied");
	    		this.facingRight = true;
				this.facingLeft = false;
	    	
	    	}
	    	else
	    	{
	    		//System.out.println("Collied with "+ b.toString());
	    		
	    		xDiff = b.mapCol*bm.brickWidth - (this.x + this.w);
	    	}
			
			
			this.x +=xDiff;
			this.c.shiftHorizontal((int)xDiff);
			this.currentSprite = this.FlyingRight;
						
		}
		
		//.println(bullets.size());					
		
		currentSprite.update();
		rect.setFrame(x,y,w,h);
		
		if(this.fire)
		{
			//.println("ADDING BULLET " + fire);
			
			if (this.facingRight)
			{
				bullets.add
				(
						new Bullet(
						this.x + w/2,
						this.y+8,
						this.id,
						"")
				);
			} 
			
			else
			if (this.facingLeft)
			{
				projectiles.add
				(
						new Projectiles(
						this.x,
						this.y+8,
						this.id,
						"")
				);
			}								
			
			fire = false;
		}
	
	

//		Bullet b1;
//		Projectiles p1;
//		
//		for(int i =0; i < bullets.size(); i++)
//		{
//		
//			b1 = bullets.get(i);
//			if(b1.getX() > c.getX2())
//			{
//				bullets.remove(b1);
//			}
//			else
//			{
//				b1.update();
//			}
//		}
//		
//		for(int i =0; i < projectiles.size(); i++)
//		{
//		
//			p1 = projectiles.get(i);
//			if(p1.getX() > c.getX2())
//			{
//				projectiles.remove(p1);
//			}
//			else
//			{
//				p1.update();
//			}
//		}
	
	}

}
