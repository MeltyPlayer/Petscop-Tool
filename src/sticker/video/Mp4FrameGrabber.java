package sticker.video;

import java.awt.Image;
import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * Loads a video and returns a stream of images.
 */
public class Mp4FrameGrabber {
  private FFmpegFrameGrabber frameGrabber;
  private Java2DFrameConverter converter;

  public Mp4FrameGrabber(File mp4File) {
    frameGrabber = new FFmpegFrameGrabber(mp4File);
    converter = new Java2DFrameConverter();
  }

  public void start() throws FrameGrabberException {
    try {
      frameGrabber.start();
    } catch (Exception e) {
      throw new FrameGrabberException(e);
    }
  }

  public void stop() throws FrameGrabberException {
    try {
      frameGrabber.stop();
    } catch (Exception e) {
      throw new FrameGrabberException(e);
    }
  }

  public double getFramesPerSecond() {
    return frameGrabber.getFrameRate();
  }

  public int getFrameCount() {
    return frameGrabber.getLengthInFrames();
  }

  public Image getFrameImage(int index) throws FrameGrabberException {
    try {
      frameGrabber.setFrameNumber(index);
      Frame frame = frameGrabber.grab();
      return converter.convert(frame);
    } catch (Exception e) {
      throw new FrameGrabberException(e);
    }
  }
}