import java.awt.Color;
import java.awt.Graphics2D;


public class Ribbon
{
	
	private float x,y,dx,dy;
	private int id;
	private String name;
	private Sprite s;
	public float speed;
	
	public Ribbon(
			
			float x,
			float y,
			float dx,
			float dy,
			int id,
			String name,
			String ref
			)
	{
		s = new Sprite(ref,x,y,id,name);
		this.x = x;
		this.y = y;
		this.id = id;
		this.name = name;
		this.dx = dx;
		this.dy = dy;
		this.speed =20;
	}
	
	
	public void draw(Graphics2D g2d,int cx1,int cy1, int cx2, int cy2)
	{
		//map where the camera is looking
		//to points on image...
		int diff =cx2 - cx1;
		cx1 = (int)(speed*cx1);
		cx2 = cx1 + diff;
		
		//now cx1 and cx2 represent point
		//on the image
		//remember image here refers to the 
		//infinitely large 'wrapping' image...
		int mCx1,cx1Diff,mCx2,cx2Diff;
		
		mCx1 = (int) (cx1 / s.getW());
		cx1Diff = (int) (cx1 % s.getW());

		mCx2 = (int) (cx2 / s.getW());
		cx2Diff = (int) (cx2 % s.getW());

		
		if(cx1 >= 0 && cx2 > 0)
		{				
			if(mCx1 == mCx2 )
			{//same ribbon image
				if(cx2Diff > cx1Diff)
				{//render from cx1Diff to cx2Diff
					g2d.drawImage(s.image, 
							(int)x, (int)y,MainGame.getSCREEN_WIDTH(),(int)(y+s.getH()), 
							cx1Diff,0,cx2Diff,(int)s.getH(), null);
				}
			}
			else
			if(mCx2 > mCx1)
			{//crossing a ribbon to the right
				
				int l1 = (int)s.getW() - cx1Diff;
				//first portion
				g2d.drawImage(s.image, 
						(int)x,(int)y,l1,(int)(y+s.getH()), 
						cx1Diff,0,
						cx1Diff + l1,(int)s.getH(), null);
				
				
			//	int l2 = MainGame.SCREEN_WIDTH - l1;
//				
//				//second portion
				g2d.drawImage(s.image, 
						l1,(int)y,
						MainGame.getSCREEN_WIDTH(),(int)(y+s.getH()), 
						0,0,
						cx2Diff,(int)s.getH(), null);
			
				g2d.setColor(Color.RED);
				
//				int sum = l1+l2;
//				
//				g2d.drawString("cx1Diff " + cx1Diff+ "src : " +s.getW() + " srcW: "+ (int)(s.getW() - cx1Diff )+ " des : " +(MainGame.SCREEN_WIDTH - cx1Diff) + " des " + MainGame.SCREEN_HEIGHT,30,300);
//				g2d.drawString("cx2Diff " + cx2Diff+ " des : " +(MainGame.SCREEN_WIDTH - cx1Diff) + " des " + MainGame.SCREEN_HEIGHT,30,360);
//				g2d.drawString("Sum: " +(sum) ,30, 390);
			}
			
		}
		else
		if(cx1 < 0 && cx2 < 0)
		{//negative part of world
		
			if(mCx1 == mCx2 )
			{//same ribbon image
				if(cx2Diff > cx1Diff)
				{//render from cx1Diff to cx2Diff
					
					g2d.drawImage(s.image, 
							(int)x, (int)y,MainGame.getSCREEN_WIDTH(),(int)(y+s.getH()), 
							(int)(s.getW() + cx1Diff),0,
							(int)(s.getW()+ cx2Diff),(int)s.getH(), null);
				}
			}
			else
			if(mCx2 > mCx1)
			{//crossing a ribbon to the right
				
				int l1 = -1*cx1Diff;
				//first portion
				g2d.drawImage(s.image, 
						(int)x,(int)y,l1,(int)(y+s.getH()), 
						(int)(s.getW() + cx1Diff),0,
						(int) s.getW(),(int)s.getH(), null);
//				
//				//second portion
				g2d.drawImage(s.image, 
						l1,(int)y,
						MainGame.getSCREEN_WIDTH(),(int)(y+s.getH()), 
						0,0,
						(int)(s.getW() + cx2Diff),(int)s.getH(), null);
			}
		}
		else
		if( cx1 < 0 && cx2 >=0)
		{
			int l1 = -1*cx1Diff;
			//first portion
			g2d.drawImage(s.image, 
					(int)x,(int)y,l1,(int)(y+s.getH()), 
					(int)(s.getW() + cx1Diff),0,
					(int) s.getW(),(int)s.getH(), null);
			
			//second portion
			g2d.drawImage(s.image, 
					l1,(int)y,
					MainGame.getSCREEN_WIDTH(),(int)(y+s.getH()), 
					0,0,
					(int)(cx2Diff),(int)s.getH(), null);
	
			
		}
		
		//g2d.setColor(Color.RED);
		//g2d.drawString(this.name + " "+cx1+ " "+cx2 + " speed : " + speed +" Diff : "+diff,this.x +10,this.y + 10);
		
		
	}
	
}
