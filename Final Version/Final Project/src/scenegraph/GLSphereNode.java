/**
   A class that represents a renderable sphere in a scene graph
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLSphereNode extends GLRenderableNode
{
   public GLSphereNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour, final float radius)
   {  this(nodeName, sceneGraph, glut, materialColour, radius, 16, 16);
   }

   public GLSphereNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour, final float radius,
      final int slices, final int stacks)
   {  super(nodeName, sceneGraph, new GLRenderable()
         {  public void drawObject(GL gl)
            {  gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,
                  materialColour.getComponents(null), 0);
               gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,
                  materialColour.getComponents(null), 0);
               glut.glutSolidSphere(radius, slices, stacks);
            }
         });
   }

   public void setRenderObject(GLRenderable renderObject)
   {  throw new UnsupportedOperationException(
         "Render object is fixed as a sphere");
   }
}
