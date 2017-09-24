package sg.edu.nus.iss.phoenix.core.android.delegate;

public class DelegateHelper {
    // RESTful parameters.
    private final static String PRMS_ROOT_URL = "http://192.168.1.19:8080/phoenix/rest";
    public final static String PRMS_BASE_URL_AUTHENTICATE = PRMS_ROOT_URL + "/Login/doLogin?";
    public final static String PRMS_BASE_URL_RADIO_PROGRAM = PRMS_ROOT_URL + "/radioprogram";

    public final static String PRMS_BASE_URL_USER = PRMS_ROOT_URL + "/users/all";

    public final static String PRMS_BASE_URL_SCHEDULE = PRMS_ROOT_URL + "/schedule";
    public final static String PRMS_BASE_URL_SCHEDULE_CREATE = PRMS_BASE_URL_SCHEDULE + "/create";
}
