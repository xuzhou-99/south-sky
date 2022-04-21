package cn.altaria.sky.login.sid;

/**
 * SidUtils
 * Generate a unique ID
 * It can be sorted, and it's unique
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/7/21 16:13
 */
public class SIDUtils {

    /**
     * 生成唯一Id
     * 长度22
     *
     * @return long
     */
    public static long getSid() {
        return SnowflakeIdWorker.getInstance().nextId();
    }

    /**
     * 生成唯一Id
     * 长度22
     *
     * @return String
     */
    public static String getSidStr() {
        return String.valueOf(SnowflakeIdWorker.getInstance().nextId());
    }
}
