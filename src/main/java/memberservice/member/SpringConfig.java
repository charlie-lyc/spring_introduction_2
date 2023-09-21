package memberservice.member;

import memberservice.member.aop.TimeTraceAop;
import memberservice.member.repository.*;
import memberservice.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    /********************************************************
     * Spring Data JPA
     */
    // MemberRepository 와 JpaRepository 가 'SpringDataJpaMemberRepository' 인터페이스로 이미 상속 됨.
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
    /* --------------------------------------------------- */

    /**
     * Java Persistence API
     */
//     private final EntityManager em;

    /**
     * Java Data Base Connectivity, JDBC Template
     */
//     private final DataSource dataSource;

//     @Autowired // <- 하나 뿐일 때는 생략 가능
//     public SpringConfig(DataSource dataSource, EntityManager em) {
//         this.dataSource = dataSource; // <- JDBC, JDBC Template
//         this.em = em; // <- JPA
//     }

//    @Bean
//    public MemberRepository memberRepository() {
//        /**
//         * 1. In Memory
//         */
//         // return new MemoryMemberRepository();
//
//        /**
//         * 2. Java Data Base Connectivity
//         */
//        // return new JdbcMemberRepository(dataSource);
//
//        /**
//         * 3. JDBC Template
//         */
//        // return new JdbcTemplateMemberRepository(dataSource);
//
//        /**
//         * 4. Java Persistence API
//         */
//        // return new JpaMemberRepository(em);
//    }

//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

}
