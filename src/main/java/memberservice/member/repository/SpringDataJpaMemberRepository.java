package memberservice.member.repository;

import memberservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository  {

    /**
     * 이 인터페이스가 JpaRepository 와 MemberRepository 모두를 상속 받았음.
     * 이 것으로 끝. 더 이상 구현할 필요가 없음!!! ( 나머지는 Spring 이 자동으로 구현 )
     **
     * 더 자세한 내용은 'JpaRepository' 인터페이스를 참고할 것
     */

    /**
     * 그렇다면 'JpaRepository' 에는 없는 아래의 method 는 어떻게 자동 완성 되는 걸까?
     * 메소드 이름을 'JpaRepository' 의 컨벤션에 따라 작성하면 Spring 이 자동으로 JPQL을  완성함.
     **
     * ex) Optional<[Member]> [find]By[Name](String [name])
     *      : "select m from Member m where m.[name] = ?"
     * ex) Optional<[Member]> [find]By[Name]And[Id](String [name], Long [id])
     *      : "select m from Member m where m.[name] = ? and m.[id] = ?"
     */
    @Override
    Optional<Member> findByName(String name);
}
