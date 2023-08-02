package kr.or.ddit.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int boNo;			// 공지 게시판 번호
	private String boTitle;		// 공지 게시판 제목
	private String boWriter;	// 공지 게시판 작성자
	private String boContent;	// 공지 게시판 내용
	private String boDate;		// 공지 게시판 작성일
	private int boHit;			// 공지 게시판 조회수
}
