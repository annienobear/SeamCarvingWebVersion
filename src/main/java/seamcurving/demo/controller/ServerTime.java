package seamcurving.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class ServerTime {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, welcome to SeamCarving project\n";
    }
}
