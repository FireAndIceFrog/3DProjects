/*
    Class to represent an imaginary camera on which a 3D scene is viewed. The camera is placed into
    the world scene according to the transformation matrices.

    PART 1: Modify this class to implement z-buffering.

 */
package main;
import java.awt.image.BufferedImage;

/* Class to represent the camera and its imaging plane. */
public class Camera {

    // z-location of the projection screen (parallel to xy plane)
    private final double dScreen = 1.0;

    // Boundaries of the image extent in physical dimensions
    private double xb1, xb2, yb1, yb2;

    // Size of a pixel in physical dimensions
    private double pixScale;

    // The 3d surfaces will be drawn to this publicly accessible image
    public BufferedImage image = null;

    // The array to hold the "z-buffer" (see lecture notes)
    public double[][] zBuffer = null;

    // Camera placement matrices
    //   - camera to world
    //   - world to camera
    public Matrix4 tCW, tWC;

    // Constructor intializes with pixel dimensions and y field of view
    public Camera(int npixx, int npixy, double fovy) {

        // Aspect ratio
        double aspectr = (double) (npixx - 1) / (double) (npixy - 1);

        // Projection plane
        double halfy = dScreen * Math.tan(0.5 * fovy);
        double halfx = aspectr * halfy;
        pixScale = 2 * halfy / (npixy - 1);

        // Boundaries of projection plane in world coordinates
        xb1 = -halfx;
        yb1 = -halfy;
        xb2 = halfx;
        yb2 = halfy;
        System.out.println("X boundary: " + xb1 + " " + xb2);
        System.out.println("Y boundary: " + yb1 + " " + yb2);

        // Initialize the image with a white background
        image = new BufferedImage(npixx, npixy, BufferedImage.TYPE_INT_RGB);
        zBuffer = new double[npixx][npixy];
        
        
        
        for (int y = 0; y < npixx; ++y) {
            for (int x = 0; x < npixy; ++x) {
                image.setRGB(x, y, 0xffffffff);
                zBuffer[x][y] = -100000;
            }
        }
    }

    /* Just draw the x and y axes on the image */
    public void putAxes() {
        int npixx = this.image.getWidth();
        int npixy = this.image.getHeight();
        for (int k = 0; k < 50; ++k) {
            int rgb = 0;
            this.image.setRGB(k+(npixx - 1) / 2, (npixy - 1) / 2, rgb);
            this.image.setRGB((npixx - 1) / 2, k+(npixy - 1) / 2, rgb);
        }
    }

    public void repoint(Point4 pOrigin, Point4 pTarget, Point4 vUp) {
    	BlobFactory.setCamera(pOrigin);
        this.tCW = Matrix4.lookBack(pOrigin, pTarget, vUp);
        this.tWC = Matrix4.lookAt(pOrigin, pTarget, vUp);
    }

    public Point4 getOrigin() {
        return tCW.times(new Point4(0,0,0,1));
    }

    public Point4 getScreen(int xpix, int ypix) {
        double xScreen = xb1 + xpix * pixScale;
        double yScreen = yb1 + ypix * pixScale;
        return tCW.times(new Point4(xScreen, yScreen, dScreen, 1));
    }

    public void setValue(Point4 pNormal,Point4 pSurface, int xpix, int ypix, int rgb) {
    	Point4 OriginVec = pSurface.minus(getOrigin());
    	Point4 tempNorm = pNormal;
    	OriginVec.normalize();
    	tempNorm.normalize();
    	//projection of pNormal -> OriginVec = originVec*(pNormal/|pNormal|)
    	double length = Math.sqrt(tempNorm.x*tempNorm.x+tempNorm.y*tempNorm.y+tempNorm.z*tempNorm.z);
    	double projection = Point4.dot(OriginVec,tempNorm)/length;
    	//will produce
    	
    	
    	if(zBuffer[xpix][ypix] < pSurface.z ) {
    		zBuffer[xpix][ypix] = pSurface.z;
    		image.setRGB(xpix, ypix, rgb);
    	}

    }

}
