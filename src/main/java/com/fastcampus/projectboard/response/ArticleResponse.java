package com.fastcampus.projectboard.response;

import com.fastcampus.projectboard.dto.ArticleDto;

import java.time.LocalDateTime;

public record ArticleResponse(
        //record 타입은 불변 객체로 설계되어 데이터 전달 및 읽기 작업에 적합한 타입으로 읽기전용으로 사용한다.
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
//Serializable 는 데이터 직열화로 데이터를 바이트 스트림으로 변환하여 데이터 외부 전송이나 저장시에 사용한다.
//파일 저장, 네트워크 통신, 세션 관리 등 해당 dto 는 응답 객체로 데이터 전달을 위해 사용되므로 직열화가 필수적으로 필요하진 않다.
//추후 세션 저장, 캐시 사용, 네트워크 전송과 같은 시나리오가 추가적으로 요구될 것으로 예상된다.
    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }
    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }
        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }
}
