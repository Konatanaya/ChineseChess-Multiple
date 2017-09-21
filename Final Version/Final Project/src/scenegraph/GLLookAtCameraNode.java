/**
   A class that represents a camera node in a scene graph
   @see GLSceneGraph.java
*/
package scenegraph;

import javax.media.opengl.glu.GLU;
import javax.media.opengl.GL;

public class GLLookAtCameraNode extends GLCameraNode {
    protected Point3D eye;
	protected Point3D at;
	protected Vector3D up;
        protected GLU glu;

	public GLLookAtCameraNode(String nodeName, GLSceneGraph sceneGraph, Point3D eye, Point3D at, Vector3D up) {
		super(nodeName, sceneGraph);
		this.eye = eye;
		this.at = at;
		this.up = up;
                this.glu = new GLU();
	}

	public void applyInverseGlobalTransformation(GL gl) {  // obtain the global transformation
		glu.gluLookAt(
				eye.getX(), eye.getY(), eye.getZ(),
				at.getX(), at.getY(), at.getZ(),
				up.getX(), up.getY(), up.getZ());
	}
}