/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.eastrobot.model.Notice;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月22日 下午5:05:20
 * @version 1.0
 */
@Service
public interface NoticeRepository extends CrudRepository<Notice, Serializable>{
	
}
