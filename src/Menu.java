import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Menu extends Entity{
	
	private Rectangle2D rect;
		
	private Sprite menuItem;
	private Sprite newGame;
	private Sprite about;
	private Sprite quit;
	private Sprite back;
	
	private int type;
	
	public Menu
	(						
			String name, 
			int type			
	)
	{

		this.id = 1500;
		this.name = name;
		this.type = type;
		
			
		if (this.type == 1)
		{
			this.x = 235;
			this.y = 180;
			
			newGame = new Sprite("images/newgame2.png",x,y,id,"Sprite: "+name);
			
			this.menuItem = this.newGame;
		}
			
		else
		if (this.type == 2)
		{
			this.x = 235;
			this.y = 240;
			
			about = new Sprite("images/about2.png",x,y,id,"Sprite: "+name);		
			
			this.menuItem = this.about;
		}
		else
		if (this.type == 3)
		{
			this.x = 235;
			this.y = 300;
			
			quit = new Sprite("images/quit2.png",x,y,id,"Sprite: "+name);
			
			this.menuItem = this.quit;
		}
		else
		if (this.type == 4)
		{
			this.x = 132;
			this.y = 520;
				
			back = new Sprite("images/back.png",x,y,id,"Sprite: "+name);
				
			this.menuItem = this.back;
		}
		
		this.h = this.menuItem.getH();
		this.w = this.menuItem.getW();
				
		this.rect = new Rectangle2D.Double();
		rect.setFrame(x,y,w,h);
				
		//this.stop();
	}
			
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
		this.drawX = this.x;
		this.drawY = this.y;
		
		menuItem.setDrawX(drawX);
		menuItem.setDrawY(drawY);
		
		menuItem.draw(g2d);		
		
	}

	@Override
	public void draw(Graphics2D g2d, int cx1, int cy1, int cx2, int cy2) {
		// TODO Auto-generated method stub
	
		this.drawX = this.x - cx1;
		this.drawY = this.y - cy1;
	
		menuItem.setDrawX(drawX);
		menuItem.setDrawY(drawY);
		
		menuItem.draw(g2d);
				
	}
	
	public int getType(){
		return this.type;
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
		menuItem.update();
		rect.setFrame(x, y, w, h);
	}

}
