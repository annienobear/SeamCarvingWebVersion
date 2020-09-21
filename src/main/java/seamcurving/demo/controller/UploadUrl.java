package seamcurving.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController
public class UploadUrl {
    public Scanner scanner = new Scanner(System.in);

    @PostMapping(path = "/api/upload", consumes = "application/json")
    public String sendUrl(@RequestBody String url) {
        return url;
    }

}
