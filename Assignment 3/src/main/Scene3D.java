
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Class to represent the 3D graphics scene

    Call the render() method whenever you have defined a surface ant want to draw it on your image
 */

public class Scene3D {

    Camera camera = null;

    Point4 pLightWorld = null;

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setLight(Point4 p) {
        pLightWorld = p;
    }

    public List<Surface> listSurfaces(ArrayList<Surface> surfaces){
    	List<Surface> listSurface = new ArrayList<Surface>();
    	for(Surface s : surfaces)
    		listSurface.add(s);
        Collections.sort(listSurface);
        return listSurface;
    }
    // Invoke the ray tracing on all surfaces in the array. Thanks to polymorphism, this will
    // for any surface that is a sub-class of Surface
    public void render(ArrayList<Surface> faces) {
    	
    	List<Surface> surfaces = faces;//listSurfaces(faces);
        int npixx = camera.image.getWidth();
        int npixy = camera.image.getHeight();

        // The location of the camera in world coordinates
        Point4 p0World = camera.getOrigin();

        int numSurfaces = surfaces.size();
        
        for (int n = 0; n < numSurfaces; ++n) {

            Surface surface = surfaces.get(n);

            // Loop over each pixel on the camera image
            for (int ix = 0; ix < npixy; ++ix) {
                for (int iy = 0; iy < npixx; ++iy) {

                    // The location of this pixel in world coordinates
                    Point4 p1World = camera.getScreen(ix, iy);
                	//camera origine, 
                    // Invoke the ray tracing for this surface at this pixels
                    boolean shoot = surface.shoot(p0World, p1World);
                    if (shoot) {

                        // Colour of the surface at which the ray hit
                        int rgb = surface.calcShading(pLightWorld, p0World);

                        // Surface hit position in world coordinates
                        Point4 pS = surface.getSurface();
                        Point4 pN = surface.getNormal();
                        // Set the image in the camera to this value
                        camera.setValue(pN,pS, ix, iy, rgb);
                    }

                }
            }
        }
    }


}
