// 앞서 만든 MemoryMemberRepository 가 잘 만들어진지 확인하기 위해서 테스트를 작성한다.

package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
	MemoryMemberRepository repository = new MemoryMemberRepository();

	// 각각의 테스트 메서드가 실행되고 나서 메모리들을 지워줘야한다. 그렇지 않으면 findALl 테스트에서 생성한 spring1 객체와
	// findByName 에서 생성된 sprint1 둘다 repository 에 들어가게 되고 이러면 우리가 원하는 테스트 결과가 나오지 않는다.
	// 테스트는 서로의 의존관계없이, 순서관계없이 실행되어야 한다.
	@AfterEach // 모든 메서드들이 끝날때 마다 실행되는 메서드
	public void afterEach(){
		repository.clearStore();
	}

	@Test       // 이제 해당 메서드의 테스트코드를 작성해보면 된다.
	public void save() {
		Member member = new Member();             // 객체를 생성하고
		member.setName("spring");

		repository.save(member);
		Member result = repository.findById(member.getId()).get();  // 이렇게 get으로 꺼내는건 좋은 방법은 아닌데 테스트니까 이렇게 해도 됨
//		System.out.println("result : " + (result == member));   // 이렇게 하면 둘이 같으면  result : true 이렇게 나올 것이다. 그런데 이걸 일일히 다 확인할 수 없다.
//		Assertions.assertEquals(member, result);            // 따라서 그대신 junit Assertions 을 사용한다. 만약 문제가 없으면 초록불이 뜬다.
//		Assertions.assertEquals(member, new Member());      // 이렇게 하면 틀려서 붉은 불이 들어온다.

		assertThat(member).isEqualTo(result);    // 그런데 요즘은 assertj Assertopms 을 사용한다. 좀더 편하다.
//		assertThat(member).isEqualTo(null);
	}

	@Test
	public void findByName(){
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		Member result =  repository.findByName("spring1").get();
		assertThat(result).isEqualTo(member1);
//		assertThat(result).isEqualTo(member2);
	}

	@Test
	public void findAll(){
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		List<Member> result = repository.findAll();

		assertThat(result.size()).isEqualTo(2);
//		assertThat(result.size()).isEqualTo(3);
	}

}
