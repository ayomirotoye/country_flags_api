package com.demo.country_flag_api.business_logic;

import com.demo.country_flag_api.models.Country;
import com.demo.country_flag_api.models.CountryRequestDto;
import com.demo.country_flag_api.models.ResponseWrapper;

public interface ICountryBusinessLogic {
    ResponseWrapper<Country> getCountries(CountryRequestDto countryRequestDto, String cursor);

    ResponseWrapper<Country> getCountriesAndFlags(CountryRequestDto countryRequestDto, String cursor);

    ResponseWrapper<Country> searchCountries(String searchText, String cursor);
}
