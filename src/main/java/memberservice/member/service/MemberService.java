package memberservice.member.service;

import memberservice.member.domain.Member;
import memberservice.member.repository.MemberRepository;
import memberservice.member.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// @Service // <- 빈 자동 등록, 직접 등록을 위해 삭제
@Transactional // <- JPA : 데이터 저장 및 변경시 안정성을 위해 필요
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

        /**
         * Aspect Oriented Programming(AOP)
         * : 핵심 관심사항 (core concern)과  공통 관심사항 (cross-cutting concern) 을 분리
         **
         * 시스템 효율성 개선을 위해 실행 처리 시간을 측정해야할 필요가 있다면?
         * 그리고 이렇게 처리해야 할 메소드가 수백개라면?
         */
//        long start = System.currentTimeMillis();
//        try {
//            validateDuplicatedMember(member);
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("Join Member : " + timeMs + " ms");
//        }
        /* --------------------------------------------------------------------------*/


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

//        long start = System.currentTimeMillis();
//        try {
//            return memberRepository.findAll();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("Find All Members : " + timeMs + " ms");
//        }

        return memberRepository.findAll();

    }

    /* 회원 조회 */
    public Optional<Member> findMember(Long memberId) {

//        long start = System.currentTimeMillis();
//        try {
//            return memberRepository.findById(memberId);
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("Find A Member : " + timeMs + " ms");
//        }

        return memberRepository.findById(memberId);

    }

}
