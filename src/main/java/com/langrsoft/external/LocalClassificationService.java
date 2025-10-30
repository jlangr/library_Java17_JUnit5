package com.langrsoft.external;

import java.util.HashMap;
import java.util.Map;

public class LocalClassificationService implements ClassificationApi {
    private final Map<String, Material> materials = new HashMap<>();

    @Override
    public Material retrieveMaterial(String sourceId) {
        return materials.get(sourceId);
    }

    public void addBook(Material material) {
        materials.put(material.getSourceId(), material);
    }

    @Override
    public String toString() {
        return materials.toString();
    }
}
