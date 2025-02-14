package com.demo.country_flag_api.business_logic;

import com.demo.country_flag_api.models.ApiResponse;
import com.demo.country_flag_api.models.Country;
import com.demo.country_flag_api.models.CountryRequestDto;
import com.demo.country_flag_api.models.ResponseWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CountryBusinessLogic implements ICountryBusinessLogic {

    private static final Logger log = LoggerFactory.getLogger(CountryBusinessLogic.class);

    private final RestClient countryClient;

    private final RestClient countryClient2;

    private final ObjectMapper objectMapper;

    @Override
    public ResponseWrapper<Country> getCountriesAndFlags(CountryRequestDto countryRequestDto, String cursor) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/v0.1/countries/flag/images");
        return countryClient2
                .get()
                .uri(builder.build().toUri())
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    writeRequestUrlToLog(request);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        var typeReference = new TypeReference<ApiResponse<List<Country.SparseCountryInfo>>>() {
                        };
                        var responseBody = objectMapper.readValue(response.bodyTo(String.class), typeReference);
                        return ResponseWrapper.of(Country.from(responseBody.getData()));
                    }
                    writeResponseErrorBodyToLog(response);
                    return ResponseWrapper.empty();
                });
    }

    private static void writeResponseErrorBodyToLog(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response) throws IOException {
        log.info("Response From API has statusCode: {} and body: {}",
                response.getStatusCode().value(), response.bodyTo(String.class));
    }

    @Override
    public ResponseWrapper<Country> getCountries(CountryRequestDto countryRequestDto, String cursor) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/v1/country")
                .queryParam("name", countryRequestDto.getNonBlank());
        return countryClient
                .get()
                .uri(builder.build().toUri())
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    writeRequestUrlToLog(request);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        var typeReference = new TypeReference<List<Country>>() {
                        };
                        return ResponseWrapper.of(objectMapper.readValue(response.bodyTo(String.class), typeReference));
                    }
                    writeResponseErrorBodyToLog(response);
                    return ResponseWrapper.empty();
                });
    }

    private static void writeRequestUrlToLog(HttpRequest request) {
        log.info("URL TO CALL:{}", request.getURI());
    }

    //Making 2 async calls because due to the API provider constraints of having
    //to either fetch the data by isoCode or by country name
    @Override
    public ResponseWrapper<Country> searchCountries(String searchText, String cursor) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/v0.1/countries/flag/unicode");
        var searchByCountryName = CompletableFuture.supplyAsync(() -> countryClient2
                .post()
                .uri(builder.build().toUri())
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("country", searchText))
                .exchange((request, response) -> {
                    writeRequestUrlToLog(request);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        var typeReference = new TypeReference<ApiResponse<Country.SparseCountryInfo>>() {
                        };
                        var responseBody = objectMapper.readValue(response.bodyTo(String.class), typeReference);

                        return !responseBody.isError() ?
                                ResponseWrapper.of(List.of(Country.from(responseBody.getData()))) : ResponseWrapper.empty();
                    }
                    writeResponseErrorBodyToLog(response);
                    return ResponseWrapper.empty();
                }));

        var searchByIsoCode = CompletableFuture.supplyAsync(() -> countryClient2
                .post()
                .uri(builder.build().toUri())
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("iso2", searchText))
                .exchange((request, response) -> {
                    writeRequestUrlToLog(request);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        var typeReference = new TypeReference<ApiResponse<Country.SparseCountryInfo>>() {
                        };
                        var responseBody = objectMapper.readValue(response.bodyTo(String.class), typeReference);

                        return !responseBody.isError() ?
                                ResponseWrapper.of(List.of(Country.from(responseBody.getData()))) :
                                ResponseWrapper.empty();
                    }
                    writeResponseErrorBodyToLog(response);
                    return ResponseWrapper.empty();
                }));


        CompletableFuture<List<?>> future = CompletableFuture
                .allOf(searchByCountryName, searchByIsoCode)
                .thenApply(ignored -> {
                    var resultA = searchByCountryName.join();
                    var resultB = searchByIsoCode.join();

                    if (!resultA.dataList().isEmpty() && !resultB.dataList().isEmpty()) {
                        var mergedList = new ArrayList<>();
                        mergedList.addAll(resultA.dataList());
                        mergedList.addAll(resultB.dataList());
                        return mergedList;
                    } else if (!resultA.dataList().isEmpty()) {
                        return resultA.dataList();
                    } else {
                        return resultB.dataList();
                    }
                });
        return (ResponseWrapper<Country>) ResponseWrapper.of(future.join());
    }
}
