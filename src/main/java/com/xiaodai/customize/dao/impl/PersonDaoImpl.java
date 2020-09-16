package com.xiaodai.customize.dao.impl;

import com.xiaodai.customize.controller.po.Men;
import com.xiaodai.customize.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author My
 */

@Repository(value = "personDao")
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addPerson(Men men) {
        String sql = "INSERT INTO `user`(username,age) VALUES(?,?)";
        String username = UUID.randomUUID().toString().substring(0, 5);
        jdbcTemplate.update(sql, men.getName(), men.getAge());
    }

    @Override
    public void delPerson(Men men) {
        String sql = "DELETE FROM `user` WHERE ID = ?";
        jdbcTemplate.update(sql, men.getId());
    }

    @Override
    public void updatePerson(Men men) {


    }

    @Override
    public List<Men> getPerson(Men men) {
        String sql = "SELECT * FROM `user` WHERE ID = ?";
        List<Men> menList = new ArrayList<>();

        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Men men = new Men();
                men.setName(resultSet.getString("name"));
                men.setId(resultSet.getInt("id"));
                menList.add(men);
            }

        });
        return  menList;
    }
}
