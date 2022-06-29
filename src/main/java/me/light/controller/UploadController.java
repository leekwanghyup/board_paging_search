package me.light.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
	}
	
	@PostMapping("uploadFormAction")
	public void uploadFormAction(MultipartFile[] uploadFile, Model model) {
		for(MultipartFile file : uploadFile) {
			String uploadFileName = file.getOriginalFilename();
			
			UUID uuid = UUID.randomUUID(); 
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			File saveFile = new File("C://storage", uploadFileName);
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {}
	
	@PostMapping("/uploadAjaxAction")
	@ResponseBody
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		File uploadPath = new File("C:/storage", getFolder());
		if(!uploadPath.exists()) {
			uploadPath.mkdirs(); 
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			String uploadFileName = multipartFile.getOriginalFilename();
			UUID uuid = UUID.randomUUID(); 
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			File savefile = new File(uploadPath, uploadFileName);
			try {
				//multipartFile.transferTo(savefile);
				checkImageType(savefile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(new Date());
		return str.replace("-", File.separator);
		// 2022\06\28
	}

	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			System.out.println(contentType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	
}
