package com.admin.admin_back.pojo.constant;

import java.text.SimpleDateFormat;

/**
 * @author 陈群矜
 */
public class Constant {

    /**
     * xls文件类型
     */
    public final static String FILE_XLS = "xls";

    /**
     * xlsx文件类型
     */
    public final static String FILE_XLSX = "xlsx";

    /**
     * Excel列映射配置，是主键
     */
    public final static int IS_PRIMARY_KEY = 1;

    /**
     * Excel映射配置，与库里数据重复时，覆盖
     */
    public final static int IS_COVER = 1;

    /**
     * int类型常量
     */
    public final static int INT_5 = 5;

    public final static int INT_10 = 10;

    public final static int INT_15 = 15;

    public final static int INT_16 = 16;

    public final static int INT_20 = 20;

    public final static int INT_40 = 40;

    public final static int INT_50 = 50;

    public final static int INT_100 = 100;

    /**
     * JwtTokenUtil 常量
     */
    public final static String CLAIM_KEY_USER_NO = "userNo";

    public final static String CLAIM_KEY_USERNAME = "username";

    public final static String CLAIM_KEY_USER_TYPE = "userType";

    public final static String CLAIM_KEY_ROLES = "roles";

    public final static String SECRET = "secret";

    public final static String ISSUER = "sdu-admin";

    public final static int JWT_TOKEN_EXPIRATION = 300;

    public final static int REFRESH_TOKEN_EXPIRATION = 604800;

    /**
     * RsaUtil 常量
     */
    public final static String RSA_ALGORITHM = "RSA";

    public final static String PUBLIC_KEY = "RSA_PUBLIC_KEY";

    public final static String PRIVATE_KEY = "RSA_PRIVATE_KEY";

    public final static String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * Excel任务 常量
     */
    public final static int TASK_CREATE = 0;

    public final static int TASK_SUCCESS = 1;

    public final static int TASK_ERROR = 2;

    /**
     * 日期格式
     */
    public final static SimpleDateFormat[] SIMPLE_DATE_FORMATS = new SimpleDateFormat[]{
            new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyyMMdd")
    };

    /**
     * 日期时间格式
     */
    public final static SimpleDateFormat[] SIMPLE_DATE_TIME_FORMATS = new SimpleDateFormat[]{
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    };

    /**
     * 数据库名称
     */
    public final static String TABLE_SCHEMA = "emp";

    public final static String DATE_TYPE_DATE = "date";

    public final static String DATE_TYPE_DATE_TIME = "datetime";

    public final static String IS_NULLABLE = "YES";

}
