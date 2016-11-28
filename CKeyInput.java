package engine.core;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import engine.entity.CEntity;

public class CKeyInput implements KeyListener {
	
	private static CKeyInput p_instance = new CKeyInput();
	private static CEntity p_currentPlayer = null;
	
	private CKeyInput() {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W: p_currentPlayer.setJumping(true); break;
			case KeyEvent.VK_A: p_currentPlayer.setLeft(true); break;
			case KeyEvent.VK_D: p_currentPlayer.setRight(true); break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W: p_currentPlayer.setJumping(false); break;
			case KeyEvent.VK_A: p_currentPlayer.setLeft(false); break;
			case KeyEvent.VK_D: p_currentPlayer.setRight(false); break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static void setPlayer(CEntity newPlayer) {
		p_currentPlayer = newPlayer;
	}
	
	public static CKeyInput getInstance() {
		return p_instance;
	}
	
}