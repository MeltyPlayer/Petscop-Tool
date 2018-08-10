package sticker.tool;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import sticker.estimated.Camera;
import sticker.estimated.ThreeDReconstructionLayer;
import sticker.video.FrameGrabberException;
import sticker.video.VideoConstants;
import sticker.video.VideoLayer;

public class TimelineWindow {
  public static void main(String args[]) throws FrameGrabberException {
    TimelineWindow window = new TimelineWindow();
  }

  private Camera camera = new Camera();
  private JFrame window;
  private List<TimelineLayer> layers = new LinkedList<>();
  private Timeline timeline = new Timeline();

  public TimelineWindow() throws FrameGrabberException {
    layers.add(new VideoLayer());
    layers.add(new ThreeDReconstructionLayer(camera));

    window = new JFrame() {
      @Override
      public void paint(Graphics g) {
        TimelineWindow.this.paint((Graphics2D) g);
      }
    };
    window.setSize(VideoConstants.RESOLUTION);
    window.setVisible(true);
    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        TimelineWindow.this.onClose();
        System.exit(0);
      }
    });
    window.addKeyListener(new KeyListener() {
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int adDir = (keyCode == KeyEvent.VK_D ? 1 : 0) - (keyCode == KeyEvent.VK_A ? 1 : 0);
        if (adDir != 0) {
          if (timeline.move(adDir)) {
            setFrame(timeline.getFrame());
          }
        }
        int arrowDir = (keyCode == KeyEvent.VK_RIGHT ? 1 : 0) - (keyCode == KeyEvent.VK_LEFT ? 1 : 0);
        if (arrowDir != 0) {
          if (timeline.move(100 * arrowDir)) {
            setFrame(timeline.getFrame());
          }
        }

        int jlDir = (keyCode == KeyEvent.VK_L ? 1 : 0) - (keyCode == KeyEvent.VK_J ? 1 : 0);
        int ikDir = (keyCode == KeyEvent.VK_I ? 1 : 0) - (keyCode == KeyEvent.VK_K ? 1 : 0);
        int uoDir = (keyCode == KeyEvent.VK_U ? 1 : 0) - (keyCode == KeyEvent.VK_O ? 1 : 0);
        if (jlDir != 0 || ikDir != 0 || uoDir != 0) {
          camera.setPosition(camera.x() + jlDir, camera.y() + ikDir, camera.z() + uoDir);
          setFrame(timeline.getFrame());
        }

        int nmDir = (keyCode == KeyEvent.VK_M ? 1 : 0) - (keyCode == KeyEvent.VK_N ? 1 : 0);
        if (nmDir != 0) {
          camera.setFov(camera.fov() + nmDir);
          setFrame(timeline.getFrame());
        }

        int yhDir = (keyCode == KeyEvent.VK_Y ? 1 : 0) - (keyCode == KeyEvent.VK_H ? 1 : 0);
        if (nmDir != 0) {
          camera.setAngle(camera.angle() + yhDir);
          setFrame(timeline.getFrame());
        }
      }

      public void keyReleased(KeyEvent e) {
      }

      public void keyTyped(KeyEvent e) {
      }
    });

    setFrame(13905);
  }

  private void onClose() {
    for (TimelineLayer layer : layers) {
      layer.dispose();
    }
  }

  private void setFrame(int i) {
    timeline.goTo(i);
    for (TimelineLayer layer : layers) {
      layer.setFrame(i);
    }
    window.repaint();
  }

  private void paint(Graphics2D g) {
    for (TimelineLayer layer : layers) {
      layer.paint(g);
    }
    int frameIndex = timeline.getFrame();
    final String timeText = frame2Time(frameIndex);
    FontMetrics fm = g.getFontMetrics();
    int w = fm.stringWidth(timeText);
    System.out.println(w);
    g.setColor(Color.white);
    g.drawString("" + frameIndex, 10, 40);
    g.drawString(timeText, VideoConstants.RESOLUTION.width - 10 - w, 40);
    g.setColor(Color.black);
    g.drawString("" + frameIndex, 10, 50);
    g.drawString(timeText, VideoConstants.RESOLUTION.width - 10 - w, 50);
  }

  private String frame2Time(int frameIndex) {
    final int fps = 30;
    final double rawSeconds = 1. * frameIndex / fps;
    final int milliseconds = (int) (rawSeconds * 1000) % 1000;
    final int seconds = (int) rawSeconds % 60;
    final int minutes = (int) (rawSeconds / 60);
    return String.format("%d:%02d:%04d", minutes, seconds, milliseconds);
  }
}
