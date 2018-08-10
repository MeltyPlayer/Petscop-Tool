package sticker.estimated;

import java.awt.Graphics2D;

public class Line implements ThreeDPart {
  private Vertex a, b;

  public Line(Vertex a, Vertex b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void render(Graphics2D g, Camera c) {
    Pixel a = c.projectVertex(this.a), b = c.projectVertex(this.b);
    System.out.println("Line");
    System.out.println(this.a + " ==> " + a);
    System.out.println(this.b + " ==> " + b);
    g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
  }
}
