package com.bitcamp.jackpot.dto;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                if(member.getIsAdmin()==1){
                    return "ROLE_ADMIN";
                }else  return "ROLE_USER";


            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPwd();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //
    }

    @Override
    public boolean isEnabled() {
        return true; //
    }
}
