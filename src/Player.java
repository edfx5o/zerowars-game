import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Player extends Entity{

	private Queue<KeyEvent> keyQueue;
	
	private Sprite hero;
	private Sprite StandLeft;
	private Sprite StandRight;
	private Sprite RunLeft;
	private Sprite RunRight;
	private Sprite JumpR;
	private Sprite JumpL;
	private Sprite FallR;
	private Sprite FallL;
	
	private Sprite MRR;
	private Sprite MRL;
	private Sprite MSR;
	private Sprite MSL;
	private Sprite MJR;
	private Sprite MJL;	
	
	private Sprite currentSprite;

	private Rectangle2D rect;
	private Camera c;
	private BrickMap bm;
	private HiddenBrickMap hm;
	
	private int jumpSize;
	private int numJumps;
	private int totalNumJumps;
	private int dj;
	private int dh;
	private int pcount;
	private int timer;
	
	private boolean movingLeft;
	private boolean movingRight;
	private boolean movingUp;
	private boolean movingDown;
	private boolean isJumping;
	private boolean isFalling;
	
	private boolean facingLeft;
	private boolean facingRight;
	
	private boolean invincible;
	
	private Bullet bullet;
	private Projectiles projectile;
	
	private boolean fire;
	private List<Bullet> bullets;
	private List<Projectiles> projectiles;
	
	private int Limit;	
	private int LIFE;
	
	public Player
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
		
		hero = new Sprite(ref,x,y,id,"Sprite: "+name);
				
		StandRight = new AnimatedSprite("images/HeroSR.png", x, y, 4, 100, id, "Sprite: "+name);
		StandLeft = new ReverseAnimation("images/HeroSL.png", x, y, 4, 100, id, "Sprite: "+name);
		
		RunRight = new AnimatedSprite("images/HeroRR.png",x,y,10,100,id,"Sprite: "+name);
		RunLeft = new ReverseAnimation("images/HeroRL.png",x,y,10,100,id,"Sprite: "+name);
				
		JumpR = new AnimatedSprite("images/HeroJR2.png", x, y, 5, 100, id, "Sprite: "+name);
		JumpL = new ReverseAnimation("images/HeroJL2.png",x,y,5,100,id,"Sprite: "+name);
		
		FallR = new AnimatedSprite("images/HeroFR.png", x, y, 5, 100, id,"Sprite: "+name);
		FallL = new ReverseAnimation("images/HeroFL.png",x,y,5,100,id,"Sprite: "+name);			
				
		MRR = new AnimatedSprite("images/MarioRR.png", x, y, 3, 50, id,"Sprite: "+name);
		MRL = new ReverseAnimation("images/MarioRL.png", x, y, 3, 50, id,"Sprite: "+name);
		MSR = new AnimatedSprite("images/MarioSR.png", x, y, 1, 50, id,"Sprite: "+name);
		MSL = new ReverseAnimation("images/MarioSL.png", x, y, 1, 50, id,"Sprite: "+name);
		MJR = new AnimatedSprite("images/MarioJR.png", x, y, 1, 50, id,"Sprite: "+name);
		MJL = new ReverseAnimation("images/MarioJL.png", x, y, 1, 50, id,"Sprite: "+name);
		
		this.h = hero.getH();
		this.w = hero.getW() + 25;
		this.c = c;
		this.bm = bm;
		this.hm = hm;
		this.currentSprite = this.hero;
		
		this.rect = new Rectangle2D.Double();
		rect.setFrame(x,y,w,h);
		
		keyQueue = new LinkedList<KeyEvent>();
		
		this.jumpSize = 120;
		this.totalNumJumps = 50;
		this.numJumps = this.totalNumJumps;
		this.dh = 5;
		this.isFalling = false;
		this.pcount = 0;
		this.Limit = 0;
		this.timer = 900;
	
		this.LIFE = 300;			
		
		this.facingLeft = false;
		this.facingRight = true;
		this.invincible = false;
		
		this.fire = false;
		this.bullets = new LinkedList<Bullet>();
		this.projectiles = new LinkedList<Projectiles>();
		//this.stop();
	}
	
	public void minusLIFE(int minus){
		
		if (this.LIFE - minus > -1)
			this.LIFE -= minus;
	}
	
	public void plusLIFE(int plus){
		
		if (this.LIFE + plus < 101)
			this.LIFE += plus;
	}
	
	public void setPowerUp()
	{
		pcount++;		
	}
	
	public void setInvincible()
	{
		this.invincible = true;
	}
	
	public boolean isInvincible()
	{
		return this.invincible;
	}
	
	public void stop()
	{
		this.movingDown = this.movingUp 
	  = this.movingLeft = this.movingRight 
	  = this.isJumping = this.isFalling = false;
		
	}
	
	public boolean isMoving()
	{
		return this.movingDown||this.movingUp||this.movingLeft
		||this.movingRight;
	}
	
	public void processKey(KeyEvent e, boolean keyPressed)
	{
		//filter if you want...
	
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(keyPressed){this.movingRight = true;}
			else 
			{ 
				this.movingRight = false;
				
				if (!this.invincible)
					this.currentSprite = this.StandRight;
				else
					this.currentSprite = this.MSR;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(keyPressed){this.movingLeft = true;}
			else
			{ 
				this.movingLeft = false;
				
				if (!this.invincible)
					this.currentSprite = this.StandLeft;
				else
					this.currentSprite = this.MSL;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			if(keyPressed)
			{
				if(!this.isFalling  && !this.isJumping )
				{
					this.movingUp = true;
					this.isJumping = true;					
				}
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if(keyPressed)
			{
				this.movingDown = true;
				this.isFalling = true;
				this.isJumping = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			this.fire = true;
			System.out.println("FIRE: "+fire);
			if (this.facingRight) System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>RIGHT");
			if (this.facingLeft) System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>LEFT");
		}
		
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
		
		g2d.setColor(Color.gray.darker());
//		g2d.drawString("Player x: "+x+" y: "+y+" drawx : "+this.drawX +" drawY: "+this.drawY + " Dx : " + this.dx + " Dy : " + this.dy, this.drawX, this.drawY + this.h+  10);
		
		//g2d.drawRect((int)this.drawX, (int)this.drawY, (int)w, (int)h);
		
		if (pcount == 0){
			
		}
			//g2d.fillRect()
		else
		if (pcount == 1)
		{
			g2d.fillRect(60, 50, 5, 5);
		}
		else
		if (pcount == 2)
		{
			g2d.fillRect(60, 50, 5, 5);
			g2d.fillRect(70, 50, 5, 5);
		}
		else
		if (pcount >= 3)
		{
			g2d.fillRect(60, 50, 5, 5);
			g2d.fillRect(70, 50, 5, 5);
			g2d.fillRect(80, 50, 5, 5);
		}
		
	
		g2d.fillOval(5, 5, 50, 50);//.fillRect(10, 10, 50, 50);
		
		g2d.setColor(Color.blue);		
		g2d.fillRect(20, 20, this.LIFE, 10);
		
		g2d.setColor(Color.green);
		g2d.fillRect(20, 35, 200, 5);		
		
		
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
	
		keyQueue.poll();
		
		Brick b;
		Brick hb;
		Brick horizontalb;
		//positive amount to adjust by
		float xDiff = dx;
		float yDiff = dy;
		
		if (this.LIFE <= 0)
			MainGame.getInstance().gameOver();
		
		System.out.println(this.isFalling + " " + this.isJumping);	
		
		if (this.invincible)
		{
			MainGame.getInstance().getSoundMgr().playClip(5, false);
			this.currentSprite = this.MRL;
			this.timer--;
			
			if (this.timer == 0)
				this.invincible = false;
		}
			
		
		if(!this.isJumping)
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
				this.isFalling = true;
			}
		}
		
		
		if(this.isJumping)
		{
			System.out.println(numJumps);
			
			if(this.numJumps > 0)
			{
				dj = this.jumpSize / this.totalNumJumps;
				yDiff = dj;

				b = this.bm.checkHorizontalCollisionsWith
		    	(
		    			(int)(this.x), 
		    			(int)(this.y - dj),
		    			(int)this.w,
		    			8
		    	);
				
				hb = this.hm.checkHorizontalCollisionsWith
				    	(
				    			(int)(this.x), 
				    			(int)(this.y - dj),
				    			(int)this.w,
				    			8
				    	);
				
				if(  (b==null && hb == null) )
				{
					//System.out.println("no vertical collision");
					
					if (!this.invincible)
					{
						if (this.facingLeft) 
							this.currentSprite = this.JumpL;
						else
						if (this.facingRight)
							this.currentSprite = this.JumpR;
					}
					
					else
					if (this.invincible)
					{
						if (this.facingLeft) 
							this.currentSprite = this.MJL;
						else
						if (this.facingRight)
							this.currentSprite = this.MJR;
					}
					
				}
				else
				{
					yDiff = dy;
					if(b!=null)
					{
						System.out.println("HIT BRICK TYPE "+b.id + " FROM BOTTOM");
						yDiff = (b.mapRow+1)*bm.brickHeight - this.y;
					}
					
					if(hb!=null)
					{
						System.out.println("HIT BRICK TYPE "+hb.id + " FROM BOTTOM");
						yDiff = (hb.mapRow+1)*bm.brickHeight - this.y;
						
						if(!hb.id.equals("1"))
						{
							((HiddenBrick)hb).isHit = true;
						}
					}
					
					
					this.isJumping = false;
					this.isFalling = true;
					this.numJumps = this.totalNumJumps +1;
				}
				
				this.y-=yDiff;
				this.numJumps--;
			}
			else
			{
				this.isJumping = false;
				this.isFalling = true;
				this.numJumps = this.totalNumJumps;
			}	
		}
		
		
		if(this.isFalling)
		{		
			//this.currentSprite = this.FallL;
			yDiff = dh;
			
		 
			b = this.bm.checkHorizontalCollisionsWith
	    	(
	    			(int)(this.x), 
	    			(int)(this.y + this.h + dh),
	    			(int)this.w,
	    			4
	    	);
			
			hb = this.hm.checkHorizontalCollisionsWith
			    	(
			    			(int)(this.x), 
			    			(int)(this.y + this.h + dh),
			    			(int)this.w,
			    			4
			    	);
			//System.out.println(b.id);
			if( (b==null && hb == null))
			{
				
				//System.out.println("no vertical collision");
				
				if (!this.invincible)
				{
					if (this.facingLeft) 
						this.currentSprite = this.FallL;
					else
					if (this.facingRight)
						this.currentSprite = this.FallR;
				}
				
				else
				if (this.invincible)
				{
					if (this.facingLeft) 
						this.currentSprite = this.MJL;
					else
					if (this.facingRight)
						this.currentSprite = this.MJR;
				}
				
				
			}
			else
			{
				yDiff = dy;
				if(b!=null && !b.id.equals("0"))
				{
					yDiff = ((b.mapRow)*bm.brickHeight) - (this.y + this.h);
					this.isFalling = false;
					
					if (b.id.equals("8"))
						this.minusLIFE(20);
					
					if (!this.invincible)
					{
						if(this.facingLeft)
							this.currentSprite = this.StandLeft;
						else
						if(this.facingRight)
							this.currentSprite = this.StandRight;
					}
					
					else
					if (this.invincible)
					{
						if(this.facingLeft)
							this.currentSprite = this.MSL;
						else
						if(this.facingRight)
							this.currentSprite = this.MSR;
					}
				}
				
				if(hb!=null)
				{
					if( (((HiddenBrick)hb ).isHit) ||  hb.id.equals("1") || hb.id.equals("7") )
					{
						
						if((!((HiddenBrick)hb ).isHit) && hb.id.equals("7"))
						{
							y =0;
						}
						else
						{
							yDiff = ((hb.mapRow)*bm.brickHeight) - (this.y + this.h);
							this.isFalling = false;
							
							if (!this.invincible)
							{
								if(this.facingLeft)
									this.currentSprite = this.StandLeft;
								else
								if(this.facingRight)
									this.currentSprite = this.StandRight;
							}
							
							if (this.invincible)
							{
								if(this.facingLeft)
									this.currentSprite = this.MSL;
								else
								if(this.facingRight)
									this.currentSprite = this.MSR;
							}
						}
					}
					
				}
				
				
			}
		
			this.y+=yDiff;
		
			if(this.y + this.h > MainGame.getInstance().getSCREEN_HEIGHT())
			{								
				y =0;
				this.minusLIFE(40);
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
	    	
	    	horizontalb = this.bm.checkHorizontalCollisionsWith
			    	(
			    			(int)(this.x), 
			    			(int)(this.y - dj),
			    			(int)this.w,
			    			8
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
	    		
	    		if (!this.isInvincible())
	    		{
	    			if (b.id.equals("8"))
		    			this.minusLIFE(5);
		    		
//		    		if (horizontalb.id.equals("8"))
//		    			this.minusLIFE(5);
	    		}
	    		
	    		
	    		xDiff = x - (b.mapCol+1)*bm.brickWidth;
	    	}
	    	
	    	this.x -=xDiff;
			this.c.shiftHorizontal((int)(-1*xDiff));					
			
			if (this.invincible)
			{
				this.currentSprite = this.MRL;
			}
			else
				this.currentSprite = this.RunLeft;
			
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
	    	
	    	horizontalb = this.bm.checkHorizontalCollisionsWith
			    	(
			    			(int)(this.x), 
			    			(int)(this.y - dj),
			    			(int)this.w,
			    			8
			    	);
	    	
	    	if( (b==null && hb == null) ||(b==null && hb!=null))
	    	{//no bricks colliding shift by dx
	    		
	    		this.facingRight = true;
				this.facingLeft = false;
	    	
	    	}
	    	else
	    	{
	    		if (b!=null && b.id.equals("0"))
	    		{
	    			yDiff = dy;
	    			this.isFalling = true;
	    			this.y+=yDiff;
	    		}
	    		
	    		if (!this.isInvincible())
	    		{
	    			if (b.id.equals("8"))
		    			this.minusLIFE(5);
		    		
//		    		if (horizontalb.id.equals("8"))
//		    			this.minusLIFE(5);
	    		}
	    		
	    		
	    		xDiff = b.mapCol*bm.brickWidth - (this.x + this.w);
	    	}
			
			
			this.x +=xDiff;
			this.c.shiftHorizontal((int)xDiff);						
			
			if (this.invincible)
			{
				this.currentSprite = this.MRR;
			}
			else
				this.currentSprite = this.RunRight;
						
		}
		
		System.out.println(bullets.size());
	
		currentSprite.update();
		rect.setFrame(x,y,w,h);
		
		if(this.fire)
		{
			System.out.println("ADDING BULLET " + fire);
			
			if (this.pcount < 3)
			{
				bullet = new Bullet (this.x + w/2, this.y+8, this.id,"");
				projectile = new Projectiles (this.x, this.y+8, this.id, "");
			}
			else
			{
				bullet = new Bullet (this.x-20, this.y-40, this.id,"");
				projectile = new Projectiles (this.x, this.y-40, this.id, "");
			}
			
			
			for (int i = 0; i < this.pcount; i ++)
			{												
				bullet.setBoost();
				projectile.setBoost();
											
				if (this.Limit == 10)
				{
					this.Limit = 0;
					this.pcount = 2;				
				}
			}
			
			
			if (this.facingRight)
			{
				if (this.pcount >= 3)
					this.Limit++;
				
					bullets.add (bullet);													
			} 
			
			else
			if (this.facingLeft)
			{
				if (this.pcount >= 3)
					this.Limit++;
				
				projectiles.add (projectile);
				
			}								
			
			fire = false;
		}
	

		Bullet b1;
		Projectiles p1;
		
		for(int i =0; i < bullets.size(); i++)
		{
		
			b1 = bullets.get(i);			
			
			if(b1.getX() > c.getX2())
			{			
				bullets.remove(b1);
			}
			else
			{
				b1.update();
			}
		}
		
		for(int i =0; i < projectiles.size(); i++)
		{
		
			p1 = projectiles.get(i);			
			
			if(p1.getX() < c.getX1())
			{				
				projectiles.remove(p1);
			}
			else
			{
				p1.update();
			}
		}
	
	}
	
	public List<Bullet> getBullets(){
		return bullets;
	}
	
	public List<Projectiles> getProjectiles(){
		return projectiles;
	}

}
