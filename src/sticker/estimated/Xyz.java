package sticker.estimated;

public abstract class Xyz {
  public double x, y, z;

  public Xyz(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public abstract double get(int i);
  
  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }
}
