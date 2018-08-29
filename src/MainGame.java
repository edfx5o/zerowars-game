import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;


public class MainGame extends Thread
{    
    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 600;
    public static int X_WORLD_END;
    public static int Y_WORLD_END;
    
    private static MainGame game;
    
    private List<Entity> enemies;
    private List<Entity> entities;
    private List<PowerUp> powerUps;
    
    private BufferStrategy bs;
    private static StartUp start;
    
    public static MainGame getInstance()
    {
    	if(game == null)
    	{
    		game = new MainGame();
    	}
    	return game;
    }
    
	private GameFrame container;                  
	private int id;
	private Sound soundMgr;
	
	private Camera c;
	
	private Player p1;
	private Heatnix h1, h2;
	
	private boolean exit;
	private boolean bossArea;
	private boolean gameEnded;
	private boolean aboutScreen;
	private boolean isDeadBoss;
	
	public static boolean startUpScreen;
	
	private Ribbon sky;
	private Ribbon sky2;
	private Ribbon cloud1;
	private Ribbon cloud2;
	private Ribbon mountains;
	private Ribbon grass;
	private Ribbon ground;		
	
	private BrickMap bm;
	private HiddenBrickMap hidden;
	
	private Sprite endGame;
	 
	private MainGame()
	{
		load();
		
		container = 
			new GameFrame
			(
				"COMP 3900 - 2011 - Game Programming - Powered by Hsaka Productions 2008",
				this,
				getSCREEN_WIDTH(),
				getSCREEN_HEIGHT()
			);
		
    }
	
	private int getMyId()
	{
		id++;
		return id;
	}
	
	private void load()
	{		
		id =1;
		
		//start = new StartUp();
		
		this.bm = new BrickMap();
		this.hidden = new HiddenBrickMap();
		
		MainGame.X_WORLD_END = 20*MainGame.getSCREEN_WIDTH();
		MainGame.Y_WORLD_END = MainGame.getSCREEN_HEIGHT();
	
		this.c = new Camera(0,0,MainGame.getSCREEN_WIDTH(),MainGame.getSCREEN_HEIGHT());
	
		this.sky = new Ribbon(0,0,1,1,this.getMyId(),"Sky ","images/bg.png");
		sky.speed = 0.25f;
		
		this.sky2 = new Ribbon(0,0,1,1,this.getMyId(),"Sky ","images/sky3.png");
		sky2.speed = 0.25f;
		
		this.cloud1 = new Ribbon(0,0,1,1,this.getMyId(),"Cloud1 ","images/cloud1.png");
		cloud1.speed = 0.25f;
		
		this.cloud2 = new Ribbon(0,0,1,1,this.getMyId(),"Cloud2 ","images/cloud2.png");
		cloud2.speed = 0.5f;
		
		this.mountains = new Ribbon(0,250,1,1,this.getMyId(),"Cloud1 ","images/mountains3.png");
		mountains.speed = 0.5f;
	
		this.grass = new Ribbon(0,MainGame.getSCREEN_HEIGHT()-87,1,1,this.getMyId(),"Grass ","images/grass2.png");		
		grass.speed = 0.75f;
		
		this.ground = new Ribbon(0, MainGame.getSCREEN_HEIGHT() - 87, 1, 1, this.getMyId(), "Ground ", "images/ground2.png");
		ground.speed = 1f;	
		
		endGame = new Sprite ("images/gameover.png", 0, 0, this.getMyId(), "Game OVER");
		
		this.exit = false;
		this.bossArea = false;
		this.gameEnded = false;
		this.aboutScreen = false;
		this.isDeadBoss = false;
		
		this.startUpScreen = true;
		
		enemies = new LinkedList<Entity>();
		entities = new LinkedList<Entity>();						
		powerUps = new LinkedList<PowerUp>();
		
		
		
		p1 = new Player
				(
						200,						
						511, 
						3,
						3,
						this.getMyId(),
						"Player 1",
						"images/CollisionDummy.png",
						this.c,
						bm,
						hidden
				);
		
		enemies.add(new Boss
				(
						7860,
						350,
						3,
						3,
						this.getMyId(),
						"BOSS",
						"images/sigmaFL.png",
						this.c,
						bm,
						hidden
						)
		);
		
		h1 = new Heatnix
				(
						600,
						350,
						3,
						3,
						this.getMyId(),
						"Enemy 1",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					);		
		
		h2 = new Heatnix
				(
						3000,
						230,
						3,
						3,
						this.getMyId(),
						"Enemy 2",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					);
				
		
		this.powerUps.add(
				new PowerUp				
				(
					425,
					310,
					this.getMyId(),
					this.c,
					1
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4130,
					358,
					this.getMyId(),
					this.c,
					1
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4130,
					400,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4130,
					442,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4800,
					325,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4845,
					325,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4890,
					325,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4935,
					325,
					this.getMyId(),
					this.c,
					1
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4170,
					360,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					4215,
					360,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					6528,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					6568,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					6108,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					6148,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					6188,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					380,
					360,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					425,
					360,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					470,
					360,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					1480,
					370,
					this.getMyId(),
					this.c,
					2
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					1980,
					300,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					2100,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					2220,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					2340,
					300,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					2460,
					300,
					this.getMyId(),
					this.c,
					1
				)
		);
		
		this.powerUps.add(
				new PowerUp				
				(
					1480,
					420,
					this.getMyId(),
					this.c,
					4
				)
		);
		
		
		this.powerUps.add(
				new PowerUp				
				(
					1430,
					370,
					this.getMyId(),
					this.c,
					3
				)
		);
		
		this.entities.add(p1);
		
		this.enemies.add(h1);
		this.enemies.add(h2);
		this.enemies.add
		(
				new Skull
				(
						3891,
						450,
						3,
						3,
						this.getMyId(),
						"Enemy 3",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					)
		);
		
		this.enemies.add
		(
				new Skull
				(
						4778,
						470,
						3,
						3,
						this.getMyId(),
						"Enemy 3",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					)
		);
		
		this.enemies.add
		(
				new Heatnix
				(
						4900,
						110,
						3,
						3,
						this.getMyId(),
						"Enemy 3",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					)
		);
		
		this.enemies.add
		(
				new Skull
				(
						7200,
						440,
						3,
						3,
						this.getMyId(),
						"Enemy 3",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					)
		);
		
		this.enemies.add
		(
				new Heatnix
				(
						6500,
						200,
						3,
						3,
						this.getMyId(),
						"Enemy 3",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					)
		);
		
		this.enemies.add
		(
				new Heatnix
				(
						6800,
						400,
						3,
						3,
						this.getMyId(),
						"Enemy 3",
						"images/heatnixFL.png",
						this.c,
						bm,
						hidden
					)
		);								
		
		this.soundMgr = new Sound();
		
		soundMgr.loadClip("sounds/powerdn.wav");
		soundMgr.loadClip("sounds/xFiles.mid");
		soundMgr.loadClip("sounds/select.wav");
		soundMgr.loadClip("sounds/enter.wav");
		soundMgr.loadClip("sounds/theme.wav");
		soundMgr.loadClip("sounds/Invinvibility.wav");
		
	}
		
	
    public void draw(Graphics g)
    {
    	
    	Graphics2D g2d = (Graphics2D)bs.getDrawGraphics();
    	
    	g2d.fillRect(0,0,MainGame.getSCREEN_WIDTH(), MainGame.getSCREEN_HEIGHT());
    	
    	if (this.startUpScreen)
    	{
    		start.draw(g2d);    		    	
    			
    	}
    		
 
    	if (!this.startUpScreen && !this.gameEnded)
    	{
    		hidden.draw(g2d, c.getX1(), c.getY1(),c.getX2(),c.getY2());
        	
        	if (!bossArea)
        		sky.draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	
        	else
        		sky2.draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	
        	cloud1.draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	cloud2.draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	mountains.draw(g2d, c.getX1(), c.getY1(), c.getX2(), c.getY2());
        	grass.draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	ground.draw(g2d, c.getX1(), c.getY1(), c.getX2(), c.getY2());
        	
        	
        	
        	for(int i =0; i < enemies.size(); i++)
        	{
        		enemies.get(i).draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	}
        	
        	for(int i =0; i < powerUps.size(); i++)
        	{
        		powerUps.get(i).draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	}
        	
        	
        	
        	g2d.setColor(Color.GREEN);
        	g2d.drawString("Camera X1 : "+ c.getX1()+" Camera Y1 : "+ c.getY1()+ " Camera X2 : "+ c.getX2() + " Camera Y2 : "+ c.getY2() , 10, 10);
        
        	bm.draw(g2d, c.getX1(), c.getY1(),c.getX2(),c.getY2());
        	
        	for(int i =0; i < entities.size(); i++)
        	{
        		entities.get(i).draw(g2d,c.getX1(),c.getY1(),c.getX2(),c.getY2());
        	}
        	
        	hidden.draw(g2d, c.getX1(), c.getY1(),c.getX2(),c.getY2());        	        	
        	
    	}
    	
    	if (this.gameEnded)
    		endGame.draw(g2d);
    	
    	
    	
    	bs.show();
    }
    
    public List<Entity> getEnemies()
    {
    	return enemies;
    }
    
    public void removePowerUp (PowerUp p){
    	powerUps.remove(p);
    }
    
    public void setStartScreen(){
    	this.startUpScreen = false;
    }
    
    public void setAboutScreen(){
    	
    	if (!this.aboutScreen)
    		this.aboutScreen = true;
    	else
    		this.aboutScreen = false;
    }
    
    public void setBossArea(){
		this.bossArea = true;
	}
    
    public void gameOver(){
    	this.gameEnded = true;
    }
    
//    public void bossKilled(){
//    	this.isDeadBoss
//    }
    
    private void update()
    {
    	MainGame.setSCREEN_HEIGHT(this.container.getAera().getHeight());
    	MainGame.setSCREEN_WIDTH(this.container.getAera().getWidth());    	    	
    	//
    	if (this.startUpScreen)
    		start.update();
    	
    	
    	if (!this.startUpScreen && !this.gameEnded)
    	{
    		//game.start();
    		
        	
        	for (int i = 0; i < enemies.size(); i++)        	
        		enemies.get(i).update();    		
        	
        	
        	for(int i =0; i < entities.size(); i++)        	
        		entities.get(i).update();
        	
        	
        	for (int i = 0; i < powerUps.size(); i++)
        		powerUps.get(i).update();
    	}    	    
    	
    	
    }
 
    
    public void processKey(KeyEvent e,boolean keyPressed)
    {
 	
    	if (this.startUpScreen)
    	{
    		
    		//System.out.println("START");
    		if (e.getKeyCode() == KeyEvent.VK_UP)
        		start.processKey(e, keyPressed);
        	
        	if (e.getKeyCode() == KeyEvent.VK_DOWN)
        		start.processKey(e, keyPressed);
        	
        	if (e.getKeyCode() == KeyEvent.VK_ENTER)
        		start.processKey(e, keyPressed);
    	}
    	
    	
    	
    	if (!this.startUpScreen && !this.gameEnded)
    	{
    		if(e.getKeyCode() == KeyEvent.VK_A)
        	{// me being lazy DO NOT DO THIS
        		p1.setDx(p1.getDx() +1);
        	}
        	else
        	if(e.getKeyCode() == KeyEvent.VK_S)
        	{// me being lazy DO NOT DO THIS
        		
        		p1.setDx(p1.getDx() -1);
        	}
        	else
        	if
        	(
        			e.getKeyCode() == KeyEvent.VK_RIGHT ||
        			e.getKeyCode() == KeyEvent.VK_LEFT  ||
        			e.getKeyCode() == KeyEvent.VK_UP    ||
        			e.getKeyCode() == KeyEvent.VK_DOWN  ||
        			e.getKeyCode() == KeyEvent.VK_SPACE
        	)
        	{
        		p1.processKey(e,keyPressed);
        	}
    	}
    	
    }
    
    //public void processMouse()
    
    public void run()
    {   
    	    	
    	bs = this.container.getBs();
    	
    	while(true)
    	{
    		update();
    		this.draw(this.container.getAera().getGraphics());	

    		try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}	
    }
    
    public Player getPlayer()
    {
    	return p1;
    }             
    
	public Sound getSoundMgr() {
		return soundMgr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGame game = MainGame.getInstance();				
		start = StartUp.getInstance();								
		
		game.start();
	}

	public static int getSCREEN_HEIGHT() {
		return SCREEN_HEIGHT;
	}

	public static void setSCREEN_HEIGHT(int sCREEN_HEIGHT) {
		SCREEN_HEIGHT = sCREEN_HEIGHT;
	}

	public static int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public static void setSCREEN_WIDTH(int sCREEN_WIDTH) {
		SCREEN_WIDTH = sCREEN_WIDTH;
	}
		

}
