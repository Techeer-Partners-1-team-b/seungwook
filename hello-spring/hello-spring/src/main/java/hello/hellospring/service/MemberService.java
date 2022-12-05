package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
        회원 가입
         */
    public Long join(Member member) {

//        long start = System.currentTimeMillis();
        // 같은 이름의 중복 회원X
        try {
            validateDuplicateMember(member);

            memberRepository.save(member);
            return member.getId();
        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
        }
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /*
    전체 회원 조회
    */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
    개별 회원 조회
    */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
