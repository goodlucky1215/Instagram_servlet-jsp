package member.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;


public class UploadHandler implements CommandHandler{

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String result = null;
		if("GET".equalsIgnoreCase(req.getMethod())) {
			result = processForm(req,res);
		}
		else if("POST".equalsIgnoreCase(req.getMethod())) {
			result = processSubmit(req,res);
		}
		if(result!=null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher(result);
			dispatcher.forward(req, res);
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return "newArticleForm.jsp";
	}
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		String directory = req.getSession().getServletContext().getRealPath("/upload/");
		System.out.println(directory);
		int maxSize = 1024*1024*100;
		String encoding = "UTF-8";
		FileVO filevo = new FileVO();
		MultipartRequest multipartRequest = null;
		try {
			multipartRequest = new MultipartRequest(req, directory, maxSize, encoding, new DefaultFileRenamePolicy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = (User) req.getSession().getAttribute("authUser");
		filevo.setMEMBERID(user.getId());
		//보내는 form에서 enctype="multipart/form-data"를 사용하게 되면 req.getParameter로 값을 받아오지 못 한다.
		filevo.setContentText(multipartRequest.getParameter("content")); 
		System.out.println(multipartRequest.getParameter("content"));
		filevo.setFileRealName(multipartRequest.getOriginalFileName("file"));
		filevo.setFileName(multipartRequest.getOriginalFileName("file"));
		if(filevo.getFileRealName()==null) {
			req.setAttribute("message","글은 쓰지 않더라도 사진은 넣어주세요!");
			return "newArticleForm.jsp"; 
		}
		new FileDao().fileInsert(filevo);
		try {
			res.sendRedirect("mainview.do"); //파일을 업로드하니깐 redirect처리해주자(뒤로가기로 중복되면 아니돼니깐)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
