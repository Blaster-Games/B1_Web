package org.kdr.blaster.security;

import lombok.RequiredArgsConstructor;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.member.MemberDTO;
import org.kdr.blaster.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        return new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getUserRole(),
                member.getId(),
                member.getNickname(),
                member.getCreatedAt()
        );
    }
}
