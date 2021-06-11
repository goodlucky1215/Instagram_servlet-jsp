package com.example.demo;

import java.util.Map;

public class MemberVO {
	private String id;
	private String name;
	private String password;
	private String confirmPassword;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	//비밀번호를 썼는지 확인하고, 비밀번호랑 확인하는 비밀번호랑 같은지 확인
	public boolean isPasswordEqualToConfirm() {
		return password!=null&&password.equals(confirmPassword);
	}
	
	public void validate(Map<String,Boolean> errors) {
		checkEmpty(errors, id, "id"); //각각의 값들이 빈 값인지 아닌지 확인
		checkEmpty(errors, name, "name"); //각각의 값들이 빈 값인지 아닌지 확인
		checkEmpty(errors, password, "password"); //각각의 값들이 빈 값인지 아닌지 확인
		checkEmpty(errors, confirmPassword, "confirmPassword"); //각각의 값들이 빈 값인지 아닌지 확인
		if(!errors.containsKey("confirmPassword")) { //만약에 "confirmPassword"이 빈 값이 아니라는게 확인 된다면(이미 빈값이면 굳이 돌릴 필요가 없겠지?!)
			if(!isPasswordEqualToConfirm()) { //여기를 돌린다. 그래서 password와 confirmPassword의 일치를 확인하고,
				errors.put("notMatch", Boolean.TRUE);  //만약에 falsef라면 password와 confirmPassword가 같이 않다는 표시로 erros에다가 "notMatch"이름으로 TRUE해준다.
			}
		}
		
	}
	
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if(value == null || value.isEmpty()) { //값이 null이거나 또는 비어있다면
			errors.put(fieldName, Boolean.TRUE); //errors안에다가 그 해당 이름에 따라서 TRUE를 넣어준다.
		}
		
	}
}
