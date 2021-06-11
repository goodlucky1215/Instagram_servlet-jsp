package com.example.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Enumeration;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.HashMapBinder;
import com.oreilly.servlet.MultipartRequest;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class UploadHandler{
	@Autowired(required=true)
	private FileDao filedao = null;
	
	@RequestMapping("upload")
	public @ResponseBody ModelAndView process(HttpServletRequest req, HttpServletResponse res,MultipartFile file) throws Exception {
		String result = null;
		ModelAndView mav = new ModelAndView();
		if("GET".equalsIgnoreCase(req.getMethod())) {
			result = "newArticleForm";
		}
		else if("POST".equalsIgnoreCase(req.getMethod())) {
			//String directory = req.getSession().getServletContext().getRealPath("/upload/");
			String directory = "C:\\Users\\ariel\\git\\Instagram_servlet-jsp\\instagram_springboot\\src\\main\\webapp\\upload";
			int maxSize = 1024*1024*100;
			FileVO filevo = new FileVO();
			//String encoding = "UTF-8";
			//MultipartRequest multipartRequest = null;
			try {
				///ClassLoader cl = this.getClass().getClassLoader();
				//InputStream inputStream = cl.getResourceAsStream("classpath:static/upload/");
				//BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				MultipartRequest multipartRequest = new MultipartRequest(req,directory , maxSize, "UTF-8", new DefaultFileRenamePolicy());
				Enumeration files = multipartRequest.getFileNames();
	            String str = (String)files.nextElement();
	            User user = (User) req.getSession().getAttribute("authUser");
	            filevo.setMEMBERID(user.getId());
	            //보내는 form에서 enctype="multipart/form-data"를 사용하게 되면 req.getParameter로 값을 받아오지 못 한다.
	            filevo.setContentText(multipartRequest.getParameter("content")); 
	            System.out.println(multipartRequest.getParameter("content"));
	            filevo.setFileRealName(multipartRequest.getFilesystemName("file"));
	            filevo.setFileName(multipartRequest.getOriginalFileName("file"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(filevo.getFileRealName()==null) {
				req.setAttribute("message","글은 쓰지 않더라도 사진은 넣어주세요!");
				result = "newArticleForm";
			}else {
			filedao.fileInsert(filevo);
				result = "redirect:mainview"; 
			}
		}
		if(result!=null) {
			mav.setViewName(result);
		}
		return mav;
	}

}
