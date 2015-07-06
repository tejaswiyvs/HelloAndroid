package tejaswi_yerukalapudi.com.helloandroid.model;

import java.util.List;

/**
 * Created by Teja on 7/6/15.
 */
public class PersonList {
    private String odata;
    private List<Person> value;

    public String getOdata(){
        return this.odata;
    }
    public void setOdata(String odata){
        this.odata = odata;
    }

    public List<Person> getPersonList(){
        return this.value;
    }
    public void setValue(List<Person> PersonList){
        this.value = PersonList;
    }
}
