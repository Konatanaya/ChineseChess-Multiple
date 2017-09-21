package scenegraph;

/**
   A utility class that represents a tapered cylinder with base
   at the origin and symmetrical about the z-axis with base radius
   1, top radius s, and height 1
   @author Andrew Ensor (modified for JSR-231)
*/
import javax.media.opengl.GL;

public class TaperedCylinder implements GLRenderable
{
   private int numSlices; // number of slices through z-axis
   private int numStacks; // number of stacks cut parallel to xy-plane
   private Point3D[][] vertices;
   private Vector3D[] normals;
   
   public TaperedCylinder(double s)
   {  this(s, 20, 16);
   }
   
   public TaperedCylinder(double s, int numSlices, int numStacks)
   {  this.numSlices = numSlices;
      this.numStacks = numStacks;
      vertices = new Point3D[numStacks+1][numSlices];
      for (int i=0; i<=numStacks; i++)
      {  double c = 1.0*i/numStacks;
         double radius = 1.0 - (1.0-s)*c;
         for (int j=0; j<numSlices; j++)
         {  double angle = Math.PI*2.0*j/numSlices;
            double a = radius*Math.cos(angle);
            double b = radius*Math.sin(angle);
            vertices[i][j] = new Point3D(a, b, c);
         }
      }
      // calculate the normals for each angle
      normals = new Vector3D[numSlices];
      for (int j=0; j<numSlices; j++)
      {  double angle = Math.PI*2.0*j/numSlices;
         normals[j] = (new Vector3D(Math.cos(angle), Math.sin(angle),
            1-s)).normalized();
      }
   }
   
   public void drawObject(GL gl)
   {  // draw the side as a collection of quad strips
      for (int i=0; i<numStacks; i++)
      {  gl.glBegin(GL.GL_QUAD_STRIP);
         for (int j=0; j<=numSlices; j++)
         {  int thisJ = j%numSlices;
            gl.glNormal3d(normals[thisJ].getX(),
               normals[thisJ].getY(), normals[thisJ].getZ());
            gl.glVertex3d(vertices[i+1][thisJ].getX(),
               vertices[i+1][thisJ].getY(),
               vertices[i+1][thisJ].getZ());
            gl.glVertex3d(vertices[i][thisJ].getX(),
               vertices[i][thisJ].getY(), vertices[i][thisJ].getZ());
         }
         gl.glEnd();
      }
      // draw the base as a single triangle fan
      gl.glBegin(GL.GL_TRIANGLE_FAN);
      gl.glNormal3d(0.0, 0.0, -1.0);
      gl.glVertex3d(0.0, 0.0, 0.0); // centre point of base
      for (int j=numSlices; j>=0; j--)
      {  int thisJ = j%numSlices;
         gl.glVertex3d(vertices[0][thisJ].getX(),
            vertices[0][thisJ].getY(), vertices[0][thisJ].getZ());
      }
      gl.glEnd();
      // draw the top as a single triangle fan
      gl.glBegin(GL.GL_TRIANGLE_FAN);
      gl.glNormal3d(0.0, 0.0, 1.0);
      gl.glVertex3d(0.0, 0.0, 1.0); // centre point of top
      for (int j=0; j<=numSlices; j++)
      {  int thisJ = j%numSlices;
         gl.glVertex3d(vertices[numStacks][thisJ].getX(),
            vertices[numStacks][thisJ].getY(),
            vertices[numStacks][thisJ].getZ());
      }
      gl.glEnd();
   }
}
