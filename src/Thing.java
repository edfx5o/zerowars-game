import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Thing extends Entity
{
	private Sprite s;

	public Thing(
			int x, 
			int y,
			String url,
			int id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		s = new Sprite(url,x,y,id,"a thing");
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
			
			//System.out.println(" x: "+x+" y: "+y+" drawx : "+this.drawX +" drawY: "+this.drawY);
			s.setDrawX(drawX);
			s.setDrawY(drawY);
			
			s.draw(g2d);
			
				
	}

	@Override
	public Rectangle2D getRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCollide(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
