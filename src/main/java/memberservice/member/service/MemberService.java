package memberservice.member.service;

import memberservice.member.domain.Member;
import memberservice.member.repository.MemberRepository;
import memberservice.member.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service
public class MemberService {

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    /**
     * 접근 가능한 객체로 새롭게 정의
     */
    private final MemberRepository memberRepository;

    // @Autowired
    public MemberService (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 서비스 영역이기 때문에 비지니스적인 용어를 사용함 : ex) 회원 가입, 전체 회원 조회, 회원 조회
     */

    /* 회원 가입 */
    public Long join(Member member) {
        /* [1] 서비스 내 규칙: 같은 이름이 있으면 중복회원으로 간주하여 회원가입을 허용하지 않음 */
        // Optional<Member> result = memberRepository.findByName(member.getName());
        /* null 일 가능성이 있을 경우 Optional<> 을 사용하면 편리한 메소드를 사용할 수 있음 */
        // result.ifPresent((memb) -> {
        //     throw new IllegalStateException("이미 존재하는 회원입니다.");
        // });

        /* [2] 만약 Optional<> 을 사용하는 것이 번거롭다고 느낀다면 암묵적인 방식으로 사용할 수도 있음 */
        // memberRepository.findByName(member.getName())
        //         .ifPresent((memb) -> {
        //             throw new IllegalStateException("이미 존재하는 회원입니다.");
        //         });

        /* [3] 또는 코드가 너무 길다고 생각된다면 외부 메소드 함수로 추출할 수도 있음 */
        validateDuplicatedMember(member);

        /* 중복된 이름이 없을 경우 회원가입을 허용함 */
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent((memb) -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 조회 */
    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
