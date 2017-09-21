package ChineseChess3D;

/**
 *
 *
 * @author hackless
 */

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

class WinnerShow implements GLEventListener {
    
    //font set
    private int checker = 0;
    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
    

    public WinnerShow(int winner) {
        checker = winner;
    }
    
    
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        //init font
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        //font display
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(-1.0f, 0.0f, -10.0f);

        gl.glColor3f(255.0f,255.0f,255.0f);
        gl.glRasterPos3f(-1f, -0f,-0f);
        if(checker == 1)
            glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "White Win!");
        if(checker == 2)
            glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Black Win!");
        
    }

    public void reshape(GLAutoDrawable drawable, int xstart, int ystart, int width, int height) {
        GL gl = drawable.getGL();

        height = (height == 0) ? 1 : height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, (float) width / height, .1, 100);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}