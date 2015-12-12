package com.example.khaalijeb.newlistview_module;

import android.provider.BaseColumns;

public class UserTableData {
    public UserTableData() {

    }
    public static abstract class table_info implements BaseColumns {
        public static final String userMobileNo = "user_name";
        public static final String userMobilePass = "user_pass";
        public static final String database_name = "user_info";
        public static final String table_name = "user_table";
        public static final String val = "value";
        public static final String test = "test";
    }
}
