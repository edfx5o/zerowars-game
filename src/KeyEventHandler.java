import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyEventHandler extends KeyAdapter{
	
	//private StartUp start;
	
	public void keyPressed(KeyEvent e)
	{			
		MainGame.getInstance().processKey(e,true);
		//start.processKey(e, true);
		//System.out.println(e.getKeyChar());
	}
	
	public void keyReleased(KeyEvent e)
	{
		MainGame.getInstance().processKey(e,false);
	}
}
