package io.jee.caisan.spanel.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sp_server")
@JsonIgnoreProperties({ "username", "password" })
public class Server {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, length = 10)
	private String name;
	@Column(nullable = false, length = 64)
	private String host;
	@Column(length = 64)
	private String ip;
	@Column(length = 16)
	private String username;
	@Column(length = 1024)
	private String password;
	private String descr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIp() {
		if (StringUtils.isEmpty(ip)) {
			try {
				ip = InetAddress.getByName(host).getHostAddress();
			} catch (UnknownHostException e) {
			}
		}
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}
