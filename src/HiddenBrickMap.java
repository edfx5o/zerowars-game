import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


public class HiddenBrickMap {

	
	
      public List<Column> cols;
      public int numRows,numCols;
      public int x,y;//coordinates map rendering to screen
      public int brickHeight, brickWidth;
      public float speed;
      
      SpriteFlyWeight sw;
      
	  public static void main(String[] args)
	  {
		  
		  HiddenBrickMap m = new HiddenBrickMap();
		  m.readFromFile("Maps/hiddenmap.txt");
		  
		  SpriteFlyWeight sw = new SpriteFlyWeight();
		  
		  
		  for(int i=0; i < m.cols.size(); i ++)
		  {
			  for(int j =0; j< m.cols.get(i).rows.size(); j++)
			  {
				  System.out.println(m.cols.get(i).rows.get(j).toString());
			  }
		  }		  
	  }
	
	  public HiddenBrickMap()
	  {
		  cols = new LinkedList<Column>();
		  sw = new SpriteFlyWeight();
		  this.brickHeight = (int)sw.getSprite("0").h;
		  this.brickWidth = (int)sw.getSprite("0").w;
		  readFromFile("Maps/hiddenmap.txt");
		  speed =1.0f;
	  }
	  
	  public Brick checkHorizontalCollisionsWith(int ex,int ey,int ew,int n)
	  {
		if(ex<0)return null;
		int exCol1,exCol2,eyRow;
		
		exCol1 = ex /this.brickWidth;
		exCol2 = (ex+ew) /this.brickWidth;
		
		if(exCol2 >= this.cols.size()){return null;}
		
		int dw = ew/n;
		eyRow = ey /this.brickHeight;
		int tCol;
		Brick b;
		for(int i=exCol1; i<=exCol2; i++)
		{
			for(int j=0; j < this.cols.get(i).rows.size(); j++)
			{
				b = this.cols.get(i).rows.get(j);
				for(int k = ex; k <= ex + ew; k+=dw)
				{
					tCol = k /this.brickWidth;
					if(b.mapCol == tCol && b.mapRow == eyRow)
					{
						return b;
					}
				}
			}
		}
		
		return null;
		
	  }
	  
	  public Brick checkVerticalCollisionWith(int ex,int ey,int eh,int n)
	  {
		  if(ex < 0 )return null;
		  int exCol,eyRow;		  
		  exCol = ex /this.brickWidth;
  
		  if(exCol >= this.cols.size()){return null;}
		  Column c = this.cols.get(exCol);
		  Brick b;
		  int dh = eh/n;
		  
		  for(int i=0; i < c.rows.size();i++)
		  {//for ith brick check n points vertically
			  b = c.rows.get(i);
			  for(int j = ey; j<= ey + eh; j+=dh)
			  {
				  eyRow = j / this.brickHeight;
				  System.out.println(ex+" : "+j+" : "+eyRow+" : "+exCol +" : "+brickWidth+" : "+brickHeight);
				  if(b.mapRow ==  eyRow)
				  {
					  return b;
				  }
			  }
		  }
		  
		  return null;
	  }
	  
      private void parseLine(String s,int currRow)
      {  
    	  for(int i = 0; i < s.length(); i++)
    	  {
    		  if(i+1 >= cols.size())
			  {//add a new col
				  cols.add(new Column());
			  }
			  
    		  if(!(s.charAt(i) ==' '))
    		  {
    			  cols.get(i).rows
    			  .add(new HiddenBrick(currRow,i,""+s.charAt(i)));
    		  }
    	  }
      }
      
      
      public void draw(
    		  Graphics2D g2d,
    		  int cx1,
    		  int cy1,
    		  int cx2,
    		  int cy2) 
	  {
    	
    	  
    	  
    	 // sw.draw(g2d);
    	  
    	  //System.out.println(cx1 + " " + cx2);
    	  
    	  if(cx2<0 )return;//no platforms defined for
    	  //negative part of the world
    	 
    	  
		int diff =cx2 - cx1;
		cx1 = (int)(speed*cx1);
		cx2 = cx1 + diff;
	
    	  g2d.setColor(Color.GREEN);
    	  
    	  int col1 = cx1 / this.brickWidth;
    	  int col2 = cx2 / this.brickWidth;
    	  //System.out.println(col1 + " "+ col2 +" "+cols.size());
    	  Column currCol;
    	  Brick b;
    	  Sprite s;
    	  //System.out.println(col1 + " "+ col2 +" "+cols.size());
    	  
    	  //for(int i=0; i < )
    	  if(col1< 0 ){col1=0;}
    	  for(int i= col1; i < this.cols.size() && i <= col2; i++)
    	  {
    		currCol = this.cols.get(i);
    		
    		for(int j =0; j < currCol.rows.size(); j++)
    		{
    			
    			b = currCol.rows.get(j);
    			//System.out.println("j : " + j +" "+b.id);
    			
    			//System.out.println("S : "+(s == null) + " Id : "+ b.id);
    			
    			if( ( (HiddenBrick)b).isHit)
    			{
        			s = sw.getSprite(b.id);
        			s.setDrawX((x +(b.mapCol*this.brickWidth)) -cx1);
        			s.setDrawY((y + (b.mapRow*this.brickHeight))- cy1);
        			s.draw(g2d);
    			}
//    			else
//    			{
//	        			g2d.drawRect(
//						(x +(b.mapCol*this.brickWidth)) -cx1,
//						(y + (b.mapRow*this.brickHeight))- cy1, 
//						this.brickWidth,
//						this.brickHeight
//						);    				
//    			}

    		}
    	  }
    	  
		  
	  }
      
      
	  public void readFromFile(String filename)
	    {
	        if ((filename == null) || (filename.equals("")))
	            throw new IllegalArgumentException();
	        
	        String line;
	        int currRow = 0;
	        try
	        {    
	            InputStream in = this.getClass().getResourceAsStream(filename);
	            BufferedReader br = new BufferedReader( new InputStreamReader(in));
	            if (!br.ready())
	                throw new IOException();
	            
	            while ((line = br.readLine()) != null)
	            {              
	            	this.parseLine(line,currRow);
	            	currRow++;
	            }
	            
	            in.close();
	        }
	        
	        catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        }
	        catch (IOException e)
	        {            
	            System.out.println(e);
	        }
	    }
}
