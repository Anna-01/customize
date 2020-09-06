package com.xiaodai.customize.service;

import com.xiaodai.customize.dao.PersonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author My
 */
@Service
public class PersonService {
    @Resource
    public PersonDao personDao;

    public void addPerson() {
            personDao.addPerson();
    }
}
