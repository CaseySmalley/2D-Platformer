package engine.level;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics2D;

import engine.util.CCollisionBox;
import engine.level.CNode;

public class CMesh {
	
	private List<CCollisionBox> mp_boundingBoxes = null;
	
	public CMesh(String url) {
		try {
			this.mp_boundingBoxes = new ArrayList<CCollisionBox>();
			List<String> rawFile = Files.readAllLines(Paths.get(url), StandardCharsets.UTF_8);
			String[] line = null;
			for (int i = 0; i < rawFile.size(); ++i) {
				line = rawFile.get(i).split(" ");
				this.mp_boundingBoxes.add(new CCollisionBox(
					Float.parseFloat(line[0]),
					Float.parseFloat(line[1]),
					Float.parseFloat(line[2]),
					Float.parseFloat(line[3])
				));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setOffsetPosition(float x,float y) {
		for (int i = 0; i < this.mp_boundingBoxes.size(); ++i) {
			this.mp_boundingBoxes.get(i).setOffsetPosition(x,y);
		}
	}
	
	public boolean isCollidingMaster(CCollisionBox box) {
		return box.isColliding(this.mp_boundingBoxes.get(0));
	}
	
	public CCollisionBox isColliding(CCollisionBox box) {
			for (int i = 1; i < this.mp_boundingBoxes.size(); ++i) {
				if (box.isColliding(this.mp_boundingBoxes.get(i)))
					return this.mp_boundingBoxes.get(i);
			}
		return null;
	}
	
	public void render(Graphics2D ctx) {
		for (int i = 0; i < this.mp_boundingBoxes.size(); ++i) {
			this.mp_boundingBoxes.get(i).render(ctx);
		}
	}
	
}