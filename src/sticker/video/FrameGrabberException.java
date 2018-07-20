package sticker.video;

import org.bytedeco.javacv.FrameGrabber;

public class FrameGrabberException extends Exception {
  private static final long serialVersionUID = 6917608529889987383L;
  private FrameGrabber.Exception wrappedException;

  public FrameGrabberException(FrameGrabber.Exception wrappedException) {
    this.wrappedException = wrappedException;
  }

  @Override
  public String toString() {
    return wrappedException.toString();
  }
}
