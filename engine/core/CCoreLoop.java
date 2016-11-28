package engine.core;

import java.awt.Graphics2D;
import java.awt.Color;

import engine.core.CKeyInput;
import engine.util.CEngineGlobals;
import engine.util.CRay;
import engine.util.CCollisionBox;
import engine.entity.CEntity;
import engine.entity.CPlayer;
import engine.level.CMesh;

public class CCoreLoop {
	
	private CEntity e = null;
	
	public CCoreLoop() {
		e = new CPlayer(CEngineGlobals.WINDOW_WIDTH/2 - 30,CEngineGlobals.WINDOW_HEIGHT/2 - 60,20,120);
		CEngineGlobals.temp = new CMesh("resource/testMesh.dat");
		CEngineGlobals.temp.setOffsetPosition(0.0f,0.0f);
	
		CKeyInput.setPlayer(e);
	}
	
	public void tick(float frameTime) {
		System.out.println("FPS :" + (1.0f / frameTime));
		e.tick(frameTime);
	}
	
	public void render(Graphics2D ctx) {
		ctx.setColor(Color.GRAY);
		ctx.fillRect(0,0,CEngineGlobals.WINDOW_WIDTH,CEngineGlobals.WINDOW_HEIGHT);
		e.render(ctx);
		CEngineGlobals.temp.render(ctx);
	}
}
