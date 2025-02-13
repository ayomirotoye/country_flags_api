package com.demo.country_flag_api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record Country(
        double gdp,
        String unicodeFlag,
        @JsonProperty("sex_ratio") double sexRatio,
        @JsonProperty("surface_area") double surfaceArea,
        @JsonProperty("life_expectancy_male") double lifeExpectancyMale,
        double unemployment,
        double imports,
        @JsonProperty("homicide_rate") double homicideRate,
        Currency currency,
        String iso2,
        @JsonProperty("employment_services") double employmentServices,
        @JsonProperty("employment_industry") double employmentIndustry,
        @JsonProperty("urban_population_growth") double urbanPopulationGrowth,
        @JsonProperty("secondary_school_enrollment_female") double secondarySchoolEnrollmentFemale,
        @JsonProperty("employment_agriculture") double employmentAgriculture,
        String capital,
        @JsonProperty("co2_emissions") double co2Emissions,
        @JsonProperty("forested_area") double forestedArea,
        double tourists,
        double exports,
        @JsonProperty("life_expectancy_female") double lifeExpectancyFemale,
        @JsonProperty("post_secondary_enrollment_female") double postSecondaryEnrollmentFemale,
        @JsonProperty("post_secondary_enrollment_male") double postSecondaryEnrollmentMale,
        @JsonProperty("primary_school_enrollment_female") double primarySchoolEnrollmentFemale,
        @JsonProperty("infant_mortality") double infantMortality,
        @JsonProperty("gdp_growth") double gdpGrowth,
        @JsonProperty("threatened_species") double threatenedSpecies,
        double population,
        @JsonProperty("urban_population") double urbanPopulation,
        @JsonProperty("secondary_school_enrollment_male") double secondarySchoolEnrollmentMale,
        String name,
        @JsonProperty("pop_growth") double popGrowth,
        String region,
        @JsonProperty("pop_density") double popDensity,
        @JsonProperty("internet_users") double internetUsers,
        @JsonProperty("gdp_per_capita") double gdpPerCapita,
        double fertility,
        double refugees,
        @JsonProperty("primary_school_enrollment_male") double primarySchoolEnrollmentMale
) {

    public static List<Country> from(List<SparseCountryInfo> data) {
        return data.stream().map(sparseCountryInfo ->
                Country.builder()
                        .name(sparseCountryInfo.name())
                        .unicodeFlag(sparseCountryInfo.unicodeFlag())
                        .iso2(sparseCountryInfo.iso2()).build()).toList();
    }

    public static Country from(SparseCountryInfo sparseCountryInfo) {
        return Country.builder()
                .name(sparseCountryInfo.name())
                .unicodeFlag(sparseCountryInfo.unicodeFlag())
                .iso2(sparseCountryInfo.iso2()).build();
    }

    public record Currency(
            String code,
            String name
    ) {
    }

    public record SparseCountryInfo(String name, String iso2, String iso3, String unicodeFlag) {
    }
}