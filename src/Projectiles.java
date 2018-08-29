import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Projectiles extends Entity{

	private Rectangle2D rect;
	
	private Sprite shot;
	private Sprite normalshot;
	private Sprite supershot;
	private Sprite megashot;
	private Sprite ultrashot;
	
	private boolean boost;
	private boolean noBoost;
	private boolean maxBoost;
	
	private int Limit;
	private int type;
	
	public Projectiles
	(
		float x,
		float y,
		int id,
		String name
	)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.name = name;
		normalshot = new AnimatedSprite("images/bullet2.png",x,y,3,60,id,"");
		supershot = new AnimatedSprite("images/turboshot2.png", x, y, 4, 60, id, "");
		megashot = new AnimatedSprite("images/megashot2.png", x, y, 3, 60, id, "");
		ultrashot = new AnimatedSprite("images/ultrashot2.png", x, y, 3, 60, id, "");
		//r.setLooping(false);
		this.shot = this.normalshot;
		//l = new AnimatedSprite("images/bullet.png",x,y,3,60,id,"");
		this.w = shot.getW();
		this.h = shot.getH();
		
		//this.w = l.getW();
		//this.h = l.getH();
		rect = new Rectangle2D.Double();
		rect.setFrame(x, y, w, h);
		
		this.boost = false;
		this.noBoost = true;
		this.maxBoost = true;
		this.Limit = 100;
		this.type = 0;
		
	}
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
	
	public void setBoost() {
		if(this.noBoost)
		{
			this.shot = this.supershot;
			
			this.w = shot.getW();
			this.h = shot.getH();
			
			this.boost = true;
			this.noBoost = false;
			
			this.type = 1;
		}
		else
		if (this.boost)
		{
			this.shot = this.megashot;
			
			this.w = shot.getW();
			this.h = shot.getH();
			
			this.boost = false;
			this.maxBoost = true;
			
			this.type = 2;
		}
		else
		{
			this.shot = this.ultrashot;
			
			this.w = shot.getW();
			this.h = shot.getH();
			
			this.type = 3;
		}
	}
	
	public int getType(){
		return type;
	}

	@Override
	public void draw(Graphics2D g2d, int cx1, int cy1, int cx2, int cy2) {
		// TODO Auto-generated method stub
		
		shot.drawX = x - cx1;
		shot.drawY = y - cy1;
		
//		g2d.setColor(Color.RED);
//		g2d.drawString("Player x: "+x+" y: "+y+" drawx : "+this.drawX +" drawY: "+this.drawY + " Dx : " + this.dx + " Dy : " + this.dy, this.drawX, this.drawY + this.h+  10);
		
		//g2d.drawString("Boost Duration: ", this.Limit ,0);
		this.shot.draw(g2d);
	//	g2d.drawRect((int)shot.drawX, (int)shot.drawY,(int) w,(int) h);
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

	@Override
	public void update() {
		// TODO Auto-generated method stub	
		
		//if (MainGame.getInstance().getPlayer().getDirection())
//			if (this.maxBoost)
//			{
//				this.Limit--;				
//			}
			
			//if (this.Limit == 0)
			//{
			//	this.shot = normalshot;
			//	this.maxBoost = false;
			//	this.boost = false;
			//	this.Limit = 100;
			//}
			this.x-=5;
			//this.y-=1;
		//if (!MainGame.getInstance().getPlayer().getDirection())
		//	this.x-=3;
		
		shot.update();
		rect.setFrame(x, y, w, h);
	}

	
	
}
