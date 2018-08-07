package sticker.video;

import java.awt.image.BufferedImage;
import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
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

  public void dispose() {
    try {
      frameGrabber.release();
    } catch (Exception e) {
      System.out.println("Mp4FrameGrabber.dispose() exception");
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void start() throws FrameGrabberException {
    try {
      //frameGrabber.setSampleFormat(avutil.AV_SAMPLE_FMT_FLT);
      frameGrabber.start();
    } catch (Exception e) {
      System.out.println("Mp4FrameGrabber.start() exception");
      throw new FrameGrabberException(e);
    }
  }

  public void stop() throws FrameGrabberException {
    try {
      frameGrabber.stop();
    } catch (Exception e) {
      System.out.println("Mp4FrameGrabber.stop() exception");
      throw new FrameGrabberException(e);
    }
  }

  public double getFramesPerSecond() {
    return frameGrabber.getFrameRate();
  }

  public int getFrameCount() {
    return frameGrabber.getLengthInFrames();
  }

  public BufferedImage getFrameImage(int index) throws FrameGrabberException {
    try {
      frameGrabber.setFrameNumber(index);
      Frame frame = frameGrabber.grab();
      return converter.convert(frame);
    } catch (Exception e) {
      throw new FrameGrabberException(e);
    }
  }
}