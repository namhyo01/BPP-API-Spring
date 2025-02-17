package com.example.api.member.adapter.out.persistence;

import com.example.api.chatroom.adapter.out.persistence.ChatRoomMapper;
import com.example.api.chatroom.domain.ChatRoom;
import com.example.api.member.application.port.out.AddMemberChaatRoomPort;
import com.example.api.member.domain.Member;
import com.example.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberPersistentAdapter implements AddMemberChaatRoomPort {
    private final MemberMapper memberMapper;
    private final ChatRoomMapper chatRoomMapper;
    private final MemberRepository memberRepository;

    @Override
    public void addMember(List<Member> members, ChatRoom chatRoom) {
        memberRepository.saveAll(memberMapper.fromListDomainToEntity(members, chatRoomMapper.fromDomainToEntity(chatRoom)));
    }
}
