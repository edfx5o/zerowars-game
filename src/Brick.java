
public class Brick 
{

	public int mapRow;
	public int mapCol;
	public String id;
	
	public Brick(){}
	
	public Brick(int row, int col,String myId)
	{
		this.mapRow = row;
		this.mapCol = col;
		this.id = myId;
	}
	
	
	public String toString()
	{
		return "" + mapRow  + " : "+mapCol+" : "+id+"|";
		
	}
}
