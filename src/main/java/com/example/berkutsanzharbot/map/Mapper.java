package com.example.berkutsanzharbot.map;

import com.example.berkutsanzharbot.entity.Message;
import com.example.berkutsanzharbot.entity.dto.MessageAllDto;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    List<MessageAllDto> toMessageAllDtoList(List<Message> messages);

}
