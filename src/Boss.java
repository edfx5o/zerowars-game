import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Boss extends Entity{

private Queue<KeyEvent> keyQueue;
	
	private Sprite sigma;
	
	private Sprite FlyingLeft;
	private Sprite FlyingRight;	
	private Sprite shield;
	
	private Sprite currentSprite;

	private Rectangle2D boundary;
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
	
	private boolean isDead;
	
	private boolean protect;
	
	private boolean facingLeft;
	private boolean facingRight;
	
	private boolean fire;
	private List<Bullet> bullets;
	private List<Projectiles> projectiles;
	
	private int LIFE = 200;
	
	public Boss
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
		
		
		sigma = new Sprite(ref,x,y,id,"Sprite: "+name);						
		
		FlyingRight = new ReverseAnimation("images/sigmaFR.png",x,y,5,200,id,"Sprite: "+name);
		FlyingLeft = new AnimatedSprite("images/sigmaFL.png",x,y,5,200,id,"Sprite: "+name);
		shield = new AnimatedSprite("images/forcefield.png", x, y, 1, 100, id, "Sprite: "+name);
		
		this.h = sigma.getH();
		this.w = sigma.getW()/5;
		
		this.para1 = (int)this.x - 250;
		this.para2 = (int)this.x + (int)this.w + 500;
		this.c = c;
		this.bm = bm;
		this.hm = hm;
		this.currentSprite = this.sigma;
		this.rect = new Rectangle2D.Double();
		rect.setFrame(x,y,w,h);
		
		this.boundary = new Rectangle2D.Double();
		boundary.setFrame(x-200, y-200, w + 400, h + 400);
		
		keyQueue = new LinkedList<KeyEvent>();
		
		this.facingLeft = true;
		this.facingRight = false;
		this.protect = false;
		this.isDead = false;
		
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
		
		if (this.LIFE < 100)
		{
			shield.setDrawX(drawX-15);
			shield.setDrawY(drawY-10);
			shield.draw(g2d);	
			
			if (this.LIFE < 80)
				this.LIFE++;
		}
		
		currentSprite.draw(g2d);
		
		
		//g2d.drawRect((int)x - c.getX1(), (int)y - c.getY1(),(int) w,(int) h);
		hp = new Rectangle2D.Double();
		hp.setFrame((int)(x - c.getX1())+100, (int)(y - c.getY1())-50,(int) 5,(int) this.LIFE);
		g2d.fill(hp);
		
		//g2d.draw(boundary);
		
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
		
		if (MainGame.getInstance().getPlayer().getRectangle().intersects(boundary))
			MainGame.getInstance().setBossArea();
			
		
		if (hasCollide(MainGame.getInstance().getPlayer()))
			MainGame.getInstance().getPlayer().minusLIFE(10);
		
		for (int i = 0; i < bullets.size(); i++)
		{
			b1 = bullets.get(i);
			
			if(hasCollide(b1))
			{
				if (b1.getType() == 0)
				{
					//this.LIFE = this.LIFE - 1;
					bullets.remove(b1);
				}
				else
				if (b1.getType() == 1)
				{
					//this.LIFE = this.LIFE - 2;
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
					this.LIFE = this.LIFE - 10;
					bullets.remove(b1);
				}
				
				
				if (this.LIFE <= 0)
				{
					MainGame.getInstance().getEnemies().remove(this);
					MainGame.getInstance().gameOver();
				}
					
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
					this.LIFE = this.LIFE - 10;
					projectiles.remove(p1);
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
		boundary.setFrame(x -200, y - 200, w + 400, h + 400);
		
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
