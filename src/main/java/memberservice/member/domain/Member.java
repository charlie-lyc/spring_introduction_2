package memberservice.member.domain;

import javax.persistence.*;

@Entity // <- JPA
public class Member {

    @Id // <- JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <- JPA
    private Long id;

    @Column(name = "name") // <- JPA
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
