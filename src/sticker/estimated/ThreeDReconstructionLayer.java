package sticker.estimated;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import sticker.tool.TimelineLayer;

public class ThreeDReconstructionLayer implements TimelineLayer {
  private Camera camera;

  public ThreeDReconstructionLayer(Camera camera) {
    this.camera = camera;
  }

  private List<ThreeDPart> threeDParts = new ArrayList<ThreeDPart>() {
    {
      Vertex wallCorner = new Vertex(0, 0, 0);
      Vertex wallX = new Vertex(20, 0, 0);
      Vertex wallY = new Vertex(0, -20, 0);
      Vertex wallZ = new Vertex(0, 0, 20);
      
      add(new Point(wallCorner, "Origin"));
      add(new Point(wallX, "Wall +X"));
      add(new Line(wallCorner, wallX));
      add(new Point(wallY, "Wall +Y"));
      add(new Line(wallCorner, wallY));
      add(new Point(wallZ, "Wall +Z"));
      add(new Line(wallCorner, wallZ));

      Vertex floorCorner = new Vertex(10, -10, 0);
      Vertex floorX = new Vertex(10 + 20, -10, 0);
      Vertex floorY = new Vertex(10, -10 - 20, 0);
      add(new Point(floorCorner, "Floor Corner"));
      add(new Point(floorX, "Floor +X"));
      add(new Line(floorCorner, floorX));
      add(new Point(floorY, "Floor +Y"));
      add(new Line(floorCorner, floorY));
    }
  };

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  @Override
  public void setFrame(int i) {
    // TODO Auto-generated method stub

  }

  @Override
  public void paint(Graphics2D g) {
    g.setColor(Color.red);
    for (ThreeDPart tdp : threeDParts) {
      tdp.render(g, camera);
    }
  }
}
