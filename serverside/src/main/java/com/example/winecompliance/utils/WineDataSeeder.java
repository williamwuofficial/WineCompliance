package com.example.winecompliance.utils;

import com.example.winecompliance.model.Wine;
import com.example.winecompliance.service.WineLookupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class WineDataSeeder implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ObjectMapper jacksonObjectMapper;

    @Autowired
    WineLookupService wineService;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        try {
            loadWineFileData("classpath:data" + File.separator + "11YVCHAR001.json");
            loadWineFileData("classpath:data" + File.separator + "11YVCHAR002.json");
            loadWineFileData("classpath:data" + File.separator + "15MPPN002-VK.json");
        } catch (IOException e ) {
            e.printStackTrace();
        }

        return;
    }

    private void loadWineFileData(String fileName) throws IOException {
        InputStream stream = resourceLoader.getResource(fileName).getInputStream();
        Wine wine = jacksonObjectMapper.readValue(stream, Wine.class);
        wineService.createWine(wine);
    }

}