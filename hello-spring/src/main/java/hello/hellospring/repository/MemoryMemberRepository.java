package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

	private static Map<Long, Member> store = new HashMap<>();   // 저장소(해시로 저장)
	private static long sequence = 0L;                          // 회원ID (시스템적으로 자동으로 할당하는 것을 여기서함)

	@Override
	public Member save(Member member) {
		member.setId(++sequence);                               // 회원ID를 1 증가하고 넣어줌 (회원ID 설정)
		store.put(member.getId(), member);                      // member(회원) 을 저장소에 저장
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
//		return store.get(id);   // 이렇게 하면 되는게 만약에 널일 가능성 있다.
		return Optional.ofNullable(store.get(id));  // 따라서 이렇게 해주면 나중에 뒤에서 널 처리 가능
	}

	@Override   // Map 에서 루프로 돌면서 하나 찾아지면 반환하고 하나도 못찾아지면 Optional 에 널이 포함되서 반환된다.
	public Optional<Member> findByName(String name) {
		return store.values().stream()                              // 루프로 돌리면서 찾는것
				.filter(member -> member.getName().equals(name))    // 람다, member.getName 가 파라미터 name 과 같으면 member 반환
				.findAny();                                         // 하나라도 찾는 것
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());         // store 은 Map 인데 반환은 List 로 되어 있다. 자바에서는 실무로 활용성이 좋은 리스트로 주로 반환한다.
	}

	public void clearStore(){   // test 에서 사용하기 위한 store 을 비우는 메서드 추가
		store.clear();
	}
}
