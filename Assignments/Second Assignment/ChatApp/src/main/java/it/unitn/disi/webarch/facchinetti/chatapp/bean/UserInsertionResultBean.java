package it.unitn.disi.webarch.facchinetti.chatapp.bean;

public class UserInsertionResultBean {

    private boolean success;
    private String errorMessage;

    public UserInsertionResultBean(){
    }

    public UserInsertionResultBean(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static UserInsertionResultBean success(){
        return new UserInsertionResultBean(true, null);
    }

    public static UserInsertionResultBean error(String msg){
        return new UserInsertionResultBean(false, msg);
    }
}
