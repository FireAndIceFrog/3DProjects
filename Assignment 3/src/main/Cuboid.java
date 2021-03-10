package main;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;

public class Cuboid{
    private final Point4 pCentre = new Point4(0,0,0,1);
    Plane[] cube;
    private Point4 objectCentre;
    private Point4 objectRotation;
    private Point4 objectSize;
    private Point4 camera;
    public Cuboid(Point4 pCentre, Point4 pAngles, Point4 pScale, Point4 camera) {
    	cube = new Plane[6];
    	objectCentre = pCentre;
    	objectRotation = pAngles;
    	objectSize = pScale;
    	this.camera = camera;
    	createCube();
    	
    }
    public Plane[] getCube() {
    	return cube;
    }

    
    public void orderCube(Point4 pOrigin) {
    	
//    	Effectively, the planes are all constructed as SHIFT(x), shift(Y), shift(Z) = r0
//    	therefore the normal is [shift,shift,shift] . [x,y,z] = r0
//    	
//    	
    	//get the values of the vector from origin point to center of cube
    	Point4 vec = new Point4(pOrigin.x-objectCentre.x, 
    					pOrigin.y-objectCentre.y, 
    					pOrigin.z-objectCentre.z); 
    	//set the depth of the plane by creating a dot product of vector with 
    	//normal vector
    	double dotProduct = 0.0;
    	for(int i = 0; i < cube.length; ++i) {
    		Point4 normal = cube[i].vNormal;
    		dotProduct = vec.x*normal.x+vec.y*normal.y+vec.z*normal.z;
    		cube[i].setDepth(dotProduct);
    	}	
    }
    
    
    private final double[][] rotateCubeZ(double angleX, double [][] nodes) {
 	   
 		   double sinX = sin(angleX);
 	       double cosX = cos(angleX);
 	        for (double[] node : nodes) {
 	            double x = node[0];
 	            double y = node[1];
 	            double z = node[2];
 	            node[0] = 	x * cosX  - y * sinX;
	            node[1] = 	x * sinX  + y * cosX ;
 	        }
 	       return nodes;
 	   
     }
    private final double[][] rotateCubeX(double angleX, double [][] nodes) {
 	  
 	        double sinX = sin(angleX);
 	        double cosX = cos(angleX);
 	 
 	        for (double[] node : nodes) {
 	            double x = node[0];
 	            double y = node[1];
 	            double z = node[2];
 	 
 	            node[1] = y * cosX - z * sinX;
	            node[2] = y * sinX + z * cosX;
 	        }
 	       return nodes;
 	   
     }
    private final double[][] rotateCubeY(double angleX, double [][] nodes) {
 	   
 	        double sinX = sin(angleX);
 	        double cosX = cos(angleX);
 	 
 	       for (double[] node : nodes) {
	            double x = node[0];
	            double y = node[1];
	            double z = node[2];
	 
	            node[0] = 	x * cosX  + z * sinX;
	            node[2] = -(x * sinX) + z * cosX ;
	        }
 	        return nodes;
 	   
     }
    
    
    private double [][] setNodes() {
    	double nodes[][] = new double[6][3];
    	double xshift, yshift, zshift;
    	double xang, yang, zang;

    	xang = objectRotation.x;
    	yang = objectRotation.y;
    	zang = objectRotation.z;
    	
    	xshift = objectCentre.x;
    	yshift = objectCentre.y;
    	zshift = objectCentre.z;
    	
    	for(int i = 0; i < nodes.length; ++i) {
    		nodes[i] = new double[3];
    		nodes[i][0] = xshift;
			nodes[i][1] = yshift;
			nodes[i][2] = zshift;
    	}
    	
    	//set up the individual nodes
    	nodes[0][2] += -objectSize.z/2;
    	nodes[1][2] += objectSize.z/2;
    	
    	nodes[2][0] += -objectSize.y/2;
    	nodes[3][0] += objectSize.y/2;
    	double[][] sides = {nodes[2],nodes[3]};
    	
    	
    	nodes[4][1] += -objectSize.x/2;
    	nodes[5][1] += objectSize.x/2;
    	
    	nodes = rotateCubeY(yang, nodes);
    	nodes = rotateCubeX(xang, nodes);
    	
    	nodes = rotateCubeZ(zang, nodes);
    	
    	return nodes;    	
    }
    
   
    
    public Plane[] createCube(){
    	double nodes[][] = setNodes();
    	int i = 0;
    	double phi = Math.PI/2;
    	double xshift, yshift, zshift;
    	double xang, yang, zang;

    	xang = objectRotation.x;
    	yang = objectRotation.y;
    	zang = objectRotation.z;
    
    	
    	
    	
    	for(i = 0; i < 2; ++i) {
	    	//front & back along the z-direction face
    		Point4 planeLocation = new Point4(nodes[i][0],nodes[i][1],nodes[i][2]);
	    	cube[i] = BlobFactory.getPlane(
	    			planeLocation, //Center of plane
	    			new Point4(xang,yang,zang), //rotation of plane
	    			objectSize);//objectSize of the plane (will only be unit square?)
	   
	    	cube[i].vNormal = objectCentre.minus(planeLocation);
	    	
    	}
    	int k = -1;
    	for(i = 2; i < 4; ++i) {
    		Point4 planeLocation = new Point4(nodes[i][0],nodes[i][1],nodes[i][2]);
	    	//left & right side face along the z direction
	    	cube[i] = BlobFactory.getPlane(
	    			planeLocation, //Center of plane
	    			new Point4(xang,yang+phi,zang), //rotation of plane
	    			objectSize);//objectSize of the plane (will only be unit square?)
	    	cube[i].vNormal = objectCentre.minus(planeLocation);
	    	k+= 2;
    	}
    	k = -1;
    	for(i = 4; i < 6; ++i) {
    		Point4 planeLocation = new Point4(nodes[i][0],nodes[i][1],nodes[i][2]);
	    	//top & bottom faces along the z direction
	    	cube[i] = BlobFactory.getPlane(
	    			planeLocation, //Center of plane
	    			new Point4(xang+phi,yang,zang), //rotation of plane
	    			objectSize);//objectSize of the plane (will only be unit square?)
	    	cube[i].vNormal = objectCentre.minus(planeLocation);
	    	k+= 2;
    	}
    	k = -1;
    	
    	for(Plane p : cube) {
    		p.setProperty(RandColor.getStandardColor());
    	}
    	return cube;
    }
}
