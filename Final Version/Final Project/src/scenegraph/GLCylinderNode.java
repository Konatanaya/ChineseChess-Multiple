/**
   A class that represents a renderable cylinder in a scene graph
   whose base is on the origin in xy-plane and axis along z-axis
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLCylinderNode extends GLRenderableNode
{
   public GLCylinderNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour, final float radius,
      final float height)
   {  this(nodeName, sceneGraph, glut, materialColour, radius, height,
         16, 16);
   }

   public GLCylinderNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour, final float radius,
      final float height, final int slices, final int stacks)
   {  super(nodeName, sceneGraph, new GLRenderable()
         {  public void drawObject(GL gl)
            {  gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,
                  materialColour.getComponents(null), 0);
               gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,
                  materialColour.getComponents(null), 0);
               glut.glutSolidCylinder(radius, height, slices, stacks);
            }
         });
   }

   public void setRenderObject(GLRenderable renderObject)
   {  throw new UnsupportedOperationException(
         "Render object is fixed as a cylinder");
   }
}
