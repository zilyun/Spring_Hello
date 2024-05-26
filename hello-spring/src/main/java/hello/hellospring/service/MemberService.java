package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// ctrl + shift + T 테스트 생성
@Service
public class MemberService {
    
    // 서로 다른 memberRepository 인데, 테스트와 실제 부분에서 공유하므로 문제가 되지 않았다.
    // 여기서 static을 제거하게 되면 이슈가 발생할 수 있으므로 아래와 같이 바뀐 코드로 처리해준다.
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // ctrl + alt + V (변수 추출하기, 리팩토링)
        // ctrl + alt + M (메소드 추출, 리팩토링)
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
