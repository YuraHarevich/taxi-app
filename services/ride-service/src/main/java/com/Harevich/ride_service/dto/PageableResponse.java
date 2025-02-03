package com.Harevich.ride_service.dto;

public record PageableResponse<T> (
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        List<T> content
){
}
