package ChineseChess3D;

/**
 *
 *
 * @author hackless
 */

import ChineseChess2D.Database;
import ChineseChess2D.MainFrame;
import ChineseChess2D.TimeRecorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import scenegraph.*;
import scenegraph.Point3D;

public class ChineseChess extends ChineseChessTable {
    
    public static GLCanvas canvasf1;
    public static GLCanvas canvasf2;
    public static GLCanvas canvas;
    
    public JPanel ICPanel;
    public static JFrame ICframe;
    public static JFrame FCframe;
    private boolean smoothShading = true;
    private boolean foggy = false;
    public static String round = null;     //false means red go
    public boolean doPicking = false;
    public boolean checkstep = false; // control the step in chess
    private Point pickPoint = null;
    private float pieceheight = -0.309f;
    private float cx = 0;
    private float cy = 0;
    public static int step = 0;
    public static int winner = 0;
    private float fog = 20.0f;
    private boolean isReady = false;
    public String name,eatname = null,id,eatid = null;
    public int startLocation,endLocation;
    public boolean move = false;
    public static GLRenderableNode copyNode = null;
    private GLMeshNode pyramidNode = null;
    private GLMeshNode chessboardNode = null;
    private GLMeshNode tableclothNode = null;
    private GLMeshNode skyboxNode = null;
    private GLSirenLight sirenNode = null;
    private GLRenderableNode tableTopNode = null;
    public static HashMap<String, GLRenderableNode> map = new HashMap<String, GLRenderableNode>();
    public static HashMap<String, Integer> mapCase = new HashMap<String, Integer>();
// red chess man
    private GLRenderableNode WPiece1Node = null;
    private GLRenderableNode WPiece2Node = null;
    private GLRenderableNode WPiece3Node = null;
    private GLRenderableNode WPiece4Node = null;
    private GLRenderableNode WPiece5Node = null;
    private GLRenderableNode WPiece6Node = null;
    private GLRenderableNode WPiece7Node = null;
    private GLRenderableNode WPiece8Node = null;
    private GLRenderableNode WPiece9Node = null;
    private GLRenderableNode WPiece10Node = null;
    private GLRenderableNode WPiece11Node = null;
    private GLRenderableNode WPiece12Node = null;
    private GLRenderableNode WPiece13Node = null;
    private GLRenderableNode WPiece14Node = null;
    private GLRenderableNode WPiece15Node = null;
    private GLRenderableNode WPiece16Node = null;
// black chess man
    private GLRenderableNode BPiece1Node = null;
    private GLRenderableNode BPiece2Node = null;
    private GLRenderableNode BPiece3Node = null;
    private GLRenderableNode BPiece4Node = null;
    private GLRenderableNode BPiece5Node = null;
    private GLRenderableNode BPiece6Node = null;
    private GLRenderableNode BPiece7Node = null;
    private GLRenderableNode BPiece8Node = null;
    private GLRenderableNode BPiece9Node = null;
    private GLRenderableNode BPiece10Node = null;
    private GLRenderableNode BPiece11Node = null;
    private GLRenderableNode BPiece12Node = null;
    private GLRenderableNode BPiece13Node = null;
    private GLRenderableNode BPiece14Node = null;
    private GLRenderableNode BPiece15Node = null;
    private GLRenderableNode BPiece16Node = null;
// tableObjectsNode
    public static GLRenderableNode tableObjectsNode = null;

    private GLLookAtCameraNode cam0;
    private GLLookAtCameraNode cam1;
    private GLLookAtCameraNode cam2;
    private GLOrbitCameraNode cam3;
    private Database db;
    public int chesspiece[] = {
        0,  1,  2,  3,  4,  5,  6,  7,  8,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 
        99, 9,  99, 99, 99, 99, 99, 10, 99,
        11, 99, 12, 99, 13, 99, 14, 99, 15,
        99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99,
        27, 99, 28, 99, 29, 99, 30, 99, 31,
        99, 25, 99, 99, 99, 99, 99, 26, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99,
        16, 17, 18, 19, 20, 21, 22, 23, 24
    };
    public int piecelocation[] = {
        0,  1,  2,  3,  4,  5,  6,  7,  8,  
        19, 25, 
        27, 29, 31, 33, 35,
        81, 82, 83, 84, 85, 86, 87, 88, 89, 
        64, 70, 
        54, 56, 58, 60, 62
    };
    public int piecestep[] = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };
    private int currentchoose = 99;
    private int currentlocation = 99;
    private int situation = 99;
    private int wpiecesituation = 99;
    private int bpiecesituation = 99;
    
    
    private TimeRecorder TR;
    private MainFrame mf;

    public ChineseChess(GLCanvas canvas, TimeRecorder TR, MainFrame mf) {
        super(canvas);
        db = new Database("jdbc:derby://localhost:1527/ChineseChess");
        GLRenderableNode.newId = 0;
        this.TR = TR;
        this.mf = mf;
        canvas.addKeyListener(new KeyActionListener());
        canvas.addMouseListener(new MousePickListener());
    }
    
    //Red chess rules integration
    private class WSituation {

        private void W0Situation2() {
            copyNode.setLocalTranslation(cx, pieceheight, cy);
            //startLocation = currentchoose;
            chesspiece[currentlocation] = currentchoose;
            chesspiece[piecelocation[currentchoose]] = 99;
            startLocation = piecelocation[currentchoose];
            piecelocation[currentchoose] = currentlocation;
            endLocation = piecelocation[currentchoose];
            piecestep[currentchoose]++;
            if(mf.onlineMode){
                Object object = (Object)("#o#changeTurn");
                mf.sendObject(object);
                object = (Object)(cx+"#"+pieceheight+"#"+cy+"#");
                mf.sendObject(object);
            }
            move = true;
            pyramidNode.setLocalTranslation(-1.62f, -0.44f, -1.5f);
            copyNode.setEnabled(true);
            checkstep = false;
            round = "black";
            TR.Suspend("Red");
            situation = 99;
            wpiecesituation = 99;
        }

        private void W0Situation1() {
            switch (chesspiece[currentlocation]) {
                case 16:
                    tableObjectsNode.removeChild(BPiece1Node);
                    eatname = "車";
                    eatid = "bj1";
                    break;
                case 17:
                    tableObjectsNode.removeChild(BPiece2Node);
                    eatname = "馬";
                    eatid = "bm1";
                    break;
                case 18:
                    tableObjectsNode.removeChild(BPiece3Node);
                    eatname = "象";
                    eatid = "bx1";
                    break;
                case 19:
                    tableObjectsNode.removeChild(BPiece4Node);
                    eatname = "士";
                    eatid = "bs1";
                    break;
                case 20:
                    tableObjectsNode.removeChild(BPiece5Node);//black king
                    eatname = "将";
                    eatid = "bj";
                    Runnable runnable = new Runnable() {
                        public void run() {
                            round = null;
                            TR.stop();
                            JOptionPane.showMessageDialog(mf,"The victory belongs to red!", "Victory", JOptionPane.NO_OPTION);
                            int option = JOptionPane.showConfirmDialog(null, "Do you want to back to the menu?", "What do you want to do?", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null);
                            switch (option) {
                                case JOptionPane.YES_NO_OPTION: {
                                    mf.test();
                                    break;
                                }
                                case JOptionPane.NO_OPTION:
                            }
                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                    break;
                case 21:
                    tableObjectsNode.removeChild(BPiece6Node);
                    eatname = "士";
                    eatid = "bs2";
                    break;
                case 22:
                    tableObjectsNode.removeChild(BPiece7Node);
                    eatname = "象";
                    eatid = "bx2";
                    break;
                case 23:
                    tableObjectsNode.removeChild(BPiece8Node);
                    eatname = "馬";
                    eatid = "bm2";
                    break;
                case 24:
                    tableObjectsNode.removeChild(BPiece9Node);
                    eatname = "車";
                    eatid = "bj2";
                    break;
                case 25:
                    tableObjectsNode.removeChild(BPiece10Node);
                    eatname = "炮";
                    eatid = "bp1";
                    break;
                case 26:
                    tableObjectsNode.removeChild(BPiece11Node);
                    eatname = "炮";
                    eatid = "bp2";
                    break;
                case 27:
                    tableObjectsNode.removeChild(BPiece12Node);
                    eatname = "卒";
                    eatid = "bz1";
                    break;
                case 28:
                    tableObjectsNode.removeChild(BPiece13Node);
                    eatname = "卒";
                    eatid = "bz2";
                    break;
                case 29:
                    tableObjectsNode.removeChild(BPiece14Node);
                    eatname = "卒";
                    eatid = "bz3";
                    break;
                case 30:
                    tableObjectsNode.removeChild(BPiece15Node);
                    eatname = "卒";
                    eatid = "bz4";
                    break;
                case 31:
                    tableObjectsNode.removeChild(BPiece16Node);
                    eatname = "卒";
                    eatid = "bz5";
                    break;
            }
        }

        private void W0Situation0() {
            copyNode.setEnabled(true);
            checkstep = false;
            situation = 99;
            wpiecesituation = 99;
            move = false;
        }
    }

    //black chess rules integration
    private class BSituation {

        private void B0Situation2() {
            copyNode.setLocalTranslation(cx, pieceheight, cy);
            chesspiece[currentlocation] = currentchoose;
            chesspiece[piecelocation[currentchoose]] = 99;
            startLocation = piecelocation[currentchoose];
            piecelocation[currentchoose] = currentlocation;
            endLocation = piecelocation[currentchoose];
            move = true;
            piecestep[currentchoose]++;
            if(mf.onlineMode){
                Object object = (Object)("#o#changeTurn");
                mf.sendObject(object);
                object = (Object)(cx+"#"+pieceheight+"#"+cy+"#");
                mf.sendObject(object);
            }
            pyramidNode.setLocalTranslation(-1.62f, -0.44f, 1.5f);
            copyNode.setEnabled(true);
            checkstep = false;
            round = "red";
            TR.Suspend("Black");
            situation = 99;
            bpiecesituation = 99;
        }

        private void B0Situation1() {
            switch (chesspiece[currentlocation]) {
                case 0:
                    tableObjectsNode.removeChild(WPiece1Node);
                    eatname = "车";
                    eatid = "rj1";
                    break;
                case 1:
                    tableObjectsNode.removeChild(WPiece2Node);
                    eatname = "马";
                    eatid = "rm1";
                    break;
                case 2:
                    tableObjectsNode.removeChild(WPiece3Node);
                    eatname = "相";
                    eatid = "rx1";
                    break;
                case 3:
                    tableObjectsNode.removeChild(WPiece4Node);
                    eatname = "仕";
                    eatid = "rs1";
                    break;
                case 4:
                    tableObjectsNode.removeChild(WPiece5Node);//Red king
                    eatname = "帅";
                    eatid = "rs";
                    Runnable runnable = new Runnable() {
                        public void run() {
                            round = null;
                            TR.stop();
                            JOptionPane.showMessageDialog(mf,"The victory belongs to black!", "Victory", JOptionPane.NO_OPTION);
                            int option = JOptionPane.showConfirmDialog(null, "Do you want to back to the menu?", "What do you want to do?", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null);
                            switch (option) {
                                case JOptionPane.YES_NO_OPTION: {
                                    mf.test();
                                    break;
                                }
                                case JOptionPane.NO_OPTION:
                            }
                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                    break;
                case 5:
                    tableObjectsNode.removeChild(WPiece6Node);
                    eatname = "仕";
                    eatid = "rs2";
                    break;
                case 6:
                    tableObjectsNode.removeChild(WPiece7Node);
                    eatname = "相";
                    eatid = "rx2";
                    break;
                case 7:
                    tableObjectsNode.removeChild(WPiece8Node);
                    eatname = "马";
                    eatid = "rm2";
                    break;
                case 8:
                    tableObjectsNode.removeChild(WPiece9Node);
                    eatname = "车";
                    eatid = "rj2";
                    break;
                case 9:
                    tableObjectsNode.removeChild(WPiece10Node);
                    eatname = "炮";
                    eatid = "rp1";
                    break;
                case 10:
                    tableObjectsNode.removeChild(WPiece11Node);
                    eatname = "炮";
                    eatid = "rp2";
                    break;
                case 11:
                    tableObjectsNode.removeChild(WPiece12Node);
                    eatname = "兵";
                    eatid = "rb1";
                    break;
                case 12:
                    tableObjectsNode.removeChild(WPiece13Node);
                    eatname = "兵";
                    eatid = "rb2";
                    break;
                case 13:
                    tableObjectsNode.removeChild(WPiece14Node);
                    eatname = "兵";
                    eatid = "rb3";
                    break;
                case 14:
                    tableObjectsNode.removeChild(WPiece15Node);
                    eatname = "兵";
                    eatid = "rb4";
                    break;
                case 15:
                    tableObjectsNode.removeChild(WPiece16Node);
                    eatname = "兵";
                    eatid = "rb5";
                    break;
            }
        }

        private void B0Situation0() {
            copyNode.setEnabled(true);
            checkstep = false;
            situation = 99;
            bpiecesituation = 99;
            move = false;
        }
    }

    private GLRenderableNode createMeshNodeFromFile(String nodeName, Color materialColour, String objPath) {
        GLRenderableNode meshNode = null;
        try {
            ObjLoader loader = new ObjLoader();
            ArrayList<PolygonalMesh> meshes = loader.loadFromFile(objPath);
            meshNode = new GLRenderableNode(nodeName, sceneGraph);

            for (PolygonalMesh mesh : meshes) {
                GLMeshNode node = new GLMeshNode(mesh.getMeshName(), sceneGraph, mesh, materialColour);
                meshNode.addChild(node);
            }
        } catch (IOException ex) {
        }

        return meshNode;
    }
    
    private GLMeshNode createSkyboxNode() {
        PolygonalMesh box = new PolygonalMesh();
        Point3D[] v = {
            new Point3D(-0.75, -0.75, -0.75), new Point3D(+0.75, -0.75, -0.75), new Point3D(+0.75, -0.75, +0.75), new Point3D(-0.75, -0.75, +0.75),
            new Point3D(-0.75, +0.75, -0.75), new Point3D(+0.75, +0.75, -0.75), new Point3D(+0.75, +0.75, +0.75), new Point3D(-0.75, +0.75, +0.75)};

        Vector3D[] n = {
            new Vector3D(0, 1, 0), new Vector3D(0, -1, 0), // bottom, up
            new Vector3D(0, 0, 1), new Vector3D(-1, 0, 0), // front, right
            new Vector3D(0, 0, -1), new Vector3D(1, 0, 0)}; // back, left

        box.addFace( // bottom
                new Point3D[]{v[0], v[3], v[2], v[1]},
                new Vector3D[]{n[0], n[0], n[0], n[0]},
                new Texel2D[]{
                    new Texel2D(0.25f, 0.33f), new Texel2D(0.25f, 0.00f),
                    new Texel2D(0.50f, 0.00f), new Texel2D(0.50f, 0.33f)});

        box.addFace( // up
                new Point3D[]{v[4], v[5], v[6], v[7]},
                new Vector3D[]{n[1], n[1], n[1], n[1]},
                new Texel2D[]{
                    new Texel2D(0.25f, 0.75f), new Texel2D(0.50f, 0.75f),
                    new Texel2D(0.50f, 1.00f), new Texel2D(0.25f, 1.00f)});

        box.addFace( // front
                new Point3D[]{v[0], v[1], v[5], v[4]},
                new Vector3D[]{n[2], n[2], n[2], n[2]},
                new Texel2D[]{
                    new Texel2D(0.25f, 0.25f), new Texel2D(0.50f, 0.25f),
                    new Texel2D(0.50f, 0.75f), new Texel2D(0.25f, 0.75f)});

        box.addFace( // right
                new Point3D[]{v[1], v[2], v[6], v[5]},
                new Vector3D[]{n[3], n[3], n[3], n[3]},
                new Texel2D[]{
                    new Texel2D(0.50f, 0.25f), new Texel2D(0.75f, 0.25f),
                    new Texel2D(0.75f, 0.75f), new Texel2D(0.50f, 0.75f)});

        box.addFace( // back
                new Point3D[]{v[2], v[3], v[7], v[6]},
                new Vector3D[]{n[4], n[4], n[4], n[4]},
                new Texel2D[]{
                    new Texel2D(0.75f, 0.25f), new Texel2D(1.00f, 0.25f),
                    new Texel2D(1.00f, 0.75f), new Texel2D(0.75f, 0.75f)});

        box.addFace( // left
                new Point3D[]{v[3], v[0], v[4], v[7]},
                new Vector3D[]{n[5], n[5], n[5], n[5]},
                new Texel2D[]{
                    new Texel2D(0.00f, 0.25f), new Texel2D(0.25f, 0.25f),
                    new Texel2D(0.25f, 0.75f), new Texel2D(0.00f, 0.75f)});

        return new GLMeshNode("Skybox", sceneGraph, box, Color.BLACK);
    }

    @Override
    protected GLCameraNode createCameraNode() {
        //Fixed Camera look from black side
        cam0 = new GLLookAtCameraNode("Fixed Camera 0", sceneGraph,
                new Point3D(1.05, 5.3, -2.8), new Point3D(1.05, 0.0, 1.2), new Vector3D(0, 0, 1));
        sceneGraph.getRootNode().addChild(cam0);

        //Fixed Camera look from Red side
        cam1 = new GLLookAtCameraNode("Fixed Camera 1", sceneGraph,
                new Point3D(1.05, 5.3, 5.2), new Point3D(1.05, 0.0, 1.2), new Vector3D(0, 0, -1));
        sceneGraph.getRootNode().addChild(cam1);

        //Fixed Camera look at top
        cam2 = new GLLookAtCameraNode("Fixed Camera 2", sceneGraph,
                new Point3D(1.05, 5.8, 1.2), new Point3D(1.05, 0, 1.2), new Vector3D(0, 0, -1));
        sceneGraph.getRootNode().addChild(cam2);

        cam3 = new GLOrbitCameraNode("Orbital Camera", sceneGraph, canvas,
                new Point3D(1.3, 0.8, 1.2), 1.4, 1.6);
        sceneGraph.getRootNode().addChild(cam3);
        cam3.autoMoveOff();

        return cam2;
    }

    @Override
    protected GLLightNode createLightNode() {
        return null;
    }

    @Override
    protected GLRenderableNode createTableclothNode(float thickness) {
        PolygonalMesh tableclothMesh = new PolygonalMesh();
        tableclothMesh.addFace(
                new Point3D[]{
                    new Point3D(-0.5f, 0, -0.5f),
                    new Point3D(0.5f, 0, -0.5f),
                    new Point3D(0.5f, 0, 0.5f),
                    new Point3D(-0.5f, 0, 0.5f)},
                new Vector3D[]{
                    new Vector3D(0, 1, 0),
                    new Vector3D(0, 1, 0),
                    new Vector3D(0, 1, 0),
                    new Vector3D(0, 1, 0)},
                new Texel2D[]{
                    new Texel2D(0, 0),
                    new Texel2D(1, 0),
                    new Texel2D(1, 1),
                    new Texel2D(0, 1)});
        tableclothNode = new GLMeshNode("tablecloth", sceneGraph, tableclothMesh, Color.WHITE);
        tableclothNode.setLocalTranslation(1.3f, -0.45f, 1.2f);
        tableclothNode.setLocalScale(4.5f, 0.0f, 4.59f);

        return tableclothNode;
    }

    @Override
    protected GLRenderableNode createChessboardNode(float thickness) {
        PolygonalMesh chessboardMesh = new PolygonalMesh();
        chessboardMesh.addFace(
                new Point3D[]{
                    new Point3D(-0.5f, 0, -0.5f),
                    new Point3D(0.5f, 0, -0.5f),
                    new Point3D(0.5f, 0, 0.5f),
                    new Point3D(-0.5f, 0, 0.5f)},
                new Vector3D[]{
                    new Vector3D(0, 1, 0),
                    new Vector3D(0, 1, 0),
                    new Vector3D(0, 1, 0),
                    new Vector3D(0, 1, 0)},
                new Texel2D[]{
                    new Texel2D(0, 0),
                    new Texel2D(1, 0),
                    new Texel2D(1, 1),
                    new Texel2D(0, 1)});
        chessboardNode = new GLMeshNode("chessboard", sceneGraph, chessboardMesh, Color.WHITE);
        chessboardNode.setLocalTranslation(1.3f, -0.449f, 1.2f);
        chessboardNode.setLocalScale(5.0f, 0.0f, 5.0f);

        return chessboardNode;
    }

    protected GLRenderableNode createTableObjectsNode(float topThickness, float tableWidth, float tableLength, GLSceneGraph sceneGraph) {
        tableObjectsNode = new GLRenderableNode("Table objects", sceneGraph);

        // add the table to the table node
        tableTopNode = new GLCubeNode("Table", sceneGraph, glut, Color.GRAY);
        tableTopNode.setLocalScale(4.5f, 0.03f, 4.6f);
        tableTopNode.setLocalTranslation(0.5f, -0.444f, 0);
        tableObjectsNode.addChild(tableTopNode);

        // Pyramid
        pyramidNode = new GLPyramidNode("Pyramid", sceneGraph, Color.YELLOW);
        pyramidNode.setLocalTranslation(-1.62f, -0.44f, 1.5f);
        pyramidNode.setLocalScale(0.135f);
        tableObjectsNode.addChild(pyramidNode);

        //RED First Line
        // Piece 1 左红車
        WPiece1Node = createMeshNodeFromFile("Red Piece 1", Color.red, "data\\piece\\rook02.obj");
        WPiece1Node.setLocalScale(9.0f / 66);
        WPiece1Node.setLocalRotation(180, 0, 180, 0);
        WPiece1Node.setLocalTranslation(-1.8f, pieceheight, 2.25f);
        map.put("rj1", WPiece1Node);
        mapCase.put("rj1", 0);
        tableObjectsNode.addChild(WPiece1Node);

        //Piece 2 左红馬
        WPiece2Node = createMeshNodeFromFile("Red Piece 2", Color.red, "data\\piece\\knight02.obj");
        WPiece2Node.setLocalScale(9.0f / 65);
        WPiece2Node.setLocalRotation(180, 0, 180, 0);
        WPiece2Node.setLocalTranslation(-1.3f, pieceheight, 2.25f);
        map.put("rm1", WPiece2Node);
        mapCase.put("rm1", 1);
        tableObjectsNode.addChild(WPiece2Node);
        //Piece 3 左红象
        WPiece3Node = createMeshNodeFromFile("Red Piece 3", Color.red, "data\\piece\\bishop02.obj");
        WPiece3Node.setLocalScale(9.0f / 64);
        WPiece3Node.setLocalRotation(180, 0, 180, 0);
        WPiece3Node.setLocalTranslation(-0.8f, pieceheight, 2.25f);
        map.put("rx1", WPiece3Node);
        mapCase.put("rx1", 2);
        tableObjectsNode.addChild(WPiece3Node);
        //Piece 4 左红士
        WPiece4Node = createMeshNodeFromFile("Red Piece 4", Color.red, "data\\piece\\guard02.obj");
        WPiece4Node.setLocalScale(9.0f / 64);
        WPiece4Node.setLocalRotation(180, 0, 180, 0);
        WPiece4Node.setLocalTranslation(-0.3f, pieceheight, 2.25f);
        map.put("rs1", WPiece4Node);
        mapCase.put("rs1", 3);
        tableObjectsNode.addChild(WPiece4Node);
        //Piece 5 红帅
        WPiece5Node = createMeshNodeFromFile("Red Piece 5", Color.red, "data\\piece\\king02.obj");
        WPiece5Node.setLocalScale(9.0f / 64);
        WPiece5Node.setLocalRotation(180, 0, 180, 0);
        WPiece5Node.setLocalTranslation(0.2f, pieceheight, 2.25f);
        map.put("rs", WPiece5Node);
        mapCase.put("rs", 4);
        tableObjectsNode.addChild(WPiece5Node);
        //Piece 6 右红士
        WPiece6Node = createMeshNodeFromFile("Red Piece 6", Color.red, "data\\piece\\guard02.obj");
        WPiece6Node.setLocalScale(9.0f / 64);
        WPiece6Node.setLocalRotation(180, 0, 180, 0);
        WPiece6Node.setLocalTranslation(0.7f, pieceheight, 2.25f);
        map.put("rs2", WPiece6Node);
        mapCase.put("rs2",5);
        tableObjectsNode.addChild(WPiece6Node);
        //Piece 7 右红象
        WPiece7Node = createMeshNodeFromFile("Red Piece 7", Color.red, "data\\piece\\bishop02.obj");
        WPiece7Node.setLocalScale(9.0f / 64);
        WPiece7Node.setLocalRotation(180, 0, 180, 0);
        WPiece7Node.setLocalTranslation(1.2f, pieceheight, 2.25f);
        map.put("rx2", WPiece7Node);
        mapCase.put("rx2", 6);
        tableObjectsNode.addChild(WPiece7Node);
        //Piece 8 右红馬
        WPiece8Node = createMeshNodeFromFile("Red Piece 8", Color.red, "data\\piece\\knight02.obj");
        WPiece8Node.setLocalScale(9.0f / 65);
        WPiece8Node.setLocalRotation(180, 0, 180, 0);
        WPiece8Node.setLocalTranslation(1.7f, pieceheight, 2.25f);
        map.put("rm2", WPiece8Node);
        mapCase.put("rm2", 7);
        tableObjectsNode.addChild(WPiece8Node);
        //RED Second Line
        // Piece 9 右红車
        WPiece9Node = createMeshNodeFromFile("Red Piece 9", Color.red, "data\\piece\\rook02.obj");
        WPiece9Node.setLocalScale(9.0f / 66);
        WPiece9Node.setLocalRotation(180, 0, 180, 0);
        WPiece9Node.setLocalTranslation(2.2f, pieceheight, 2.25f);
        map.put("rj2", WPiece9Node);
        mapCase.put("rj2", 8);
        tableObjectsNode.addChild(WPiece9Node);
        //Piece 10 左红炮
        WPiece10Node = createMeshNodeFromFile("Red Piece 10", Color.red, "data\\piece\\cannon02.obj");
        WPiece10Node.setLocalScale(9.0f / 64);
        WPiece10Node.setLocalRotation(180, 0, 180, 0);
        WPiece10Node.setLocalTranslation(-1.3f, pieceheight, 1.25f);
        map.put("rp1", WPiece10Node);
        mapCase.put("rp1", 9);
        tableObjectsNode.addChild(WPiece10Node);
        //Piece 11 右红炮
        WPiece11Node = createMeshNodeFromFile("Red Piece 11", Color.red, "data\\piece\\cannon02.obj");
        WPiece11Node.setLocalScale(9.0f / 64);
        WPiece11Node.setLocalRotation(180, 0, 180, 0);
        WPiece11Node.setLocalTranslation(1.7f, pieceheight, 1.25f);
        map.put("rp2", WPiece11Node);
        mapCase.put("rp2", 10);
        tableObjectsNode.addChild(WPiece11Node);
        //Piece 12 左1红兵
        WPiece12Node = createMeshNodeFromFile("Red Piece 12", Color.red, "data\\piece\\pawn02.obj");
        WPiece12Node.setLocalScale(9.0f / 66);
        WPiece12Node.setLocalRotation(180, 0, 180, 0);
        WPiece12Node.setLocalTranslation(-1.8f, pieceheight, 0.75f);
        map.put("rb1", WPiece12Node);
        mapCase.put("rb1", 11);
        tableObjectsNode.addChild(WPiece12Node);
        //Piece 13 左2红兵
        WPiece13Node = createMeshNodeFromFile("Red Piece 13", Color.red, "data\\piece\\pawn02.obj");
        WPiece13Node.setLocalScale(9.0f / 66);
        WPiece13Node.setLocalRotation(180, 0, 180, 0);
        WPiece13Node.setLocalTranslation(-0.8f, pieceheight, 0.75f);
        map.put("rb2", WPiece13Node);
        mapCase.put("rb2", 12);
        tableObjectsNode.addChild(WPiece13Node);
        //Piece 14 左3红兵
        WPiece14Node = createMeshNodeFromFile("Red Piece 14", Color.red, "data\\piece\\pawn02.obj");
        WPiece14Node.setLocalScale(9.0f / 66);
        WPiece14Node.setLocalRotation(180, 0, 180, 0);
        WPiece14Node.setLocalTranslation(0.2f, pieceheight, 0.75f);
        map.put("rb3", WPiece14Node);
        mapCase.put("rb3", 13);
        tableObjectsNode.addChild(WPiece14Node);
        //Piece 15 左4红兵
        WPiece15Node = createMeshNodeFromFile("Red Piece 15", Color.red, "data\\piece\\pawn02.obj");
        WPiece15Node.setLocalScale(9.0f / 66);
        WPiece15Node.setLocalRotation(180, 0, 180, 0);
//        WPiece15Node.set
        WPiece15Node.setLocalTranslation(1.2f, pieceheight, 0.75f);
        map.put("rb4", WPiece15Node);
        mapCase.put("rb4", 14);
        tableObjectsNode.addChild(WPiece15Node);
        //Piece 16 左5红兵
        WPiece16Node = createMeshNodeFromFile("Red Piece 16", Color.red, "data\\piece\\pawn02.obj");
        WPiece16Node.setLocalScale(9.0f / 66);
        WPiece16Node.setLocalRotation(180, 0, 180, 0);
        WPiece16Node.setLocalTranslation(2.2f, pieceheight, 0.75f);
        map.put("rb5", WPiece16Node);
        mapCase.put("rb5", 15);
        tableObjectsNode.addChild(WPiece16Node);

        //BLACK First Line
        // Piece 1 左黑车
        BPiece1Node = createMeshNodeFromFile("Black Piece 1", Color.BLACK, "data\\piece\\rook01.obj");
        BPiece1Node.setLocalScale(9.0f / 64);
        BPiece1Node.setLocalTranslation(-1.8f, pieceheight, -2.25f);
        map.put("bj1", BPiece1Node);
        mapCase.put("bj1", 16);
        tableObjectsNode.addChild(BPiece1Node);
        //Piece 2 左黑马
        BPiece2Node = createMeshNodeFromFile("Black Piece 2", Color.BLACK, "data\\piece\\knight01.obj");
        BPiece2Node.setLocalScale(9.0f / 64);
        BPiece2Node.setLocalTranslation(-1.3f, pieceheight, -2.25f);
        map.put("bm1", BPiece2Node);
        mapCase.put("bm1", 17);
        tableObjectsNode.addChild(BPiece2Node);
        //Piece 3 左黑象
        BPiece3Node = createMeshNodeFromFile("Black Piece 3", Color.BLACK, "data\\piece\\bishop01.obj");
        BPiece3Node.setLocalScale(9.0f / 64);
        BPiece3Node.setLocalTranslation(-0.8f, pieceheight, -2.25f);
        map.put("bx1", BPiece3Node);
        mapCase.put("bx1", 18);
        tableObjectsNode.addChild(BPiece3Node);
        //Piece 4 左黑仕
        BPiece4Node = createMeshNodeFromFile("Black Piece 4", Color.BLACK, "data\\piece\\guard01.obj");
        BPiece4Node.setLocalScale(9.0f / 64);
        BPiece4Node.setLocalTranslation(-0.3f, pieceheight, -2.25f);
        map.put("bs1", BPiece4Node);
        mapCase.put("bs1", 19);
        tableObjectsNode.addChild(BPiece4Node);
        //Piece 5 黑将
        BPiece5Node = createMeshNodeFromFile("Black Piece 5", Color.BLACK, "data\\piece\\king01.obj");
        BPiece5Node.setLocalScale(9.0f / 64);
        BPiece5Node.setLocalTranslation(0.2f, pieceheight, -2.25f);
        map.put("bj", BPiece5Node);
        mapCase.put("bj", 20);
        tableObjectsNode.addChild(BPiece5Node);
        //Piece 6 右黑仕
        BPiece6Node = createMeshNodeFromFile("Black Piece 6", Color.BLACK, "data\\piece\\guard01.obj");
        BPiece6Node.setLocalScale(9.0f / 64);
        BPiece6Node.setLocalTranslation(0.7f, pieceheight, -2.25f);
        map.put("bs2", BPiece6Node);
        mapCase.put("bs2", 21);
        tableObjectsNode.addChild(BPiece6Node);
        //Piece 7 右黑象
        BPiece7Node = createMeshNodeFromFile("Black Piece 7", Color.BLACK, "data\\piece\\bishop01.obj");
        BPiece7Node.setLocalScale(9.0f / 64);
        BPiece7Node.setLocalTranslation(1.2f, pieceheight, -2.25f);
        map.put("bx2", BPiece7Node);
        mapCase.put("bx2", 22);
        tableObjectsNode.addChild(BPiece7Node);
        //Piece 8 右黑马
        BPiece8Node = createMeshNodeFromFile("Black Piece 8", Color.BLACK, "data\\piece\\knight01.obj");
        BPiece8Node.setLocalScale(9.0f / 64);
        BPiece8Node.setLocalTranslation(1.7f, pieceheight, -2.25f);
        map.put("bm2", BPiece8Node);
        mapCase.put("bm2", 23);
        tableObjectsNode.addChild(BPiece8Node);
        //BLACK Second Line
        // Piece 9 右黑车
        BPiece9Node = createMeshNodeFromFile("Black Piece 9", Color.BLACK, "data\\piece\\rook01.obj");
//        BPiece9Node = createMeshNodeFromFile("Black Piece 9", Color.BLACK, "data\\piece\\rook01.mtl");
        BPiece9Node.setLocalScale(9.0f / 64);
        BPiece9Node.setLocalTranslation(2.2f, pieceheight, -2.25f);
        map.put("bj2", BPiece9Node);
        mapCase.put("bj2", 24);
        tableObjectsNode.addChild(BPiece9Node);
        //Piece 10 左黑炮
        BPiece10Node = createMeshNodeFromFile("Black Piece 10", Color.BLACK, "data\\piece\\cannon01.obj");
        BPiece10Node.setLocalScale(9.0f / 64);
        BPiece10Node.setLocalTranslation(-1.3f, pieceheight, -1.25f);
        map.put("bp1", BPiece10Node);
        mapCase.put("bp1", 25);
        tableObjectsNode.addChild(BPiece10Node);
        //Piece 11 右黑炮
        BPiece11Node = createMeshNodeFromFile("Black Piece 11", Color.BLACK, "data\\piece\\cannon01.obj");
        BPiece11Node.setLocalScale(9.0f / 64);
        BPiece11Node.setLocalTranslation(1.7f, pieceheight, -1.25f);
        map.put("bp2", BPiece11Node);
        mapCase.put("bp2", 26);
        tableObjectsNode.addChild(BPiece11Node);
        //Piece 12 左1黑卒
        BPiece12Node = createMeshNodeFromFile("Black Piece 12", Color.BLACK, "data\\piece\\pawn01.obj");
        BPiece12Node.setLocalScale(9.0f / 64);
        BPiece12Node.setLocalTranslation(-1.8f, pieceheight, -0.75f);
        map.put("bz1", BPiece12Node);
        mapCase.put("bz1", 27);
        tableObjectsNode.addChild(BPiece12Node);
        //Piece 13 左2黑卒
        BPiece13Node = createMeshNodeFromFile("Black Piece 13", Color.BLACK, "data\\piece\\pawn01.obj");
        BPiece13Node.setLocalScale(9.0f / 64);
        BPiece13Node.setLocalTranslation(-0.8f, pieceheight, -0.75f);
        map.put("bz2", BPiece13Node);
        mapCase.put("bz2", 28);
        tableObjectsNode.addChild(BPiece13Node);
        //Piece 14 左3黑卒
        BPiece14Node = createMeshNodeFromFile("Black Piece 14", Color.BLACK, "data\\piece\\pawn01.obj");
        BPiece14Node.setLocalScale(9.0f / 64);
        BPiece14Node.setLocalTranslation(0.2f, pieceheight, -0.75f);
        map.put("bz3", BPiece14Node);
        mapCase.put("bz3", 29);
        tableObjectsNode.addChild(BPiece14Node);
        //Piece 15 左4黑卒
        BPiece15Node = createMeshNodeFromFile("Black Piece 15", Color.BLACK, "data\\piece\\pawn01.obj");
        BPiece15Node.setLocalScale(9.0f / 64);
        BPiece15Node.setLocalTranslation(1.2f, pieceheight, -0.75f);
        map.put("bz4", BPiece15Node);
        mapCase.put("bz4", 30);
        tableObjectsNode.addChild(BPiece15Node);
        //Piece 16 左5黑卒
        BPiece16Node = createMeshNodeFromFile("Black Piece 16", Color.BLACK, "data\\piece\\pawn01.obj");
        BPiece16Node.setLocalScale(9.0f / 64);
        BPiece16Node.setLocalTranslation(2.2f, pieceheight, -0.75f);
        map.put("bz5", BPiece16Node);
        mapCase.put("bz5", 31);
        tableObjectsNode.addChild(BPiece16Node);

        return tableObjectsNode;
    }

    @Override
    protected void setProjection(GL gl, double width, double height) {
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, width / height, WORLD_NEAR, WORLD_FAR);
    }

    // called when OpenGL is initialized
    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);

        GL gl = drawable.getGL();
        
        sceneGraph.setBackground(Color.BLACK);
        sceneGraph.setAmbientLight(Color.WHITE);

        skyboxNode = createSkyboxNode();
        sceneGraph.getRootNode().addChild(skyboxNode);

        // add the table to the scene graph
        float tableWidth = 2.0f;
        float topThickness = 0.02f;
        float tableLength = 1.6f;
        GLRenderableNode tableNode = createTableNode(tableWidth, tableLength, topThickness, sceneGraph, Color.GRAY);
        // centre table at (0.8,0,1.2)
        tableNode.setLocalTranslation(0.8f, -0.05f, 1.2f);
        sceneGraph.getRootNode().addChild(tableNode);
        // add a node for all the objects on the table
        GLRenderableNode tableObjectsNode = createTableObjectsNode(topThickness, tableWidth, tableLength, sceneGraph);
        tableObjectsNode.setLocalTranslation(0, topThickness, 0);
        tableNode.addChild(tableObjectsNode);

        Texture tex0 = new FileTexture("data\\wood.jpg", gl, true, true);
        tableclothNode.getMesh().setTextureId(tex0.getID());
        
        Texture tex1 = new FileTexture("data\\chessboard.jpg", gl, true, true);
        chessboardNode.getMesh().setTextureId(tex1.getID());

        Texture tex3 = new FileTexture("data\\cloudy_noon.jpg", gl, true, true);
        skyboxNode.getMesh().setTextureId(tex3.getID());
        skyboxNode.getMesh().setTextureMode(GL.GL_DECAL);
        skyboxNode.setLocalScale(10.0f);
        skyboxNode.setLocalTranslation(0.5f, -0.5f, 0.5f);

        sirenNode = new GLSirenLight("Siren Light", sceneGraph);
        sirenNode.setLocalTranslation(0.5f, 0.3f, 0.5f);
        sirenNode.setEnabled(true);
        sceneGraph.getRootNode().addChild(sirenNode);
        
//        isReady = true;

    }
    public boolean initOK(){
        return ChineseChessTable.isReady;
        
    }
    // draws the scene
    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        setProjection(gl, canvas.getWidth(), canvas.getHeight());
        gl.glShadeModel(smoothShading ? GL.GL_SMOOTH : GL.GL_FLAT);
        WSituation ws = new WSituation();
        BSituation bs = new BSituation();
        if (foggy) {
            gl.glEnable(GL.GL_FOG);
            float[] fogColour = {0.2f, 0.2f, 0.2f, 1.0f};
            gl.glFogfv(GL.GL_COLOR, fogColour, 0);
            gl.glFogi(GL.GL_FOG_MODE, GL.GL_LINEAR);
            gl.glFogf(GL.GL_FOG_START, fog);
            gl.glFogf(GL.GL_FOG_END, 0.0f);
        } else {
            gl.glDisable(GL.GL_FOG);
        }
        pyramidNode.setEnabled(true);

        if (doPicking) {
            GLRenderableNode pickedNode = sceneGraph.renderScene(gl, pickPoint);
            
            if (pickedNode != null) {
                //set currentlocation
                //getting x
                float b1 = -1.8f;
                for (double a1 = 35.625; a1 <= 705; a1 = a1 + 78.75) {
                    if (pickPoint.getX() >= a1 && pickPoint.getX() < a1 + 78.75) {
                        cx = b1;
                    }
                    b1 = b1 + 0.5f;
                }
                //getting y
                float b2 = 2.25f;
                for (double a2 = 20; a2 <= 780; a2 = a2 + 76) {
                    if (pickPoint.getY() >= a2 && pickPoint.getY() < a2 + 76) {
                        cy = b2;
                    }
                    b2 = b2 - 0.5f;
                }
                //getting the mouseclick point index location
                int m = 0;
                for (float a3 = 2.25f; a3 >= -2.25f; a3 = a3 - 0.5f) {
                    for (float b3 = -1.8f; b3 <= 2.2f; b3 = b3 + 0.5f) {
                        if (cy == a3 && cx == b3) {
                            currentlocation = m;
                        }
                        m++;
                    }
                }
                //System.out.println(currentlocation);
                //System.out.println("cp = "+ chesspiece[currentlocation]);
                System.out.println("黑棋类型 = "+ bpiecesituation);
                System.out.println("红棋类型 = "+ wpiecesituation);
                System.out.println("Y = "+pickPoint.getY());
                System.out.println("X = "+pickPoint.getX());
//                System.out.println("piecelocation[]:"+piecelocation[currentchoose]);
//                System.out.println("ID = "+pickedNode.getId());
//                }
                if ((pickedNode != tableclothNode && pickedNode != skyboxNode && pickedNode != tableTopNode && round != null && !mf.onlineMode)) {
                    //black go
                    if (checkstep == false && round.equals("black") ) {
                        //对每个棋子分类（确定类别）
                        int i = (pickedNode.getId() - 10) / 3;
                        switch ((pickedNode.getId() - 10) / 3) {
                            case 16://黑左車
                                name = "車";
                                id = "bj1";
                                currentchoose = i;
                                BPiece1Node.setEnabled(false);
                                copyNode = BPiece1Node;
                                checkstep = true;
                                bpiecesituation = 1; //b1 means black chaRiot 黑車
                                break;
                            case 17://黑左馬
                                name = "馬";
                                id = "bm1";
                                currentchoose = i;
                                BPiece2Node.setEnabled(false);
                                copyNode = BPiece2Node;
                                checkstep = true;
                                bpiecesituation = 2; //b2 means black horse 黑馬
                                break;
                            case 18://黑左象
                                name = "象";
                                id = "bx1";
                                currentchoose = i;
                                BPiece3Node.setEnabled(false);
                                copyNode = BPiece3Node;
                                bpiecesituation = 3; //b3 means black elephant 黑象
                                checkstep = true;
                                break;
                            case 19://黑左仕
                                name = "士";
                                id = "bs1";
                                currentchoose = i;
                                BPiece4Node.setEnabled(false);
                                copyNode = BPiece4Node;
                                bpiecesituation = 4; //b4 means black queen 黑仕
                                checkstep = true;
                                break;
                            case 20://黑将
                                name = "将";
                                id = "bj";
                                currentchoose = i;
                                BPiece5Node.setEnabled(false);
                                copyNode = BPiece5Node;
                                bpiecesituation = 5; //b5 means black king 黑将
                                checkstep = true;
                                break;
                            case 21://黑右仕
                                name = "士";
                                id = "bs2";
                                currentchoose = i;
                                BPiece6Node.setEnabled(false);
                                copyNode = BPiece6Node;
                                bpiecesituation = 4;
                                checkstep = true;
                                break;
                            case 22://黑右象
                                name = "象";
                                id = "bx2";
                                currentchoose = i;
                                BPiece7Node.setEnabled(false);
                                copyNode = BPiece7Node;
                                checkstep = true;
                                bpiecesituation = 3;
                                break;
                            case 23://黑右馬
                                name = "馬";
                                id = "bm2";
                                currentchoose = i;
                                BPiece8Node.setEnabled(false);
                                copyNode = BPiece8Node;
                                checkstep = true;
                                bpiecesituation = 2;
                                break;
                            case 24://黑右車
                                name = "車";
                                id = "bj2";
                                currentchoose = i;
                                BPiece9Node.setEnabled(false);
                                copyNode = BPiece9Node;
                                checkstep = true;
                                bpiecesituation = 1;
                                break;
                            case 25://黑左炮
                                name = "炮";
                                id = "bp1";
                                currentchoose = i;
                                BPiece10Node.setEnabled(false);
                                copyNode = BPiece10Node;
                                checkstep = true;
                                bpiecesituation = 6; //b6 means 黑炮
                                break;
                            case 26://黑右炮
                                name = "炮";
                                id = "bp2";
                                currentchoose = i;
                                BPiece11Node.setEnabled(false);
                                copyNode = BPiece11Node;
                                checkstep = true;
                                bpiecesituation = 6;
                                break;
                            case 27://黑左1卒
                                name = "卒";
                                id = "bz1";
                                currentchoose = i;
                                BPiece12Node.setEnabled(false);
                                copyNode = BPiece12Node;
                                checkstep = true;
                                bpiecesituation = 0; //b0 means black pawn 黑卒
                                break;
                            case 28://黑左2卒
                                name = "卒";
                                id = "bz2";
                                currentchoose = i;
                                BPiece13Node.setEnabled(false);
                                copyNode = BPiece13Node;
                                checkstep = true;
                                bpiecesituation = 0;
                                break;
                            case 29://黑左3卒
                                name = "卒";
                                id = "bz3";
                                currentchoose = i;
                                BPiece14Node.setEnabled(false);
                                copyNode = BPiece14Node;
                                checkstep = true;
                                bpiecesituation = 0;
                                break;
                            case 30://黑左4卒
                                name = "卒";
                                id = "bz4";
                                currentchoose = i;
                                BPiece15Node.setEnabled(false);
                                copyNode = BPiece15Node;
                                checkstep = true;
                                bpiecesituation = 0;
                                break;
                            case 31://黑左5卒
                                name = "卒";
                                id = "bz5";
                                currentchoose = i;
                                BPiece16Node.setEnabled(false);
                                copyNode = BPiece16Node;
                                checkstep = true;
                                bpiecesituation = 0;
                                break;
                        }
                    } else if (checkstep == true && round.equals("black") ) {
                        for (int a4 = 16; a4 <= 31; a4++) {
                            if (piecelocation[a4] == currentlocation) {
                                situation = 0;
                            }
                        }
                        for (int b4 = 0; b4 <= 15; b4++) {
                            if (piecelocation[b4] == currentlocation) {
                                situation = 1;
                            }
                        }
                        if (chesspiece[currentlocation] == 99) {
                            situation = 2;
                        }
                        if (situation == 0) {
                            bs.B0Situation0();
                        } else if (situation == 1) {
                            //setting eat rules of black pawn(situation1).(bpiecesituation0) 黑卒吃的规则
                            if (bpiecesituation == 0) {
                                if(piecelocation[currentchoose] >= 45){
                                    if(piecelocation[currentchoose] - 9 == currentlocation){
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    }
                                    else
                                        bs.B0Situation0();
                                }else{
                                    if(piecelocation[currentchoose] - 9 == currentlocation){
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] + 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 8)){
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] - 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 0)){
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    }
                                    else
                                        bs.B0Situation0();
                                }
                            }//pawn eat rules end
                            //setting eat rules of black chaRiot(situation1).(bpiecesituation1) 黑車吃的规则
                            if (bpiecesituation == 1) {
                                int bchariot = 99;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward eat rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of forward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of backward be blocked by chess
                                        }
                                    }
                                    if (bchariot == 99) {
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    } else if (bchariot == 0) {
                                        bs.B0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward eat rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of rightward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of leftward be blocked by chess
                                        }
                                    }
                                    if (bchariot == 99) {
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    } else if (bchariot == 0) {
                                        bs.B0Situation0();
                                    }
                                } else {
                                    bs.B0Situation0();
                                }
                            }//chaRiot eat rules end
                            //setting eat rules of black horse(situation1).(bpiecesituation2) 黑馬吃的规则
                            if (bpiecesituation == 2) {
                                if (currentlocation > piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] + 7) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] + 11) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] + 17) && (piecelocation[currentchoose]) % 8 != 0) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] + 19) && (piecelocation[currentchoose]) % 8 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else {
                                        bs.B0Situation0();
                                    }
                                } else if (currentlocation < piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] - 7) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] - 11) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] - 17) && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] - 19) && (piecelocation[currentchoose]) % 9 != 0) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            bs.B0Situation0();
                                        else{
                                            bs.B0Situation1();
                                            pickedNode = copyNode;
                                            bs.B0Situation2();
                                        }
                                    } else {
                                        bs.B0Situation0();
                                    }
                                }
                            }//horse eat rules end
                            //setting eat rules of black elephant(situation1).(bpiecesituation3) 黑象吃的规则
                            if (bpiecesituation == 3) {
                                if(((piecelocation[currentchoose] + 20 == currentlocation && chesspiece[piecelocation[currentchoose] + 10] == 99) 
                                        || (piecelocation[currentchoose] + 16 == currentlocation && chesspiece[piecelocation[currentchoose] + 8] == 99)
                                        || (piecelocation[currentchoose] - 20 == currentlocation && chesspiece[piecelocation[currentchoose] - 10] == 99)
                                        || piecelocation[currentchoose] - 16 == currentlocation && chesspiece[piecelocation[currentchoose] - 8] == 99) && (currentlocation >= 45)){
                                    bs.B0Situation1();
                                    pickedNode = copyNode;
                                    bs.B0Situation2();
                                }
                                else{
                                    bs.B0Situation0();
                                }
                            }//elephant eat rules end
                            //setting eat rules of black guard(situation1).(bpiecesituation4) 黑仕吃的规则
                            if (bpiecesituation == 4) {
                                if((piecelocation[currentchoose] + 10 == currentlocation || piecelocation[currentchoose] + 8 == currentlocation
                                        || piecelocation[currentchoose] - 10 == currentlocation || piecelocation[currentchoose] - 8 == currentlocation) && 
                                        (currentlocation == 66 || currentlocation == 67 || currentlocation == 68 || 
                                        currentlocation == 75 || currentlocation == 76 || currentlocation == 77 || currentlocation == 84 || currentlocation == 85 || currentlocation == 86)){
                                    bs.B0Situation1();
                                    pickedNode = copyNode;
                                    bs.B0Situation2();
                                }
                                else
                                    bs.B0Situation0();
                            }//guard eat rules end
                            //setting eat rules of black king(situation1).(bpiecesituation5) 黑将吃的规则
                            if (bpiecesituation == 5) {
                                if((piecelocation[currentchoose] + 9 == currentlocation || piecelocation[currentchoose] - 9 == currentlocation) &&(currentlocation >= 66)){
                                    bs.B0Situation1();
                                    pickedNode = copyNode;
                                    bs.B0Situation2();
                                }
                                else if((piecelocation[currentchoose] - 1 == currentlocation || piecelocation[currentchoose] + 1 == currentlocation) && (currentlocation == 66 || currentlocation == 67 || currentlocation == 68 || 
                                        currentlocation == 75 || currentlocation == 76 || currentlocation == 77 || currentlocation == 84 || currentlocation == 85 || currentlocation == 86)){
                                    bs.B0Situation1();
                                    pickedNode = copyNode;
                                    bs.B0Situation2();
                                }
                                else
                                    bs.B0Situation0();
                            }  //king eat rules end
                            //setting eat rules of black cannon(situation1).(bpiecesituation6) 黑炮吃的规则
                            if (bpiecesituation == 6) {
                                int bchariot = 99;
                                int count = 0;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward eat rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    if (count == 1) {
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    } else {
                                        bs.B0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward eat rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    if (count == 1) {
                                        bs.B0Situation1();
                                        pickedNode = copyNode;
                                        bs.B0Situation2();
                                    } else if (bchariot == 0 || count != 1) {
                                        bs.B0Situation0();
                                    }
                                } else {
                                    bs.B0Situation0();
                                }
                            }  //cannon eat rules end
                            
                        } else if (pickedNode == chessboardNode && situation == 2) {
                            //setting regular rules of black pawn(situation2).(bpiecesituation0) 黑卒的普通规则
                            if (bpiecesituation == 0) {
                                if(piecelocation[currentchoose] >= 45){
                                    if(piecelocation[currentchoose] - 9 == currentlocation){
                                         bs.B0Situation2();
                                    }
                                    else
                                        bs.B0Situation0();
                                }
                                else{
                                    if(piecelocation[currentchoose] - 9 == currentlocation){
                                         bs.B0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] + 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 8)){
                                         bs.B0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] - 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 0)){
                                         bs.B0Situation2();
                                    }
                                    else
                                        bs.B0Situation0();
                                }
                            }//pawn regular rules end
                            //setting regular rules of black chaRiot(situation2).(bpiecesituation1) 黑車的普通规则
                            if (bpiecesituation == 1) {
                                int bchariot = 99;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of forward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of backward be blocked by chess
                                        }
                                    }
                                    if (bchariot == 99) {
                                        bs.B0Situation2();
                                    } else if (bchariot == 0) {
                                        bs.B0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of rightward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of leftward be blocked by chess
                                        }
                                    }
                                    if (bchariot == 99) {
                                        bs.B0Situation2();
                                    } else if (bchariot == 0) {
                                        bs.B0Situation0();
                                    }
                                } else {
                                    bs.B0Situation0();
                                }
                            }//chaRiot regular rules end
                            //setting regular rules of black horse(situation2).(bpiecesituation2) 黑馬的普通规则
                            if (bpiecesituation == 2) {
                                if (currentlocation > piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] + 7) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] + 11) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] + 17) && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] + 19) && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else {
                                        bs.B0Situation0();
                                    }
                                } else if (currentlocation < piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] - 7) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] - 11) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] - 17) && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] - 19) && (piecelocation[currentchoose]) % 9 != 0) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            bs.B0Situation0();
                                        else
                                            bs.B0Situation2();
                                    } else {
                                        bs.B0Situation0();
                                    }
                                }
                            }//horse regular rules end
                            //setting regular rules of black elephant(situation2).(bpiecesituation3) 黑象的普通规则
                            if (bpiecesituation == 3) {
                                if(((piecelocation[currentchoose] + 20 == currentlocation && chesspiece[piecelocation[currentchoose] + 10] == 99) 
                                        || (piecelocation[currentchoose] + 16 == currentlocation && chesspiece[piecelocation[currentchoose] + 8] == 99)
                                        || (piecelocation[currentchoose] - 20 == currentlocation && chesspiece[piecelocation[currentchoose] - 10] == 99)
                                        || piecelocation[currentchoose] - 16 == currentlocation && chesspiece[piecelocation[currentchoose] - 8] == 99) && (currentlocation >= 45)){
                                    bs.B0Situation2();
                                }
                                else{
                                    bs.B0Situation0();
                                }
                            }//elephant regular rules end
                            //setting regular rules of black guard(situation2).(bpiecesituation4) 黑仕的普通规则
                            if (bpiecesituation == 4) {
                                if((piecelocation[currentchoose] + 10 == currentlocation || piecelocation[currentchoose] + 8 == currentlocation
                                        || piecelocation[currentchoose] - 10 == currentlocation || piecelocation[currentchoose] - 8 == currentlocation) && 
                                        (currentlocation == 66 || currentlocation == 67 || currentlocation == 68 || 
                                        currentlocation == 75 || currentlocation == 76 || currentlocation == 77 || currentlocation == 84 || currentlocation == 85 || currentlocation == 86)){
                                    bs.B0Situation2();
                                }
                                else
                                    bs.B0Situation0();
                            }//guard regular rules end
                            //setting regular rules of black king(situation2).(bpiecesituation5) 黑将的普通规则
                            if (bpiecesituation == 5) {
                                if((piecelocation[currentchoose] + 9 == currentlocation || piecelocation[currentchoose] - 9 == currentlocation) &&(currentlocation >= 66)){
                                    bs.B0Situation2();
                                }
                                else if((piecelocation[currentchoose] - 1 == currentlocation || piecelocation[currentchoose] + 1 == currentlocation) && 
                                        (currentlocation == 66 || currentlocation == 67 || currentlocation == 68 || 
                                        currentlocation == 75 || currentlocation == 76 || currentlocation == 77 || currentlocation == 84 || currentlocation == 85 || currentlocation == 86)){
                                    bs.B0Situation2();
                                }
                                else
                                    bs.B0Situation0();
                            } //king regular rules end
                            //setting regular rules of black cannon(situation2).(bpiecesituation6) 黑炮的普通规则
                            if (bpiecesituation == 6) {
                                int bchariot = 99;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of forward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of backward be blocked by chess
                                        }
                                    }
                                    if (bchariot == 99) {
                                        bs.B0Situation2();
                                    } else if (bchariot == 0) {
                                        bs.B0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward rules of black chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of rightward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            bchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            bchariot = 0;  // direction of leftward be blocked by chess
                                        }
                                    }
                                    if (bchariot == 99) {
                                        bs.B0Situation2();
                                    } else if (bchariot == 0) {
                                        bs.B0Situation0();
                                    }
                                } else {
                                    bs.B0Situation0();
                                }
                            } //cannon regular rules end
                        }
                        if(move){
                            step++;
                            int startX,startY,endX,endY;
                            String eatcolor;
                            if(round.equals("red"))
                                eatcolor = "black";
                            else
                                eatcolor = "red";
                            startX = (startLocation+1)%9;
                            endX = (endLocation+1)%9;
                            if(startX == 0)
                                startX = 9;
                            if(endX == 0)
                                endX = 9;
                            startY = 11-(startLocation/9+1);
                            endY = 11-(endLocation/9+1);
                            if(eatname != null && eatid != null){
                                db.insertTable("insert into CHESSMANUAL values("+step+",'"+id+"','"
                                            +name+"','black',"+startX+","+startY+","
                                            +endX+","+endY+",'"+eatid+"','"+eatname+"','"+eatcolor+"','"+mf.p1.getName()+"')");
                                mf.appendText("System: black "+name+" moves from "
                                            +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n");
                                mf.appendText("System: red "+eatname+" is eaten.\n");
                                eatname = null;
                                eatid = null;
                            }
                            else{
                                db.insertTable("insert into CHESSMANUAL values("+step+",'"+id+"','"
                                            +name+"','black',"+startX+","+startY+","
                                            +endX+","+endY+","+null+","+null+","+null+",'"+mf.p1.getName()+"')");
                                mf.appendText("System: black "+name+" moves from "
                                            +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n");
                            }
                            move = false;
                        }
                    }

                    //Red go
                    if (checkstep == false && round.equals("red")) {
                        System.out.println((pickedNode.getId() - 10) / 3);
                        int i = (pickedNode.getId() - 10) / 3;
                        switch ((pickedNode.getId() - 10) / 3) {
                            case 0://红左车
                                name = "车";
                                id = "rj1";
                                currentchoose = i;
                                WPiece1Node.setEnabled(false);
                                copyNode = WPiece1Node;
                                checkstep = true;
                                wpiecesituation = 1; //w1 means red chaRiot 红车
                                break;
                            case 1://红左马
                                name = "马";
                                id = "rm1";
                                currentchoose = i;
                                WPiece2Node.setEnabled(false);
                                copyNode = WPiece2Node;
                                checkstep = true;
                                wpiecesituation = 2; //w2 means red horse 红马
                                break;
                            case 2://红左象
                                name = "相";
                                id = "rx1";
                                currentchoose = i;
                                WPiece3Node.setEnabled(false);
                                copyNode = WPiece3Node;
                                checkstep = true;
                                wpiecesituation = 3; //w3 means red elephant 红象
                                break;
                            case 3://红左士
                                name = "仕";
                                id = "rs1";
                                currentchoose = i;
                                WPiece4Node.setEnabled(false);
                                copyNode = WPiece4Node;
                                checkstep = true;
                                wpiecesituation = 4; //w4 means red guard 红士
                                break;
                            case 4://红帅
                                name = "帅";
                                id = "rs";
                                currentchoose = i;
                                WPiece5Node.setEnabled(false);
                                copyNode = WPiece5Node;
                                checkstep = true;
                                wpiecesituation = 5; //w4 means red king 红帅
                                break;
                            case 5://红右士
                                name = "仕";
                                id = "rs2";
                                currentchoose = i;
                                WPiece6Node.setEnabled(false);
                                copyNode = WPiece6Node;
                                checkstep = true;
                                wpiecesituation = 4;
                                break;
                            case 6://红右象
                                name = "相";
                                id = "rx2";
                                currentchoose = i;
                                WPiece7Node.setEnabled(false);
                                copyNode = WPiece7Node;
                                checkstep = true;
                                wpiecesituation = 3;
                                break;
                            case 7://红右马
                                name = "马";
                                id = "rm2";
                                currentchoose = i;
                                WPiece8Node.setEnabled(false);
                                copyNode = WPiece8Node;
                                checkstep = true;
                                wpiecesituation = 2;
                                break;
                            case 8://红右车
                                name = "车";
                                id = "rj2";
                                currentchoose = i;
                                WPiece9Node.setEnabled(false);
                                copyNode = WPiece9Node;
                                checkstep = true;
                                wpiecesituation = 1;
                                break;
                            case 9://红左炮
                                name = "炮";
                                id = "rp1";
                                currentchoose = i;
                                WPiece10Node.setEnabled(false);
                                copyNode = WPiece10Node;
                                checkstep = true;
                                wpiecesituation = 6;  //w6 means red Cannon 红炮
                                break;
                            case 10://红右炮
                                name = "炮";
                                id = "rp2";
                                currentchoose = i;
                                WPiece11Node.setEnabled(false);
                                copyNode = WPiece11Node;
                                checkstep = true;
                                wpiecesituation = 6;
                                break;
                            case 11://红左1兵
                                name = "兵";
                                id = "rb1";
                                currentchoose = i;
                                WPiece12Node.setEnabled(false);
                                copyNode = WPiece12Node;
                                checkstep = true;
                                wpiecesituation = 0; //w0 means red pawn 红兵
                                break;
                            case 12://红左2兵
                                name = "兵";
                                id = "rb2";
                                currentchoose = i;
                                WPiece13Node.setEnabled(false);
                                copyNode = WPiece13Node;
                                checkstep = true;
                                wpiecesituation = 0;
                                break;
                            case 13://红左3兵
                                name = "兵";
                                id = "rb3";
                                currentchoose = i;
                                WPiece14Node.setEnabled(false);
                                copyNode = WPiece14Node;
                                checkstep = true;
                                wpiecesituation = 0;
                                break;
                            case 14://红左4兵
                                name = "兵";
                                id = "rb4";
                                currentchoose = i;
                                WPiece15Node.setEnabled(false);
                                copyNode = WPiece15Node;
                                checkstep = true;
                                wpiecesituation = 0;
                                break;
                            case 15://红左5兵
                                name = "兵";
                                id = "rb5";
                                currentchoose = i;
                                WPiece16Node.setEnabled(false);
                                copyNode = WPiece16Node;
                                checkstep = true;
                                wpiecesituation = 0;
                                break;
                        }
                    } else if (checkstep == true && round.equals("red")) {
                        for (int a4 = 0; a4 <= 15; a4++) {
                            if (piecelocation[a4] == currentlocation) {
                                situation = 0;
                            }
                        }
                        for (int b4 = 16; b4 <= 31; b4++) {
                            if (piecelocation[b4] == currentlocation) {
                                situation = 1;
                            }
                        }
                        if (chesspiece[currentlocation] == 99) {
                            situation = 2;
                        }
                        if (situation == 0) {
                            ws.W0Situation0();
                        } else if (situation == 1) {
                            //setting eat rules of red pawn(situation1).(wpiecesituation0)红兵吃的规则
                            if (wpiecesituation == 0) {
                                if(piecelocation[currentchoose] <= 44){
                                    if(piecelocation[currentchoose] + 9 == currentlocation){
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    }
                                    else
                                        ws.W0Situation0();
                                }else{
                                    if(piecelocation[currentchoose] + 9 == currentlocation){
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] + 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 8)){
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] - 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 0)){
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    }
                                    else
                                        ws.W0Situation0();
                                }
                            }//pawn eat rules end
                            //setting eat rules of red chaRiot(situation1).(wpiecesituation1)红车吃的规则
                            if (wpiecesituation == 1) {
                                int wchariot = 99;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward eat rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of forward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of backward be blocked by chess
                                        }
                                    }
                                    if (wchariot == 99) {
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    } else if (wchariot == 0) {
                                        ws.W0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward eat rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of rightward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of leftward be blocked by chess
                                        }
                                    }
                                    if (wchariot == 99) {
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    } else if (wchariot == 0) {
                                        ws.W0Situation0();
                                    }
                                } else {
                                    ws.W0Situation0();
                                }
                            }//chaRiot eat rules end
                            //setting eat rules of red horse(situation1).(wpiecesituation2) 红马吃的规则
                            if (wpiecesituation == 2) {
                                if (currentlocation > piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] + 7) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] + 11) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] + 17) && (piecelocation[currentchoose]) % 8 != 0) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] + 19) && (piecelocation[currentchoose]) % 8 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else {
                                        ws.W0Situation0();
                                    }
                                } else if (currentlocation < piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] - 7) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] - 11) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] - 17) && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else if (currentlocation == (piecelocation[currentchoose] - 19) && (piecelocation[currentchoose]) % 9 != 0) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            ws.W0Situation0();
                                        else{
                                            ws.W0Situation1();
                                            pickedNode = copyNode;
                                            ws.W0Situation2();
                                        }
                                    } else {
                                        ws.W0Situation0();
                                    }
                                }
                            }//horse eat rules end
                            //setting eat rules of red elephant(situation1).(wpiecesituation3) 红象吃的规则
                            if (wpiecesituation == 3) {
                                if(((piecelocation[currentchoose] + 20 == currentlocation && chesspiece[piecelocation[currentchoose] + 10] == 99) 
                                        || (piecelocation[currentchoose] + 16 == currentlocation && chesspiece[piecelocation[currentchoose] + 8] == 99)
                                        || (piecelocation[currentchoose] - 20 == currentlocation && chesspiece[piecelocation[currentchoose] - 10] == 99)
                                        || piecelocation[currentchoose] - 16 == currentlocation && chesspiece[piecelocation[currentchoose] - 8] == 99) && (currentlocation <= 44)){
                                    ws.W0Situation1();
                                    pickedNode = copyNode;
                                    ws.W0Situation2();
                                }
                                else{
                                    ws.W0Situation0();
                                }
                            }//elephant eat rules end
                            //setting eat rules of red guard (situation1).(wpiecesituation4) 红士吃的规则
                            if (wpiecesituation == 4) {
                                if((piecelocation[currentchoose] + 10 == currentlocation || piecelocation[currentchoose] + 8 == currentlocation
                                        || piecelocation[currentchoose] - 10 == currentlocation || piecelocation[currentchoose] - 8 == currentlocation) && 
                                        (currentlocation == 23 || currentlocation == 22 || currentlocation == 21 || 
                                        currentlocation == 12 || currentlocation == 13 || currentlocation == 14 || currentlocation == 3 || currentlocation == 4 || currentlocation == 5)){
                                    ws.W0Situation1();
                                    pickedNode = copyNode;
                                    ws.W0Situation2();
                                }
                                else
                                    ws.W0Situation0();
                            }//guard eat rules end
                            //setting eat rules of red king(situation1).(wpiecesituation5) 红帅吃的规则
                            if (wpiecesituation == 5) {
                                if((piecelocation[currentchoose] + 9 == currentlocation || piecelocation[currentchoose] - 9 == currentlocation) &&(currentlocation <= 23)){
                                    ws.W0Situation1();
                                    pickedNode = copyNode;
                                    ws.W0Situation2();
                                }
                                else if((piecelocation[currentchoose] - 1 == currentlocation || piecelocation[currentchoose] + 1 == currentlocation) && 
                                        (currentlocation == 23 || currentlocation == 22 || currentlocation == 21 || 
                                        currentlocation == 12 || currentlocation == 13 || currentlocation == 14 || currentlocation == 3 || currentlocation == 4 || currentlocation == 5)){
                                    ws.W0Situation1();
                                    pickedNode = copyNode;
                                    ws.W0Situation2();
                                }
                                else
                                    ws.W0Situation0();
                            }//king eat rules end
                            //setting eat rules of red cannon(situation1).(wpiecesituation6) 红炮吃的规则
                            if (wpiecesituation == 6) {
                                int wchariot = 99;
                                int count = 0;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward eat rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
//                                    if (wchariot == 99) {
                                    if (count == 1) {
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    } else {
                                        ws.W0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward eat rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (a != currentlocation && chesspiece[a] != 99) {
                                            count++;
                                        }
                                    }
                                    if (wchariot == 99 && count == 1) {
                                        ws.W0Situation1();
                                        pickedNode = copyNode;
                                        ws.W0Situation2();
                                    } else if (wchariot == 0 || count != 1) {
                                        ws.W0Situation0();
                                    }
                                } else {
                                    ws.W0Situation0();
                                }
                            }//cannon eat rules end
                            
                        } else if (pickedNode == chessboardNode && situation == 2) {
                            //setting regular rules of red pawn(situation2).(wpiecesituation0) 红兵的普通规则
                            if (wpiecesituation == 0) {
                                if(piecelocation[currentchoose] <= 44){
                                    if(piecelocation[currentchoose] + 9 == currentlocation){
                                         ws.W0Situation2();
                                    }
                                    else
                                        ws.W0Situation0();
                                }
                                else{
                                    if(piecelocation[currentchoose] + 9 == currentlocation){
                                         ws.W0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] + 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 8)){
                                         ws.W0Situation2();
                                    }
                                    else if((piecelocation[currentchoose] - 1 == currentlocation) && (piecelocation[currentchoose] % 9 != 0)){
                                         ws.W0Situation2();
                                    }
                                    else
                                        ws.W0Situation0();
                                }
                            }//pawn regular rules end
                            //setting regular rules of red chaRiot(situation2).(wpiecesituation1) 红车的普通规则
                            if (wpiecesituation == 1) {
                                int wchariot = 99;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of forward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of backward be blocked by chess
                                        }
                                    }
                                    if (wchariot == 99) {
                                        ws.W0Situation2();
                                    } else if (wchariot == 0) {
                                        ws.W0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of rightward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of leftward be blocked by chess
                                        }
                                    }
                                    if (wchariot == 99) {
                                        ws.W0Situation2();
                                    } else if (wchariot == 0) {
                                        ws.W0Situation0();
                                    }
                                } else {
                                    ws.W0Situation0();
                                }
                            }//chaRiot regular rules end
                            //setting regular rules of red horse(situation2).(wpiecesituation2) 红马的普通规则
                            if (wpiecesituation == 2) {
                                if (currentlocation > piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] + 7) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] + 11) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] + 17) && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] + 19) && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+9] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else {
                                        ws.W0Situation0();
                                    }
                                } else if (currentlocation < piecelocation[currentchoose]) {
                                    if (currentlocation == (piecelocation[currentchoose] - 7) && (piecelocation[currentchoose]) % 9 != 7 && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]+1] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] - 11) && (piecelocation[currentchoose]) % 9 != 0 && (piecelocation[currentchoose]) % 9 != 1) {
                                        if(chesspiece[piecelocation[currentchoose]-1] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] - 17) && (piecelocation[currentchoose]) % 9 != 8) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else if (currentlocation == (piecelocation[currentchoose] - 19) && (piecelocation[currentchoose]) % 9 != 0) {
                                        if(chesspiece[piecelocation[currentchoose]-9] != 99)
                                            ws.W0Situation0();
                                        else
                                            ws.W0Situation2();
                                    } else {
                                        ws.W0Situation0();
                                    }
                                }
                            }//horse regular rules end
                            //setting regular rules of red elephant(situation2).(wpiecesituation3) 红象的普通规则
                            if (wpiecesituation == 3) {
                                if(((piecelocation[currentchoose] + 20 == currentlocation && chesspiece[piecelocation[currentchoose] + 10] == 99) 
                                        || (piecelocation[currentchoose] + 16 == currentlocation && chesspiece[piecelocation[currentchoose] + 8] == 99)
                                        || (piecelocation[currentchoose] - 20 == currentlocation && chesspiece[piecelocation[currentchoose] - 10] == 99)
                                        || piecelocation[currentchoose] - 16 == currentlocation && chesspiece[piecelocation[currentchoose] - 8] == 99) && (currentlocation <= 44)){
                                    ws.W0Situation2();
                                }
                                else{
                                    ws.W0Situation0();
                                }
                            }//elephant regular rules end
                            //setting regular rules of red guard(situation2).(wpiecesituation4) 红士的普通规则
                            if (wpiecesituation == 4) {
                                if((piecelocation[currentchoose] + 10 == currentlocation || piecelocation[currentchoose] + 8 == currentlocation
                                        || piecelocation[currentchoose] - 10 == currentlocation || piecelocation[currentchoose] - 8 == currentlocation) && 
                                        (currentlocation == 23 || currentlocation == 22 || currentlocation == 21 || 
                                        currentlocation == 12 || currentlocation == 13 || currentlocation == 14 || currentlocation == 3 || currentlocation == 4 || currentlocation == 5)){
                                    ws.W0Situation2();
                                }
                                else
                                    ws.W0Situation0();
                            }//guard regular rules end
                            //setting regular rules of red king(situation2).(wpiecesituation5) 红帅的普通规则
                            if (wpiecesituation == 5) {
                                if((piecelocation[currentchoose] + 9 == currentlocation || piecelocation[currentchoose] - 9 == currentlocation) && (currentlocation <= 23)){
                                    ws.W0Situation2();
                                }
                                else if((piecelocation[currentchoose] - 1 == currentlocation || piecelocation[currentchoose] + 1 == currentlocation) && 
                                        (currentlocation == 23 || currentlocation == 22 || currentlocation == 21 || 
                                        currentlocation == 12 || currentlocation == 13 || currentlocation == 14 || currentlocation == 3 || currentlocation == 4 || currentlocation == 5)){
                                    ws.W0Situation2();
                                }
                                else
                                    ws.W0Situation0();
                            }//king regular rules end
                            //setting regular rules of red cannon(situation2).(wpiecesituation6) 红炮的普通规则
                            if (wpiecesituation == 6) {
                                int wchariot = 99;
                                if (piecelocation[currentchoose] % 9 == currentlocation % 9) {  //setting the forward and backward rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 9); a < currentlocation; a = a + 9) {
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of forward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 9); a > currentlocation; a = a - 9) {
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of backward be blocked by chess
                                        }
                                    }
                                    if (wchariot == 99) {
                                        ws.W0Situation2();
                                    } else if (wchariot == 0) {
                                        ws.W0Situation0();
                                    }
                                } else if (abs(currentlocation - piecelocation[currentchoose]) <= 8) {   //setting the rightward and leftward rules of red chaRiot
                                    for (int a = (piecelocation[currentchoose] + 1); a <= currentlocation; a++) {
                                        if (a % 9 == 0) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of rightward be blocked by chess
                                        }
                                    }
                                    for (int a = (piecelocation[currentchoose] - 1); a >= currentlocation; a--) {
                                        if (a % 9 == 8) {
                                            wchariot = 0;  // not on the same line
                                        }
                                        if (chesspiece[a] != 99) {
                                            wchariot = 0;  // direction of leftward be blocked by chess
                                        }
                                    }
                                    if (wchariot == 99) {
                                        ws.W0Situation2();
                                    } else if (wchariot == 0) {
                                        ws.W0Situation0();
                                    }
                                } else {
                                    ws.W0Situation0();
                                }
                            }//cannon regular rules end
                        }
                        if(move){
                            step++;
                            int startX,startY,endX,endY;
                            startX = (startLocation+1)%9;
                            endX = (endLocation+1)%9;
                            if(startX == 0)
                                startX = 9;
                            if(endX == 0)
                                endX = 9;
                            startY = 11-(startLocation/9+1);
                            endY = 11-(endLocation/9+1);
                            if(eatname != null && eatid != null){
                                db.insertTable("insert into CHESSMANUAL values("+step+",'"+id+"','"
                                            +name+"','red',"+startX+","+startY+","
                                            +endX+","+endY+",'"+eatid+"','"+eatname+"','black','"+mf.p1.getName()+"')");
                                mf.appendText("System: red "+name+" moves from "
                                            +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n");
                                mf.appendText("System: black "+eatname+" is eaten.\n");
                                eatname = null;
                                eatid = null;
                            }
                            else{
                                db.insertTable("insert into CHESSMANUAL values("+step+",'"+id+"','"
                                            +name+"','red',"+startX+","+startY+","
                                            +endX+","+endY+","+null+","+null+","+null+",'"+mf.p1.getName()+"')");
                                mf.appendText("System: red "+name+" moves from "
                                            +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n");
                            }
                            move = false;
                        }
                    }
                }
            }
            doPicking = false;
        } else {
        sceneGraph.renderScene(gl);
        }
        sceneGraph.renderScene(gl);
    }
    public char transCoding(int i){
        char c;
        c = (char)(i+64);
        return c;
    } 
    public void init() {  // prepare an OpenGL canvas suitable for capabilities of display
        //normal
        GLCapabilities capabilities = new GLCapabilities();
        canvas = new GLCanvas(capabilities);
        canvas.setPreferredSize(new Dimension(800,800));
        canvas.addGLEventListener(new ChineseChess(canvas,TR,mf));
        // prepare a frame to hold the canvas
        ICPanel = new JPanel();
        ICPanel.setBounds(250, 10, 800, 800);
        ICPanel.add(canvas);
        ChineseChessTable.isReady = false;
//        ICframe = new JFrame("Chinese Chess Battle Room");
//        ICframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ICframe.getContentPane().add(canvas);
//        ICframe.setVisible(true);
//        ICframe.pack();
        // position the frame in the middle of the screen
//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Dimension screenDimension = tk.getScreenSize();
//        Dimension frameDimension = ICframe.getSize();
//        ICframe.setLocation((screenDimension.width - frameDimension.width) / 2,
//                (screenDimension.height - frameDimension.height) / 2);
        
        
        
//        WinnerShow renderer = new WinnerShow(winner);
//        WinnerShow win1 = new WinnerShow(1);
//        WinnerShow win2 = new WinnerShow(2);
//        //fonts
//        canvasf1 = new GLCanvas(capabilities);
//        canvasf1.setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        canvasf1.addGLEventListener(win1);
//        
//        canvasf2 = new GLCanvas(capabilities);
//        canvasf2.setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        canvasf2.addGLEventListener(win2);
        
        
//        ICstart ICS = new ICstart();
//        ICS.setVisible(true);
    }

    // inner class that listens for the arrow keys for moving camera
    private class KeyActionListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_F:
                    foggy = !foggy;
                    fog = 20f;
                    break;
                case KeyEvent.VK_0:
                    sceneGraph.setCurrentCamera(cam0);
                    break;
                case KeyEvent.VK_1:
                    sceneGraph.setCurrentCamera(cam1);
                    break;
                case KeyEvent.VK_2:
                    sceneGraph.setCurrentCamera(cam2);
                    break;
                case KeyEvent.VK_3:
                    sceneGraph.setCurrentCamera(cam3);
                    break;
                case KeyEvent.VK_Q:
                    cam3.up();
                    break;
                case KeyEvent.VK_E:
                    cam3.down();
                    break;
                case KeyEvent.VK_W:
                    cam3.forward();
                    break;
                case KeyEvent.VK_S:
                    cam3.backward();
                    break;
                case KeyEvent.VK_A:
                    cam3.left();
                    break;
                case KeyEvent.VK_D:
                    cam3.right();
                    break;
                case KeyEvent.VK_K:
                    sirenNode.setEnabled(!sirenNode.isEnabled());
                    break;
                case KeyEvent.VK_MINUS:
                    if(fog > 0.2f)
                        fog -=0.2f;
                    else
                        fog = fog;
                    break;
            }
            canvas.repaint();
        }
    }
    // inner class that listens for mouse clicks
    private class MousePickListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            pickPoint = new Point(e.getX(), canvas.getHeight() - e.getY());
            doPicking = true;
        }
    }
    
    private class GLSirenLight extends GLRenderableNode {

        private GLSpotLightNode whiteLightNode;

        public GLSirenLight(String nodeName, GLSceneGraph sceneGraph) {
            super(nodeName, sceneGraph);

            whiteLightNode = new GLSpotLightNode("White Siren", sceneGraph, Color.WHITE, Color.WHITE, Color.WHITE);
            whiteLightNode.setCutoffAngle(180);
            addChild(whiteLightNode);
            Timer timer = new Timer();
            timer.schedule(new AngleChanger(), 0, 20); // start in 0ms, then every 20ms
        }
        private class AngleChanger extends TimerTask {
            public void run() {
                whiteLightNode.setLocalRotation(0, 0, 1, 0);
                canvas.repaint();
            }
        }
    }
}
