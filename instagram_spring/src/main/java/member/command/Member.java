package member.command;

import java.util.Date;

public class Member {
	private String id=null;
	private String name;
	private String password;
	private Date   regDate;

	public Member(String id, String name, String password, Date date) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.regDate = date;

	}
	public Member() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
