package kr.or.ddit.free.web;

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
import kr.or.ddit.free.service.IFreeService;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/free")
public class FreeController {
	
	@Inject
	private IFreeService freeService;
	
	@RequestMapping(value = "/list.do")
	public String freeList(@RequestParam(name="page", required = false, defaultValue = "1") int currentPage,
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
		
		if(session.getAttribute("type") != null && !session.getAttribute("type").equals("")) {
			if(session.getAttribute("type").equals("free")) {
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
		
		PaginationInfoVO<FreeVO> pagingVO = new PaginationInfoVO<FreeVO>();
		
		if(StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType",searchType);
			model.addAttribute("searchWord",searchWord);
		}
		
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord = freeService.selectFreeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<FreeVO> dataList = freeService.selectFreeList(pagingVO);
		pagingVO.setDataList(dataList);
		model.addAttribute("pagingVO",pagingVO);
		
		return "free/list";
	}
	
	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String freeForm() {
		return "free/form";
	}
	
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String insertFree(FreeVO freeVO, Model model) {
		String goPage = "";
		Map<String, Object> errors = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(freeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요!");
		}
		if(StringUtils.isBlank(freeVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요!");
		}
		
		if(errors.size() > 0) { // 에러가 발생!
			// model은 데이터 전달자
			// 내가 넘기고자 하는 데이터를 대신 넘겨주는 역할을 담당한다.
			model.addAttribute("errors", errors);
			model.addAttribute("free", freeVO);
			goPage = "free/form";
		} else { // 에러가 발생하지 않고 정상적인 데이터가 넘어옴(정상)
			freeVO.setBoWriter("a001");
			ServiceResult result = freeService.insertFree(freeVO);
			if(result.equals(ServiceResult.OK)) { // 등록 성공
				goPage = "redirect:/free/detail.do?boNo=" + freeVO.getBoNo();
			} else { // 등록 실패
				errors.put("msg", "서버에러! 다시 시도해주세요!");
				model.addAttribute("errors", errors);
				goPage = "free/form";
			}
		}
		return goPage;
	}
	
	@RequestMapping(value = "detail.do", method = RequestMethod.GET)
	public String freeDetail(int boNo, Model model) {
		String goPage = "";
		FreeVO freeVO = freeService.selectFree(boNo);
		if(freeVO == null) {
			goPage = "error";
		} else {
			model.addAttribute("free", freeVO);
			goPage = "free/view";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String updateFreeForm(int boNo, Model model) {
		String goPage = "";
		FreeVO freeVO = freeService.selectFree(boNo);
		if(freeVO == null) {
			goPage = "error";
		} else {
			model.addAttribute("free", freeVO);
			model.addAttribute("status", "u");
			goPage = "free/form";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String updateFree(FreeVO freeVO, Model model) {
		String goPage = "";
		ServiceResult result = freeService.updateFree(freeVO);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/free/detail.do?boNo=" + freeVO.getBoNo();
		} else {
			model.addAttribute("free", freeVO);
			model.addAttribute("status", "u");
			goPage = "free/form";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String deleteFree(int boNo, Model model) {
		String goPage = "";
		ServiceResult result = freeService.deleteFree(boNo);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/free/list.do";
		} else {
			goPage = "redirect:/free/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}
