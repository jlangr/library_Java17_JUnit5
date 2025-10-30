package com.langrsoft.controller;

import com.langrsoft.domain.ClassificationApiFactory;
import com.langrsoft.external.LocalClassificationService;
import com.langrsoft.external.Material;
import com.langrsoft.external.MaterialType;
import com.langrsoft.service.library.LibraryData;
import com.langrsoft.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class LibraryController {
    @Autowired
    LibraryData libraryData;

    @PostMapping(value = "/clear")
    public void clear() {
        libraryData.deleteBranchesHoldingsPatrons();
    }

    @PostMapping(value = "/use_local_classification")
    public void useLocalClassificationService() {
        ClassificationApiFactory.setService(new LocalClassificationService());
    }

    @PostMapping(value = "/reset_current_date")
    public void resetCurrentDate() {
        DateUtil.resetClock();
    }

    @PostMapping(value = "/current_date")
    public void setCurrentDate(@RequestBody Date date) {
        DateUtil.fixClockAt(date);
    }

    @GetMapping(value = "/current_date")
    public Date getCurrentDate() {
        return DateUtil.getCurrentDate();
    }

    @PostMapping(value = "/materials")
    public void addMaterial(@RequestBody MaterialRequest materialRequest) {
        var service = (LocalClassificationService) ClassificationApiFactory.getService();
        service.addBook(createMaterial(materialRequest));
    }

    @GetMapping(value = "/retrieveMaterial/{classification}")
    public Material retrieveMaterial(@PathVariable String classification) {
        var service = (LocalClassificationService) ClassificationApiFactory.getService();
        return service.retrieveMaterial(classification);
    }

    private Material createMaterial(MaterialRequest materialRequest) {
        var material = new Material();
        material.setSourceId(materialRequest.getSourceId());
        material.setClassification(materialRequest.getClassification());
        material.setTitle(materialRequest.getTitle());
        material.setYear(materialRequest.getYear());
        material.setFormat(MaterialType.valueOf(materialRequest.getFormat()));
        material.setAuthor(materialRequest.getAuthor());
        return material;
    }
}
