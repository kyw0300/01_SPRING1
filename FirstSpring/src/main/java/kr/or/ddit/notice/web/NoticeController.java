package kr.or.ddit.notice.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.notice.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Inject
	private INoticeService noticeService;

	@RequestMapping(value = "/list.do")
	public String noticeList(
			@RequestParam(name="page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			@RequestParam(required = false) String type,
			Model model,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		System.out.println("type : "+type);
		System.out.println("searchType : "+searchType);
		System.out.println("searchWord : "+searchWord);
		
		System.out.println("sessionType : "+session.getAttribute("type"));
		System.out.println("sessionSearchType : "+session.getAttribute("searchType"));
		System.out.println("sessionSearchWord : "+session.getAttribute("searchWord"));
//		session.setAttribute("searchType", searchType);
//		session.setAttribute("searchWord", searchWord);
		
		if(session.getAttribute("type") != null && !session.getAttribute("type").equals("")) {
			if(session.getAttribute("type").equals("notice")) {
				searchType = (String) session.getAttribute("searchType");
				searchWord = (String) session.getAttribute("searchWord");
			} else {
				session.invalidate();
			}
		}
		
		System.out.println("type : "+type);
		System.out.println("searchType : "+searchType);
		System.out.println("searchWord : "+searchWord);
		
		session = request.getSession();
		System.out.println("sessionType : "+session.getAttribute("type"));
		System.out.println("sessionSearchType : "+session.getAttribute("searchType"));
		System.out.println("sessionSearchWord : "+session.getAttribute("searchWord"));
		
		PaginationInfoVO<NoticeVO> pagingVO = new PaginationInfoVO<NoticeVO>();
		
		if(StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType",searchType);
			model.addAttribute("searchWord",searchWord);
		}
		
		pagingVO.setCurrentPage(currentPage);
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("type",type);
		map.put("searchType",searchType);
		map.put("searchWord",searchWord);
		
		int totalRecord = noticeService.selectNoticeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<NoticeVO> dataList = noticeService.selectNoticeList(pagingVO);
		pagingVO.setDataList(dataList);
		model.addAttribute("pagingVO",pagingVO);
		
		model.addAttribute("searchMap",map);
		
		return "notice/list";
	}
	
	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String noticeForm() {
		return "notice/form";
	}
	
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String insertNotice(NoticeVO noticeVO, Model model) {
		String goPage = "";
		Map<String, Object> errors = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요!");
		}
		if(StringUtils.isBlank(noticeVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요!");
		}
		
		if(errors.size() > 0) { // 에러가 발생!
			// model은 데이터 전달자
			// 내가 넘기고자 하는 데이터를 대신 넘겨주는 역할을 담당한다.
			model.addAttribute("errors", errors);
			model.addAttribute("notice", noticeVO);
			goPage = "notice/form";
		} else { // 에러가 발생하지 않고 정상적인 데이터가 넘어옴(정상)
			noticeVO.setBoWriter("a001");
			ServiceResult result = noticeService.insertNotice(noticeVO);
			if(result.equals(ServiceResult.OK)) { // 등록 성공
				goPage = "redirect:/notice/detail.do?boNo=" + noticeVO.getBoNo();
			} else { // 등록 실패
				errors.put("msg", "서버에러! 다시 시도해주세요!");
				model.addAttribute("errors", errors);
				goPage = "notice/form";
			}
		}
		return goPage;
	}
	
	@RequestMapping(value = "detail.do", method = RequestMethod.GET)
	public String noticeDetail(int boNo, String searchType, String searchWord, Model model) {
		String goPage = "";
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		if(noticeVO == null) {
			goPage = "error";
		} else {
			model.addAttribute("notice", noticeVO);
//			model.addAttribute("searchType", searchType);
//			model.addAttribute("searchWord", searchWord);
			goPage = "notice/view";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String updateNoticeForm(int boNo, Model model) {
		String goPage = "";
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		if(noticeVO == null) {
			goPage = "error";
		} else {
			model.addAttribute("notice", noticeVO);
			model.addAttribute("status", "u");
			goPage = "notice/form";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String updateNotice(NoticeVO noticeVO, Model model) {
		System.out.println("update");
		String goPage = "";
		ServiceResult result = noticeService.updateNotice(noticeVO);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/detail.do?boNo=" + noticeVO.getBoNo();
		} else {
			model.addAttribute("notice", noticeVO);
			model.addAttribute("status", "u");
			goPage = "notice/form";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String deleteNotice(int boNo, Model model) {
		String goPage = "";
		ServiceResult result = noticeService.deleteNotice(boNo);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/list.do";
		} else {
			goPage = "redirect:/notice/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}
