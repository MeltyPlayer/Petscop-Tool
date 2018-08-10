package sticker.estimated;

public class Pixel {
  public double x, y;

  public Pixel(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "(" + (int) x + ", " + (int) y + ")";
  }
}
