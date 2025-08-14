package Background;

public class Rock {
    private int x, y, size;

    public Rock() {
        this.x = 0;
        this.y = 0;
        this.size = 0;
    }
    
    public Rock(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // Getter methods
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    
    // Setter methods (ถ้าต้องการ)
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSize(int size) { this.size = size; }
}
