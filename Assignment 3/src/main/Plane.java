
package main;
import java.awt.*;

/*
    Ray tracing implementation for a plane
 */

public class Plane extends Surface {

    public final Point4 pOrigin = new Point4(0, 0, 0, 1);

    private Color foreground;

    public Plane(Matrix4 tLW, Matrix4 tWL) {
        this.tLW = tLW;
        this.tWL = tWL;
        this.vNormal = new Point4(0, 0, 1, 0);
        this.setProperty(Color.blue);
    }
    public void setProperty(Color c) {
    	super.setProperty(c);
    	foreground = c;
    }
    public void setProperty(String s) {
    	colour = Color.black;
    	super.setProperty(s);
    	
    	foreground = colour;
    }
    


    public boolean shoot(Point4 p0World, Point4 p1World) {

        Point4 p0 = tWL.times(p0World);
        Point4 p1 = tWL.times(p1World);

        Point4 U = p1.minus(p0);
        Point4 V = pOrigin.minus(p0);
        

        double t = V.z / U.z;
        pSurface = p0.addVector(U, t); // ray of the current object
        // These are the texture coordinates for the texture mapping
        u = pSurface.x + 0.5;
        v = 1 - (pSurface.y + 0.5);


        return (pSurface.x >=-0.5 && pSurface.x <= 0.5 && pSurface.y >= -0.5 && pSurface.y <= 0.5);


    }
    public int calcShading(Point4 pLightWorld, Point4 pViewWorld) {
        if(texImage!=null) {
        	this.foreground = texImage.pickColour(this.u, this.v);
        	this.colour = foreground;
        }
    	
    	Point4 p0 = tWL.times(pLightWorld);
        Point4 p1 = tWL.times(pViewWorld);
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
		double bFactor = 0.5;
		Point4 n = this.getNormal(); 
		Point4 l = p0.minus(pSurface); //vector in the direction  light -> origin
		Point4 v = p1.minus(pSurface);  //vector in the direction camera -> origin
    	n.normalize();
		l.normalize();
		v.normalize();
		
		Point4 r =  n.addVector(l.minus(l), (2*(Point4.dot(n, l)))) ;
    	
		r.normalize();
		
		double f = bFactor*(Point4.dot(n,l)) + (1-bFactor)*Math.pow(Point4.dot(v,r),nShiny); 
		Color tempColor = foreground;
		if(f>0&&f<1) {

			float colors [] = new float[3];
			foreground.getRGBColorComponents(colors);
			
			colors[0] = (float) (colors[0]*f);
			colors[1] = (float) (colors[1]*f);
			colors[2] = (float) (colors[2]*f);
			
			tempColor = new Color(colors[0],colors[1],colors[2]);
			return tempColor.getRGB();
		} else if(f>1) {
			return Color.black.getRGB();
		}

		return colour.getRGB();

    }


}
