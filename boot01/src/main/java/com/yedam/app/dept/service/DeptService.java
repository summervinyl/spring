package com.yedam.app.dept.service;

import java.util.List;
import java.util.Map;

public interface DeptService {
	// 전체 조회
	public List<DeptVO> deptList();

	// 단건 조회
	public DeptVO deptInfo(DeptVO deptVO);

	// 등록
	public int deptInsert(DeptVO deptVO);

	// 수정
	public Map<String, Object> deptUapdate(DeptVO deptVO);

	// 삭제
	public Map<String, Object> deptDelete(int deptId);
}
