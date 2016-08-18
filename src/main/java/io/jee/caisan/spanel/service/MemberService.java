package io.jee.caisan.spanel.service;

import io.jee.caisan.spanel.domain.Member;
import io.jee.medusa.util.ResultModel;

public interface MemberService {
	
	Member getMemberByUsername(String username);
	
	Member getMemberByUsernameLock(String username);
	
	ResultModel<Member> addMember(String username, String password);
	
}
