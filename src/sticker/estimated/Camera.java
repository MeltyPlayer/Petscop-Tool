package sticker.estimated;

import sticker.video.VideoConstants;

public class Camera {
  private Vertex xyz = new Vertex(0, 0, 0);
  double fov, angle;

  private Matrix viewMatrix = new Matrix(), projectionMatrix = new Matrix(), outputMatrix = new Matrix();

  public Camera() {
    setFov(30);
    setAngle(-20);
    setPosition(12.0, -49.0, 22.0);
    // setPosition(60.00, 21.21, 91.92);

    Vertex v = new Vertex(100, 200, 300);
    Pixel p = projectVertex(v);

    // 916.168865 7496.484173 0.953813
    System.out.println(p);
    // System.exit(1);
  }

  public Pixel projectVertex(Vertex v) {
    Pixel p = new Pixel(0, 0);
    outputMatrix.multiply(v, p);
    p.x = (p.x + 1) / 2 * VideoConstants.RESOLUTION.width;
    p.y = VideoConstants.RESOLUTION.height - (p.y + 1) / 2 * VideoConstants.RESOLUTION.width;
    // p.x = (p.x - .5) * VideoConstants.RESOLUTION.width;
    // p.y = (p.y - .5) * VideoConstants.RESOLUTION.height;
    return p;
  }

  public void move(double dir, double amt) {
    double xAmt = Math.cos(dir) * amt;
    double yAmt = Math.sin(dir) * amt;
    setPosition(xyz.x + xAmt, xyz.y + yAmt, xyz.z);

    System.out.println(outputMatrix);
    System.out.println("Camera to " + xyz);
  }

  public void setFov(double fov) {
    this.fov = fov;
    System.out.println("Camera fov to " + fov);
    perspective(fov, 1. * VideoConstants.RESOLUTION.width / VideoConstants.RESOLUTION.height, 1, 1000);
    updateOutputMatrix();
  }

  public double fov() {
    return fov;
  }
  
  public void setAngle(double angle) {
    this.angle = angle;
    setPosition(xyz.x, xyz.y, xyz.z);
    System.out.println("Camera angle to " + angle);
  }
  
  public double angle() {
    return angle;
  }

  public double x() {
    return xyz.x;
  }

  public double y() {
    return xyz.y;
  }

  public double z() {
    return xyz.z;
  }

  public void setPosition(double x, double y, double z) {
    xyz.x = x;
    xyz.y = y;
    xyz.z = z;
    double rad = angle / 180 * Math.PI;
    double co = Math.cos(rad);
    double si = Math.sin(rad);
    Vertex center = new Vertex(x, y + co, z + si);
    lookAt(xyz, center, new Normal(0, 0, 1));
    updateOutputMatrix();
    System.out.println("Camera to " + xyz);
  }

  private void lookAt(Vertex eye, Vertex center, Normal up) {
    Normal forward = new Normal(center.x - eye.x, center.y - eye.y, center.z - eye.z);
    forward.normalize();

    Normal right = new Normal(forward.y * up.z - forward.z * up.y, forward.z * up.x - forward.x * up.z,
        forward.x * up.y - forward.y * up.x);
    right.normalize();

    up.x = forward.y * right.z - forward.z * right.y;
    up.y = forward.z * right.x - forward.x * right.z;
    up.z = forward.x * right.y - forward.y * right.x;
    up.multiply(-1);
    up.normalize();

    viewMatrix.identity();
    viewMatrix.set(0, 0, right.x);
    viewMatrix.set(0, 1, right.y);
    viewMatrix.set(0, 2, right.z);

    viewMatrix.set(1, 0, up.x);
    viewMatrix.set(1, 1, up.y);
    viewMatrix.set(1, 2, up.z);

    viewMatrix.set(2, 0, -forward.x);
    viewMatrix.set(2, 1, -forward.y);
    viewMatrix.set(2, 2, -forward.z);

    glhTranslatef2(viewMatrix, -eye.x, -eye.y, -eye.z);
  }

  void glhTranslatef2(Matrix matrix, double x, double y, double z) {
    matrix.set(12, matrix.get(0)*x+matrix.get(4)*y+matrix.get(8)*z+matrix.get(12));
    matrix.set(13, matrix.get(1)*x+matrix.get(5)*y+matrix.get(9)*z+matrix.get(13));
    matrix.set(14, matrix.get(2)*x+matrix.get(6)*y+matrix.get(10)*z+matrix.get(14));
    matrix.set(15, matrix.get(3)*x+matrix.get(7)*y+matrix.get(11)*z+matrix.get(15));
  }

  private void perspective(double fov, double aspect, double nearValue, double farValue) {
    double t = nearValue * Math.tan(Math.PI / 180 * fov / 2);
    double r = t * aspect;
    projectionMatrix.identity();
    projectionMatrix.set(0, 0, nearValue / r);
    projectionMatrix.set(1, 1, nearValue / t);
    projectionMatrix.set(2, 2, -(farValue + nearValue) / (farValue - nearValue));
    projectionMatrix.set(2, 3, -2 * farValue * nearValue / (farValue - nearValue));
    projectionMatrix.set(3, 2, -1);
    projectionMatrix.set(3, 3, 0);
  }

  private void updateOutputMatrix() {
    // projectionMatrix.multiply(viewMatrix, outputMatrix);
    viewMatrix.multiply(projectionMatrix, outputMatrix); // WORKS
  }
}
