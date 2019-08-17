package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.gltf.GltfModelKey;
import com.jme3.texture.Texture;


class IO {
    private AssetManager assetManager;
    IO(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    Spatial loadAsset(String asset) {
        assetManager.registerLocator("3D-model-loader/src/main/resources/models/", FileLocator.class);
        GltfModelKey key = new GltfModelKey(asset);
        return assetManager.loadModel(key);
    }

    Texture loadTexture(String texture){
        assetManager.registerLocator("3D-model-loader/src/main/resources/textures/", FileLocator.class);
        TextureKey tk = new TextureKey(texture, false);
        return assetManager.loadTexture(tk);
    }
}
