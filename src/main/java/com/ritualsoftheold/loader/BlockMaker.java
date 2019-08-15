package com.ritualsoftheold.loader;

import com.jme3.math.FastMath;
import com.jme3.scene.*;
import com.jme3.util.TempVars;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.nio.FloatBuffer;

public class BlockMaker {
    private float defaultDistanceX;
    private float defaultDistanceY;
    private float defaultDistanceZ;

    private MultiKeyMap<Integer, byte[][][]> blocks;

    public BlockMaker(Spatial spatial){

        blocks = new MultiKeyMap<>();
        while (spatial instanceof Node) {
            spatial = ((Node) spatial).getChild(0);
        }

        Geometry geometry = (Geometry) spatial;
        Mesh mesh = geometry.getMesh();

        FloatBuffer points = mesh.getFloatBuffer(VertexBuffer.Type.Position);

        if (points != null) {

            points.rewind();
            if (points.remaining() > 2) // we need at least a 3 float vector
            {

                TempVars vars = TempVars.get();

                float[] tmpArray = vars.skinPositions;

                float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, minZ = Float.POSITIVE_INFINITY;
                float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;

                int iterations = (int) FastMath.ceil(points.limit() / ((float) tmpArray.length));
                for (int i = iterations - 1; i >= 0; i--) {
                    int bufLength = Math.min(tmpArray.length, points.remaining());
                    points.get(tmpArray, 0, bufLength);

                    for (int j = 0; j < bufLength; j += 3) {
                        vars.vect1.x = tmpArray[j];
                        vars.vect1.y = tmpArray[j + 1];
                        vars.vect1.z = tmpArray[j + 2];

                        if (vars.vect1.x < minX) {
                            minX = vars.vect1.x;
                        }
                        if (vars.vect1.x > maxX) {
                            maxX = vars.vect1.x;
                        }

                        if (vars.vect1.y < minY) {
                            minY = vars.vect1.y;
                        }
                        if (vars.vect1.y > maxY) {
                            maxY = vars.vect1.y;
                        }

                        if (vars.vect1.z < minZ) {
                            minZ = vars.vect1.z;
                        }
                        if (vars.vect1.z > maxZ) {
                            maxZ = vars.vect1.z;
                        }
                    }
                }

                vars.release();

                maxX = Math.round(maxX / 0.25f);
                minX = Math.round(minX / 0.25f);

                maxY = Math.round(maxY / 0.25f) ;
                minY = Math.round(minY / 0.25f);

                maxZ = Math.round(maxZ / 0.25f);
                minZ = Math.round(minZ / 0.25f);

                defaultDistanceX = Math.abs(maxZ + minZ);
                defaultDistanceY = Math.abs(maxY + minY);
                defaultDistanceZ = Math.abs(maxX + minX);

                float distanceX = defaultDistanceX;
                float distanceY = defaultDistanceY;
                float distanceZ = defaultDistanceZ;

                int posX = 0;
                int posY = 0;
                int posZ = 0;

                do{
                    int sizeX = (int)Math.max(Math.min(distanceX, 64), 1);
                    int sizeY = (int)Math.max(Math.min(distanceX, 64), 1);
                    int sizeZ = (int)Math.max(Math.min(distanceX, 64), 1);

                    distanceX -= sizeX;
                    distanceY -= sizeY;
                    distanceZ -= sizeZ;

                    blocks.put(posX, posY, posZ, new byte[sizeZ][sizeY][sizeX]);

                    if(distanceX > 64){
                        posX ++;
                    }

                    if(distanceY > 64){
                        posX ++;
                    }

                    if(distanceZ > 64){
                        posX ++;
                    }

                } while (distanceX > 64 || distanceY > 64 || distanceZ > 64);
            }
        }
    }

    public MultiKeyMap<Integer, byte[][][]> getBlocks() {
        return blocks;
    }

    public float getDefaultDistanceX() {
        return defaultDistanceX;
    }

    public float getDefaultDistanceY() {
        return defaultDistanceY;
    }

    public float getDefaultDistanceZ() {
        return defaultDistanceZ;
    }
}