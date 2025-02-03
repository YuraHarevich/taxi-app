package com.Harevich.ride_service.util.mapper;


import com.Harevich.ride_service.dto.response.PageableResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PageMapper{
    default <T> PageableResponse<T> toResponse(Page<T> page){
        return new PageableResponse(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.getContent()
        );

    }
}