package com.yedam.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.emp.mapper.EmpMapper;
import com.yedam.app.emp.service.EmpVO;

//스프링이 컨테이너에 등록한 매퍼를 테스트하는 것이기 때문에 환경 설정(@SpringBootTest)을 해주어야 한다.
@SpringBootTest //테스트 환경에서 IoC 컨테이너를 생성
class Boot01ApplicationTests {
	
	//jUnit 단위테스트
	//생성하고자 하는 대상에 대해서 컨테이너 생성
	//empMapper가 컨테이너 안에 들어갈 것이고, 사용을 위해 호출
	@Autowired // 필드 주입 (강제) -> 보안상 위험이 있어서 테스트 환경에서만 사용
	EmpMapper empMapper;
	//private EmpMapper empMapper;
	//@Autowired을 설정하고 private써도 무시하고 접근하기 때문에 보안에 안 좋음, 그래서 DI에서는 안 쓰임
	
	
	// 메소드 단위로 테슽
	// 메소드마다 독립적이라서, 순서대로 진행되지 않아.
	// 테스트 안 하려면 @Test에만 주석 처리
	// 전체 조회
	//@Test
	void empList() {
		// 전체 조회의 결과를 null로 체크할 수 없다.
		List<EmpVO> list = empMapper.selectEmpAllList();
		//assert : assertTrue 불린 체크 메소드
		//List와 같은 컬렉션 타입은 isEmpty() 메소드를 통해 내부 값이 비어있는지 확인
		//isEmpty() == 비어있음
		assertTrue(!list.isEmpty()); // 값이 비어있지 않으면, 즉 쿼리가 잘 실행되면!!!!
	}
	
	// 단건 조회 테스트
	//@Test
	void empInfo() {
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(100);
		
		EmpVO findVO = empMapper.selectEmpInfo(empVO);
		assertEquals("King", findVO.getLastName());
	}
	
	// 등록 테스트
	//@Test
	void empInsert() {
		EmpVO empVO = new EmpVO();
		
		empVO.setLastName("Yoon");
		empVO.setEmail("yoonn1217@gmail.com");
		empVO.setJobId("IT_PROG");
		//empVO.setSalary(1000);
		
		int result = empMapper.insertEmpInfo(empVO);
		System.err.println("★ employee id : " + empVO.getEmployeeId());
		
		assertEquals(1, result);
	}
	
	// 삭제 테스트
	//@Test
	void empDelete() {
		int result = empMapper.deleteEmpInfo(315);		
		assertEquals(1, result);
	}
	
	// 수정 테스트 : 수정 대상 조회 (단건 조회) => 수정하고 하는 내용 입력 => 업데이트 진행
	@Test
	void empUpdate() {
		// 1) 수정 대상 단건 조회
		EmpVO empVO = new EmpVO();
		empVO.setEmployeeId(315);
		
		EmpVO findVO = empMapper.selectEmpInfo(empVO);
		System.err.println("★ 출력 ★ " + findVO);
		
		// 2) 사용자의 수정 내용 입력
		findVO.setLastName("Min");
				
		// 3) update
		int result = empMapper.updateEmpInfo(findVO.getEmployeeId(), findVO);
		assertEquals(1, result);
	}

}
