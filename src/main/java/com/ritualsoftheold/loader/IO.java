package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.gltf.GltfModelKey;
import com.jme3.texture.Texture;


public class IO {
    public AssetManager assetManager;
    public IO(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Spatial loadAsset(String asset) {
        assetManager.registerLocator("3D-model-loader/src/main/resources/models", FileLocator.class);
        GltfModelKey key = new GltfModelKey(asset);
        return assetManager.loadModel(key);
    }

    public Texture loadTexture(String texture){
        assetManager.registerLocator("3D-model-loader/src/main/resources/textures", FileLocator.class);
        return assetManager.loadTexture(texture);
    }
}
