package io.jee.caisan.spanel.service.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jee.caisan.spanel.domain.Member;
import io.jee.caisan.spanel.service.MemberService;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberService.getMemberByUsername(username);
		if(member!=null){
			Set<GrantedAuthority> authorities = new HashSet<>();
			if(member.getId()==10000){
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
			return new User(username, member.getPassword(), member.isEnabled(), true, true, true, authorities);
		}else{
			throw new UsernameNotFoundException(username);
		}
	}

}
