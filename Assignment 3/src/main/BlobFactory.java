package main;
import java.awt.*;

/*
    This class contains a set of "factory" functions to generate instances of various surfaces.

    You can add other functions here for generating cubes, spheres, etc.

 */

public class BlobFactory {

    final  static double a090 = Math.toRadians(90);
    final  static double a180 = Math.toRadians(180);
    final  static double a270 = Math.toRadians(270);
    static double xz, yz ;
    static double xy = xz = yz = Math.PI/2;
    static Cuboid cube;
    static Point4 camera;
    static Plane p;
    public static void setCamera (Point4 origin) {
    	camera = origin;
    }
    public static Plane getPlane(Point4 pCentre, Point4 pAngles, Point4 pScale) {

        Matrix4 tLW = Matrix4.localToWorld(pCentre, pAngles, pScale);
        Matrix4 tWL = Matrix4.worldToLocal(pCentre, pAngles, pScale);

        Plane plane = new Plane(tLW, tWL);
        plane.setDepth(pCentre.z);
        plane.setProperty(Color.DARK_GRAY, 1, 1);
        p = plane;
        return plane;

    }
    public static Plane[] getCube(Point4 pCentre, Point4 pAngles, Point4 pScale) {
    	RandColor.setColor(Color.red);
    	cube = new Cuboid( pCentre, pAngles, pScale, camera);
    	cube.orderCube(camera);
    	return cube.getCube();
    }
    public static Plane[] getCube(String filepath,Point4 pCentre, Point4 pAngles, Point4 pScale) {
    	cube = new Cuboid(pCentre, pAngles, pScale, camera);
    	cube.orderCube(camera);
    	
    	Plane [] planes = cube.getCube();
    	for(Plane p : planes) {
    		p.setProperty(filepath);
    	}
    	
    	return planes;
    }
    public static Plane[] getWierdCube(String filepath,Point4 pCentre, Point4 pAngles, Point4 pScale) {
    	Cube cube = new Cube(pCentre, pAngles, pScale);
    	cube.setPlanes();
    	cube.setProperty(filepath);
    	return cube.getPlanes();
    }
    
    public static Sphere getSphere(double radius,Point4 pCentre, Point4 pAngles, Point4 pScale) {
    	 Matrix4 tLW = Matrix4.localToWorld(pCentre, pAngles, pScale);
         Matrix4 tWL = Matrix4.worldToLocal(pCentre, pAngles, pScale);
         Sphere sphere = new Sphere(tLW, tWL);
         sphere.setCentre(pCentre);
         sphere.setRadius(radius);
         return sphere;
    }
    public static Surface[] setColor(Surface [] pSurface, Color c) {
    	for(Surface s : pSurface)
    		s.setProperty(c);
    	return pSurface;
    }
    
    public static Sphere getCircle(double radius,Point4 pCentre, Point4 pScale) {
        Matrix4 mScale = Matrix4.createScale(pScale.x, pScale.y, pScale.z);
    	
        Matrix4 tLW = Matrix4.lookBack(pCentre,camera , new Point4(1,1,1,1));
        Matrix4 tWL = Matrix4.lookAt(pCentre, camera, new Point4(1,1,1,1));

        tLW = tLW.times(mScale);
        tWL = tWL.times(mScale);
        
        Sphere sphere = new Sphere(tLW, tWL);
        sphere.setDepth(pCentre.z);
        sphere.setProperty(Color.DARK_GRAY);
        sphere.setRadius(radius);
        return sphere;
   }
    
    
    
    public static Surface[] setDepth (Surface[] surfaces, double depth) {
    	for(Surface s : surfaces) {
    		s.setDepth(s.getDepth()+depth);
    	}
    	return surfaces;
    }
    public static Surface setDepth (Surface surfaces, double depth) {
    	surfaces.setDepth(surfaces.getDepth()+depth);
    	return surfaces;
    }
    
    
    

    // You can add other functions as you see fit, e.g.:
    // public static Plane[] getCuboid( .....


}
