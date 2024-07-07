package com.logwatcher.controller;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LogController {

    @PostMapping("/logs")
    public @ResponseBody Map<String, String> getLogs(@RequestBody String requestBody){
        System.out.println(requestBody);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("Status", "Success");
        return responseBody;
    }
}
