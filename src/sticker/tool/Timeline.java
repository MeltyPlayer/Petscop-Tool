package sticker.tool;

public class Timeline {
  private int frameIndex;
  private int start = 13800;
  private int end = 15150;

  public Timeline() {
    frameIndex = start;
  }

  public int getFrame() {
    return frameIndex;
  }

  public boolean goTo(int frameIndex) {
    int originalFrame = this.frameIndex;
    this.frameIndex = Math.max(start, Math.min(frameIndex, end));
    return originalFrame != this.frameIndex;
  }

  /**
   * Attempts to move the number of frames. Positive is forward, negative is
   * backward. If moving was successful, returns true.
   * 
   * @param numFrames
   * @return
   */
  public boolean move(int numFrames) {
    return goTo(frameIndex + numFrames);
  }

  private class Range {
    public int start, end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }
  }
}