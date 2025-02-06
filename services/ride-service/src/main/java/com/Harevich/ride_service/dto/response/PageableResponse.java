package com.Harevich.ride_service.dto.response;

import java.util.List;

public record PageableResponse<T> (

        long totalElements,

        int totalPages,

        int currentPage,

        int pageSize,

        List<T> content

) {
}
