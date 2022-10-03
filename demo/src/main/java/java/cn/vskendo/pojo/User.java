package cn.vskendo.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * User
 *
 * @author vskendo
 * @since 2022/8/31
 */
@Data
@Accessors(chain = true)
public class User {
    private String username;
    private String password;
}
