package com.client.services;

import com.client.enums.AdInterActions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
@Service
public class LogGeneratorService {

    public JSONArray createNJsonLogs(int numLogs){
        /*
        By default, build tools like Maven, Gradle, or common Java practice will copy all files from src/main/resources
         to the root of target/classes or build/classes.
         */
        List<String> countries = csvToArray("SampleCountries.csv");
        List<String> ipAddresses = csvToArray("SampleIPAddress.csv");
        AdInterActions[] adInterActions = AdInterActions.values();

        JSONArray jsonArray = new JSONArray();

        for(int i = 1; i <= numLogs; i++){
            JSONObject jo = new JSONObject();
            jo.put("EventType",  adInterActions[((int)(Math.random()*100))%adInterActions.length]);
            jo.put("TimeStamp", System.currentTimeMillis());
            jo.put("UserId", (int)(Math.random()*100));
            jo.put("AdId", (int)(Math.random()*100));
            jo.put("ip", ipAddresses.get(((int)(Math.random()*100))% ipAddresses.size()));
            jo.put("country", countries.get(((int)(Math.random()*100))% countries.size()));
            jsonArray.put(jo);
        }

        return jsonArray;
    }

    private List<String> csvToArray(String csvFileName){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(csvFileName);
        List<String> content = new LinkedList<>();

        try{
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line;
            boolean firstLine = true;
            while((line = bufferedReader.readLine()) != null){
                // Skip the header
                if(!firstLine){
                    content.add(line.split(",")[0]);
                }
                firstLine = false;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }
}
