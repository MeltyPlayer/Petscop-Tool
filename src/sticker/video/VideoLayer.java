package sticker.video;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import sticker.tool.TimelineLayer;

public class VideoLayer implements TimelineLayer {
  private Mp4FrameGrabber frameGrabber;
  private Image frameImage;

  public VideoLayer() {
    frameGrabber = new Mp4FrameGrabber(new File("Episodes/Petscop 9.mp4"));
    try {
      frameGrabber.start();
    } catch (FrameGrabberException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void dispose() {
    frameGrabber.dispose();
  }

  public void setFrame(int i) {
    try {
      frameImage = frameGrabber.getFrameImage(i);
    } catch (FrameGrabberException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // TODO Auto-generated method stub
  }

  public void paint(Graphics2D g) {
    g.drawImage(frameImage, 0, 0, VideoConstants.RESOLUTION.width, VideoConstants.RESOLUTION.height, null);
  }
}
