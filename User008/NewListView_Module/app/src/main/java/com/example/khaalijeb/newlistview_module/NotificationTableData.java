package com.example.khaalijeb.newlistview_module;

import android.provider.BaseColumns;

/**
 * Created by Rajdeep Singh on 22-07-2015.
 */
public class NotificationTableData {
    public static abstract class notiftable_info implements BaseColumns {
        public static final String mSenderMobileNo = "mSenderMobileNo";
        public static final String mSenderName = "mSenderName";
        public static final String database_name = "sender_info";
        public static final String table_name = "sender_table";
        public static final String flag = "0";
        public static final String mAmountRequested = "value";
    }
}
