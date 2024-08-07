package com.yedam.app.emp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.emp.mapper.EmpMapper;
import com.yedam.app.emp.service.EmpService;
import com.yedam.app.emp.service.EmpVO;

import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class EmpServiceImpl implements EmpService {

	private EmpMapper empMapper;

	@Autowired
	public EmpServiceImpl(EmpMapper empMapper) {
		this.empMapper = empMapper;
	}

	@Override
	public List<EmpVO> empList() {
		return empMapper.selectEmpAllList();
	}

	@Override
	public EmpVO empInfo(EmpVO empVO) {
		return empMapper.selectEmpInfo(empVO);
	}

	@Override
	public int empInsert(EmpVO empVO) {
		int result = empMapper.insertEmpInfo(empVO);

		return result == 1 ? empVO.getEmployeeId() : -1;
	}

	@Override
	public Map<String, Object> empUpdate(EmpVO empVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;

		int result = empMapper.updateEmpInfo(empVO.getEmployeeId(), empVO);

		if (result == 1) {
			isSuccessed = true;
		}

		// isSuccessed와 empVO의 타입은 다르다. 서로 다른 타입의 값을 한 번에 전달하려면 Map을 사용하면 편리하다.
		map.put("result", isSuccessed);
		map.put("target", empVO);

		return map;
	}

	@Override
	public Map<String, Object> empDelete(int empId) {
		Map<String, Object> map = new HashMap<>();

		int result = empMapper.deleteEmpInfo(empId);

		if (result == 1) {
			map.put("employeeId", empId);
		}
		// 실패 = {} : 빈 객체 반환 : map으로 new map을 생성했기 때문에 객체가 반환은 됨
		// 성공 = {"employeeId", 100}
		return map;
	}

}
