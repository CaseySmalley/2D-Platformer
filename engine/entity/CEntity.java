package engine.entity;

import java.awt.Graphics2D;
import java.awt.Color;

import engine.util.CEngineGlobals;
import engine.util.CCollisionBox;
import engine.level.CMesh;

public abstract class CEntity {
	
	protected float m_x;
	protected float m_y;
	protected float m_dx;
	protected float m_dy;
	
	protected float m_width;
	protected float m_height;
	
	protected float m_moveSpeed;
	protected float m_stopSpeed;
	protected float m_fallSpeed;
	protected float m_jumpStart;
	
	protected float m_maxMoveSpeed;
	protected float m_maxFallSpeed;
	
	protected boolean mf_jumping;
	protected boolean mf_falling;
	protected boolean mf_left;
	protected boolean mf_right;
	
	protected CCollisionBox mp_masterBoundingBox;
	protected CCollisionBox mp_topBoundingBox;
	protected CCollisionBox mp_bottomBoundingBox;
	protected CCollisionBox mp_leftBoundingBox;
	protected CCollisionBox mp_rightBoundingBox;
	
	protected CEntity(float x,float y,float width,float height) {
		this.m_x = x;
		this.m_y = y;
		this.m_dx = 0.0f;
		this.m_dy = 0.0f;
		
		this.m_width = width;
		this.m_height = height;
		
		this.mf_jumping = false;
		this.mf_falling = false;
		this.mf_left = false;
		this.mf_right = false;
		
		float heightScale = (1.0f / 4.0f);
		float xOffset = 0.1f;
		
		this.mp_masterBoundingBox = new CCollisionBox(0.0f,0.0f,width,height);
		this.mp_topBoundingBox = new CCollisionBox(width * xOffset,0.0f,width * 0.8f,height * heightScale);
		this.mp_bottomBoundingBox = new CCollisionBox(width * xOffset,height * 3.0f * heightScale,width * 0.8f,height * heightScale);
		this.mp_leftBoundingBox = new CCollisionBox(0.0f,height * heightScale,width * 0.5f,height * heightScale * 2.0f);
		this.mp_rightBoundingBox = new CCollisionBox(width * 0.5f,height * heightScale,width * 0.5f,height * heightScale * 2.0f);
	}
	
	protected void calculateMovement(float frameTime) {
		if (this.mf_left) {
			this.m_dx -= this.m_moveSpeed * frameTime;
			if (this.m_dx < -this.m_maxMoveSpeed) {
				this.m_dx = -this.m_maxMoveSpeed;
			}
		} else if (this.m_dx < 0.0f) {
			this.m_dx += this.m_stopSpeed * frameTime;
			if (this.m_dx > 0.0f) {
				this.m_dx = 0.0f;
			}
		}
		
		if (this.mf_right) {
			this.m_dx += this.m_moveSpeed * frameTime;
			if (this.m_dx > this.m_maxMoveSpeed) {
				this.m_dx = this.m_maxMoveSpeed;
			}
		} else if (this.m_dx > 0.0f) {
			this.m_dx -= this.m_stopSpeed * frameTime;
			if (this.m_dx < 0.0f) {
				this.m_dx = 0.0f;
			}
		}
		
		if (this.mf_falling) {
			this.m_dy += this.m_fallSpeed;
			if (this.m_dy > this.m_maxFallSpeed)
				this.m_dy = this.m_maxFallSpeed;
		} else if (this.mf_jumping) {
			this.mf_falling = true;
			this.m_dy = this.m_jumpStart;
		}
	}
	
	protected void calculateCollision(CMesh mesh) {
		if (mesh.isCollidingMaster(this.mp_masterBoundingBox)) {
			CCollisionBox box = null;
			//if (this.m_dx < 0.0f) {
				this.mp_leftBoundingBox.setOffsetPosition(this.m_x + this.m_dx,this.m_y + this.m_dy);
				box = mesh.isColliding(this.mp_leftBoundingBox);
				if (box != null) {
					this.m_dx = 0.0f;
					this.m_x = box.getX() + box.getWidth();
				}
			//} else if (this.m_dx > 0.0f) {
				this.mp_rightBoundingBox.setOffsetPosition(this.m_x + this.m_dx,this.m_y + this.m_dy);
				box = mesh.isColliding(this.mp_rightBoundingBox);
				if (box != null) {
					this.m_dx = 0.0f;
					this.m_x = box.getX() - this.m_width;
				}
			//}
			
			if (this.m_dy < 0.0f) {
				this.mp_topBoundingBox.setOffsetPosition(this.m_x + this.m_dx,this.m_y + this.m_dy);
				box = mesh.isColliding(this.mp_topBoundingBox);
				if (box != null) {
					this.m_dy = 0.0f;
					this.m_y = box.getY() + box.getHeight();
				}
			}
		
			this.mp_bottomBoundingBox.setOffsetPosition(this.m_x + this.m_dx,this.m_y + this.m_dy);
			box = mesh.isColliding(this.mp_bottomBoundingBox);
			this.mf_falling = (box == null);
			if (!this.mf_falling) {
				this.m_dy = 0.0f;
				this.m_y = box.getY() - this.m_height - 1.0f;
			}
		}
	}
	
	protected void updatePosition() {
		this.m_x += this.m_dx;
		this.m_y += this.m_dy;
	}
	
	protected void updateCollisionBox(float x,float y) {
		this.mp_masterBoundingBox.setOffsetPosition(x,y);
		this.mp_topBoundingBox.setOffsetPosition(x,y);
		this.mp_leftBoundingBox.setOffsetPosition(x,y);
		this.mp_rightBoundingBox.setOffsetPosition(x,y);
		this.mp_bottomBoundingBox.setOffsetPosition(x,y);
	}
	
	public void setJumping(boolean b) {
		this.mf_jumping = b;
	}
	
	public void setLeft(boolean b) {
		this.mf_left = b;
	}
	
	public void setRight(boolean b) {
		this.mf_right = b;
	}
	
	public float getX() { 
		return this.m_x;
	}
	
	public float getY() { 
		return this.m_y;
	}
	
	public abstract void tick(float frameTime);
	public abstract void render(Graphics2D ctx);
	
}
