package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller
public class MemberController {
	
//	private MemberService memberService = new MemberServiceImpl();
	/*
	 * 위의 방식 기존 객체 생성 방식임
	 * 서비스가 동시에 매우 많은 회수가 요청될 경우 그만큼의 객체가 생성됨 너무 낭비
	 * 객체간의 결합도가 높아짐.(B클래스의 수정이 일어날 경우 B클래스를 의존하고 있던 A클래스도 전부 바꿔줘야 한다.)
	 */
	@Autowired // 의존성 주입
	private MemberService memberService; // 의존성 주입
	
	@Autowired // spring이 알아서 관리하게 해주는 연결고리!
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	// bean으로 등록된 객체와 연결을 해줘야함~ @Autowired
	/*
	 * spring의 DI(Dependency Injection)을 이용한 방식 의존성주입
	 * 이건 Field Injection방식임
	 * 필드에 선언을 해놓고 @Autowired라는 애노테이션을 달아서 밑에 필드와 일치하는 타입을
	 * bean객체 중에 검색해서 필드로 주입을 시켜주는 장점이 있음.
	 */
	
	// RequestMapping타입의 애노테이션을 붙여줌으로써 HandlerMapping을 등록
//	@RequestMapping(value="login.me")
//	public void loginMember() {
//		System.out.println("로그인 요청");
//	}
	
	/*
	 * Spring 요청시 전달값(Parameter)을 받는 방법
	 * 
	 * 1. HttpServletReuqest를 이용해서 전달받기(기존의 JSP/Servlet방식)
	 * 해당 메서드의 매개변수로 HttpServletReuqest타입을 작성해두면
	 * DispatcherServlet이 해당 메서드를 호출할 때 해당 객체를 전달해서 매개변수로 주입해줌
	 */
	
//	@RequestMapping(value="login.me")
//	public String loginMember(HttpServletRequest request) {
//		String userId = request.getParameter("userId");
//		String userPwd = request.getParameter("userPwd");
//		
//		System.out.println("userId : " + userId);
//		System.out.println("userPwd : " + userPwd);
//		
//		return "main";
//	}
	
	/*
	 * 2. @RequestParam 애노테이션 이용하는 방법
	 * request.getParameter("키")로 value를 뽑아오는 역할을 대신해주는 애노테이션
	 * value속성의 값으로 jsp에서 작성했던 name속성값을 적으면 알아서 해당 매개변수로 받아올 수 있다.
	 * 만약, 넘어온 값이 비어있는 형태라면 defaultValue속성으로 기본값을 지정할 수 있다.
	 * 
	 */
//	@RequestMapping("login.me")
//	public String loginMember(@RequestParam(value="userId", defaultValue="aaa") String userId,
//			@RequestParam(value="userPwd") String userPwd) {
//		
//		System.out.println(userId);
//		System.out.println(userPwd);
//		
//		return "main";
//	}
	
	/*
	 * 3. @RequestParam 애노테이션 생략하는 방법
	 * 단, 매개변수명을 jsp의 name속성값(요청 시 전달하는 값의 키값)과 동일하게 세팅해둬야 자동으로 값이 주입
	 * 단점으로는 위에서 사용했던 defaultValue속성을 사용할 수 없음.
	 */
//	@RequestMapping("login.me")
//	public String loginMember(String userId, String userPwd) {
//		
//		Member m = new Member();
//		m.setUserId(userId);
//		m.setUserPwd(userPwd);
//		
//		System.out.println(m.getUserId());
//		
//		return "main";
//	}
	
	/*
	 * 4. 커맨드 객체 방식
	 * name속성값과 필드명 동일
	 * 기본생성자 존재해야함
	 * setter메소드가 존재해야함
	 * 
	 * 해당 메소드의 매개변수로
	 * 요청 시 전달값을 담고자하는 VO클래스의 타입을 세팅 후
	 * 요청 시 전달값의 키값(jsp의 name속성값)을 VO클래스의 담고자하는 필드명으로 작성
	 * 
	 * 스프링 컨테이너가 해당 객체를 기본생성자로 생성 후 내부적으로 setter메소드를 찾아서
	 * 요청 시 전달값을 해당 필드에 담아줌(setter injection)
	 */
//	@RequestMapping("login.me")
//	public String loginMember(Member m) {
//		
////		System.out.println("userId : " + m.getUserId());
////		System.out.println("userPwd : " + m.getUserPwd());
////		new MemberSerivceImpl().loginMember(m);
//		Member loginUser = memberService.loginMember(m);
//		
//		if(loginUser == null) { // 로그인 실퍠 => 에러 문구를 requestScope에 담아서 에러페이지 포워딩
//			System.out.println("로그인 실패");
//		} else { // 로그인 성공=> loginUser를 sessionScope에 담고 메인페이지로 url 재요청
//			System.out.println("로그인 성공!");
//		}
//		
//		return "main";
//	}
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 url재요청 하는 방법
	 * 1. 스프링에서 request담아서 보내고 싶을때 사용할수 있는 객체 Model객체를 이용하는 방법!
	 * 포워딩할 응답 뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 * Model객체는 requestScope / Model이 request를 대체한다 생각하면됨.
	 * 단, setAttribute가 아닌 addAttribute메소드를 호출해야함.
	 */
	
	// @PostMapping
	// @GetMapping
	// 모든 다 수용 RequestMapping
//	@RequestMapping("login.me")
//	public String loginMember(Member m, Model model, HttpSession session) {
//		Member loginUser = memberService.loginMember(m);
//		
//		if(loginUser == null) {
//			// 로그인 실패 -> 에러문구 requestScope에 담아서 에러페이지로 포워딩
//			model.addAttribute("errorMsg", "에러가남");
//			/*
//			 * 포워딩? ( 파일 경로를 포함한 파일 명을 제시 )
//			 * return "xxx"
//			 * servlet-context.xml <- 자동완성도구 ViewResolver
//			 * - prefix : /WEB-INF/views/
//			 * 중간 내용을 입력해주면 됨(파일 경로를 포함한 파일명)
//			 * - suffix : .jsp
//			 */
//			return "common/errorPage";
//		} else { // 로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url요청
//			session.setAttribute("loginUser", loginUser);
//			// url재요청방식 == sendRedirect(url경로를 제시)
//			// redirect:요청할url
//			return "redirect:/"; // 
//			// localhost:8007/spring/
//		}
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView객체를 사용하는 방법
	 * 스프링에서 어차피 controller에서 값이 나갈때는 
	 * DispatcherServlet를 또 거침. 그리고 거치는 과정에서 DispatcherServlet에 도달했을때는
	 * 형태가 ModelAndView로 바뀜. 그러므로 즉, 미리 보낼때 ModelAndView로 바꾸면 좋음.
	 * 
	 * Model은 request대용임 request가 비효율이라 대신 Model을 써라 제공해주는 것
	 * 
	 * Model은 데이터를 key-value세트로 담을 수 있는 공간이라고 한다면
	 * View라는 객체는 응답뷰에 대한 정보를 담을 수 있는 공간
	 * 
	 * Model과 View가 합쳐진 객체는 ModelAndView객체임
	 * 단, Model객체는 단독 사용이 가능하지만 View객체는 ModelAndView타입으로만 사용이 가능
	 * 
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, ModelAndView mv, HttpSession session) {
		
		Member loginUser = memberService.loginMember(m);
		
		//bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd());
		// 암호문과 평문을 비교해서 맞으면 true 틀리면 false
		// Member m의 userId필드 : 사용자가 입력한 아이디값
		// Member loginUser의 userId필드 : 실제 아이디값(조회된 ID값)
		// Member md의 userPwd필드 : 사용자가 입력한 비밀번호(평문이 들어있음)
		// Member loginUser의 userPwd필드 : DB기록된 암호화된 pwd가 조회된 값
		// BCryptPasswordEncoder 객체에 matches()메소드
		// matches(평문, 암호문) 비교 후 true / false 반환
		// 암호문에 포함되어있는 Salt값을 판단해서 평문에 Salt값을 더해서 암호화를 진행하고
		// 두 값이 같은지 비교후 일치하면 true / 일치하지 않으면 false 반환
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 로그인 성공
			session.setAttribute("loginUser", loginUser); // session에 담아서 보내기
			mv.setViewName("redirect:/"); // 뷰에 대한 정보를 넘겨준다.
		} else {
			// model.addAttribute
			// mv.addObject
			mv.addObject("errorMsg", "안돼").setViewName("common/errorPage");
			// 메소드 체이닝이 되는 이유는 mv == ModelAndView객체 안에 addObject > return
			// setViewName > return 같은 곳에서 리턴을 하기 떄문에 mv.addObject 리턴 후 돌아와서
			// 다시 setViewName으로 가서 값을 넣고 다시 리턴을 해오기 떄문임
			// 즉, VO클래스 느낌에 값을 한번에 넣을 수 있음.
		}
		return mv;      //14:28
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollFrom() {
		return "member/memberEnrollForm";
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model) {
		
		//memberService.insertMember(m);
		// 1. 한글 문자가 인코딩 때문에 깨짐! => web.xml에 스프링에서 제공하는 인코딩 필터 등록
		// 2. 나이를 입력하지 않았을 경우 int자료형에 빈 문자열이 넘어오기 때문에 자료형이 맞지 않는 문제 발생
		// (400 Bad Request Error 발생)
		// => Member클래스의 age필드의 int형 => String형으로 변경
		// 3. 비밀번호가 사용자가 입력한 있는 그대로의 평문이 문제임
		// Bcrypt방식을 이용해서
		// 스프링 시큐리티 모듈에서 제공(pom.xml에 라이브러리 추가)
		// BcryptPasswordEncoder 클래스를 .xml파일에 bean으로 등록
		// => web.xml에 spring-security.xml파일을 로딩할 수 있도록 작성
		
		// 암호화란 무엇인가?
		// 암호화 알고리즘을 이용!
		// 평문(프레임 텍스트) == 암호화가 안된 문장
		// 스프링 시큐리티! 사용!
		
		//System.out.println("평문 : " + m.getUserPwd());
		// 평문 결과 : 1234
		// 암호화 작업(암호문을 만들어내는 과정)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		m.setUserPwd(encPwd); // Member객체에 userPwd필드에 평문x 암호문 값 변경 후 호출
		if(memberService.insertMember(m) > 0) { // 성공 => 메인페이지
			return "redirect:/";
			// mv.setViewName("redirect:/");
		} else { // 실패 => 에러문구를 담아서 에러페이지로 포워딩
			model.addAttribute("errorMsg", "회원가입 실패");
			return "common.errorPage";
			//mv.addObject("errorMsg", "회원가입 실패").setViewName("commom.errorPage");
		}
		//System.out.println("암호문 : " + encPwd);
		// encode메소드가 존재함( rawPassword == 평문 넣어라 )
		// 암호문 결과 : $2a$10$oQj/gpTwshOZMqkPs3S.9eJ1zH0x1YyRAKmfn1ct8kjqYWU0/n7pa
		// 두번째 결과 : $2a$10$ThqVgouCQL0gr3RrrBEDzOLYWIWNCgI7RQC5tPDYOdwEm/Q4.u2FS
		
	}
	

	//asdasdasdasdasd
	
	
	
	
	
	
	
	
}
