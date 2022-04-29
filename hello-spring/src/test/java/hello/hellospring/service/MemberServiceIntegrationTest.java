package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

	@Autowired MemberRepository memberRepository;
	@Autowired MemberService memberService;

	@BeforeEach
	public void beforeEach(){
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}

	@Test
	void 회원가입() { // 테스트코드는 과감하게 한글로 사용해도 실제 빌드에는 포함이 안되서 상관없음
		// given
		Member member = new Member();
		member.setName("spring");

		// when
		Long saveId = memberService.join(member);

		// then
		Member findMember = memberService.findOne(saveId).get();
		assertThat(member.getName()).isEqualTo(findMember.getName());
	} // 그런데 이 테스트는 반쪽짜리 테스트임, 예외케이스가 훨씬 더 있을 수 있다.

	@Test
	void 중복_회원_예외() {
		// given
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");

		//when
		memberService.join(member1);
		/*
		해당 람다를 실행하면 IllegalStateException 예외가 발생하기를 기대한다는 의미
		assertThrows(IllegalStateException.class, () -> memberservie.join(member2));
		 */

		/* 이렇게 해당 메시지가 같은지 확인할 수 있음 */
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*
	이렇게 try-catch 로 만들어도 되는데 좀 복잡하다 따라서 위와 같은 방법을 사용한다.
		try{
			memberservie.join(member2);
			fail();
		} catch (IllegalStateException e){
			assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		}
*/

		//then
	}

	@Test
	void findMembers() {
	}

	@Test
	void findOne() {
	}
}