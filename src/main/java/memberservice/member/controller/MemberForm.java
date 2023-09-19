package memberservice.member.controller;

import org.springframework.stereotype.Controller;

@Controller
public class MemberForm {

    /**
     * createMemberForm.html 파일의 <input> 엘리먼트 중에서
     * name 속성의 값이 "name" 인 엘리먼트의 값과 매칭이 됨
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
