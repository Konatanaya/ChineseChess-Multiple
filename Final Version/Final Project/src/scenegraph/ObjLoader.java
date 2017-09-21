package scenegraph;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * @Author Seth Hall
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author sehall
 */
public class ObjLoader
{
    public ArrayList<PolygonalMesh> loadFromFile(String fileName)
            throws IOException
    {   //loaded in shape consisting of 1 or more polygonal mesh
        ArrayList<Point3D> verticesList = new ArrayList<Point3D>();
        ArrayList<Vector3D> normalList = new ArrayList<Vector3D>();
        ArrayList<Texel2D> texelList = new ArrayList<Texel2D>();
        ArrayList<PolygonalMesh> loadedShape
                = new ArrayList<PolygonalMesh>();
        BufferedReader br = new BufferedReader(
                new FileReader(new File(fileName)));
        String inputLine = "";
        StringTokenizer tokenizer = null;
        //current Polygonal Mesh that we can construct
        //by adding polygonal faces
        PolygonalMesh shape = new PolygonalMesh();
        //Loop until we read in a null value
        do
        {   //read first line and obtain the Obj command (either v, vn,
            //vt, f or g), values should be seperated by spaces
            inputLine = br.readLine();
            String objCommand = "";
            if(inputLine != null)
            {   tokenizer = new StringTokenizer(inputLine);
                objCommand = tokenizer.nextToken(" ");
            }
            //if command is a vertex read in the (x,y,z) co-ordinates
            //and store in the array list of vertices
            if(objCommand.equals("v"))
            {   double x = Double.parseDouble(
                        tokenizer.nextToken(" \t"));
                double y = Double.parseDouble(
                        tokenizer.nextToken(" \t"));
                double z = Double.parseDouble(
                        tokenizer.nextToken(" \t"));
                verticesList.add(new Point3D(x,y,z));
            }
            //if command is a vector normal read in the (x,y,z) values
            //and add to the array list of vectors
            else if(objCommand.equals("vn"))
            {   double x = Double.parseDouble(
                        tokenizer.nextToken(" \t"));
                double y = Double.parseDouble(
                        tokenizer.nextToken(" \t"));
                double z = Double.parseDouble(
                        tokenizer.nextToken(" \t"));
                normalList.add(new Vector3D(x,y,z));
            }
            //if command is a texel, read in the (s,t) values and add
            //to array list of texel values (might not be given)
            else if(objCommand.equals("vt"))
            {  double s = Double.parseDouble(
                       tokenizer.nextToken(" \t"));
               double t = Double.parseDouble(
                       tokenizer.nextToken(" \t"));
               texelList.add(new Texel2D(s, t));
            }
            //called if command is to add a face to the polygonal mesh
            else if(objCommand.equals("f"))
            {   boolean texturesAdded = true;
                //textures are optional, need to check this condition
                //as StringTokenizer will not determine if there is
                //nothing between the 2 slashes.
               if(inputLine.indexOf("//") >= 0)
                    texturesAdded = false;
               //create ArrayLists for points, normals and texels for
               //this face, each index of point/texel(optional)/normal
               //corresponds to the loaded in array lists,
               //these values start from index 1.
               ArrayList<Point3D> facePoints
                       = new ArrayList<Point3D>();
               ArrayList<Vector3D> faceNormals
                       = new ArrayList<Vector3D>();
               ArrayList<Texel2D> faceTexels
                       = new ArrayList<Texel2D>();
               //loop for each set of point/texel/normal values
               while(tokenizer.hasMoreTokens())
               {  //get index for the vertex
                  int vertexIndex = Integer.parseInt(
                          tokenizer.nextToken(" /\t"));
                  facePoints.add(verticesList.get(vertexIndex-1));
                  //get index value for the texel (if included in face)
                  if(texturesAdded)
                  {  int textureIndex = Integer.parseInt(
                             tokenizer.nextToken(" /\t"));
                     faceTexels.add(texelList.get(textureIndex-1));
                  }
                  int normalIndex = Integer.parseInt(
                          tokenizer.nextToken(" /\t"));
                  faceNormals.add(normalList.get(normalIndex-1));
               }
               //add Vector3D[], Point3D[], Texel2D[] (if any)
               //to create polygonal face
               if(faceTexels.size() > 0)
               {    shape.addFace(facePoints.toArray(
                       new Point3D[facePoints.size()]),
                       faceNormals.toArray(
                       new Vector3D[faceNormals.size()]),
                       faceTexels.toArray(
                       new Texel2D[faceTexels.size()]));
               }else
               {    shape.addFace(facePoints.toArray(
                       new Point3D[facePoints.size()]),
                       faceNormals.toArray(
                       new Vector3D[faceNormals.size()]));
               }
            }
            //create a new Polygonal mesh.
            else if(objCommand.equals("g"))
            {   //get the optional name for this mesh
                String meshName = tokenizer.nextToken(" \t");
//                System.out.println("NEW OBJECT BEGIN "+meshName);
                //need to check this condition to handle if the object
                //is the first shape in the list to avoid adding a
                //emtpy polygonal mesh.
                if(verticesList.size() > 0 && normalList.size() > 0)
                {  //add previous constructed mesh to the loaded object
                   //and create a new polygonal mesh to construct.
                   loadedShape.add(shape);
                }
                shape = new PolygonalMesh(meshName);
            }
         }while(inputLine != null);
         //add previous constructed mesh to loaded object
         loadedShape.add(shape);
         //clear the Lists of points, texels and vectors
         verticesList.clear();
         normalList.clear();
         texelList.clear();
         return loadedShape;
    }
}
