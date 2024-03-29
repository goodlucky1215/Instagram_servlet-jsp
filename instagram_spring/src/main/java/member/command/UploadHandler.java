package member.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.HashMapBinder;
import com.oreilly.servlet.MultipartRequest;


public class UploadHandler extends MultiActionController{
	private FileDao filedao = null;
	public void setFiledao(FileDao filedao) {
		this.filedao = filedao;
	}	
	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMapBinder hmb = new HashMapBinder(req);
		String result = null;
		ModelAndView mav = new ModelAndView();
		if("GET".equalsIgnoreCase(req.getMethod())) {
			result = processForm(req,res);
		}
		else if("POST".equalsIgnoreCase(req.getMethod())) {
			result = processSubmit(req,res);
		}
		if(result!=null) {
			mav.setViewName(result);
		}
		return mav;
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return "newArticleForm";
	}
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws SQLException {
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
		filevo.setFileRealName(multipartRequest.getFilesystemName("file"));
		filevo.setFileName(multipartRequest.getOriginalFileName("file"));
		if(filevo.getFileRealName()==null) {
			req.setAttribute("message","글은 쓰지 않더라도 사진은 넣어주세요!");
			return "newArticleForm"; 
		}
		filedao.fileInsert(filevo);
		return "redirect:mainview.do"; //파일을 업로드하니깐 redirect처리해주자(뒤로가기로 중복되면 아니돼니깐)
	}

}
