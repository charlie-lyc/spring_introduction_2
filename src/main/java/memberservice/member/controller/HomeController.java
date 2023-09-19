package memberservice.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 외부로부터 요청이 들어오면 spring container 는 먼저 controller 에서 매핑된 route 를  찾고,
     * 먄약 없다면 그 다음에 static 에서 index.html 을 우선적으로 찾도록 설계되어 있음.
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }



}
