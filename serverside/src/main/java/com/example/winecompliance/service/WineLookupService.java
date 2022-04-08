package com.example.winecompliance.service;

import com.example.winecompliance.model.Wine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WineLookupService {

    private static Map<String, Wine> wineMap = new HashMap<>();

    public void createWine(Wine wine) {
        wineMap.put(wine.lotCode, wine);
    }

    public Wine readWine(String lotCode) {
        return wineMap.get(lotCode);
    }

    public List<Wine> searchWine(String searchInput) {
        String escapedFragment = Pattern.quote(searchInput);
        Pattern pattern = Pattern.compile(escapedFragment, Pattern.CASE_INSENSITIVE);

        return wineMap.entrySet().parallelStream()
                .filter(wineEntry -> (wineEntry.getKey() != null && pattern.matcher(wineEntry.getKey()).find())
                        || (wineEntry.getValue().description != null && pattern.matcher(wineEntry.getValue().description).find()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}