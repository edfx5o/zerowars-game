import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Cursor extends Entity{
	
	private Rectangle2D rect;
	

	private Sprite cursor;
	
	private int type;
	
	public Cursor()
	{
				
		this.x = 156;
		this.y = 180;
		
		cursor = new Sprite("images/cursor2.png",x,y,id,"Sprite: "+name);

		this.h = cursor.getH();
		this.w = cursor.getW();
				
		this.rect = new Rectangle2D.Double();
		rect.setFrame(x,y,w,h);
				
		//this.stop();
	}
	
	public void moveCursor(int loc){
		
		if (loc == 0)
		{			
			this.x = 156;
			this.y = 180;
			System.out.println(this.y);
		}
		
		if (loc == 1)
		{
			this.x = 156;
			this.y = 240;
			System.out.println(this.y);
		}
		
		if (loc == 2)
		{
			this.x = 156;
			this.y = 300;
			System.out.println(this.y);
		}
		
		if (loc == 3)
		{
			this.x = 55;
			this.y = 520;
		}
		update();
			
	}
			
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
		this.drawX = this.x;
		this.drawY = this.y;
		
		cursor.setDrawX(drawX);
		cursor.setDrawY(drawY);
		
		cursor.draw(g2d);
		//g2d.draw(rect);
		//g2d.drawString("Cursor", this.drawX, this.drawY);
	}
		

	@Override
	public void draw(Graphics2D g2d, int cx1, int cy1, int cx2, int cy2) {
		// TODO Auto-generated method stub
	
		this.drawX = this.x - cx1;
		this.drawY = this.y - cy1;
	
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
		
		cursor.update();
		rect.setFrame(x, y, w, h);
	}

}
