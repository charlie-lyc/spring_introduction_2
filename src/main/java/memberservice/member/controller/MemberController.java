package memberservice.member.controller;

import memberservice.member.domain.Member;
import memberservice.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    /**
     * 서비스를 스프링 컨테이너에 등록하여 이용함으로써 다양한 잇점을 얻을 수 있음
     * 그래서 '@Autowired' 를 이용하여 연결하였는데 MemberService, MemoryMemberRepository 를 인식하지 못함.
     * 결론: Spring에 의해 생성되고 관리되는 자바객체(Bean)를 등록하는 과정이 필요
     **
     * 스프링 빈 등록 과정
     *
     * 1. 컴포넌트 스캔 방식 : 3가지 정형화된 패턴
     * - (1) MemberController: 외부로부터 요청 -  @Controller, @Autowired
     * - (2) MemberService   : 비지니스 로직   - @Service, @Autowired
     * - (3) MemberRepository: 데이터 저장소   - @Repository
     *
     * 2. 자바 코드로 직접 등록 방식
     * : service 와 repository 의 @Service, @Repository, @Autowired 제거 후 등록
     *  ( => 단, controller 의 @Controller 와 @Autowired 는 제거하지 않음 )
     **
     * 현재의 메모리 저장소를 향후에 다른 저장소로 변경할 예정이므로
     * 여기서는 컴포넌트 스캔 방식 대신에 자바 코드로 직접 스프링 빈을 설정함.
     */


    /**
     * 의존도 주입 ( DI : Dependency Injection ) 의 방식에도 3가지가 있음
     * 1. field 주입
     * 2. setter 주입
     * 3. constructor 주입
     */

    /* 1. 필드 주입 : 애플리케이션 조립시 교체할 수 있는 방법이 없음 */
    // @Autowired private MemberService memberService;

    /* 2. 세터 주입 : 교체할 수는 있으나 setter가 노출되어 위험함 */
    // private MemberService memberService;
    // @Autowired
    // public void setMemberService(MemberService memberService) {
    //     this.memberService = memberService;
    // }

    /* 3. 생성자 주입 : 애플리케이션 조립시 한번만 교체할 수 있고 이후에는 교체할 수 없음 */
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        // System.out.println("member's name : " + member.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String listMember(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
