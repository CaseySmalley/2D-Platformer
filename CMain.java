import java.applet.Applet;
import javax.swing.JFrame;
import java.awt.image.VolatileImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import engine.util.CEngineGlobals;
import engine.core.CKeyInput;
import engine.core.CCoreLoop;

// A singleton class that houses the main gameloop

// naming convention
// c - class
// e - enum
// m - member variable
// f - flag (boolean)
// p - reference (Pointer)
public class CMain implements Runnable {

	private boolean mf_isRunning = true;
	private long m_frameStart = 0;
	private float m_frameTime = 1.0f;
	
	private Applet mp_applet = null;
	private JFrame mp_frame = null;
	private VolatileImage mp_frameBuffer = null;
	private Graphics2D mp_ctx = null;
	private Graphics mp_aCtx = null;
	private CCoreLoop mp_gameLoop = null;
	
	private static CMain p_instance = null;
	
	public CMain() {
		this.mp_applet = new Applet();
		this.mp_frame = new JFrame();
		this.mp_gameLoop = new CCoreLoop();
		
		this.mp_applet.addKeyListener(CKeyInput.getInstance());
		this.mp_applet.setSize(CEngineGlobals.WINDOW_WIDTH,CEngineGlobals.WINDOW_HEIGHT);
		
		this.mp_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mp_frame.setSize(CEngineGlobals.WINDOW_WIDTH,CEngineGlobals.WINDOW_HEIGHT);
		this.mp_frame.setResizable(false);
		this.mp_frame.setLocationRelativeTo(null);
		this.mp_frame.add(this.mp_applet);
		this.mp_frame.setVisible(true);
		
		this.mp_frameBuffer = this.mp_applet.createVolatileImage(CEngineGlobals.WINDOW_WIDTH,CEngineGlobals.WINDOW_HEIGHT);
		
		new Thread(this).start();
	}
	
	public void run() {
		while(this.mf_isRunning) {
			this.m_frameStart = System.nanoTime();
			// Tick
			this.mp_gameLoop.tick(this.m_frameTime);
			// Render
			this.mp_ctx = this.mp_frameBuffer.createGraphics();
			this.mp_gameLoop.render(this.mp_ctx);
			this.mp_ctx.dispose();
			this.mp_aCtx = this.mp_applet.getGraphics();
			this.mp_aCtx.drawImage(this.mp_frameBuffer,0,0,null);
			this.mp_aCtx.dispose();
			
			try {
				Thread.sleep(CEngineGlobals.SLEEP_TIME);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
			this.m_frameTime = (float) (System.nanoTime() - this.m_frameStart) / 1000000000.0f;
		}
	}
	
	public static void main(String args[]) {
		p_instance = new CMain();
	}
}