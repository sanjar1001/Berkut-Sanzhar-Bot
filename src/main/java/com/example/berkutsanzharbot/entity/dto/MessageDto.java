package com.example.berkutsanzharbot.entity.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    public String text;
    public String token;
    public LocalDateTime createdAt;
    public Long userId;


}
