package cn.altaria.sky.login.exception;

/**
 * LoginException
 *
 * @author xuzhou
 * @version v1.0.0
 * @create 2021/5/31 10:41
 */
public class LoginException extends Exception {

    private static final long serialVersionUID = 400606197221706216L;

    public LoginException() {
        super();
    }

    public LoginException(String msg) {
        super(msg);
    }
}
