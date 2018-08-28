/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.solr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eastrobot.solr.model.SolrGovMail;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年8月28日 下午2:56:55
 * @version 1.0
 */
@Repository
public interface SolrGovMailRepository extends CrudRepository<SolrGovMail, String>{

}
