package com.yedam.app.emp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.emp.service.EmpVO;

public interface EmpMapper {
	// 쿼리문 단위로 메소드 생성
	// mapper의 목적 -> 쿼리문 실행
	// 등록수정삭제의 리턴 타입은 정수(int) = dbms에 따라서 리턴 값이 달라짐
	
	
	// 전체 조회
	public List<EmpVO> selectEmpAllList();
	
	// 단건 조회
	public EmpVO selectEmpInfo(EmpVO empVO);
	
	// 등록
	public int insertEmpInfo(EmpVO empVO);
	
	// 수정
	//public int updateEmpInfo(int empId, EmpVO empVO);
	//매개변수가 두개이상일 경우 @Params(아파치 거) 사용 
	//@Params = xml 내부에서 사용하는 변수
	public int updateEmpInfo(@Param("id") int empId, @Param("emp")EmpVO empVO);
	// @Param(emp) => 넘겨주는 매개변수가 두개이상(id, emp)일 경우 xmp에서 emp.~ 작성 
	
	// 삭제
	public int deleteEmpInfo(int empId);

}
