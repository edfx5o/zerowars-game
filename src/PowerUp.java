import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class PowerUp extends Entity{
	
	private Sprite bullet;
	private Sprite morph;
	private Sprite item1;
	private Sprite item2;
	private Sprite currentSprite;		

	private Rectangle2D rect;		
	private Camera c;	
	private int index;
	
	public PowerUp
	(
			float x,
			float y,			
			int id,			
			Camera c,
			int index
	)
	{
		super();
		this.x = x;
		this.y = y;		
		this.id = id;
		this.index = index;
				
		bullet = new Sprite("images/item5.png",x,y,id,"Bullet-PowerUP");
		morph = new Sprite ("images/item6.png", x, y, id, "State-PowerUp");
		item1 = new Sprite ("images/item2.png", x, y, id, "Item1-PowerUp");
		item2 = new Sprite ("images/item3.png", x, y, id, "Item1-PowerUp");
		
		this.h = bullet.getH();
		this.w = bullet.getW();
		this.c = c;
		
		if (this.index == 1)
			this.currentSprite = this.bullet;
		if (this.index == 2)
			this.currentSprite = this.morph;
		if (this.index == 3)
			this.currentSprite = this.item1;
		if (this.index == 4)
			this.currentSprite = this.item2;
		
		this.rect = new Rectangle2D.Double();
		rect.setFrame(x,y,w,h);
		
		//this.stop();
	}
	
	public int getIndex()
	{
		return this.index;
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
		
		//g2d.setColor(Color.RED);
		//g2d.drawString("Player x: "+x+" y: "+y+" drawx : "+this.drawX +" drawY: "+this.drawY + " Dx : " + this.dx + " Dy : " + this.dy, this.drawX, this.drawY + this.h+  10);
				
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
		
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		//positive amount to adjust by
		float xDiff = dx;
		float yDiff = dy;
		
		if (hasCollide(MainGame.getInstance().getPlayer()))
		{
			System.out.println("THIS");
			if (getIndex() == 1)
				MainGame.getInstance().getPlayer().setPowerUp();
			
			if (getIndex() == 2)
				MainGame.getInstance().getPlayer().setInvincible();
			
			MainGame.getInstance().removePowerUp(this);//
			
		}
	}
		
		
		
		
}
