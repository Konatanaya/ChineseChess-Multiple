package scenegraph;

/**
   A utility class that represents a Coons patch for 0<=a<=1 and
   0<=b<=1 tweening between four specified boundaries to generate c
   @author Andrew Ensor (modified for JSR-231)
*/
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;

public class CoonsPatch implements GLRenderable
{
   private Point3D[][] vertices;
   private Vector3D[][] normals;

   // constructs a Coons patch from the boundary parameters
   // requires bottom.length=top.length and left.length=right.length
   public CoonsPatch(double[] bottom, double[] top, double[] left,
      double[] right)
   {  if (bottom.length != top.length ||
         left.length != right.length)
         throw new IllegalArgumentException("not rectangular");
      if (bottom[0]!=left[0] || bottom[bottom.length-1]!=right[0]
         || top[0]!=left[left.length-1]
         || top[top.length-1]!=right[right.length-1])
         throw new IllegalArgumentException("not matching boundary");
      vertices = new Point3D[bottom.length][left.length];
      for (int i=0; i<bottom.length; i++)
      {  double a = 1.0*i/(bottom.length-1);
         for (int j=0; j<left.length; j++)
         {  double b = 1.0*j/(left.length-1);
            double verticalTween = (1-b)*bottom[i] + b*top[i];
            double horizontalTween = (1-a)*left[j] + a*right[j];
            double bottomTween = (1-a)*bottom[0]
               + a*bottom[bottom.length-1];
            double topTween = (1-a)*top[0] +  a*top[top.length-1];
            double bilinearTween = (1-b)*bottomTween + b*topTween;
            double c = verticalTween+horizontalTween-bilinearTween;
            vertices[i][j] = new Point3D(a, b, c);
         }
      }
      // calculate the normals for each vertex
      normals = new Vector3D[bottom.length][left.length];
      List<Point3D> adjacentVertices = new ArrayList<Point3D>();
      for (int i=0; i<bottom.length; i++)
      {  for (int j=0; j<left.length; j++)
         {  // add the adjacent vertices in anticlockwise order
            if (i<bottom.length-1)
               adjacentVertices.add(vertices[i+1][j]);
            else
               adjacentVertices.add(vertices[i][j].add
                  (vertices[i][j].subtract(vertices[i-1][j])));
            if (j<left.length-1)
               adjacentVertices.add(vertices[i][j+1]);
            else
               adjacentVertices.add(vertices[i][j].add
                  (vertices[i][j].subtract(vertices[i][j-1])));
            if (i>0)
               adjacentVertices.add(vertices[i-1][j]);
            else
               adjacentVertices.add(vertices[i][j].add
                  (vertices[i][j].subtract(vertices[i+1][j])));
            if (j>0)
               adjacentVertices.add(vertices[i][j-1]);
            else
               adjacentVertices.add(vertices[i][j].add
                  (vertices[i][j].subtract(vertices[i][j+1])));
            normals[i][j] = getAverageNormal(adjacentVertices);
            adjacentVertices.clear();
         }
      }
   }

   public void drawObject(GL gl)
   {  // draw the patch as a collection of quad strips
      for (int i=0; i<vertices.length-1; i++)
      {  gl.glBegin(GL.GL_QUAD_STRIP);
         for (int j=0; j<vertices[i].length; j++)
         {  gl.glNormal3d(normals[i][j].getX(),
               normals[i][j].getY(), normals[i][j].getZ());
            gl.glVertex3d(vertices[i][j].getX(),
               vertices[i][j].getY(), vertices[i][j].getZ());
            gl.glNormal3d(normals[i+1][j].getX(),
               normals[i+1][j].getY(), normals[i+1][j].getZ());
            gl.glVertex3d(vertices[i+1][j].getX(),
               vertices[i+1][j].getY(),
               vertices[i+1][j].getZ());
         }
         gl.glEnd();
      }
   }

   // helper method that calculates the average normal for vertices
   private Vector3D getAverageNormal(List<Point3D> vertexList)
   {  double x = 0.0, y = 0.0, z = 0.0;
      for (int i=0; i<vertexList.size(); i++)
      {  Point3D thisVertex = vertexList.get(i);
         Point3D nextVertex = vertexList.get((i+1)%vertexList.size());
         x += (thisVertex.getY()-nextVertex.getY())
            * (thisVertex.getZ()+nextVertex.getZ());
         y += (thisVertex.getZ()-nextVertex.getZ())
            * (thisVertex.getX()+nextVertex.getX());
         z += (thisVertex.getX()-nextVertex.getX())
            * (thisVertex.getY()+nextVertex.getY());
      }
      return new Vector3D(x, y, z).normalized();
   }

}
