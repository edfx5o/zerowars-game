import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;


public class StartUp extends Entity{
	
	//private Rectangle2D cursor;
	private static StartUp start;
	
	private List<Menu> menuItems;
	
	private Cursor cursor;
	
	private Menu newGame;
	private Menu about;
	private Menu back;
	private Menu quit;
	
	private boolean newG;
	private boolean aboutG;
	private boolean quitG;
	private boolean backG;
	
	private boolean showAbout;
	
	private Sprite screen;
	private Sprite startScreen;
	private Sprite aboutScreen;
	
	private int position;

	public static StartUp getInstance()
    {
    	if(start == null)
    	{
    		start = new StartUp();
    	}
    	return start;
    }
	
	private StartUp () {
		this.id = 1000;
		this.x = 0;
		this.y = 0;
				
		startScreen = new Sprite ("images/startsceen.png", x, y, id, "Start Screen");	
		aboutScreen = new Sprite("images/aboutScreen.png", x, y, id, "About Screen");
		
		this.screen = this.startScreen;
		
		this.w = screen.getW();
		this.h = screen.getH();
		
		this.position = 0;
		
		cursor = new Cursor();		
		
		menuItems = new LinkedList <Menu>();
		
		this.newG = false;
		this.aboutG = false;
		this.quitG = false;
		this.backG = false;
		
		this.showAbout = false;
		
		newGame = new Menu ("New Game", 1);
		about = new Menu ("Menu", 2);
		quit = new Menu ("Quit", 3);
		back = new Menu ("Back", 4);
		
		menuItems.add(newGame);
		menuItems.add(about);		
		menuItems.add(quit);
		//menuItems.add(back);
	}
	
	public List<Menu> getMenu(){
		return menuItems;
	}
	
	public void processKey(KeyEvent e, boolean keyPressed)
	{
		
		if (!this.showAbout)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP && !keyPressed)
			{
				System.out.println("UP");
				if (this.position - 1 < 0)
					this.position = 2;
				else
					this.position--;
				
				MainGame.getInstance().getSoundMgr().playClip(2, false);
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN && !keyPressed)
			{
				System.out.println("DOWN");
				if (this.position + 1 > 2)
					this.position = 0;
				else
					this.position++;
				
				MainGame.getInstance().getSoundMgr().playClip(2, false);
			}
			
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !keyPressed)
		{
			System.out.println("ENTER");
			MainGame.getInstance().getSoundMgr().playClip(3, false);
			
			if(this.newG)
				//System.out.println("NEW GAME");
				MainGame.getInstance().setStartScreen();
			else
			if (this.aboutG)
			{
				MainGame.getInstance().setAboutScreen();
				
				if (!this.showAbout)
					this.showAbout = true;
				else
					this.showAbout = false;
			}
			else
			if (this.backG)
			{
				MainGame.getInstance().setAboutScreen();
				
				if (!this.showAbout)
					this.showAbout = true;
				else
					this.showAbout = false;
			}
			else
			if (this.quitG)
			{
				System.out.println("QUIT!");
				System.exit(1);
			}
				
		}
		
		cursor.moveCursor(this.position);
			
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if (this.showAbout)
		{
			this.screen = this.aboutScreen;
			cursor.moveCursor(3);
			
			this.newG = false;
			this.aboutG = false;
			this.quitG = false;
			this.backG = true;
		}
			
		
		if (!this.showAbout)
		{
			this.screen = this.startScreen;
			//cursor.moveCursor(this.position);
			
			for (int i = 0; i < menuItems.size(); i ++)
			{
				if (cursor.hasCollide(menuItems.get(i)))
				{
					if(menuItems.get(i).getType() == 1)
					{
						
						this.newG = true;
						this.aboutG = false;
						this.quitG = false;
						this.backG = false;
					}
						
					
					if(menuItems.get(i).getType() == 2)
					{
						
						this.newG = false;
						this.aboutG = true;
						this.quitG = false;
						this.backG = false;
					}
					
					if(menuItems.get(i).getType() == 3)
					{
						
						this.newG = false;
						this.aboutG = false;
						this.quitG = true;
						this.backG = false;
					}
				}
			}
		}
		
		
		screen.update();		
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub		
		screen.draw(g2d);
		
		if (this.showAbout)
		{
			back.draw(g2d);
			cursor.draw(g2d);
		}
			
		
		if (!this.showAbout)
		{
			for (int i = 0; i < menuItems.size(); i++)
				menuItems.get(i).draw(g2d);
			
			cursor.update();
			cursor.draw(g2d);
		}
		
	}

	@Override
	public void draw(Graphics2D g2d, int cx1, int cy1, int cx2, int cy2) {
		// TODO Auto-generated method stub		
	}

	@Override
	public boolean hasCollide(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle2D getRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

}
