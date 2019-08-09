package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.system.JmeSystem;
import com.jme3.system.JmeSystemDelegate;

import java.net.URL;

public class ModelLoader3D {

    private static final String FORMAT = ".gltf";
    private IO input;

    public ModelLoader3D(AssetManager assetManager){
        input = new IO(assetManager);
    }

    public Spatial getAsset(String asset){
        return input.loadAsset(asset+"."+FORMAT);
    }
}
