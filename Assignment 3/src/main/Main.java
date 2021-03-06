package main;

import java.awt.Color;
import java.util.ArrayList;

/*
    Driver class to create a 3D graphics scene.
 */
public class Main {


    public static void main(String[] args) {
	    System.out.println("Here comes my awesome 3D scene");
	    int i = 0;
	    // Define the camera field of view and image dimensions
	    int npixx = 801;
	    int npixy = 801;
	    double fovy = Math.toRadians(20);
	    Camera camera = new Camera(npixx, npixy, fovy);
	    Point4 cameraOrigin = new Point4(0,10,100);

	    // Instance of the graphics scene
        Scene3D myscene = new Scene3D();

        // Place the camera in the scene
        camera.repoint(
        		cameraOrigin,    // Position of camera is up the z-axis
                new Point4(0,0,0),        // Camera is pointing to the origin
                new Point4(0,-1, 0, 0));  // Up direction is down the y-axis!
      
        myscene.setCamera(camera);

        // Position of the light source
        myscene.setLight(new Point4(0,-10,100));
        
        ArrayList<Surface> render= createObjects();
        
        
        
     
        
        
    	myscene.render(render);
        
       

        // Use this to verify the pointing direction of the camera. Comment this out when not
        // needed.
        camera.putAxes();

        // Display the image in a JPanel
        Display.show(camera.image);

    }
    static Surface [] addList(int i, Surface[] render, Surface[] object) {
    	for(int j = 0; j<object.length; ++j) {
    		render[i+j] = object[j];
    	}
    	return render;
    }
    static Surface [] addList(int i, Surface[] render, Surface object) {
    	 
    	render[i+1] = object;
    	return render;
    }
    
    
    public static ArrayList<Surface> createObjects() {
    	Point4 pCenter = new Point4(0,-4,0);
    	Point4 pScale = new Point4(4,4,4);
    	Surface[] render = new Plane[100];
    	int counter = 0;
    	
    	Plane[] cube = BlobFactory.getWierdCube(
    			"Images//scifi.jpg", 
    			new Point4(5,0,0).subPoint(pCenter), 
    			new Point4(0,0,0), 
    			new Point4(0,0,0).addPoint(pScale));
    	render = addList(counter, render, cube);
    	counter += cube.length;
    	
    	
    	
    	cube = BlobFactory.getWierdCube(
    			"Images//scifi.jpg", 
    			new Point4(-5,1,0).subPoint(pCenter), 
    			new Point4(0,0,0), 
    			new Point4(1,1,1).addPoint(pScale));
    	render = addList(counter, render, cube);
    	counter += cube.length;
    	
    	
    	Plane backWall = BlobFactory.getPlane(
                new Point4(-1,2,-20),    // Plane is centred on the origin
                new Point4(0,0,0),    // orientation angles in radians
                new Point4(30,30,2));   // unit square plane (the z value is not used)
        backWall.vNormal = new Point4(0,0,-1);
        backWall.setProperty("Images//trees.jpg");
        render = addList(counter, render, backWall);
    	counter += 1;
    	Plane floor = BlobFactory.getPlane(
                new Point4(0,-8,1),    // Plane is centred on the origin
                new Point4(Math.PI/2,0,0),    // orientation angles in radians
                new Point4(30,30,2));   // unit square plane (the z value is not used)
    	floor.vNormal = new Point4(0,1,0);
        floor.setProperty("Images//letterP.png");
        render = addList(counter, render, floor);
    	counter += 1;
        
        
        Plane leftWall = BlobFactory.getPlane(
                new Point4(-14,7,3),    // Plane is centred on the origin
                new Point4(Math.PI/2,Math.PI/2,0),    // orientation angles in radians
                new Point4(30,30,30));   // unit square plane (the z value is not used)
        leftWall.vNormal = new Point4(-1,0,0);
        leftWall.setProperty("Images//clouds.jpg");
        render = addList(counter, render, leftWall);
    	counter += 1;
        
        
    	
    	
    	
    	
    	Sphere soccer = BlobFactory.getSphere(
    			5, 
    			new Point4(-5,2,30).addPoint(pCenter), 
    			new Point4(0,Math.PI/2,0), 
    			new Point4(1,1,1));
    	soccer.setProperty("Images//soccer.jpg", 0.9, 10);
    	Sphere ball = BlobFactory.getSphere(
    			5, 
    			new Point4(5,2,35).addPoint(pCenter), 
    			new Point4(0,Math.PI/2,0), 
    			new Point4(1,1,1));
    	ball.setProperty(Color.blue, 0.9, 10);
    	
    	
    	
    	ArrayList<Surface> s = new ArrayList<>();
    	
    	for(int i = 0; i < render.length; ++i) {
    		if(render[i]!=null)
    		s.add(render[i]);
    	}
    	s.add(soccer);
    	s.add(ball);
    	
    	return s;
    }
}
