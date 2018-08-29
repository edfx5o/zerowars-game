
public class HiddenBrick  extends Brick
{
	
	
	public boolean isHit;
	public HiddenBrick(int row, int col,String myId)
	{
		this.mapRow = row;
		this.mapCol = col;
		this.id = myId;
		this.isHit = false;
	}

}
