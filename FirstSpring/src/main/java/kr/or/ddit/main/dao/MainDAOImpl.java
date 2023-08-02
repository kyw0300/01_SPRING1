package kr.or.ddit.main.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.NoticeVO;

@Repository
public class MainDAOImpl implements IMainDAO {

	@Inject
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<BoardVO> selectBoardList() {
		return sqlSession.selectList("Main.selectBoardList");
	}

	@Override
	public Map<String, Integer> selectCount() {
		return sqlSession.selectOne("Main.selectCount");
	}

	@Override
	public List<NoticeVO> selectNoticeList() {
		return sqlSession.selectList("Main.selectNoticeList");
	}

	@Override
	public List<FreeVO> selectFreeList() {
		return sqlSession.selectList("Main.selectFreeList");
	}

}
