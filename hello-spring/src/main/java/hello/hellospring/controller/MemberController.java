package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberForm;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    /**
     * 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.
     * 그리고 정형화되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈을 등록한다.
     */


    // 1. 필드 주입 방법은 좋지 않은 방법이다.
    // @Autowired
    private final MemberService memberService;

    // 2. 생성자 주입, 조립 시점에 주입이 들어오고 이후 내용이 변경할 수 없도록 막을 수 있다.
    // 의존관계가 실행 중에 동적응로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 3. setter 주입 - 호출되면 안되는 내용을 호출할 수 있게 되므로 권장하지 않는다.
    // 중간 중간 내용이 바뀔 수 있음
    /*@Autowired
    public void setMe mberService(MemberService memberService) {
        this.memberService = memberService;
    }*/

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
