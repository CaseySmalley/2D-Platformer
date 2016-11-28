package engine.util;

import java.awt.Color;
import java.awt.Graphics2D;

public class CCollisionBox {
	
	private float m_x;
	private float m_y;
	private float m_width;
	private float m_height;
	
	private float m_offsetX;
	private float m_offsetY;
	
	public CCollisionBox(float x,float y,float width,float height) {
		this.m_x = x;
		this.m_y = y;
		this.m_width = width;
		this.m_height = height;
	}
	
	public void setOffsetPosition(float x,float y) {
		this.m_offsetX = x;
		this.m_offsetY = y;
	}
	
	public float getX() {
		return this.m_x + this.m_offsetX;
	}
	
	public float getY() {
		return this.m_y + this.m_offsetY;
	}
	
	public float getWidth() {
		return this.m_width;
	}
	
	public float getHeight() {
		return this.m_height;
	}
	
	public boolean isColliding(CCollisionBox b) {
		return (this.m_x + this.m_offsetX < b.getX() + b.getWidth() &&
				this.m_x + this.m_offsetX + this.m_width > b.getX() &&
				this.m_y + this.m_offsetY < b.getY() + b.getHeight() &&
				this.m_y + this.m_offsetY + this.m_height > b.getY());
	}
	
	public void render(Graphics2D ctx) {
		ctx.setColor(Color.WHITE);
		ctx.drawRect((int) this.getX(),(int) this.getY(),(int) this.m_width,(int) this.m_height);
	}
	
}