package com.dawidpater.project.carrental.dto.webrequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@Component
public class ReclaimProtocolRequestDto {
    @NotNull
    private Long rentalId;
    @NotEmpty
    private String managerDetails;
    @NotEmpty
    @Length(min = 20)
    private String protocol;
}
