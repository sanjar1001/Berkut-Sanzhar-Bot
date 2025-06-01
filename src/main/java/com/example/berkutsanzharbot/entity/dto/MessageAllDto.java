package com.example.berkutsanzharbot.entity.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageAllDto {

    public String text;
    public LocalDateTime createdAt;

}
