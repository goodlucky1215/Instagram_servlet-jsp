package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;



@Controller
@RequestMapping("/mystudy/instagram/*")
public class UploadHandler{
	@Autowired(required=true)
	private FileDao filedao = null;
	
	@RequestMapping("upload")
	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String result = "newArticleForm";
		ModelAndView mav = new ModelAndView();
		mav.setViewName(result);
		return mav;
	}
	@RequestMapping("upload1")
	public String a(HttpServletRequest req, HttpServletResponse res,@RequestParam("content")String content,@RequestParam("file") MultipartFile file) throws SQLException, IllegalStateException, IOException {
		String rootPath = req.getServletContext().getRealPath("/upload/");
	    System.out.println(rootPath);
		FileVO filevo = new FileVO();
		User user = (User) req.getSession().getAttribute("authUser");
		filevo.setMEMBERID(user.getId());
		filevo.setContentText(content); 
		filevo.setFileRealName(file.getOriginalFilename());
		filevo.setFileName(file.getOriginalFilename());
		if(file.getOriginalFilename().length()==0) {
			req.setAttribute("message","글은 쓰지 않더라도 사진은 넣어주세요!");
			return "newArticleForm";
		}else {
			file.transferTo(new File(rootPath+file.getOriginalFilename()));
			filedao.fileInsert(filevo);
			return "redirect:mainview"; 
		}
	}
	
}
