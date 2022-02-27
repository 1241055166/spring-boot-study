package com.henry.controller;

import com.henry.model.NBAPlayer;
import com.henry.service.NBAPlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName：NBASearchController
 * @Description：TODO
 * @Author：henry
 * @Date：2021/12/22 11:36 下午
 * @Versiion：1.0
 */
@RestController
@RequestMapping("nba")
public class NBASearchController {
    @Autowired
    private NBAPlayerService nbaPlayerService;

    @RequestMapping("importAll")
    public String importAll() throws IOException {
        nbaPlayerService.importAll();
        return "success";
    }

    @RequestMapping("searchMatch")
    public List<NBAPlayer> searchMatch(@RequestParam(value = "displayNameEn", required = false) String displayNameEn) throws IOException {
        return nbaPlayerService.searchMatch("displayNameEn", displayNameEn);
    }
    @RequestMapping("searchTerm")
    public List<NBAPlayer> searchTerm(@RequestParam(value = "country", required = false) String country
            ,@RequestParam(value = "teamName", required = false) String teamName) throws IOException {
        return StringUtils.isNoneBlank(country)?nbaPlayerService.searchTerm("country", country):nbaPlayerService.searchTerm("teamName", teamName);
    }
    @RequestMapping("searchMatchPrefix")
    public List<NBAPlayer> searchMatchPrefix(@RequestParam(value = "displayNameEn", required = false) String displayNameEn) throws IOException {
        return nbaPlayerService.searchMatchPrefix("displayNameEn", displayNameEn);
    }
}
