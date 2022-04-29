package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 컨트롤러는 컴파일이 스프링이 스프링컨테이너에 넣어둠
public class MemberController {
	/*
	이렇게 하면 멤버 컨트롤러 말고 다른 컨트롤러가 MmeberSeirve 를 가져다가 쓸 수 없게 된다.
	즉, 필요할 때 마다 생성해야한다. 이렇게 하는 방법보다는 하나만 만들어서 가져다 쓰는 방식으로 하는게 좋다.

	private final MemberService memberService = new MemberService();
	*/

	private final MemberService memberService;

	@Autowired  // 컨트롤러와 서비스를 연결해줌. 이렇게 해주면 컨트롤러가 생성될때 스프링 빈에 등록되어 있는 MemberService 객체를 가져다가 넣어줌.
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/members/new")
	public String createForm(){
		return "members/createMemberForm";
	}

	@PostMapping("/members/new")
	public String create(MemberForm form){
		Member member = new Member();
		member.setName(form.getName());
		System.out.println(form.getName());
		memberService.join(member);

		return "redirect:/";
	}

	@GetMapping("/members")
	public String list(Model model){
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}

