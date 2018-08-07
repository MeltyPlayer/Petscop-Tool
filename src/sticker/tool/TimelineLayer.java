package sticker.tool;

import java.awt.Graphics2D;

public interface TimelineLayer {
  public void dispose();
  public void setFrame(int i);
  public void paint(Graphics2D g);
}
