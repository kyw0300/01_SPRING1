package kr.or.ddit.free.dao;

import java.util.List;

import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IFreeDAO {

	int selectFreeCount(PaginationInfoVO<FreeVO> pagingVO);

	List<FreeVO> selectFreeList(PaginationInfoVO<FreeVO> pagingVO);

	int insertFree(FreeVO freeVO);

	void incrementHit(int boNo);

	FreeVO selectFree(int boNo);

	int updateFree(FreeVO freeVO);

	int deleteFree(int boNo);

}
