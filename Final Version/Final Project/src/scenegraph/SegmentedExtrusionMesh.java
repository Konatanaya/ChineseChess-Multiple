package scenegraph;

/**
   A utility class that represents a polygonal mesh for a segmented
   extrusion of a polygon along a curve
   @author Andrew Ensor
*/
import scenegraph.PolygonalMesh;
import java.awt.geom.Point2D;

public class SegmentedExtrusionMesh extends PolygonalMesh
{
   private Point3D[] spine;
   private Point2D[] polygon;
   
   // constructs a mesh from the points given in polygon in
   // anticlockwise order using spine (must have at least 4 points)
   public SegmentedExtrusionMesh(Point2D[] polygon, Point3D[] spine)
   {  super();
      this.polygon = polygon;
      this.spine = spine;
      // determine direction of spine and direction of bend in curve
      Vector3D[] directionVel = new Vector3D[spine.length];
      Vector3D[] directionAcc = new Vector3D[spine.length];
      for (int i=1; i<spine.length-1; i++)
      {  directionVel[i] = spine[i+1].subtract(spine[i-1]);
         directionAcc[i] = spine[i+1].subtract(spine[i]).add
            (spine[i-1].subtract(spine[i]));
      }
      // handle the end vectors separately using extrapolation
      directionVel[0] = directionVel[1].subtract
         (directionVel[2].subtract(directionVel[1]));
      directionVel[spine.length-1] = directionVel[spine.length-2].add
         (directionVel[spine.length-2].subtract
         (directionVel[spine.length-3]));
      directionAcc[0] = directionAcc[1].subtract
         (directionAcc[2].subtract(directionAcc[1])).normalized();
      directionAcc[spine.length-1] = directionAcc[spine.length-2].add
         (directionAcc[spine.length-2].subtract
         (directionAcc[spine.length-3]));
      // determine the points on each segment
      Point3D[][] segmentVertices
         = new Point3D[spine.length][polygon.length];
      for (int i=0; i<spine.length; i++)
      {  // get local coordinate system on spine using normal as
         // x-axis, binormal as y-axis, tangent as z-axis
         Vector3D tangent = directionVel[i].normalized();
         Vector3D binormal = directionVel[i].crossProduct
            (directionAcc[i]).normalized();
         Vector3D normal = binormal.crossProduct(tangent);
         Point3D oPrime = spine[i];
         for (int j=0; j<polygon.length; j++)
         {  // get coordinates in local coordinate system on spine
            double aPrime = polygon[j].getX();
            double bPrime = polygon[j].getY();
            // transform back to x,y,z-axis coordinate system
            double a = normal.getX()*aPrime + binormal.getX()*bPrime
               + oPrime.getX();
            double b = normal.getY()*aPrime + binormal.getY()*bPrime
               + oPrime.getY();
            double c = normal.getZ()*aPrime + binormal.getZ()*bPrime
               + oPrime.getZ();
            segmentVertices[i][j] = new Point3D(a, b, c);
         }
      }
      // add the faces that join adjacent segments
      for (int i=0; i<spine.length-1; i++)
      {  for (int j=0; j<polygon.length; j++)
         {  int nextJ = (j+1)%polygon.length;
            Point3D[] vertices = {segmentVertices[i][j],
               segmentVertices[i][nextJ], segmentVertices[i+1][nextJ],
               segmentVertices[i+1][j]};
            addFace(vertices, getAverageNormal(vertices));
         }
      }
   }
   
   // helper method that calculates the average normal for a face
   private Vector3D getAverageNormal(Point3D[] vertices)
   {  double x = 0.0, y = 0.0, z = 0.0;
      for (int i=0; i<vertices.length; i++)
      {  int nextIndex = (i+1)%vertices.length;
         x += (vertices[i].getY()-vertices[nextIndex].getY())
            * (vertices[i].getZ()+vertices[nextIndex].getZ());
         y += (vertices[i].getZ()-vertices[nextIndex].getZ())
            * (vertices[i].getX()+vertices[nextIndex].getX());
         z += (vertices[i].getX()-vertices[nextIndex].getX())
            * (vertices[i].getY()+vertices[nextIndex].getY());
      }
      return new Vector3D(x, y, z).normalized();
   }
}
