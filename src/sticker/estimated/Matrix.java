package sticker.estimated;

public class Matrix {
  private double[] rows = new double[16];

  public void identity() {
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        set(x, y, x == y ? 1 : 0);
      }
    }
  }

  public double get(int i) {
    return rows[i];
  }
  
  public double get(int x, int y) {
    return get(y * 4 + x);
  }

  public void set(int i, double value) {
    rows[i] = value;
  }

  public void set(int x, int y, double value) {
    set(y * 4 + x, value);
  }

  public void multiply(Matrix rhs, Matrix dst) {
    Matrix lhs = this;
    assert (lhs != rhs);
    assert (lhs != dst);
    assert (rhs != dst);

    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        double total = 0;
        for (int i = 0; i < 4; i++) {
          total += lhs.get(i, y) * rhs.get(x, i);
        }
        dst.set(x, y, total);
      }
    }
  }

  public void multiply(Xyz rhs, Pixel dst) {
    Matrix lhs = this;
    double[] values = new double[4];
    for (int y = 0; y < 4; y++) {
      double total = 0;
      for (int i = 0; i < 4; i++) {
        //total += lhs.get(i, y) * rhs.get(i);
        total += lhs.get(y, i) * rhs.get(i); // WORKS
      }
      values[y] = total;
    }
    //System.out.println("Raw: " + arrayToString(values));
    values[0] /= values[3];
    values[1] /= values[3];
    dst.x = values[0];
    dst.y = values[1];
    //System.out.println("Processed: " + arrayToString(values));
  }
  
  public String arrayToString(double[] values) {
    String str = "(";
    for (int i = 0; i < 4; i++) {
      if (i > 0) {
        str += ", ";
      }
      str += values[i];
    }
    str += ")";
    return str;
  }

  @Override
  public String toString() {
    String str = "";
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        str += get(x, y) + "  ";
      }
      str += "\n";
    }
    return str;
  }
}
