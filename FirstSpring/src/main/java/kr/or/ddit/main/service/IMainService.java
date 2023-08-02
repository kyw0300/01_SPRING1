package kr.or.ddit.main.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.NoticeVO;

public interface IMainService {

	public List<BoardVO> selectBoardList();

	public Map<String, Integer> selectCount();

	public List<NoticeVO> selectNoticeList();

	public List<FreeVO> selectFreeList();

}
