package cn.vskendo.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Staff
 *
 * @author vskendo
 * @since 2022/9/3
 */
@Data
@Accessors(chain = true)
public class Staff {
    private String staffId;
    private String username;
    private String staffName;
}
