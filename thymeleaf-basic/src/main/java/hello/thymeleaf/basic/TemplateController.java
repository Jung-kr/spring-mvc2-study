package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    // 템플릿 조각 : 사이트 작은 경우 사용, 단순하지만 중복이 많음
    @GetMapping("/fragment")
    public String template() {
        return "template/fragment/fragmentMain";
    }

    // 템플릿 레이아웃 : 페이지가 많아지고 관리가 중요해지면 사용 (fragment vs layout)
    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layoutMain";
    }

    // 템플릿 레이아웃 확장
    @GetMapping("/layoutExtend")
    public String layoutExtends() {
        return "template/layoutExtend/layoutExtendMain";
    }

}
