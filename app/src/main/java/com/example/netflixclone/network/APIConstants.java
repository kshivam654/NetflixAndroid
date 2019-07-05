package com.example.netflixclone.network;

public class APIConstants {
    public static class Params {

        public static final String ERROR_CODE = "error_code";
        public static final String ERROR_MSG = "error_message";
    }

    public static class ErrorCodes {

        public static final int INVALID_TOKEN = 104;
        public static final int USER_LOGIN_DECLINED = 905;
        public static final int EMAIL_NOT_ACTIVATED = 111;
        static final int TOKEN_EXPIRED = 103;
         static final int SUB_PROFILE_DOESNT_EXIST = 3002;
         static final int USER_DOESNT_EXIST = 133;
         static final int USER_RECORD_DELETED_CONTACT_ADMIN = 3000;
    }

    static class URLs {
        static final String BASE_URL = "http://adminview.streamhash.com/";
    }

    public static class APIs {
        public static final String API_STR = "userApi/";
        public static final String LOGIN = API_STR + "login";
    }
}
