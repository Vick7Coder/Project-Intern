package com.hieuph.todosmanagement.dto.request.Paging.common;

public class ConstantCommon {
    public static final int LIMIT = 5;
    public static final int STATUS_ON = 1;

    public static final int STATUS_OFF = 0;

    public static final String SORT_ASC = "ASC";

    public static final String SORT_DESC = "DESC";

    public final class SQL {

        public static final String AND = "AND";

        public static final String LIKE = "LIKE";

        public static final String LIKE_START_WITH = "_%";

        public static final String LIKE_END_WITH = "%_";

        public static final String LIKE_CONTAIN_WITH = "%_%";

        public static final String OR = "OR";

        public static final String WHERE = "WHERE";

        public static final String LIMIT = "LIMIT";

        public static final String OFFSET = "OFFSET";

        public static final String ORDER_BY = "ORDER BY";

        public static final String SELECT = "SELECT";

        public static final String FROM = "FROM";

        public static final String AS = "AS";

        public static final String EQ = "=";

        public static final String GT = ">";

        public static final String GTE = ">=";

        public static final String LT = "<";

        public static final String LTE = "<=";
    }
}
