package com.fastcampus.projectboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;
    // 표시되는 페이지 수는 사용자가 설정하도록 = 외부에서 값을 받아오도록  할 수도 있지만
    // 여기에서는 서버 내부에서 지정된 고정 표시 페이지 수로 값을 지정한다. EX) 12345 , 678910 5페이지 까지만 표시
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
