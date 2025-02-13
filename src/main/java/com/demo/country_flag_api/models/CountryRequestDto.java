package com.demo.country_flag_api.models;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryRequestDto {
    private String countryIsoCode;
    private String countryName;

    public String getNonBlank() {
        return StringUtils.defaultIfBlank(this.countryIsoCode, this.countryName);
    }
}
