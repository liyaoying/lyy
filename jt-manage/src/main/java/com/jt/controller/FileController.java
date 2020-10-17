package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws Exception, Exception {
		//页面input中的name属性就是fileImage,下面两行可以不写
		String inputName = fileImage.getName();
		System.out.println("1:"+inputName);
		
		//获取输入的文件的名字
		String fileName = fileImage.getOriginalFilename();
		//指定存入的路径
		File fileDir = new File("D:/1-jt/image");
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		//实现上传文件
		fileImage.transferTo(new File("D:/1-jt/image/"+fileName));
		//重定向到原来页面
		return "redirect:/file.jsp";
		}
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO uploadFile(MultipartFile uploadFile) {
		return fileService.updateFile(uploadFile);
	}
}
