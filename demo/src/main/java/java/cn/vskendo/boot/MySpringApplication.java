package cn.vskendo.boot;

import cn.vskendo.pojo.Staff;
import cn.vskendo.pojo.User;
import cn.vskendo.utils.PojoUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringApplication.class, args);
        // test 1
        User user = new User().setUsername("123").setPassword("321");
        Staff staff = new Staff();
        PojoUtils.copyProperties(user, staff);
        System.out.println(staff);

        // test 2
        user = new User();
        staff = new Staff().setStaffId("No1").setUsername("no1");
        PojoUtils.copyPropertiesLambda(staff,user,Staff::getUsername);
        System.out.println(user);
    }

}
