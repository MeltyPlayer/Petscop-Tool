package sticker.estimated;

public class Normal extends Xyz {
  public Normal(double x, double y, double z) {
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
        return 0;
    }
    return -1;
  }

  public void normalize() {
    double length = Math.sqrt(x * x + y * y + z * z);
    multiply(1 / length);
  }
  
  public void multiply(double factor) {
    x *= factor;
    y *= factor;
    z *= factor;
  }
}
