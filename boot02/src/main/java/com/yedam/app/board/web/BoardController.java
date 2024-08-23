package com.yedam.app.board.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.board.service.BoardService;
import com.yedam.app.board.service.BoardVO;

@Controller
public class BoardController {
	private BoardService boardService;

	@Autowired
	private BoardController(BoardService boardService) {
		this.boardService = boardService;

	}

	// 전체조회 : URI - boardList / RETURN - board/boardList
	@GetMapping("boardList")
	public String boardList(Model model) { // Model 스프링 프레임 워크가 제공하는 객체
		List<BoardVO> BoardList = boardService.boardList();
		model.addAttribute("boards", BoardList); // ("사용할 변수명", 페이지에서 사용할 데이터)

		return "board/boardList";
		// prefix + return + suffix => classpath:/templates/ + board/boardList + .html
	}

	// 단건조회 : URI - boardInfo / PARAMETER - BoardVO(QueryString)
	// RETURN - board/boardInfo
	@GetMapping("boardInfo")
	public String boardInfo(BoardVO boardVO, Model model) {
		BoardVO getBoard = boardService.boardInfo(boardVO);

		model.addAttribute("board", getBoard);

		return "board/boardInfo";
	}

	// 등록 - 페이지 : URI - boardInsert / RETURN - board/boardInsert
	@GetMapping("boardInsert")
	public String boardInsertForm() {
		return "board/boardInsert";
	}

	// 등록 - 처리 : URI - boardInsert / PARAMETER - BoardVO(QueryString)
	// RETURN - 단건조회 다시 호출
	@PostMapping("boardInsert") // 등록 일반적으로 폼태그를 활용한다고 생각하고 매개변수를 쿼리스트릥 타입으로 지정, 화면에서 사용자가 값을 어떻게 넘겨줄 건지도 함께
								// 생각해서 코딩하기.
	public String boardInsertProcess(BoardVO boardVO) {
		int bno = boardService.insertBoard(boardVO);

		return "redirect:boardInfo?bno=" + bno;
	}

	// 수정 - 페이지 : URI - boardUpdate / PARAMETER - BoardVO(QueryString)
	// RETURN - board/boardUpdate
	@GetMapping("boardUpdate")
	public String boardUpdateForm(BoardVO boardVO, Model model) {
		// 단건 조회
		BoardVO getBoard = boardService.boardInfo(boardVO);

		model.addAttribute("board", getBoard);
		
		return "board/boardUpdate";
	}

	
	// 수정 - 처리 : URI - boardUpdate / PARAMETER - BoardVO(JSON)
	// RETURN - 수정결과 데이터(Map)
	// => 등록(내부에서 수행하는 쿼리문 - update문)
	// 스프링에서 JSON 처리 방식
	// 리턴 타입이 데이터면 아작스용 컨트롤러라고 생각하면 된다. 
	@PostMapping("boardUpdate")
	@ResponseBody // => 타입에 상관 없이 리턴 타입이 데이터면 @ResponseBody를 사용!
	public Map<String, Object> boardUpdateProcess(@RequestBody BoardVO boardVO) {
		return boardService.updateBoard(boardVO);		
	}
	

	// 삭제 - 처리 : URI - boardDelete / PARAMETER - Integer
	// RETURN - 전체조회 다시 호출
	// Integer : 단일 값 처리한다는 의미, 쿼리스트링 형태로 데이터를 넘겨받아야 한다는 의미 = @RequestParam(Integer
	// bno)에서 @RequestParam이 생략되어있따.
	// 생략하는 이유 : 융통성있게 하려면! 반드시 매개변수 값이 들어와야 하는 경우 명시해야 한다.
	@GetMapping("boardDelete") //@GetMapping 페이지 처리
	public String boardDelete(@RequestParam Integer bno) {
		boardService.deleteBoard(bno);
		return "board/boardList";
	}

}
