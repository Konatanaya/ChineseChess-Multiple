package ChineseChess3D;

/**
 * 
 *
 * @author hackless
 */
import com.sun.opengl.util.GLUT;
import java.awt.Color;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import scenegraph.GLCameraNode;
import scenegraph.GLCubeNode;
import scenegraph.GLDirectionalLightNode;
import scenegraph.GLLightNode;
import scenegraph.GLRenderableNode;
import scenegraph.GLSceneGraph;

public class ChineseChessTable implements GLEventListener {

    protected static final int WIDTH = 640; // initial screen width
    protected static final int HEIGHT = 480; // initial screen height
    protected static final double WORLD_HEIGHT = 1.4;
    protected static final double WORLD_NEAR = 0.1;
    protected static final double WORLD_FAR = 100;
    protected GLU glu;
    protected GLUT glut;//GLUT utility library used for sphere and cube
    protected GLCanvas canvas;
    protected GLSceneGraph sceneGraph;
    protected static boolean isReady = false;

    public ChineseChessTable(GLCanvas canvas) {
        this.glu = new GLU();
        this.glut = new GLUT();
        this.canvas = canvas;
        sceneGraph = null;
    }
    protected GLRenderableNode createTableclothNode(float thickness) {
        GLRenderableNode tableclothNode = new GLCubeNode("Tablecloth", sceneGraph, glut, Color.WHITE);
        tableclothNode.setLocalScale(1, thickness, 1);
        tableclothNode.setLocalTranslation(0.5f, -0.5f * thickness, 0.5f);

        return tableclothNode;
    }
    

    // creates a node for the table with bottom middle at (0,0,0)
    public GLRenderableNode createTableNode(float tableWidth, float tableLength,
            float topThickness, GLSceneGraph sceneGraph, Color colour) {
        GLRenderableNode tableNode = new GLRenderableNode("Table", sceneGraph);
        return tableNode;
    }

    protected GLRenderableNode createTableObjectsNode(float topThickness, float tableWidth,float tableLength, GLSceneGraph sceneGraph) {
        GLRenderableNode tableObjectsNode = new GLRenderableNode("Table objects", sceneGraph);
        tableObjectsNode.setLocalTranslation(0, topThickness, 0);

        return tableObjectsNode;
    }

    protected GLLightNode createLightNode() {
        GLLightNode lightNode = new GLDirectionalLightNode("Directional light", sceneGraph);
        lightNode.setDiffuseColour(new Color(0.7f, 0.7f, 0.7f, 1.0f));
        lightNode.setLocalRotation(65, -6, 2, 0);

        return lightNode;
    }

    protected GLCameraNode createCameraNode() {
        GLCameraNode cameraNode = new GLCameraNode("Scene camera",
                sceneGraph);
        cameraNode.setLocalRotation(31.75f, -0.38f, 1.5f, 0.0f);
        cameraNode.setLocalTranslation(2.0f, 0.7f, 3.0f);
        sceneGraph.getRootNode().addChild(cameraNode);

        return cameraNode;
    }

    protected GLRenderableNode createChessboardNode(float thickness) {
        GLRenderableNode chessboardNode = new GLCubeNode("Floor", sceneGraph, glut, Color.WHITE);
        chessboardNode.setLocalScale(1, thickness, 1);
        chessboardNode.setLocalTranslation(0.5f, -0.5f * thickness, 0.5f);

        return chessboardNode;
    }

    // called when OpenGL is initialized
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // set material characteristics (apart from ambient and diffuse)
        float[] shininessMaterial = {10.0f};
        float[] specularMaterial = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, shininessMaterial, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularMaterial, 0);

        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glEnable(GL.GL_NORMALIZE); //norm. vectors for proper shading
        gl.glEnable(GL.GL_DEPTH_TEST); // remove hidden surfaces

        // set the world window with projection view (no perspective)
        double worldWidth = WORLD_HEIGHT * WIDTH / HEIGHT;
        setProjection(gl, worldWidth, WORLD_HEIGHT);

        // create the scene graph
        sceneGraph = new GLSceneGraph();
        sceneGraph.setBackground(Color.WHITE);

        // add floor (in xz-plane) with top back corner (0,0,0) to scene
        float floorThickness = 0.04f;
        GLRenderableNode floorNode = createChessboardNode(floorThickness);
        sceneGraph.getRootNode().addChild(floorNode);
        
        // add tablecloth (in xz-plane) with top back corner (0,0,0) to scene
        float tableclothThickness = 0.04f;
        GLRenderableNode tableclothNode = createTableclothNode(tableclothThickness);
        sceneGraph.getRootNode().addChild(tableclothNode);

        // add a node for a directional light
        sceneGraph.getRootNode().addChild(createLightNode());

        // add a node for the camera (positioned to be equivalent to
        // the gluLookAt(2, 0.7, 3, 0.5, 0.32, 0.5, 0, 1, 0);
        GLCameraNode cameraNode = createCameraNode();
        sceneGraph.getRootNode().addChild(cameraNode);
        sceneGraph.setCurrentCamera(cameraNode);
        isReady = true;
        //System.out.println(sceneGraph.toString());
        
    }

    protected void setProjection(GL gl, double width, double height) {
        // preserve the aspect ratio
        double worldWidth = WORLD_HEIGHT * width / height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-worldWidth / 2, worldWidth / 2, -WORLD_HEIGHT / 2,
                WORLD_HEIGHT / 2, WORLD_NEAR, WORLD_FAR);
    }

    // draws the scene
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        // draw the scene graph
        sceneGraph.renderScene(gl);
    }

    // called when component's location or size has changed
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        setProjection(gl, width, height);
        canvas.repaint(); // note some graphics cards require a repaint
    }

    // called when display mode of device has changed
    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {  // empty implementation
    }
}
