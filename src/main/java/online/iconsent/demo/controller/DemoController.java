package online.iconsent.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DemoController {
    Logger LOG = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${spring.application.name}")
    String appName;

    @Value("${application.version}")
    String appVersion;

    @Value("${custom.property}")
    String customProperty;

    @Value("${build.date}")
    String buildDate;

    @CrossOrigin
    @RequestMapping(
            path = "/properties"
    )
    ResponseEntity<Map<String, String>> getProperties(@RequestHeader Map<String, String> headers) throws SocketException, UnknownHostException {
        Map<String,String> response = new HashMap<>();
        response.put("Application",appName);
        response.put("Version",appVersion);
        response.put("Build-Date",buildDate);
        response.put("Custom property",customProperty);
        response.put("Backend IP Addresses",getIPAddresses());
        response.put("Hostname",InetAddress.getLocalHost().getHostName());
        response.put("Time",new Date().toString());
        response.forEach((key, value) -> LOG.info(key+":"+value));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public String getIPAddresses() throws SocketException {
        List<InetAddress> addrList = new ArrayList<InetAddress>();
        for(Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces(); eni.hasMoreElements(); ) {
            final NetworkInterface ifc = eni.nextElement();
            if(ifc.isUp()) {
                for(Enumeration<InetAddress> ena = ifc.getInetAddresses(); ena.hasMoreElements(); ) {
                    InetAddress ia = ena.nextElement();
                    if(!ia.isLoopbackAddress() && ia instanceof Inet4Address){
                        addrList.add(ia);
                    }
                }
            }
        }
        return addrList.stream().map(Object::toString).collect(Collectors.joining(","));
    }

}
