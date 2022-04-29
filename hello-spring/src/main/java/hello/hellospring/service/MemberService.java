package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service // 이게 있으면 스프링 컨테이너에 딱 등록을 함
public class MemberService {
	private final MemberRepository memberRepository;

//	@Autowired // 이것 또한 MemberRepository 객체가 필요하기 떄문에 Autowired 를 해줘야 MemberRepository 를 넣어줌
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * 회원 가입
	 */
	public Long join(Member member){
		// 이름이 같은 회원x
//		Optional<Member> result = memberRepository.findByName(member.getName()); // Optional 로 감싼 덕분에 널일때도 여러 메서드 사용 가능
//		result.ifPresent(m -> {     // 만약 널일 경우
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		});

		// 위 방법은 참조변수를 선언해야하니까 이렇게 간단하게 할 수도 있음 
		// 그런데 이 작업은 로직이 쭉 나오니까 그냥 메서드로 만드는 것이 좋음
//		memberRepository.findByName(member.getName())
//				.ifPresent(m ->{
//					throw new IllegalStateException("이미 존재하는 회원입니다.");
//				});

		validateDuplicatMmeber(member); // 중복회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicatMmeber(Member member) {
		memberRepository.findByName(member.getName())
						.ifPresent(m ->{
							throw new IllegalStateException("이미 존재하는 회원입니다.");
						});
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	/**
	 * 회원 아이디로 조회
	 */
	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
}
