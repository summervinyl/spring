package com.yedam.app.upload.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 스프링 내부에 있는 로그 불러옴? 로그 이용 / 해당 컨드롤ㄹ러 안에 로그가 들어옴 - 빈임, 메소드 활용해 정보 출력
@Controller
public class UploadController {

//	private String uploadPath = "D:\\upload"; //내부코드작성법: 이 방식은 좋지 않음 쓰지마셈, 밑의 방법 ㄱㄱ

	// 파일 저장
	@Value("${file.upload.path}") // 환경변수 기반은 까다로워서 프로퍼타이즈를 기반으로 처리하기. => application.propertiles 설정ㄱ
	private String uploadPath; // value로 경로를 호출해서 uploadPath라는 필드에 담음

	// 페이지 넘기기 - 파일 호출
	@GetMapping("formUpload") // 경로 - 데이터가 넘어요먼 이클립스 console에 값이 넘어온 게 적힌다.
	public void formUploadPage() {
	}

	// 파일 처리
	@PostMapping("uploadForm")
	// 다중 업로드일 경우 배열로 값을 받아서 반복문을 돌리면 됨
	public String formUploadFile(@RequestPart MultipartFile[] files) { // 화면에서 넘기는 이름이 매개변수가 되어야 함. formUpload.html의
																		// files가 매개변수임
		for (MultipartFile file : files) {
			log.info(file.getContentType()); // @Slf4j 현재 넘어오는 게 이미지인지 pdf인지 등등
			log.info(file.getOriginalFilename());
			log.info(String.valueOf(file.getSize()));

			// 파일 저장
			String fileName = file.getOriginalFilename();
			String saveName = uploadPath + File.separator + fileName; // separator(분리기호) 자바가 인식하는 D:/upload/\덱스2.jpeg

			log.debug("saveName : " + saveName);

			Path savePath = Paths.get(saveName); // Path 순수한 경로 - 스트링 경롤르 Paths.get(saveName)로 변환?!
			log.debug("savePath : " + savePath); // D:\\upload\덱스2.jpeg

			// 외부와 소통하는 것이기 때문에 try catch 작성
			try {
				file.transferTo(savePath); // MultipartFile 안에 지정된 경로를 transferTo: 실제 동작 - 경로를 기반으로해서 파일을 생성하고 파일이 가져야
											// 하는 데이터를 작성하는 작업
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "redirect:formUpload";
	}
	
	// http://127.0.0.1:8099/yedam/upload
	// 해당 파일의 날짜 폴더들이 생성 되고 이미지 이름에 uuid가 붙는다.
	@GetMapping("upload")
	public void uploadPage() {
	}

	@PostMapping("/uploadsAjax")
	@ResponseBody
	public List<String> uploadFile(@RequestPart MultipartFile[] uploadFiles) {

		List<String> imageList = new ArrayList<>();
		
		for (MultipartFile uploadFile : uploadFiles) {
			// 파일 업로드 제한 - 이미지에 대해서만 제한 / 이미지 또는 파일 확장자 등등 여러가지에 대해서 제한을 걸 수 있다.
			if (uploadFile.getContentType().startsWith("image") == false) {
				System.err.println("this file is not image type");
				return null;
			}

			String fileName = uploadFile.getOriginalFilename();

			System.out.println("fileName : " + fileName);

			// 날짜 폴더 생성
			String folderPath = makeFolder();
			// UUID - 파일명 중복 방지
			String uuid = UUID.randomUUID().toString();
			// 저장할 파일 이름 중간에 "_"를 이용하여 구분

			String uploadFileName = folderPath + File.separator + uuid + "_" + fileName;

			String saveName = uploadPath + File.separator + uploadFileName;

			Path savePath = Paths.get(saveName);
			// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
			System.out.println("path : " + saveName);
			try {
				uploadFile.transferTo(savePath);
				// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
			} catch (IOException e) {
				e.printStackTrace();
			}
			// DB에 해당 경로 저장
			// 1) 사용자가 업로드할 때 사용한 파일명
			// 2) 실제 서버에 업로드할 때 사용한 경로
			imageList.add(setImagePath(uploadFileName)); // 아작스로 사용하기 때문에 데이터를 변수에 담아서 사용자에게 전달
		}

		return imageList;
	}

	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); // Date 클래스 simpledateformat 사용해도 됨
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator);
		File uploadPathFoler = new File(uploadPath, folderPath); // 자바에서 지원하는 File 클래스
		// File newFile= new File(dir,"파일명");
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}
		return folderPath;
	}

	private String setImagePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}

}
