package com.example.test;

import com.example.domain.SocialMan;
import com.example.domain.User;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    @Test
    public void listForeach() {
        List<User> users = new ArrayList<>();
        User user = new User("2", "大狗", "嫁给他!嫁给他!嫁给他!");
        User user2 = new User("7", "二狗", "干!");
        User user3 = new User("8", "小狗", "我也干!");
        users.add(user);
        users.add(user2);
        users.add(user3);

        SocialMan socialMan = new SocialMan(8L, null, "不了不了, 伤身体");

        users.forEach(chen -> {
            Long id = Long.valueOf(chen.getId());
            if (id == socialMan.getSocialId()) {
                BeanUtils.copyProperties(socialMan, chen);
            }
        });

        System.out.println(users);
    }
}
