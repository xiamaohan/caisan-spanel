package io.jee.caisan.spanel.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.jee.medusa.util.CalendarUtils;

@Entity
@Table(name = "sp_virtual_host")
@JsonIgnoreProperties({ "password" })
public class VirtualHost implements Serializable {

	public static String PREFIX = "";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true, length = 16)
	private String username;
	@Column(nullable = false, unique = true, length = 64)
	private String domain;
	@Column(nullable = false, length = 12)
	private String password;
	@Column(name = "open_time")
	private Date openTime;
	@Column(name = "expire_time")
	private Date expireTime;
	@Column(nullable = false, length = 1)
	private ItemStatus status;
	@ManyToOne
	@JoinColumn(name = "server_plan", nullable = false)
	private VirtualHostPlan serverPlan;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Member member;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public ItemStatus getStatus() {
		return status;
	}

	public void setStatus(ItemStatus status) {
		this.status = status;
	}

	public VirtualHostPlan getServerPlan() {
		return serverPlan;
	}

	public void setServerPlan(VirtualHostPlan serverPlan) {
		this.serverPlan = serverPlan;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Transient
	public int getExpireDay() {
		if (expireTime != null) {
			return CalendarUtils.dayDiff(new Date(), expireTime).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		} else {
			return 0;
		}
	}

}
