package engine.util;

public class CRayResult {
	private float m_x;
	private float m_y;
	private static CRayResult mp_instance = new CRayResult();
	
	private CRayResult() {
		
	}
	
	public void setX(float x) {
		this.m_x = x;
	}
	
	public void setY(float y) {
		this.m_y = y;
	}
	
	public float getX() {
		return this.m_x;
	}
	
	public float getY() {
		return this.m_y;
	}
	
	public static CRayResult getInstance() {
		return mp_instance;
	}
}
