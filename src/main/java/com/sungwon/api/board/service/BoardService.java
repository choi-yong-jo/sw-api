package com.sungwon.api.board.service;

import com.sungwon.api.board.dto.BoardDTO;
import com.sungwon.api.board.entity.Board;
import com.sungwon.api.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    /**
     * 게시글 목록 가져오기
     */
    public List<BoardDTO> getBoardList() {
        List<Board> list = boardRepository.findAll();
        List<BoardDTO> dtos = new ArrayList<>();
        for(Board board : list) {
            BoardDTO dto = BoardDTO.builder()
                    .boardSeq(board.getBoardSeq())
                    .boardType(board.getBoardType())
                    .title(board.getTitle())
                    .contents(board.getContents())
                    .memberId(board.getMemberId())
//                    .fileList(board.getFileList())
                    .hit(board.getHit())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 게시글 가져오기
     */
    public BoardDTO getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        BoardDTO dto = BoardDTO.builder()
                .boardSeq(board.getBoardSeq())
                .title(board.getTitle())
                .contents(board.getContents())
                .memberId(board.getMemberId())
//                .fileList(board.getFileList())
                .hit(board.getHit())
                .createdAt(board.getCreatedAt())
                .build();

        return dto;
    }

    /**
     * 게시글 등록
     */
    public Board insert(BoardDTO dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .memberId(dto.getMemberId())
                .boardType(dto.getBoardType())
//                .fileList(dto.getFileList())
                .build();
        return boardRepository.save(board);
    }

    /**
     * 게시글 수정
     */
    public Board update(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getBoardSeq()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        board.setTitle(dto.getTitle());
        board.setContents(dto.getContents());
        board.setMemberId(dto.getMemberId());
        board.setBoardType(dto.getBoardType());
//        board.setFileList(dto.getFileList());
        return boardRepository.save(board);
    }

    /**
     * 게시글 삭제
     */
    public void delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(board);
    }
}
