package scenegraph;

/**
   A utility class that represents sphere centred at origin, radius 1
   @author Andrew Ensor (modified for JSR-231)
*/
import javax.media.opengl.GL;

public class Sphere implements GLRenderable
{
   protected int numSlices; // number of slices through z-axis
   protected int numStacks; // number of stacks cut parallel to xy-plane
   protected Point3D[][] vertices; // vertices and unit normal vectors
   
   public Sphere()
   {  this(16, 16);
   }
   
   public Sphere(int numSlices, int numStacks)
   {  this.numSlices = numSlices;
      this.numStacks = numStacks;
      vertices = new Point3D[numStacks+1][numSlices];
      for (int i=0; i<=numStacks; i++)
      {  double phi = Math.PI*i/numStacks;
         for (int j=0; j<numSlices; j++)
         {  double theta = Math.PI*2.0*j/numSlices;
            double a = Math.sin(phi)*Math.cos(theta);
            double b = Math.sin(phi)*Math.sin(theta);
            double c = Math.cos(phi);
            vertices[i][j] = new Point3D(a, b, c);
         }
      }
   }
   
   public void drawObject(GL gl)
   {  for (int i=0; i<numStacks; i++) //draw a stack as triangle strip
      {  gl.glBegin(GL.GL_TRIANGLE_STRIP);
         for (int j=0; j<=numSlices; j++)
         {  int thisJ = j%numSlices;
            gl.glNormal3d(vertices[i][thisJ].getX(),
               vertices[i][thisJ].getY(), vertices[i][thisJ].getZ());
            gl.glVertex3d(vertices[i][thisJ].getX(),
               vertices[i][thisJ].getY(), vertices[i][thisJ].getZ());
            gl.glNormal3d(vertices[i+1][thisJ].getX(),
               vertices[i+1][thisJ].getY(),
               vertices[i+1][thisJ].getZ());
            gl.glVertex3d(vertices[i+1][thisJ].getX(),
               vertices[i+1][thisJ].getY(),
               vertices[i+1][thisJ].getZ());
         }
         gl.glEnd();
      }
   }
}
