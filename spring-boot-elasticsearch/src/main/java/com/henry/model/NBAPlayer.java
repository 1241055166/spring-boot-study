package com.henry.model;

import lombok.Data;

/**
 * @ClassName：NBAPlayer
 * @Description：TODO
 * @Author：henry
 * @Date：2021/12/22 11:39 下午
 * @Versiion：1.0
 */
@Data
public class NBAPlayer {
    private Integer id;
    private String countryEn;
    private String country;
    private String code;
    private String displayAffiliation;
    private String displayName;
    private Integer draft;
    private String schoolType;
    private String weight;
    private Integer playYear;
    private String jerseyNo;
    private Long birthDay;
    private String birthDayStr;
    private String displayNameEn;
    private String position;
    private Double heightValue;
    private String playerId;
    private String teamCity;
    private String teamCityEn;
    private String teamName;
    private String teamNameEn;
    private String teamConference;
    private String teamConferenceEn;
    private Integer age;

}
