package com.example.winecompliance.controller;

import com.example.winecompliance.model.Wine;
import com.example.winecompliance.model.WineBreakdown;
import com.example.winecompliance.model.WineComponent;
import com.example.winecompliance.service.WineLookupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

@RestController
@RequestMapping("/api/breakdown")
public class WineController {

    @Autowired
    WineLookupService wineService;

    @Autowired
    ObjectMapper jacksonObjectMapper;

    @GetMapping("/wine")
    public String getWineSearch(@RequestParam(name="searchInput") String searchInput) throws JsonProcessingException {
        return jacksonObjectMapper.writeValueAsString(wineService.searchWine(searchInput));
    }

    @GetMapping("/wine/{lotCode}")
    public String getWineBreakdown(@PathVariable(name="lotCode") String lotCode) throws JsonProcessingException {
        return jacksonObjectMapper.writeValueAsString(wineService.readWine(lotCode));
    }

    private WineBreakdown getBreakdownByClassifier(String lotCode, String classificationName, Function<WineComponent,?> classifier) {
        Wine wine = wineService.readWine(lotCode);
        if (wine != null) {
            List<WineBreakdown.Percentage> components = wine.components.stream()
                    .collect(Collectors.groupingBy(classifier,
                            Collectors.summingDouble(foo -> foo.percentage)))
                    .entrySet()
                    .stream()
                    .sorted(reverseOrder(Map.Entry.comparingByValue()))
                    .map(entry -> new WineBreakdown.Percentage(entry.getKey().toString(), entry.getValue()))
                    .collect(Collectors.toList());
            return new WineBreakdown(classificationName, components);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "wine data not found"
            );
        }
    }

    @GetMapping("/year/{lotCode}")
    public String getYearBreakdown(@PathVariable(name = "lotCode") String lotCode) throws JsonProcessingException {
        return jacksonObjectMapper.writeValueAsString(
                getBreakdownByClassifier(lotCode,
                        "year",
                        wineComponent -> wineComponent.year));
    }

    @GetMapping("/variety/{lotCode}")
    public String getVarietyBreakdown(@PathVariable(name="lotCode") String lotCode) throws JsonProcessingException {
        return jacksonObjectMapper.writeValueAsString(
                getBreakdownByClassifier(lotCode,
                        "variety",
                        wineComponent -> wineComponent.variety)
        );
    }

    @GetMapping("/region/{lotCode}")
    public String getRegionBreakdown(@PathVariable(name="lotCode") String lotCode) throws JsonProcessingException {
        return jacksonObjectMapper.writeValueAsString(
                getBreakdownByClassifier(lotCode,
                        "region",
                        wineComponent -> wineComponent.region)
        );
    }

    @GetMapping("/year-variety/{lotCode}")
    public String getYearVarietyBreakdown(@PathVariable(name = "lotCode") String lotCode) throws JsonProcessingException {
        return jacksonObjectMapper.writeValueAsString(
                getBreakdownByClassifier(lotCode,
                        "year-variety",
                        WineComponent.YearVarietyKey::new)
        );
    }

}