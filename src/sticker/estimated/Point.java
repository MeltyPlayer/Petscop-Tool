package sticker.estimated;

import java.awt.Graphics2D;

public class Point implements ThreeDPart {
  private Vertex pt;
  private String text;

  public Point(Vertex pt, String text) {
    this.pt = pt;
    this.text = text + " (" + (int) this.pt.x + ", " + (int) this.pt.y + ", " + (int) this.pt.z + ")";
  }

  @Override
  public void render(Graphics2D g, Camera c) {
    Pixel pt = c.projectVertex(this.pt);
    int r = 3;
    g.drawOval((int) pt.x - r, (int) pt.y - r, 2 * r, 2 * r);
    g.drawString(text, (int) pt.x, (int) pt.y);
  }
}
