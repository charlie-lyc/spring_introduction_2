package memberservice.member.service;

import memberservice.member.domain.Member;
import memberservice.member.repository.MemberRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // -> spring container 를 통한 통합 테스트를 진행함. 이 annotation 이 없다면 단순히 JVM 에서의 단위 테스트를 의미함.
@Transactional // -> JDBC 에서 반복적인 테스트 실행을 가능하게 함. 따라서 ForEach 또는 ForAfter 가 필요 없어짐.
public class MemberServiceIntegrationTest {
    /**
     * 테스트의 적합성 ?
     * Unit Test >= Integration Test
     */

    /**
     * 실제 구현에서는 생성자 주입 방식(DI)을 따르지만,
     * 테스트 케이스는 다시 사용할 일이 없으므로 간편하게 작성함
     */
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        /* (1) GIVEN */
        Member member = new Member();
        member.setName("John"); // <- DB에 이미 있는 이름이라면 다른 이름을 사용하거나 DB 데이터 삭제 요망

        /* (2) WHEN */
        Long memberId = memberService.join(member);

        /* (3) THEN */
        Member foundMember = memberRepository.findById(memberId).get();
        assertThat(foundMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void 중복이름_회원가입_예외() throws Exception {
        /* (1) GIVEN */
        Member member1 = new Member();
        member1.setName("Pete");
        Member member2 = new Member();
        member2.setName("Pete");
        memberService.join(member1);

        /* (2) WHEN */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        /* (3) THEN */
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    public void 전체회원조회() throws Exception {
        /* (1) GIVEN */
        List<Member> members = memberService.findMembers();

        /* (2) WHEN */
        List<Member> foundMembers = memberRepository.findAll();

        /* (3) THEN */
        assertThat(foundMembers.size()).isEqualTo(members.size());
    }

    @Test
    public void 회원조회() throws Exception {
        /* (1) GIVEN */
        Member member1 = new Member();
        member1.setName("Jack");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setName("Jerry");
        memberService.join(member2);

        /* (2) WHEN */
        Member foundMember1 = memberRepository.findById(member1.getId()).get();
        Member foundMember2 = memberRepository.findById(member2.getId()).get();

        /* (3) THEN */
        assertThat(foundMember1.getName()).isEqualTo(member1.getName());
        assertThat(foundMember2.getName()).isEqualTo(member2.getName());
    }

}
