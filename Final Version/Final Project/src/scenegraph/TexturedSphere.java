package scenegraph;

/**
   A utility class that represents a textured sphere
   @author Andrew Ensor (modified for JSR-231)
*/
import javax.media.opengl.GL;

public class TexturedSphere extends Sphere
{
   public TexturedSphere()
   {  super();
   }
   
   public TexturedSphere(int numSlices, int numStacks)
   {  super(numSlices, numStacks);
   }
   
   // overridden method of Sphere that includes texture coordinates
   public void drawObject(GL gl)
   {  for (int i=0; i<numStacks; i++) //draw a stack as triangle strip
      {  gl.glBegin(GL.GL_TRIANGLE_STRIP);
         for (int j=0; j<=numSlices; j++)
         {  int thisJ = j%numSlices;
            gl.glNormal3d(vertices[i][thisJ].getX(),
               vertices[i][thisJ].getY(), vertices[i][thisJ].getZ());
            gl.glTexCoord2d(1.0*j/numSlices,
               1.0*(numStacks-i)/numStacks);
            gl.glVertex3d(vertices[i][thisJ].getX(),
               vertices[i][thisJ].getY(), vertices[i][thisJ].getZ());
            gl.glNormal3d(vertices[i+1][thisJ].getX(),
               vertices[i+1][thisJ].getY(),
               vertices[i+1][thisJ].getZ());
            gl.glTexCoord2d(1.0*j/numSlices,
               1.0*(numStacks-(i+1))/numStacks);
            gl.glVertex3d(vertices[i+1][thisJ].getX(),
               vertices[i+1][thisJ].getY(),
               vertices[i+1][thisJ].getZ());
         }
         gl.glEnd();
      }
   }
}
