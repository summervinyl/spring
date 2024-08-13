package com.yedam.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.board.mapper.BoardMapper;
import com.yedam.app.board.service.BoardVO;

@SpringBootTest
class Boot02ApplicationTests {

	@Autowired
	BoardMapper boardMapper;

	// 전제 조회
	// @Test
	void boardList() {
		List<BoardVO> list = boardMapper.selectBoardAll();
		// System.err.println(list);
		assertTrue(!list.isEmpty());
	}

	// 단건 조회
	// @Test
	void boardInfo() {
		BoardVO boardVO = new BoardVO();
		boardVO.setBno(100);

		BoardVO findVO = boardMapper.selectBoardInfo(boardVO);
		assertEquals(100, findVO.getBno());
	}

	// 등록
	@Test
	void boardInsert() {
		BoardVO boardVO = new BoardVO();

		boardVO.setTitle("테스트 중");
		boardVO.setContents("잘 됐으면 좋겠다.");
		boardVO.setWriter("냥");
		boardVO.setRegdate(new Date());

		int result = boardMapper.insertBoardInfo(boardVO);
		System.err.println(" 게시글 제목 : " + boardVO.getTitle());

		assertEquals(1, result);
	}

	// 삭제
	// @Test
	void boardDelete() {
		int result = boardMapper.deleteBoardInfo(200);
		assertEquals(1, result);
	}

	// 수정
	// @Test
	void boardUpdate() {
		// 수정할 대상 단건 조회
		BoardVO boardVO = new BoardVO();
		boardVO.setBno(200);

		BoardVO findVO = boardMapper.selectBoardInfo(boardVO);
		System.err.println("★★★★★★" + findVO);

		// 사용자의 수정 내용 입력
		findVO.setTitle("화이팅");

		//
		int result = boardMapper.updateBoardInfo(findVO);
		System.err.println(findVO.getTitle());
		assertEquals(1, result);
	}

}
