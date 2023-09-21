package memberservice.member.repository;

import memberservice.member.domain.Member;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    /**
     * JPA 를 이용하면 ORM 방식을 통해 SQL 작성에서 벗어날 수 있음.
     * 그러기 위해서 Entity Manager 를 주입 받아야 함.
     */
    private final EntityManager em;
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // <- SQL 작성, DB 작동 및 id 설정 등 실행
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        /**
         * 'JPA' 에서 객체의 primary key (id) 를 이용할 수 없을 경우에는 JPQL 을 통해 객체 entity 에 쿼리를 보내야 함.
         * 주의 : JPQL 에서 select 의 대상은 entity 자신 임. ( DB 의 SQL 에서 select 의 대상은 * (모두) 또는 일부 column 임 )
         **
         * 다음에 적용할 'Data JPA' 에서는 이런 쿼리 마저도 사용할 필요가 없게 됨.
         */
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                                .setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
