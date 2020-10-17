package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;
@Service
@PropertySource("classpath:/properties/image.properties")//存入properties文件中
public class FileServiceImpl implements FileService{
	//定义文件存储路径
	@Value("${image.localDirPath}")
	private String localDirPath;
	//定义虚拟访问路径
	@Value("${image.urlPath}")
	private String urlPath;

	@Override
	public ImageVO updateFile(MultipartFile uploadFile) {
		ImageVO imageVO=new ImageVO();
		//获取上传文件名
		String fileName = uploadFile.getOriginalFilename();
		fileName.toLowerCase();//转化成小写
		//判断文件扩展名是否符合图片命名
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			imageVO.setError(1);
			return imageVO;
		}
		try {
			//判断文件是否具有图片属性
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width=bufferedImage.getWidth();
			int height=bufferedImage.getHeight();
			if(width==0||height==0) {
				imageVO.setError(1);
				return imageVO;
			}
			//定义创建时间字符串
			String dateDir =
					new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//实际存储路径由定义存储路径加上生产日期
			String localDir=localDirPath+dateDir;
			//创建存储文件夹
			File dirFile=new File(localDir);
			if(!dirFile.exists()) {
				dirFile.mkdirs();
			}
			//自由产生随机数,转换成字符串,并把连接符去掉
			String uuid = UUID.randomUUID().toString().replace("-","");
			//截取上传文件的文件名扩展名包括.
			String fileType=fileName.substring(fileName.lastIndexOf("."));
			
					//拼接新的文件名称
					//D:/1-jt/image/yyyy/MM/dd/文件名称.类型
					String realLocalPath = localDir+"/"+uuid+fileType;
					
					//7.2完成文件上传
					uploadFile.transferTo(new File(realLocalPath));
					
					//路径:D:/1-jt/image/2019/05/31/425cfb315d214ba8b1e4ec7e545de42f.jpg
					//形成访问路径
					String realUrlPath=urlPath+dateDir+"/"+uuid+fileType;
					//http://image.jt.com/2019/05/31/704e26877ec2419a9e0b972ac00d06d7.jpg
					//将文件文件信息回传给页面
					imageVO.setError(0);
					imageVO.setHeight(height);
					imageVO.setWidth(width);
					imageVO.setUrl(realUrlPath);
		} catch (Exception e) {
			e.printStackTrace();
			imageVO.setError(1);
			return imageVO;
		}
		return imageVO;
	}

}
