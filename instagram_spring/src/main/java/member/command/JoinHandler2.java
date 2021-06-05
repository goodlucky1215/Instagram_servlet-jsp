package member.command;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.util.HashMapBinder;

public class JoinHandler2 extends MultiActionController{

	private static final String FORM_VIEW = "joinForm";
	private JoinService joinService = new JoinService();

	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException{
		HashMapBinder hmb = new HashMapBinder(req);
		ModelAndView mav = new ModelAndView();
		String viewPage = null;
		if(req.getMethod().equalsIgnoreCase("GET")) {//보내는 방식이 get일때,equalsIgnoreCase이것은 대소문자 구분 안함.
			viewPage = processForm(req,res); 
		}else if(req.getMethod().equalsIgnoreCase("POST")) { //보내는 방식이 post일때,equalsIgnoreCase이것은 대소문자 구분 안함.
			viewPage = processSubmit(req,res);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		if(viewPage != null) {
			mav.setViewName(viewPage);
		}
		
		return mav;
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) { //get으로 받으면 다시 회원가입 창으로 이동
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) { //post로 받으면 정상으로 폼 전송 처리
		//joinForm으로 부터 값을 다 가져온다.
		MemberVO joinReq = new MemberVO();
		joinReq.setId(req.getParameter("id")); //joinForm에서 name이 "id"니깐!
		joinReq.setName(req.getParameter("name")); //joinForm에서 name이 "name"니깐!
		joinReq.setPassword(req.getParameter("password")); //joinForm에서 name이 "password"니깐!
		joinReq.setConfirmPassword(req.getParameter("confirmPassword")); //joinForm에서 name이 "confirmPassword"니깐!
		System.out.println(joinReq.getId());
		
		Map<String, Boolean> errors = new HashMap<>(); //errors를 담을 map을 만들어줌.
		System.out.println(joinReq.getId());
		req.setAttribute("errors", errors); //req에다가 errors라는 이름으로 객체 errors를 저장해두는 것임
		
		joinReq.validate(errors); //값이 빈값인지 아닌지 확인하는 메소드
		System.out.println(errors);

		if(!errors.isEmpty()) { //만약에 errors안에 값이 존재하면 -빈 값이 있다는 것(혹은 비번!=확인비번)이므로 다시 회원가입 폼으로 간다.
			System.out.println(joinReq.getId());
			return FORM_VIEW;
		}
		
		//errors가 비어있다면, 에러가 없다는 것이므로 로그인 화면으로 넘어 갈 준비를 한다.
		try {
			joinService.join(joinReq);//회원가입 dao에 값 저장하기위해서
			return "joinSuccess";
		} catch (DuplicateIdException e) {
		//그러나 만약에 이미 존재하는 아이디로 한다면 다시 회원가입 폼으로 돌아간다.
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}


}
