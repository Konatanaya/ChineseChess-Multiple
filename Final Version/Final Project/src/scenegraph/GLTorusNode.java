/**
   A class that represents a renderable torus in a scene graph
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLTorusNode extends GLRenderableNode
{
   public GLTorusNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour,
      final float innerRadius, final float outerRadius)
   {  this(nodeName, sceneGraph, glut, materialColour, innerRadius,
         outerRadius, 16, 16);
   }

   public GLTorusNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour,
      final float innerRadius, final float outerRadius,
      final int nsides, final int rings)
   {  super(nodeName, sceneGraph, new GLRenderable()
         {  public void drawObject(GL gl)
            {  gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,
                  materialColour.getComponents(null), 0);
               gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,
                  materialColour.getComponents(null), 0);
               glut.glutSolidTorus(innerRadius, outerRadius, nsides,
                  rings);
            }
         });
   }

   public void setRenderObject(GLRenderable renderObject)
   {  throw new UnsupportedOperationException(
         "Render object is fixed as a torus");
   }
}
