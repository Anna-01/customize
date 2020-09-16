package com.xiaodai.customize.dao;

import com.xiaodai.customize.controller.po.Men;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author My
 */
public interface PersonDao {

    /**
     * 增加men
     * @param men
     */
    void addPerson(Men men);

    void delPerson(Men men);

    void updatePerson(Men men);

    List<Men> getPerson(Men men);
}
