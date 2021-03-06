package main;
import java.awt.*;

/*
    Generic surface class. This defines one abstract method "shoot" whose implementation
    depends on the type of surface we are dealing with.
 */

abstract class Surface implements Comparable<Surface>{

    // Matrices to transform from local to world and vice versa
    protected Matrix4 tLW, tWL;
    //to sort the surface
    protected double depth;

    // Position on the surface and the normal vector at that surface position
    // These are set up by the shoot() method
    // Defined in local coordinates
    protected Point4 pSurface, vNormal;

    // Normalized texture map coordinates - also set up by shoot()
    protected double u, v;

    // Surface colour is defined as either a given colour or from a texture map
    protected Color colour;
    protected TextureMap texImage;

    // Parameters for the shading function
    protected double beta;
    protected double nShiny;

    // Implemented by subclasses
    public abstract boolean shoot(Point4 p0, Point4 p1);

    // Get the current surface position in world coordinates
    public Point4 getSurface() {
        return tLW.times(this.pSurface);
    }

    // Get the current surface normal vector in world coordinates
    public Point4 getNormal() {
        return tLW.times(this.vNormal);
    }

    // Get the current uv texture coordinates
    public double[] getuv() {return new double[]{u, v};}

    // Set surface as all points having the same colour
    public void setProperty(Color c, double b, double n) {
        this.colour = c;
        this.texImage = null;
        this.beta = b;
        this.nShiny = n;
    }

    public void setProperty(Color c) {
        this.setProperty(c, 1, 1);
    }

    // Set surface so that its colour is obtained from a texture map
    public void setProperty(String texFileName, double b, double n) {
        this.texImage = new TextureMap(texFileName);
        this.colour = null;
        this.beta = b;
        this.nShiny = n;
    }

    public void setProperty(String texFileName) {
        setProperty(texFileName, 1, 1);
    }
    public double getDepth() {
    	return this.depth;
    }
    public void setDepth(double depth) {
    	this.depth = depth;
    }

    /*
        PART 3: complete the implemetation of the shading method

        This takes the position of the light source and view points and determines the shading factor to
        apply to the surface colour or texture

     */
    public int calcShading(Point4 pLightWorld, Point4 pViewWorld) {
    						//( Light Source Origin , Camera Origin    )
    	//get the current hit vector of the light source
    	
    	/*
    	 * n = normal of the surface
    	 * v = viewer unit vector		( object origin - light source )
    	 * r = mirror reflectance unit vector ( R = (2 (N ? L)) N - L )
    	 * l = light source unit vector  ( object origin - light source )

    	 * f = ?N(surface normal) ? L + (1 - ?)(V ? R)^n(shiny)
    	 * where ? is the amount of mixing between the diffuse and specular terms and 0 = ? = 1.
    	 *Note that N, L, and R are normalized vectors. The shading factor, f, is the scale factor
		 *that is applied to the surface colour. You need to ensure that 0 < f < 1, ie check for negative
		 *dot products
		 */
    	 	
        return colour.getRGB();

     }
    @Override
    public int compareTo(Surface other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than 
        // other and 0 if they are supposed to be equal
        double thisDepth = this.getDepth();
        double thatDepth = other.getDepth();
        if(thisDepth == thatDepth)
        	return 0;
        else if (thisDepth < thatDepth)
        	return -1;
        else
        	return 1;
    }


}



