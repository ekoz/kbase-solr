/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eastrobot.solr.config.BaseController;
import com.eastrobot.solr.service.DictionaryService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月9日 下午6:55:08
 * @version 1.0
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {

	@Autowired
	private DictionaryService dictionaryService;
	
	@PostMapping("/loadStopwords")
	public String loadStopwords() {
		try {
			return dictionaryService.loadStopwords();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@PostMapping("/saveStopwords")
	public void saveStopwords(@RequestParam("data") String data) {
		try {
			dictionaryService.saveStopwords(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/loadSynonyms")
	public String loadSynonyms() {
		try {
			return dictionaryService.loadSynonyms();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@PostMapping("/saveSynonyms")
	public void saveSynonyms(@RequestParam("data") String data) {
		try {
			dictionaryService.saveSynonyms(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
