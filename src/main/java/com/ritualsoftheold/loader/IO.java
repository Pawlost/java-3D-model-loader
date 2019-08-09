package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

import java.io.IOException;

public class IO {
    public AssetManager assetManager;
    public IO(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Spatial loadAsset(String file){
        return assetManager.loadModel(file);
    }
}
