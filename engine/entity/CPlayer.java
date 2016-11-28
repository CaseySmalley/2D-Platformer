package engine.entity;

import java.awt.Graphics2D;
import java.awt.Color;

import engine.util.CEngineGlobals;
import engine.entity.CEntity;

public class CPlayer extends CEntity {
	
	public CPlayer(float x,float y,float width,float height) {
		super(x,y,width,height);
		
		this.m_moveSpeed = 5.0f;
		this.m_maxMoveSpeed = 7.0f;
		this.m_stopSpeed = 6.0f;
		this.m_fallSpeed = 0.5f;
		this.m_maxFallSpeed = 20.0f;
		this.m_jumpStart = -10.0f;
	}
	
	@Override
	public void tick(float frameTime) {
		this.calculateMovement(frameTime);
		this.calculateCollision(CEngineGlobals.temp);
		this.updatePosition();
		this.updateCollisionBox(this.m_x,this.m_y);
	}
	
	@Override
	public void render(Graphics2D ctx) {
		ctx.setColor(Color.black);
		ctx.fillRect((int) this.m_x,(int) this.m_y,(int) this.m_width,(int) this.m_height);
		this.mp_topBoundingBox.render(ctx);
		this.mp_leftBoundingBox.render(ctx);
		this.mp_rightBoundingBox.render(ctx);
		this.mp_bottomBoundingBox.render(ctx);
	}
	
}
