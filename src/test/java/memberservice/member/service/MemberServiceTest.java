package memberservice.member.service;

import memberservice.member.domain.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import memberservice.member.repository.MemberRepository;
import memberservice.member.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemberServiceTest {

    // MemberService memberService = new MemberService();
    MemberService memberService;

    /**
     * clearStore() 메소드 사용을 위해 새로운 인스턴스를 정의하는 것은
     * 엄밀히 말해 현재 서비스를 제공하는 repository와 서로 다른 repository 객체를 사용하게 되는 셈.
     * 따라서 MemberService 클래스에서 기존의 repository 를 외부에서 제공하는 객체로 새롭게 정의할 필요가 있음.
     */
    // MemoryMemberRepository repository = new MemoryMemberRepository();
    MemoryMemberRepository repository;

    /**
     * 위와 같이 수정되면 각 테스트 전에 실행해야할 동작이 아래와 같이 추가됨
     * 이것이 이른바 'Dependency Injection'
     */
    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    /**
     * 빌드될때 테스트 코드는 프로덕션 코드에 포함되지 않으므로
     * 협업하는 경우가 아니라면 테스트 코드는 한글로 작성해도 관계없음.
     */

    @Test
    public void 회원가입() {
        /* (1) GIVEN */
        Member member = new Member();
        member.setName("spring");

        /* (2) WHEN */
        Long memberId = memberService.join(member);

        /* (3) THEN */
        Member foundMember = memberService.findMember(memberId).get();

        /* 'name' 의 중복을 허용하지 않음을 이용 */
        assertThat(foundMember.getName()).isEqualTo(member.getName());
    }

    /* 정상적인 회원 가입의 테스트보다 예외 처리의 테스트가 더 중요 */
    @Test
    public void 중복이름_회원가입_예외() {
        /* (1) GIVEN */
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        /* (2) WHEN */
        memberService.join(member1);

        /* (3) THEN */
        /* 일반적으로 try catch 문법을 많이 이용하나 번거롭고 assertThat 을 사용하기가 애매함 */
        // try {
        //     memberService.join(member2);
        //     fail("예외가 발생해야 합니다.");
        // } catch (IllegalStateException e) {
        //     assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // }
        /* 그래서 이러한 문법을 이용 */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 전체회원조회() {
        /* (1) GIVEN */
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        /* (2) WHEN */
        List<Member> foundMembers = memberService.findMembers();

        /* (3) THEN */
        assertThat(foundMembers.size()).isEqualTo(2);
    }

    @Test
    void 회원조회() {
        /* (1) GIVEN */
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        /* (2) WHEN */
        Member foundMember1 = memberService.findMember(member1.getId()).get();
        Member foundMember2 = memberService.findMember(member2.getId()).get();

        /* (3) THEN */
        assertThat(foundMember1.getName()).isEqualTo(member1.getName());
        assertThat(foundMember2.getName()).isEqualTo(member2.getName());
    }
}