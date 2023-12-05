package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    //쿠키를 받을 수 있도록 홈 컨트롤러 수정
    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) { //로그인 안한 사용자도 접속 가능해야 하기 때문에 required=false

        if (memberId == null) {
            return "home";
        }

        //로그인 한(쿠키가 있는) 사용자
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) { // ex) 만료된 쿠키
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //로그아웃 -> 쿠키의 시간을 없애면 됨
}