package view;

public class Dimension {
    private int width;
    private int height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Dimension(double width, double height) {
        this.width = (int) width;
        this.height = (int) height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
