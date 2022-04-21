package cn.altaria.sky.login.exception;

/**
 * RegisterException
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/31 10:42
 */
public class RegisterException extends Exception {

    private static final long serialVersionUID = 6483797939518069119L;

    public RegisterException() {
        super();
    }

    public RegisterException(String msg) {
        super(msg);
    }
}
