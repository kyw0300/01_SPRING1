package kr.or.ddit.free.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IFreeService {

	int selectFreeCount(PaginationInfoVO<FreeVO> pagingVO);

	List<FreeVO> selectFreeList(PaginationInfoVO<FreeVO> pagingVO);

	ServiceResult insertFree(FreeVO freeVO);

	FreeVO selectFree(int boNo);

	ServiceResult updateFree(FreeVO freeVO);

	ServiceResult deleteFree(int boNo);

}
