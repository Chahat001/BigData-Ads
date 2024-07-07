package com.client.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class LogUploader {

    @Autowired
    LogGeneratorService logGeneratorService;

    @Autowired
    Environment environment;

    public void uploadLogs()  {
        long durationInMilliSec = Long.parseLong(environment.getProperty("logUploader.durationInMilliSec"));
        long pauseTimeInMills = Long.parseLong(environment.getProperty("logUploader.pauseTimeInMills"));
        String restEndPoint = environment.getProperty("logUploader.restEndPoint");
        int numLogsPerBatch =  Integer.parseInt(environment.getProperty("logUploader.numLogsPerBatch"));
        try{
            URL url = new URL(restEndPoint);

            long startTimeStamp = System.currentTimeMillis();
            while(System.currentTimeMillis() < startTimeStamp+durationInMilliSec){
                Thread.sleep(pauseTimeInMills);
                /*Create a new http connection for each request as server closes each connection after response*/

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                JSONArray logs = logGeneratorService.createNJsonLogs(numLogsPerBatch);
                byte[] payload = logs.toString().getBytes("utf-8");
                os.write(payload, 0, payload.length);
                os.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while((responseLine = br.readLine()) != null){
                    response.append(responseLine);
                }
                System.out.println(response);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
