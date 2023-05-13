package com.admin.admin_back.pojo.constant;

import java.text.SimpleDateFormat;
import java.util.*;

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
     * 不需要转换为RequestWrapper的URI
     */
    public final static String[] CHANNEL_FILTER_URI = new String[]{
            "/excel/upload"
    };

    /**
     * Excel映射配置，与库里数据重复时，覆盖
     */
    public final static int IS_COVER = 1;

    /**
     * Excel映射配置，部分列需要进行特殊处理
     */
    public final static int IS_SPECIAL = 1;

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

    public final static int INT_64 = 64;

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

    /**
     * 令牌过期时间
     * 1 * 24 * 60 * 60 单位：秒
     */
    public final static int JWT_TOKEN_EXPIRATION = 86400;

    /**
     * 刷新令牌过期时间
     * 7 * 24 * 60 * 60 单位：秒
     */
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
     * 数据库相关约束、属性
     */
    public final static String CONSTRAINT_TYPE_PRIMARY_KEY = "PRIMARY KEY";

    public final static String CONSTRAINT_TYPE_UNIQUE = "UNIQUE";

    public final static String AUTO_INCREMENT = "auto_increment";

    /**
     * 数据库名称
     */
    public final static String TABLE_SCHEMA = "emp";

    /**
     * 学生表
     */
    public final static String STUDENT_TABLE = "students";

    /**
     * 教师表
     */
    public final static String TEACHER_TABLE = "teacheres";

    /**
     * 需要特殊处理的Excel表格名称，以及对应的列名
     */
    public final static Map<String, Set<String>> EXCEL_SPECIAL = new HashMap<String, Set<String>>() {{
        put("选课课程管理信息", new HashSet<String>() {{
            add("课程编号");
            add("课序号");
            add("上课时间");
            add("上课地点");
        }});
    }};

    public final static Map<String, Integer> WEEK_DAY_MAPPER = new HashMap<String, Integer>() {{
        put("一", 1);
        put("二", 2);
        put("三", 3);
        put("四", 4);
        put("五", 5);
        put("六", 6);
        put("七", 7);
    }};

    public final static Set<String> EMPTY_STRING_SET = new HashSet<String>();

    public final static String DATE_TYPE_DATE = "date";

    public final static String DATE_TYPE_DATE_TIME = "datetime";

    public final static String IS_NULLABLE = "YES";

    public final static String BLANK_STRING = "";

    /**
     * 数据获取接口相关常量
     */
    public final static Set<String> ALLOWED_SQL_TABLE_NAMES = new HashSet<String>() {{
        add("admin_excel");
        add("admin_excel_column");
        add("admin_resource");
        add("admin_role");
        add("admin_system");
        add("admin_user");
        add("buildings");
        add("classes");
        add("classestimestatus");
        add("classrooms");
        add("course_select");
        add("courses");
        add("major");
        add("students");
        add("studentsselect");
        add("studentschedule");
        add("teacheres");
        add("testschedule");
    }};

    public final static List<String> PRIVACY_COLUMN_NAMES = new ArrayList<String>() {{
        add("password");
    }};

}
