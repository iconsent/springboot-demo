package online.iconsent.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DemoController {

    @Value("${spring.application.name}")
    String appName;

    @Value("${application.version}")
    String appVersion;

    @Value("${custom.property}")
    String customProperty;

    @RequestMapping(
            path = "/properties"
    )
    ResponseEntity<Map<String, String>> getProperties(){
        Map<String,String> response = new HashMap<>();
        response.put("spring.application.name",appName);
        response.put("application.version",appVersion);
        response.put("custom.property",customProperty);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
