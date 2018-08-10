package sticker.estimated;

public class Vertex extends Xyz {
  public Vertex(double x, double y, double z) {
    super(x, y, z);
  }

  public double get(int i) {
    switch (i) {
      case 0:
        return x;
      case 1:
        return y;
      case 2:
        return z;
      case 3:
        return 1;
    }
    return -1;
  }
}
