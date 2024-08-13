package com.yedam.app.board.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.board.mapper.BoardMapper;
import com.yedam.app.board.service.BoardService;
import com.yedam.app.board.service.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	private BoardMapper boardMapper;

	// 생성자 주입
	@Autowired
	BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	// 전체 조회
	@Override
	public List<BoardVO> boardList() {
		return boardMapper.selectBoardAll();
	}

	// 단건 조회
	@Override
	public BoardVO boardInfo(BoardVO boardVO) {
		return boardMapper.selectBoardInfo(boardVO);
	}

	// 등록
	@Override
	public int insertBoard(BoardVO boardVO) {
		int result = boardMapper.insertBoardInfo(boardVO);
		return result == 1 ? boardVO.getBno() : -1;
	}

	// 수정
	// Map 사용하는 이유 : 아작스 통신을 하면 넘길 데이터가 많다.
	// Object : 클래스, 데이터 타입 상관 없이 값을 담아서 사용하겠다.
	@Override
	public Map<String, Object> updateBoard(BoardVO boardVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;

		int result = boardMapper.updateBoardInfo(boardVO);
		if (result == 1) {
			isSuccessed = true;
		}

		// 선택 사항
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = sdf.format(today);

		map.put("date", updateDate);
		map.put("result", isSuccessed);
		map.put("target", boardVO);

		return map;
	}

	// 구현 클래스에서 기능들을 분리해서 사용하는 방법도 있다. / 이 기능을 현재 있는내부에서만 사용한다면 인터페이스에 추가할 필요는 없다.
	/*
	 * private String getUpdateDate() { Date today = new Date(); SimpleDateFormat
	 * sdf = new SimpleDateFormat("yyyy-MM-dd"); return sdf.format(today); }
	 */
	
	// 삭제
	@Override
	public int deleteBoard(int boardNO) {
		return boardMapper.deleteBoardInfo(boardNO);
	}

}
