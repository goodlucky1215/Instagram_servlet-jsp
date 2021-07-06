package member.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoginService {
	private MemberDao memberDao = null;
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public User login(Map<String,Object> map) throws SQLException {
		Member member = memberDao.selectById(map);
		//아이디나 비번이 틀렸을 시
		if(member.getId() == null) {
				throw new LoginFailException();
		}else {
		//아이디나 비번이 맞을 시, 자동로그인 이용시 -- id면 아이디상태저장, login이면 로그인 상태저장
		//	1. 만약에 자동 로그인 저장한다면 세션아이디를 쿠키에서 받아와서 저장시켜준다.
		//	2. 그리고 그 쿠키 기간을 일주일? 한달?로 연장을 해둬야겠지.
		//	3. 사용자가 나중에 접속 시 세션아이디가 DB에 저장되어 있는지를 확인한다. - 인터셉터를 이용한다.
		//	4. 저장되어 있다면, 자동 로그인을 시켜준다.
		//	5. 사용자가 로그아웃한다면 세션아이디를 삭제해준다.
			if(map.get("isSavedIdChecked")!=null) {
				System.out.println("나 저장해줘1");
				memberDao.saveAutoIdLogin(map,"id");
			}
			if(map.get("isAutoLoginChecked")!=null) {
				System.out.println("나 저장해줘2");
				memberDao.saveAutoIdLogin(map,"login");
			}
		}
		return new User(member.getId(),member.getName());
	}
	
	//자동로그인이나 아이디 저장 존재 시
	public Map<String,Object> autoCookieConfirm(String cookie) throws SQLException {
		Map<String,Object> map = memberDao.autoCookieConfirm(cookie);
		if(map.containsKey("SAVETYPE")) {
			if("login".equals(map.get("SAVETYPE").toString())) {
				map.put("name",memberDao.putIdName(map.get("MEMBERID").toString()));
			}
		}
		return map;
	}
	
	public List<Map<String, Object>> articleList(String memberId,String text) throws SQLException {
		List<Map<String, Object>> articles = new ArrayList<>();
		articles = memberDao.selectArticle(memberId,text);
		return articles;
	}
	public void naverLogin(String email, String namehan) throws SQLException {
		//없는 아이라면 추가해준다.
		if("false".equals(memberDao.findNaverId(email))) {
			Member member = new Member(email,namehan,null,new Date());
			memberDao.insert(member);
		};
		
	}
	//자동로그인 삭제해주기
	public void autoCookiedelete(String memberId) throws SQLException {
		memberDao.autoCookiedelete(memberId);
		
	}
}
