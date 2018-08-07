package sticker.video;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Loads a video and returns a stream of images.
 */
public class DeinterlacedFrameGrabber {
  private Mp4FrameGrabber frameGrabber;
  private BufferedImage outImage;
  private int[] evenArray, oddArray, outArray;

  public DeinterlacedFrameGrabber(File file) {
    frameGrabber = new Mp4FrameGrabber(file);
    outImage = new BufferedImage(VideoConstants.RESOLUTION.width, VideoConstants.RESOLUTION.height,
        BufferedImage.TYPE_INT_RGB);
    evenArray = new int[VideoConstants.VIDEO_RESOLUTION.width * VideoConstants.VIDEO_RESOLUTION.height];
    oddArray = new int[VideoConstants.VIDEO_RESOLUTION.width * VideoConstants.VIDEO_RESOLUTION.height];
    outArray = new int[VideoConstants.RESOLUTION.width * VideoConstants.RESOLUTION.height];
  }

  public void dispose() {
    frameGrabber.dispose();
  }

  public void start() throws FrameGrabberException {
    frameGrabber.start();
  }

  public void stop() throws FrameGrabberException {
    frameGrabber.stop();
  }

  public double getFramesPerSecond() {
    return frameGrabber.getFramesPerSecond();
  }

  public int getFrameCount() {
    return frameGrabber.getFrameCount() - 1;
  }

  public BufferedImage getFrameImage(int index) throws FrameGrabberException {
    BufferedImage evenImage = frameGrabber.getFrameImage(index);
    BufferedImage oddImage = frameGrabber.getFrameImage(index + 0);
    int iw = evenImage.getWidth(), ih = evenImage.getHeight();
    int w = outImage.getWidth(), h = outImage.getHeight();
    evenImage.getRGB(0, 0, iw, ih, evenArray, 0, iw);
    oddImage.getRGB(0, 0, iw, ih, oddArray, 0, iw);
    for (int y = 0; y < h; y += 2) {
      for (int x = 0; x < w; x++) {
        int ii = getOutputIndex(x, y);
        outArray[ii] = evenArray[getInputIndex(x, y)];
        outArray[ii + w] = oddArray[getInputIndex(x, y)];
      }
    }
    outImage.setRGB(0, 0, w, h, outArray, 0, w);
    return outImage;
  }

  private int getOutputIndex(int x, int y) {
    return y * VideoConstants.RESOLUTION.width + x;
  }

  private int getInputIndex(int x, int y) {
    double xf = 1. * x / VideoConstants.RESOLUTION.width;
    double yf = 1. * y / VideoConstants.RESOLUTION.height;
    int ix = (int) (VideoConstants.VIDEO_RESOLUTION.width * xf);
    int iy = (int) (VideoConstants.VIDEO_RESOLUTION.height * yf);
    return iy * VideoConstants.VIDEO_RESOLUTION.width + ix;
  }
}