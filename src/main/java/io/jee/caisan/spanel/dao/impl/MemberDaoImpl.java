package io.jee.caisan.spanel.dao.impl;

import org.springframework.stereotype.Repository;

import io.jee.caisan.spanel.dao.MemberDao;
import io.jee.caisan.spanel.domain.Member;
import io.jee.medusa.orm.Hibernate5DaoSupport;

@Repository
public class MemberDaoImpl extends Hibernate5DaoSupport<Member> implements MemberDao {
	

}
