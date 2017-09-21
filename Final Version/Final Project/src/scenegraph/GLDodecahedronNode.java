/**
   A class that represents a renderable dodecahedron in a scene graph
   centred on the origin
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLDodecahedronNode extends GLRenderableNode
{
   public GLDodecahedronNode(String nodeName, GLSceneGraph sceneGraph,
      final GLUT glut, final Color materialColour)
   {  super(nodeName, sceneGraph, new GLRenderable()
         {  public void drawObject(GL gl)
            {  gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,
                  materialColour.getComponents(null), 0);
               gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,
                  materialColour.getComponents(null), 0);
               glut.glutSolidDodecahedron();
            }
         });
   }

   public void setRenderObject(GLRenderable renderObject)
   {  throw new UnsupportedOperationException(
         "Render object is fixed as a dodecahedron");
   }
}
