
package main;
/*
    Class to represent a 4-D location in projective space, i.e. homogeneous coordinates
 */

public class Point4 {

    public double x;
    public double y;
    public double z;
    public double w;

    public Point4() {
        x = y = z = w = 0.0;
    }

    // x,y,z are the familiar Cartesian coordinates, so set w=1
    public Point4(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    public Point4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    // Modify the vector and replace with normalized values
    public void normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        x /= length;
        y /= length;
        z /= length;
    }
    public Point4 addPoint(Point4 that) {
    	this.x += that.x;
    	this.y += that.y;
    	this.z += that.z;
    	return this;
    }
    public Point4 subPoint(Point4 that) {
    	this.x -= that.x;
    	this.y -= that.y;
    	this.z -= that.z;
    	return this;
    }

    // String representation
    public String toString() {
        String br = System.lineSeparator();
        return x + " " + y + " " + z + " " + w;
    }

    // return vector with rhs subtracted off this vector. Both vectors must be homogenized (w=1)
    public Point4 minus(Point4 rhs) {
        return minus(rhs, new Point4());
    }
    public Point4 minus(Point4 rhs, Point4 res) {
        res.x = this.x - rhs.x;
        res.y = this.y - rhs.y;
        res.z = this.z - rhs.z;
        res.w = this.w - rhs.w;
        return res;
    }

    // Return vector: this + t * rhs
    public Point4 addVector(Point4 rhs, double t) {
        return new Point4(
                this.x + t * rhs.x,
                this.y + t * rhs.y,
                this.z + t * rhs.z,
                this.w + t * rhs.w
        );
    }

    // Return the dot product scalar
    // Only makes sense if p1 and p2 are vectors, ie w=0
    public static double dot(Point4 p1, Point4 p2) {
        return p1.x * p2.x + p1.y * p2.y + p1.z * p2.z;
    }

    // Return the cross product vector
    public static Point4 crossProduct(Point4 u, Point4 v) {
        return new Point4(
                u.y * v.z - u.z * v.y,
                u.z * v.x - u.x * v.z,
                u.x * v.y - u.y * v.x,
                0
        );
    }

}
