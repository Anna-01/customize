package com.xiaodai.customize.service.json;

import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.dao.PersonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author My
 */
@Service
public class PersonService {
    @Resource
    public PersonDao personDao;

    public void addPerson() {
        Men men = new Men();
        men.setName("lijiaxing");
        men.setAge("11");
        personDao.addPerson(men);
    }

    public void delPerson() {
        Men men = new Men();
        men.setId(16);
        personDao.delPerson(men);
    }

    public void findById() {
        Men men = new Men();
        men.setId(1);
        List<Men> menList = personDao.getPerson(men);

    }


    public void updatePerson() {
    }
}
