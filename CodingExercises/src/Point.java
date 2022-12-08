public class Point {
    private int x;
    private int y;
    public Point()
    {

    }
    public Point(int x, int y)
    {
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public double distance()
    {
        return Math.sqrt((getX()-0)*(getX()-0)+(getY()-0)*(getY()-0));
    }
    public double distance(int x, int y)
    {
        return Math.sqrt((getX()-x)*(getX()-x)+(getY()-y)*(getY()-y));
    }
    public double distance(Point var) //parameter of type point (thus a parameter of a defined class can also be defined
    {
        return Math.sqrt((getX()-var.getX())*(getX()-var.getX())+(getY()-var.getY())*(getY()-var.getY()));
    }
}
