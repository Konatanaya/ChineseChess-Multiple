/**
   A class that represents a renderable cone in a scene graph
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLConeNode extends GLRenderableNode
{
   public GLConeNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour, final float base,
         final float height)
   {  this(nodeName,sceneGraph,glut,materialColour,base,height,16,16);
   }

   public GLConeNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour, final float base,
      final float height, final int slices, final int stacks)
   {  super(nodeName, sceneGraph, new GLRenderable()
         {  public void drawObject(GL gl)
            {  gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,
                  materialColour.getComponents(null), 0);
               gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,
                  materialColour.getComponents(null), 0);
               glut.glutSolidCone(base, height, slices, stacks);
            }
         });
   }

   public void setRenderObject(GLRenderable renderObject)
   {  throw new UnsupportedOperationException(
         "Render object is fixed as a cone");
   }
}
