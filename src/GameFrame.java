import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameFrame extends JFrame{
    
//	public static final int SCREEN_WIDTH = 800;
//    public static final int SCREEN_HEIGHT = 600;
	
    public Component area;
    private MainGame game;
    private Canvas c;
	public GameFrame(String title,MainGame game,int SCREEN_WIDTH, int SCREEN_HEIGHT)
	{
		
		super(title);
		this.game = game;
	
		JPanel panel = (JPanel) getContentPane();
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		panel.setLayout(null);
		setBounds(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		
		//set decide what want game to draw on
		//could be JFrame, Component etc
		c = new Canvas();
		area = c;
		
		area.setSize(panel.getPreferredSize());
	
		panel.add(area);
	
        area.addKeyListener(new KeyEventHandler());
        
        area.addMouseListener(new MouseEventHandler());
        
		setIgnoreRepaint(true);
        pack();
		//container.setResizable(false);
		setVisible(true);
		
		c.createBufferStrategy(2);
		
		//set up Listeners
        addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
        

        //addKeyListener(new KeyEventHandler(game));
        //addMouseListener(new MouseEventHandler(game));
                
		area.requestFocus();
	}
	
	public Component getAera()
	{
		return area;
	}
	
	public BufferStrategy getBs()
	{
		return c.getBufferStrategy();
	}
}
