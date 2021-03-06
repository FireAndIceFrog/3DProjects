package main;

import java.awt.Color;

public class Sphere extends Surface {
	private double Radius = 0;
    private Point4 pCentre = new Point4(0,0,0,1);
    private Color shadow = Color.black;
    private Color foreground;
    public Sphere(Matrix4 tLW, Matrix4 tWL) {
        this.tLW = tLW;
        this.tWL = tWL;
        this.vNormal = new Point4(0, 0, 1, 0);
        this.setProperty(Color.blue);
        
    }
    public void setCentre(Point4 Center) {
    	
    	pCentre = tWL.times(Center);
    }
    public void setRadius(double R){
    	Radius = R;
    	System.out.println(tLW.toString());
    }
    public void setProperty(Color c) {
    	super.setProperty(c);
    	foreground = c;
    }

    /*
        Part 2: complete the shoot method code to implement ray tracing for the surface of a sphere
     */
    //camera origin, world co-ordinates of pixel
    public boolean shoot(Point4 p0World, Point4 p1World) {
    	boolean statement;
    	
        // We need to work in local coordinates of the sphere
        Point4 p0 = tWL.times(p0World);
        Point4 p1 = tWL.times(p1World);
    	
        Point4 U = p1.minus(p0);
        Point4 V = p0.minus(pCentre);
        ;
        
        double a = Point4.dot(U, U);
        double b = 2*Point4.dot(U, V);
        double c = Point4.dot(V, V)-Radius;
        
        vNormal = new Point4(a,b,c);
        
//        quadratic equation:
//        (U.U)t^2+2(U.V)+V.V = R^2
//        (-b +- sqrt(b*b - 4*a*c))/2*a
//      discremenent:
//        	b^2-4ac
        double D = b*b - 4*a*c;
        
        double t = (-b + Math.sqrt(b*b - 4*a*c))/2*a;
        if(t<0) {
        	t = (-b - Math.sqrt(b*b - 4*a*c))/2*a;
        }
        Point4 P = p0.addVector(U, t); //ray tracing equation P = p0+(p1-p0)t
        P.z = P.z+pCentre.z;
        pSurface = P;
        double zed = pSurface.z/Math.sqrt((pSurface.y*pSurface.y+pSurface.x*pSurface.x+pSurface.z*pSurface.z));
//        pSurface = P;
        u = (0.5+0.5*Math.atan2(pSurface.y,pSurface.x)/Math.PI);
        
        v = 0.5+Math.asin(zed)/Math.PI;
        if(String.valueOf(v) == "NaN") {
        	//if asin is wrong, remove it from the equation.
        	v = -1;
        }
        
        
        statement = D == 0 ? true :  D>0 ? true : false;
        if(statement) {
        	return statement;
        }
        return statement;

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
		* v = viewer unit vector		( object origin -> light source )
		* r = mirror reflectance unit vector ( R = (2 (N ? L)) N - L )
		* l = light source unit vector  ( object origin -> light source )
		
		* f = ?N(surface normal ? L )+ (1 - ?)(V ? R)^n(shiny)
		
		* where ? is the amount of mixing between the diffuse and specular terms and 0 = ? = 1.
		*Note that N, L, and R are normalized vectors. The shading factor, f, is the scale factor
		*that is applied to the surface colour. You need to ensure that 0 < f < 1, ie check for negative
		*dot products
		*/
		double bFactor = beta;
		Point4 n = pCentre.minus(pSurface);
		
		this.vNormal = n; //tWL.times(n);
		Point4 l = p0.minus(pSurface); //vector in the direction  light -> surface
		Point4 v = p0.minus(pSurface);  //vector in the direction camera -> surface
		n.normalize();
		l.normalize();
		v.normalize();
		//R = (2 (N ? L)) N - L
		Point4 r =  new Point4(0,0,0).minus(l).addVector(n, (2*(Point4.dot(n, l)))) ;
    	
		r.normalize();
		double f =( bFactor*(Point4.dot(n,l)) + (1-bFactor)*Math.pow(Point4.dot(v,r),nShiny)); 
		
		Color tempColor = foreground;
		if(f>0&&f<1) {

			float colors [] = new float[3];
			tempColor.getRGBColorComponents(colors);
			
			colors[0] = (float) (colors[0]*f);
			colors[1] = (float) (colors[1]*f);
			colors[2] = (float) (colors[2]*f);
			
			tempColor = new Color(colors[0],colors[1],colors[2]);
			return tempColor.getRGB();
		}else if(f<0) {
			return Color.black.getRGB();
		}
		return colour.getRGB();
    }
}
