package kr.or.ddit.book.web;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.book.service.BookService;

/*
 * @Controller 어노테이션이 있는 클래스는 스프링이 브라우저의 요청(request)을 받아들이는 컨트롤러라고 인지해서
 * 자바 빈(Java Bean)으로 등록해서 관리한다.
 * 여기서 자바 빈이란 객체를 만들어서 메모리에 올리는 형태를 말한다.
 */
@Controller
@RequestMapping("/book")
public class BookInsertController {
	
	/*
	 * 서비스를 호출하기 위해 BookService를 의존성을 주입한다.
	 * 의존성 주입을 통한 결합도 낮추기
	 */
	@Inject
	private BookService bookService;
	
	/*
	 * @RequestMapping
	 * - 요청 URL을 어떤 메소드가 처리할 지 여부를 결정
	 * 
	 * method 속성은 http 요청 메소드를 의미한다.
	 * 일반적인 웹 페이지 개발에서 GET 메소드는 데이터를 변경하지 않는 경우에, POST 메소드는 데이터가 변경될 경우 사용된다.
	 * 
	 * ModelAndView는 컨트롤러가 반환할 데이터를 담당하는 모델(Model)과 화면을 담당하는 뷰(View)의 경로를 합쳐놓은 객체다.
	 * ModelAndView의 생성자에 문자열 타입 파라미터가 입력되면 뷰의 경로라고 간주한다.
	 * 뷰의 경로를 'book/form'과 같은 형태로 전달하는 이유는 요청(request)에 해당하는 url의 mapping되는 화면의 경로 값을
	 * viewresolver라는 녀석의 제일 먼저 받아 surfix, prefix 속성에 의해서 앞에는 '/WEB-INF/views/'가 붙고
	 * 뒤에는 '.jsp'가 붙어 최종 위치에 해당하는 jsp파일을 찾아준다.
	 */
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public ModelAndView bookForm() {
		return new ModelAndView("book/form"); // forward(URL을 반환하지 않음.)
	}
	
	/*
	 * 데이터의 변경이 일어나므로 http 메소드는 POST 방식으로 처리
	 * @RequestParam은 HTTP 파라미터를 map변수에 자동으로 바인딩한다.
	 * Map 타입의 경우는 @RequestParam을 붙여야만 HTTP 파라미터의 값을 map안에 바인딩해준다. 
	 */
	@RequestMapping(value="/form.do", method = RequestMethod.POST)
	public ModelAndView insertBook(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		// 서비스 메소드 insertBook를 호출하여 책을 등록한다.
		// 서비스 메소드 insertBook을 통해서 책을 등록하고 결과로 bookId를 리턴 받아온다.
		String bookId = bookService.insertBook(map);
		if(bookId == null) {
			// 데이터 입력이 실패할 경우 다시 데이터를 입력받아야 하므로 생성 화면으로 redirect한다.
			// ModelAndView 객체는 .setViewName 메소드를 통해 뷰의 경로를 지정할 수 있다.
			mav.setViewName("redirect:/book/form.do");
			// 뷰의 경로가 redirect:로 시작하면 스프링은 뷰 파일을 찾아가는게 아니라
			// 웹 페이지의 주소(/form.do)를 찾아간다.
		} else {
			// 데이터 입력이 성공하면 상세 페이지로 이동한다.
			mav.setViewName("redirect:/book/detail.do?bookId=" + bookId);
		}
		return mav;
	}
}
