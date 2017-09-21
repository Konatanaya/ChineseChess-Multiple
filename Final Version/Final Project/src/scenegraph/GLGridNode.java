/**
   @see GLSceneGraph.java
*/
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLGridNode extends GLRenderableNode {
        public GLGridNode(String nodeName, GLSceneGraph sceneGraph, final GLUT glut, final Color color, int lines) {
            super(nodeName, sceneGraph, new GLRenderable() {
                public void drawObject(GL gl) {
                    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, color.getComponents(null), 0);
                    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, color.getComponents(null), 0);
                    gl.glBegin(GL.GL_LINES);
                    for (int i = 0; i <= lines; i++) {
                        float x = (float) i / lines - 0.5f;
                        gl.glVertex3f(x, 0, -0.5f);
                        gl.glVertex3f(x, 0, +0.5f);
                        gl.glVertex3f(-0.5f, 0, x);
                        gl.glVertex3f(+0.5f, 0, x);
                    }
                    gl.glEnd();
                }
            });
        }

        public void setRenderObject(GLRenderable renderObject) {
            throw new UnsupportedOperationException("");
        }
    }