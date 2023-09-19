package memberservice.member.repository;

import memberservice.member.domain.Member;

import static org.assertj.core.api.Assertions.*;
// import org.assertj.core.api.Assertions; // assertThat(actual).isEqualTo(expected)
// import org.junit.jupiter.api.Assertions; // assertEquals(expected, actual)
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {

    /* 인터페이스 */
    // MemberRepository repository = new MemoryMemberRepository();
    /* 클래스 */
    MemoryMemberRepository repository = new MemoryMemberRepository();

    /**
     * 아래와 같은 여러 테스트 케이스들의 일괄 실행시 순서없이  실행되는데,
     * 이 때 동일한 값을 반복해서 사용하면 에러가 발생됨.
     * 따라서 하나의 테스트 케이스가 끝날때마다 메모리에 저장된 값들을 지워야 함.
     * 결론: 각 테스트 케이스들은 상화 의존성 없이 독립적으로 설계되어야 함.
     */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        /* get()을 사용함으로써 Optional<> 와 Optional.of() 을 사용하지 않게 됨, 다만 적극적으로 권장하지는 않음 */
        Member result = repository.findById(member.getId()).get();

        // System.out.println("Result = " + (member == result));
        // Assertions.assertEquals(member, result);
        // Assertions.assertThat(result).isEqualTo(member);
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findById() {
        Member member1 = new Member();
        member1.setName("spring1"); // <-
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2"); // <-
        repository.save(member2);

        Member result1 = repository.findById(member1.getId()).get(); // <-
        Member result2 = repository.findById(member2.getId()).get(); // <-

        assertThat(result1).isEqualTo(member1);
        assertThat(result2).isEqualTo(member2);
        assertThat(result1).isNotEqualTo(member2);
        assertThat(result2).isNotEqualTo(member1);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1"); // <-
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2"); // <-
        repository.save(member2);

         Member result1 = repository.findByName("spring1").get(); // <-
         Member result2 = repository.findByName("spring2").get(); // <-

        assertThat(result1).isEqualTo(member1);
        assertThat(result2).isEqualTo(member2);
        assertThat(result1).isNotEqualTo(member2);
        assertThat(result2).isNotEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1"); // <-
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2"); // <-
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.size()).isNotEqualTo(1);
        assertThat(result.size()).isNotEqualTo(3);
    }

}
