package com.udacity.jwdnd.course1.cloudstorage.model;

public final class ResultTypes {
    public static final int Success = 0;
    public static final int ErrorWithoutMessage = 1;
    public static final int ErrorWithMessage = 2;
    public static final  int ErrorDuplicateExist = 3;
    public static final  int ErrorEmptyObject = 4;
    public static final int SuccessWithMessage = 5;

    public static String getErrorDescription(int resultType) {
        switch (resultType) {
            case Success:
                return "Successfully completed operation.";
            case ErrorWithoutMessage:
            case ErrorWithMessage:
                return "Error occured while trying todo operation.";
            case ErrorDuplicateExist:
                return "Object with same name allready exists in the database.";
            case ErrorEmptyObject:
                return "Object is empty";
        }
        return "";
    }
}
