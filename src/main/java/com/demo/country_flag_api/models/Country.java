package com.demo.country_flag_api.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record Country(
        double gdp,
        String flagUrl,
        @JsonAlias("sex_ratio") double sexRatio,
        @JsonAlias("surface_area") double surfaceArea,
        @JsonAlias("life_expectancy_male") double lifeExpectancyMale,
        double unemployment,
        double imports,
        @JsonAlias("homicide_rate") double homicideRate,
        Currency currency,
        String iso2,
        @JsonAlias("employment_services") double employmentServices,
        @JsonAlias("employment_industry") double employmentIndustry,
        @JsonAlias("urban_population_growth") double urbanPopulationGrowth,
        @JsonAlias("secondary_school_enrollment_female") double secondarySchoolEnrollmentFemale,
        @JsonAlias("employment_agriculture") double employmentAgriculture,
        String capital,
        @JsonAlias("co2_emissions") double co2Emissions,
        @JsonAlias("forested_area") double forestedArea,
        double tourists,
        double exports,
        @JsonAlias("life_expectancy_female") double lifeExpectancyFemale,
        @JsonAlias("post_secondary_enrollment_female") double postSecondaryEnrollmentFemale,
        @JsonAlias("post_secondary_enrollment_male") double postSecondaryEnrollmentMale,
        @JsonAlias("primary_school_enrollment_female") double primarySchoolEnrollmentFemale,
        @JsonAlias("infant_mortality") double infantMortality,
        @JsonAlias("gdp_growth") double gdpGrowth,
        @JsonAlias("threatened_species") double threatenedSpecies,
        double population,
        @JsonAlias("urban_population") double urbanPopulation,
        @JsonAlias("secondary_school_enrollment_male") double secondarySchoolEnrollmentMale,
        String name,
        @JsonAlias("pop_growth") double popGrowth,
        String region,
        @JsonAlias("pop_density") double popDensity,
        @JsonAlias("internet_users") double internetUsers,
        @JsonAlias("gdp_per_capita") double gdpPerCapita,
        double fertility,
        double refugees,
        @JsonAlias("primary_school_enrollment_male") double primarySchoolEnrollmentMale
) {

    public static List<Country> from(List<SparseCountryInfo> data) {
        return data.stream().map(sparseCountryInfo ->
                Country.builder()
                        .name(sparseCountryInfo.name())
                        .flagUrl(sparseCountryInfo.flagUrl())
                        .iso2(sparseCountryInfo.iso2()).build()).toList();
    }

    public static Country from(SparseCountryInfo sparseCountryInfo) {
        return Country.builder()
                .name(sparseCountryInfo.name())
                .flagUrl(sparseCountryInfo.flagUrl())
                .iso2(sparseCountryInfo.iso2()).build();
    }

    public record Currency(
            String code,
            String name
    ) {
    }

    public record SparseCountryInfo(String name, String iso2, String iso3, @JsonAlias("flag") String flagUrl) {
    }
}