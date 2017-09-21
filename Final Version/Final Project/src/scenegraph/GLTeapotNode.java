/**
   A class that represents a renderable teapot in a scene graph
   whose base is at origin in xz-plane and spout points along x axis
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLTeapotNode extends GLRenderableNode
{
   public GLTeapotNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour)
   {  super(nodeName, sceneGraph, new GLRenderable()
         {  public void drawObject(GL gl)
            {  gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,
                  materialColour.getComponents(null), 0);
               gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,
                  materialColour.getComponents(null), 0);
               gl.glTranslatef(0.0f, 0.75f, 0.0f); // position base
               glut.glutSolidTeapot(1.0f);
               gl.glTranslatef(0.0f, -0.75f, 0.0f);
            }
         });
   }

   public void setRenderObject(GLRenderable renderObject)
   {  throw new UnsupportedOperationException(
         "Render object is fixed as a teapot");
   }
}
