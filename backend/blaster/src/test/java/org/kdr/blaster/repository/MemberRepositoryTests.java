package org.kdr.blaster.repository;

import org.junit.jupiter.api.Test;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.domain.member.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember() {
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .nickname("u" + i)
                    .password(passwordEncoder.encode("1111"))
                    .userRole(UserRole.USER)
                    .build();
            members.add(member);
        }
        memberRepository.saveAll(members);
    }
}
