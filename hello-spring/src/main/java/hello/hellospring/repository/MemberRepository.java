package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
	Member save(Member member);                 // 회원을 저장하고 저장된 회원 객체를 반환
	Optional<Member> findById(Long id);         // 저장소에서 아이디로 찾아옴
	Optional<Member> findByName(String name);   // 저장소에서 이름으로 찾아옴
	List<Member> findAll();                     // 지금까지 저장된 모든 회원 리스트 반
}
