package com.ritualsoftheold.loader;

import com.jme3.math.FastMath;
import com.jme3.scene.*;
import com.jme3.util.TempVars;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class BlockMaker {
    private float defaultDistanceX;
    private float defaultDistanceY;
    private float defaultDistanceZ;

    public BlockMaker(Spatial spatial){

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

                defaultDistanceZ = Math.max(Math.abs(maxZ - minZ), 1);
                defaultDistanceY = Math.max(Math.abs(maxY - minY), 1);
                defaultDistanceX = Math.max(Math.abs(maxX - minX), 1);
            }
        }
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