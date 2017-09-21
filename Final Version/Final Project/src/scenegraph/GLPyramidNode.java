/**
 * @see GLSceneGraph.java
 */
package scenegraph;

import java.awt.Color;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;

public class GLPyramidNode extends GLMeshNode {

    public GLPyramidNode(String nodeName, GLSceneGraph sceneGraph, final Color materialColour) {
        super(nodeName, sceneGraph, buildPyramidMesh(), materialColour);
    }

    public void setRenderObject(GLRenderable renderObject) {
        throw new UnsupportedOperationException(
                "Render object is fixed as a cube");
    }

    protected static PolygonalMesh buildPyramidMesh() {
        PolygonalMesh pyramidMesh = new PolygonalMesh();
        Point3D[] v = {
            new Point3D(1, 0, 0), new Point3D(0, 0, 1),
            new Point3D(-1, 0, 0), new Point3D(0, 0, -1),
            new Point3D(0, 1, 0)};

        Vector3D[] n = {
            new Vector3D(0, -1, 0), new Vector3D(1, 1, 1),
            new Vector3D(-1, 1, 1), new Vector3D(-1, 1, -1),
            new Vector3D(1, 1, -1)};

        Texel2D[] t = {
            new Texel2D(0.0f, 0.0f), new Texel2D(1.0f, 0.0f),
            new Texel2D(0.0f, 1.0f), new Texel2D(1.0f, 1.0f)
        };

        pyramidMesh.addFace(
                new Point3D[]{v[0], v[1], v[2], v[3]},
                new Vector3D[]{n[0], n[0], n[0], n[0]},
                new Texel2D[]{t[0], t[1], t[2], t[3]});

        pyramidMesh.addFace(
                new Point3D[]{v[0], v[4], v[1]},
                new Vector3D[]{n[1], n[1], n[1]},
                new Texel2D[]{t[0], t[1], t[2]});

        pyramidMesh.addFace(
                new Point3D[]{v[1], v[4], v[2]},
                new Vector3D[]{n[2], n[2], n[2]},
                new Texel2D[]{t[0], t[1], t[2]});

        pyramidMesh.addFace(
                new Point3D[]{v[2], v[4], v[3]},
                new Vector3D[]{n[3], n[3], n[3]},
                new Texel2D[]{t[0], t[1], t[2]});

        pyramidMesh.addFace(
                new Point3D[]{v[3], v[4], v[0]},
                new Vector3D[]{n[4], n[4], n[4]},
                new Texel2D[]{t[0], t[1], t[2]});

        return pyramidMesh;
    }
}
