package com.yedam.app.dept.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.dept.mapper.DeptMapper;
import com.yedam.app.dept.service.DeptService;
import com.yedam.app.dept.service.DeptVO;

@Service
public class DeptServiceImpl implements DeptService {
	private DeptMapper deptMapper;

	@Autowired
	public DeptServiceImpl(DeptMapper deptMapper) {
		this.deptMapper = deptMapper;
	}

	// 전체 조회
	@Override
	public List<DeptVO> deptList() {
		return deptMapper.selectAllDeptList();
	}

	// 단건 조회
	@Override
	public DeptVO deptInfo(DeptVO deptVO) {
		return deptMapper.selectDeptInfo(deptVO);
	}

	// 등록
	@Override
	public int deptInsert(DeptVO deptVO) {
		int result = deptMapper.insertDeptInfo(deptVO);

		return result == 1 ? deptVO.getDepartmentId() : -1;
	}

	// 수정
	@Override
	public Map<String, Object> deptUapdate(DeptVO deptVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;

		int result = deptMapper.updateDeptInfo(deptVO.getDepartmentId(), deptVO);

		if (result == 1) {
			isSuccessed = true;
		}

		// isSuccessed와 empVO의 타입은 다르다. 서로 다른 타입의 값을 한 번에 전달하려면 Map을 사용하면 편리하다.
		map.put("result", isSuccessed);
		map.put("target", deptVO);

		return map;
	}

	// 삭제
	@Override
	public Map<String, Object> deptDelete(int deptId) {
		Map<String, Object> map = new HashMap<>();

		int result = deptMapper.deleteDeptInfo(deptId);

		if (result == 1) {
			map.put("departmentId", deptId);
		}
		
		return map;
	}

}
