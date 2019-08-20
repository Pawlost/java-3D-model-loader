package com.ritualsoftheold.loader;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.gltf.GltfModelKey;

import com.jme3.texture.Texture;

import java.awt.*;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class AssetIO {
    private AssetManager assetManager;

    AssetIO(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    Spatial loadAsset(String asset) {
        assetManager.registerLocator("3D-model-loader/src/main/resources/models/", FileLocator.class);
        GltfModelKey key = new GltfModelKey(asset);
        return assetManager.loadModel(key);
    }

    Texture loadTexture(String texture) {
        assetManager.registerLocator("3D-model-loader/src/main/resources/textures/", FileLocator.class);
        TextureKey tk = new TextureKey(texture, false);
        return assetManager.loadTexture(tk);
    }

    public static ByteBuffer resizeImage(ByteBuffer jmeImage, int width, int height, int prevWidth, int prevHeight) {
        BufferedImage image = new BufferedImage(prevWidth, prevHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        jmeImage.position(0);

        for (int y = 0; y < prevHeight; y++) {
            for (int x = 0; x < prevWidth; x++) {
                int pixel = jmeImage.asIntBuffer().get(x + (y * prevWidth));
                int b = (pixel >> 24) & 0xff;

                int g = (pixel >> 16) & 0xff;

                int r = (pixel >> 8) & 0xff;

                int a = pixel & 0xff;

                image.setRGB(x, y, new Color(r, g, b, a).getRGB());
            }
        }


        Image newImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(newImage, 0, 0, width, height, null);

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        buffer.position(0);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width * 4; x += 4) {
                int pixel = bufferedImage.getRGB(x / 4, y);

                byte a = (byte) ((pixel >> 24) & 0xff);
                buffer.position(x + (y * width * 4));
                buffer.put(a);

                byte r = (byte) (pixel & 0xff);
                buffer.position(x + 1 + (y * width * 4));
                buffer.put(r);

                byte g = (byte) ((pixel >> 8) & 0xff);
                buffer.position(x + 2 + (y * width * 4));
                buffer.put(g);

                byte b = (byte) ((pixel >> 16) & 0xff);
                buffer.position(x + 3 + (y * width * 4));
                buffer.put(b);
            }
        }
        buffer.order(ByteOrder.nativeOrder());
        return buffer;
    }
}