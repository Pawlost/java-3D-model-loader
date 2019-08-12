package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.gltf.GltfModelKey;
import com.jme3.texture.Texture;

import java.io.File;
import java.util.Objects;

public class IO {
    public AssetManager assetManager;
    public IO(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Spatial loadAsset(String asset) {
        File file = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("models/"+asset)).getFile());
        assetManager.registerLocator(file.getParent(), FileLocator.class);
        GltfModelKey key = new GltfModelKey(asset);
        return assetManager.loadModel(key);
    }

    public Texture loadTexture(String texture){
        File file = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("textures/"+texture)).getFile());
        assetManager.registerLocator(file.getParent(), FileLocator.class);
        return assetManager.loadTexture(texture);
    }
}
