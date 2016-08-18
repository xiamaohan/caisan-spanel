package io.jee.caisan.spanel.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.LockOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jee.caisan.spanel.dao.MemberDao;
import io.jee.caisan.spanel.domain.Member;
import io.jee.caisan.spanel.service.MemberService;
import io.jee.caisan.spanel.util.ValidationUtils;
import io.jee.medusa.util.ResultModel;

@Service
@Transactional
public class MemberServiceImpl implements MemberService, InitializingBean {
	
	@Resource
	private MemberDao memberDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Member getMemberByUsername(String username) {
		return memberDao.unique("username", username);
	}
	
	@Override
	public Member getMemberByUsernameLock(String username) {
		return memberDao.byNaturalId().using("username", username).with(LockOptions.UPGRADE).load();
	}

	@Override
	public ResultModel<Member> addMember(String username, String password) {
		ResultModel<?> resultModel = ValidationUtils.validUsername(username);
		if(!resultModel.isSuccess()){
			return ResultModel.resultError(resultModel.getMessage(), "username", resultModel.getMessage());
		}
		int passLength;
		if(password==null||(passLength=password.trim().length())<6||passLength>20){
			return ResultModel.resultError("password", "密码长度为6-20位");
		}
		Member member = this.getMemberByUsername(username);
		if(member!=null){
			return ResultModel.resultError("username", "该用户名已经被注册了");
		}
		member = new Member();
		member.setUsername(username);
		member.setPassword(passwordEncoder.encode(password));
		member.setEnabled(true);
		memberDao.save(member);
		return ResultModel.resultSuccess(member);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		int size = jdbcTemplate.query("select count(id) from sp_member where id = 10000", new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getInt(1);
			}
			
		});
		if(size==0){
			jdbcTemplate.update("insert sp_member (id, enabled, username, password) values (?,?,?,?)", new Object[]{10000, true, "admin", "admin"});
		}
	}

}
