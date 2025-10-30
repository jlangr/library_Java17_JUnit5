package com.langrsoft.controller;

import com.langrsoft.external.MaterialType;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @GetMapping(value = "/{materialType}")
    public MaterialType get(@PathVariable("materialType") String materialType, HttpServletResponse response) {
        try {
            return MaterialType.valueOf(materialType);
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND_404);
            return null;
        }
    }
}
