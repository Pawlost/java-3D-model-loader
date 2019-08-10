package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

public class ModelLoader3D {

    private static final String MODEL_FORMAT = ".gltf";
    private static final String TEXTURE_FORMAT = ".png";
    private static final String TEXTURE_MARK =  "-texture-";
    private IO input;
    private Material material;

    public ModelLoader3D(AssetManager assetManager){
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        input = new IO(assetManager);
    }

    public Spatial getAsset(String assetName, int id){
        Spatial asset = input.loadAsset(assetName + MODEL_FORMAT);
        Texture texture = input.loadTexture(assetName+TEXTURE_MARK+id+TEXTURE_FORMAT);
        material.setTexture("ColorMap", texture);
        asset.setMaterial(material);
        asset.updateGeometricState();
        return asset;
    }

    public Material getMaterial() {
        return material;
    }
}
