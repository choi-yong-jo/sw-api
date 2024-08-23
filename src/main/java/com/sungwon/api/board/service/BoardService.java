package com.sungwon.api.board.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sungwon.api.board.dto.BoardDTO;
import com.sungwon.api.board.entity.Board;
import com.sungwon.api.board.entity.QBoard;
import com.sungwon.api.board.repository.BoardRepository;
import com.sungwon.api.common.repository.SearchRepository;
import com.sungwon.api.common.utility.Header;
import com.sungwon.api.common.utility.Pagination;
import com.sungwon.api.common.utility.SearchCondition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sungwon.api.board.entity.QBoard.board;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    SearchRepository searchRepository;

    JPAQueryFactory queryFactory;
    QBoard b;

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

    public Header<List<BoardDTO>> searchBoardList(Pageable pageable, SearchCondition searchCondition) {
        List<BoardDTO> list = new ArrayList<>();

        Page<Board> boards = searchRepository.findAllByBoard(pageable, searchCondition);
        for (Board board : boards) {
            BoardDTO dto = BoardDTO.builder()
                    .boardSeq(board.getBoardSeq())
                    .boardType(board.getBoardType())
                    .memberId(board.getMemberId())
                    .title(board.getTitle())
                    .contents(board.getContents())
                    .createdAt(board.getCreatedAt())
                    .build();

            list.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) boards.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );

        return Header.OK(list, pagination);
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
                .boardType(board.getBoardType())
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
