package kr.or.ddit.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int boNo;			// 일반 게시판 번호
	private String boTitle;		// 일반 게시판 제목
	private String boWriter;	// 일반 게시판 작성자
	private String boContent;	// 일반 게시판 내용
	private String boDate;		// 일반 게시판 작성일
	private int boHit;			// 일반 게시판 조회수
	
	
}
