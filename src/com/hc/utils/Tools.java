package com.hc.utils;

public class Tools {
    public Tools() {
    }

    public static String First2LowerCase(String str) {
        return Character.isLowerCase(str.charAt(0)) ? str : Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String First2UpperCase(String s) {
        return Character.isUpperCase(s.charAt(0)) ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String field2Property(String columnName) {
        if (columnName.contains("_")) {
            while (columnName.contains("_")) {
                StringBuffer sb = new StringBuffer();
                int index = columnName.indexOf("_");
                for (int i = 0; i < columnName.length(); i++) {//把下划线后面的首字母变为大写
                    if (i == index + 1 && !(columnName.charAt(i) >= 65 && columnName.charAt(i) <= 90)) {
                        sb.append((char) (columnName.charAt(i) - 32));
                        continue;
                    }
                    sb.append(columnName.charAt(i));
                }
                columnName = sb.toString().replaceFirst("_", "");
            }
        }
        return columnName;
    }

    /**
     * 将表名转换为对应的实体类名
     *
     * @param tableName
     * @return
     */
    public static String getEntryName(String tableName) {
        String tb = tableName.trim();
        String temp = First2UpperCase(tableName);//user--User
        if (tb.indexOf('_') > 0) {//tb_user ---- User
            temp = First2UpperCase(tb.substring(tb.indexOf('_') + 1));
        }//User_sth
        return field2Property(temp);//user_sth----UserSth
    }
}
