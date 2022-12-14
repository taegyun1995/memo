package com.project.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManagerService {
	
	public static final String IMAGE_UPLOAD_PATH = "/Users/taegyun/mini project/memo/upload/";
	
	// 파일을 저장하는 접근 가능한 경로 리턴하는 기능
	public static String saveFile(int userId, MultipartFile file) {
		// 파일을 어디에 저장할지
		// /Users/taegyun/mini project/memo/upload/3_4885940493/file이름.jpg
		// 디렉토리를 새로 만든다.
		// 디렉토리 이름 규칙
		// 사용자 (userId)
		// 시간 정보 - UNIX 타임 : 1970년 1월 1일을 기준으로 몇 millisecond(1/1000)가 지났는지를 표현
		// /3_4885940493/
		
		String directoryName = userId + "_" + System.currentTimeMillis() + "/";
		
		// 디렉토리 생성
		String filePath = IMAGE_UPLOAD_PATH + directoryName;
		File directory = new File(filePath);
		
		if(directory.mkdir() == false) {
			// 디렉토리 생성 실패
			return null;
		}
		
		// 파일 저장
		try {
			byte[] bytes = file.getBytes();
			// /Users/taegyun/mini project/memo/upload/3_4885940493/file이름.jpg 
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			// 파일 처리 예외상황
			return null;
		}
		
		// 외부에서 접근 가능한 경로 리턴
		// 규칙 정하기
		// /images/3_4885940493/file이름.jpg
		return "/images/" + directoryName + file.getOriginalFilename();
		
	}
	
	public static boolean removeFile(String filePath) { //       /images/3_123141532121/test.png
		
		if(filePath == null) {
			
			return false;
		}
		
		String realFilePath = IMAGE_UPLOAD_PATH + filePath.replace("/images/", "");
		
		Path path = Paths.get(realFilePath);
		
		// 파일이 있는지 확인
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
		}
		
//		 /Users/taegyun/mini project/memo/upload/3_4885940493/file이름.jpg 
		path = path.getParent();
		
		// 디렉토리가 존재하는지 확인
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
		}
		
		return true;
		
	}
	
}