package main;
/*
    Class to represent a 4-D matrix for transformations working in homogeneous coordinates.
 */

public class Matrix4 {

    static final int SIZE = 4;
    private double mx[][] = new double[SIZE][SIZE];

    // Construct a 4X4 matrix with all 16 inputs arranged row major
    public Matrix4(double m00, double m01, double m02, double m03,
                   double m10, double m11, double m12, double m13,
                   double m20, double m21, double m22, double m23,
                   double m30, double m31, double m32, double m33)

    {
        mx[0][0] = m00;
        mx[0][1] = m01;
        mx[0][2] = m02;
        mx[0][3] = m03;
        mx[1][0] = m10;
        mx[1][1] = m11;
        mx[1][2] = m12;
        mx[1][3] = m13;
        mx[2][0] = m20;
        mx[2][1] = m21;
        mx[2][2] = m22;
        mx[2][3] = m23;
        mx[3][0] = m30;
        mx[3][1] = m31;
        mx[3][2] = m32;
        mx[3][3] = m33;
    }

    // Constructors with no parameters initializes with identity matrix
    public Matrix4() {
        this(   1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    // Get a string representation so that we can use it with System.out.println
    public String toString() {
        String br = System.lineSeparator();
        String str = "";
        for (int i = 0; i < SIZE; ++i) {
            str += mx[i][0] + " " + mx[i][1] + " " + mx[i][2] + " " + mx[i][3] + "\n";
        }
        return str;
    }

    // Overloaded methods carry out multiplications in various forms. This construct also allows
    // for the chaining of several multiplications
    public Point4 times(Point4 rhs) {
        return times(rhs, new Point4());
    }

    public Point4 times(Point4 rhs, Point4 res) {
        res.x = mx[0][0] * rhs.x + mx[0][1] * rhs.y + mx[0][2] * rhs.z + mx[0][3] * rhs.w;
        res.y = mx[1][0] * rhs.x + mx[1][1] * rhs.y + mx[1][2] * rhs.z + mx[1][3] * rhs.w;
        res.z = mx[2][0] * rhs.x + mx[2][1] * rhs.y + mx[2][2] * rhs.z + mx[2][3] * rhs.w;
        res.w = mx[3][0] * rhs.x + mx[3][1] * rhs.y + mx[3][2] * rhs.z + mx[3][3] * rhs.w;
        return res;
    }

    public Matrix4 times(Matrix4 rhs) {
        return times(rhs, new Matrix4());
    }

    public Matrix4 times(Matrix4 rhs, Matrix4 res) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                double sum = 0;
                for (int k = 0; k < SIZE; ++k) {
                    //res.mx[i][j] += mx[i][k] * rhs.mx[k][j];
                    sum += mx[i][k] * rhs.mx[k][j];
                }
                res.mx[i][j] = sum;
            }
        }
        return res;
    }

    // Some static methods to work as factory functions

    // Get a rotation matrix around x-axis (or in yz plane)
    public static Matrix4 createRotationX(double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        return new Matrix4(
                1, 0, 0, 0,
                0, cos, -sin, 0,
                0, sin, cos, 0,
                0, 0, 0, 1);
    }

    // Get a rotation matrix around y-axis (or in xz plane)
    public static Matrix4 createRotationY(double ang) {
        double c = Math.cos(ang);
        double s = Math.sin(ang);
        return new Matrix4(
                c, 0, s, 0,
                0, 1, 0, 0,
                -s, 0, c, 0,
                0, 0, 0, 1);
    }

    // Get a rotation matrix around z-axis (or in xy plane)
    public static Matrix4 createRotationZ(double ang) {
        double cos = Math.cos(ang);
        double sin = Math.sin(ang);
        return new Matrix4(cos, -sin, 0, 0,    // = x
        				   sin, cos, 0, 0,     // = y
        				   0, 0, 1, 0,         // = z
        				   0, 0, 0, 1);
    }

    // Get a scaling matrix with scale factors in x, y, and z
    public static Matrix4 createScale(double sx, double sy, double sz) {
        return new Matrix4(sx,  0,  0,  0,
                		    0, sy,  0,  0,
                		    0,  0, sz,  0,
                		    0,  0,  0,  1);
    }

    // Get a displacement matrix for translations in x, y, and z directions
    public static Matrix4 createDisplacement(double dx, double dy, double dz) {
        return new Matrix4(1, 0, 0, dx,
                		   0, 1, 0, dy,
                		   0, 0, 1, dz,
                		   0, 0, 0, 1);
    }

    // Get a matrix for object placement into a 3D world coordinate system by applying
    // the scalings, rotations, and displacements stored in the 4D Point4 instances.
    public static Matrix4 localToWorld(Point4 pOrigin, Point4 pAngles, Point4 pScale) {
        return Matrix4.createDisplacement(pOrigin.x, pOrigin.y, pOrigin.z)
                .times(Matrix4.createRotationX(pAngles.x))
                .times(Matrix4.createRotationY(pAngles.y))
                .times(Matrix4.createRotationZ(pAngles.z))
                .times(Matrix4.createScale(pScale.x, pScale.y, pScale.z));
    }

    // Do the inverse of the above object placement method
    public static Matrix4 worldToLocal(Point4 pOrigin, Point4 pAngles, Point4 pScale) {
        return Matrix4.createScale(1.0 / pScale.x, 1.0 / pScale.y, 1.0 / pScale.z)
                .times(Matrix4.createRotationZ(-pAngles.z))
                .times(Matrix4.createRotationY(-pAngles.y))
                .times(Matrix4.createRotationX(-pAngles.x))
                .times(Matrix4.createDisplacement(-pOrigin.x, -pOrigin.y, -pOrigin.z));
    }

    // Get a matrix to convert from world to camera coordinates (see lectures for details)
    //   pCamera -> coordinates of the camera in the world scene
    //   pTarget -> camera is point towards these coordinates
    //   vUp -> vector representing up direction
    public static Matrix4 lookAt(Point4 pCamera, Point4 pTarget, Point4 vUp) {
        Point4 zaxis = pTarget.minus(pCamera);
        Point4 xaxis = Point4.crossProduct(vUp, zaxis);
        Point4 yaxis = Point4.crossProduct(zaxis, xaxis);
        xaxis.normalize();
        yaxis.normalize();
        zaxis.normalize();
        Matrix4 R = new Matrix4(
                xaxis.x, xaxis.y, xaxis.z, 0,
                yaxis.x, yaxis.y, yaxis.z, 0,
                zaxis.x, zaxis.y, zaxis.z, 0,
                0, 0, 0, 1);
        Matrix4 T = createDisplacement(-pCamera.x, -pCamera.y, -pCamera.z);
        return R.times(T);
    }

    // Inverse of the above - get a matrix to convert from camera to world coordinates
    public static Matrix4 lookBack(Point4 pCamera, Point4 pTarget, Point4 vUp) {
        Point4 zaxis = pTarget.minus(pCamera);
        Point4 xaxis = Point4.crossProduct(vUp, zaxis);
        Point4 yaxis = Point4.crossProduct(zaxis, xaxis);
        xaxis.normalize();
        yaxis.normalize();
        zaxis.normalize();
        Matrix4 R = new Matrix4(
                xaxis.x, yaxis.x, zaxis.x, 0,
                xaxis.y, yaxis.y, zaxis.y, 0,
                xaxis.z, yaxis.z, zaxis.z, 0,
                0, 0, 0, 1);
        Matrix4 T = createDisplacement(pCamera.x, pCamera.y, pCamera.z);
        return T.times(R);
    }
}
