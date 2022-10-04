package cn.vskendo.common.utils;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import lombok.Data;

/**
 * SqlIdGenerator
 *
 * @author vskendo
 * @since 2022/8/24
 */
@Data
public class SqlIdGenerator {
    private Sequence sequence;

    public SqlIdGenerator() {
        this.sequence = new Sequence(null);
    }

    public SqlIdGenerator(Sequence sequence) {
        this.sequence = sequence;
    }

    public Long nextID() {
        return sequence.nextId();
    }

    public String nextStringID() {
        return String.valueOf(sequence.nextId());
    }
}
