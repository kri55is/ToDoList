package pounpong.simpletodo;

/**
 * Created by Emilie on 16/08/2017.
 */

public class TodoItem {

    private String itemText;
    private boolean checkStatus;

    public TodoItem(){
        itemText = "";
        checkStatus = false;
    }

    public TodoItem(String text){
        itemText = text;
        checkStatus = false;
    }


    public String getItemText() {
        return itemText;
    }

    public boolean getCheckStatus() {
        return checkStatus;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

}
