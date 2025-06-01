package com.example.berkutsanzharbot.map;

import com.example.berkutsanzharbot.entity.Message;
import com.example.berkutsanzharbot.entity.dto.MessageAllDto;
import com.example.berkutsanzharbot.entity.dto.MessageDto;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    MessageDto toMessageDto(Message message);
    Message toMessage(MessageDto messageDto);

    List<MessageAllDto> toMessageAllDtoList(List<Message> messages);
}
