package com.sungwon.api.board.controller;

import com.sungwon.api.board.dto.BoardDTO;
import com.sungwon.api.board.entity.Board;
import com.sungwon.api.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Board")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Operation(summary = "게시물 조회")
    @GetMapping("/list")
    public List<BoardDTO> boardList() { return boardService.getBoardList(); }

    @Operation(summary = "게시물 상세조회")
    @GetMapping("/detail/{id}")
    public BoardDTO getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @Operation(summary = "게시물 등록")
    @PostMapping("")
    @Transactional("transactionManager")
    public Board create(@RequestBody BoardDTO boardDto) {
        return boardService.insert(boardDto);
    }

    @Operation(summary = "게시물 수정")
    @PatchMapping("")
    @Transactional("transactionManager")
    public Board update(@RequestBody BoardDTO boardDto) {
        return boardService.update(boardDto);
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/{id}")
    @Transactional("transactionManager")
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }

}
