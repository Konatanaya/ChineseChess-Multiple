package scenegraph;

/**
   A utility class that represents a polygonal mesh for a prism
   that is the extrusion of a polygon by a distance d
   @author Andrew Ensor
*/
import java.awt.geom.Point2D;

public class PrismMesh extends PolygonalMesh
{
   private static final Vector3D NEG_Z = new Vector3D(0, 0, -1);
   private static final Vector3D POS_Z = new Vector3D(0, 0, 1);
   
   // constructs a mesh from the points given in polygon in 
   // anticlockwise order
   public PrismMesh(Point2D[] polygon, double d)
   {  super();
      Point3D[] baseVertices = new Point3D[polygon.length];
      Point3D[] extrudedVertices = new Point3D[polygon.length];
      for (int i=0; i<polygon.length; i++)
      {  double a = polygon[i].getX();
         double b = polygon[i].getY();
         baseVertices[i] = new Point3D(a, b, 0.0);
         extrudedVertices[i] = new Point3D(a, b, d);
      }
      // add the front and back faces
      if (d >= 0)
      {  addFace(baseVertices, NEG_Z); // actually given clockwise
         addFace(extrudedVertices, POS_Z);
      }
      else
      {  addFace(baseVertices, POS_Z);
         addFace(extrudedVertices, NEG_Z); // actually given clockwise
      }
      // add the side faces
      for (int i=0; i<polygon.length; i++)
      {  int nextIndex = (i+1)%polygon.length;
         Vector3D baseVector
            = baseVertices[nextIndex].subtract(baseVertices[i]);
         Vector3D normal =baseVector.crossProduct(POS_Z).normalized();
         Point3D[] face;
         if (d >= 0)
            face = new Point3D[]{baseVertices[i],
               baseVertices[nextIndex], extrudedVertices[nextIndex],
               extrudedVertices[i]};
         else
            face = new Point3D[]{baseVertices[i], extrudedVertices[i],
               extrudedVertices[nextIndex], baseVertices[nextIndex]};         
         addFace(face, normal);
      }
   }
}
