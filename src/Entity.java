import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


public abstract class Entity {

	protected float x,y,dx,dy,w,h,drawX,drawY;
	
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	protected int id;
	protected String name;
	
	public abstract void update();
	public abstract void draw(Graphics2D g2d);
	public abstract void draw(Graphics2D g2d,int cx1,int cy1,int cx2,int cy2);
	public abstract boolean hasCollide(Entity e);
	public abstract Rectangle2D getRectangle();
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getWorldX() {
		return drawX;
		
	}
	public float getDrawX() {
		return drawX;
	}
	public void setDrawX(float drawX) {
		this.drawX = drawX;
	}
	public float getDrawY() {
		return drawY;
	}
	public void setDrawY(float drawY) {
		this.drawY = drawY;
	}
	
	
}
