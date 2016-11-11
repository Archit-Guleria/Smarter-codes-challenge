//please add json-simple-1.1.1.jar to you project library
//**************Archit Guleria*************************
package smartercodes;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;
import org.json.simple.parser.JSONParser;
public class Smart{
    static void search_for_selected_one(JSONArray arr, String tabs) {//function to explore the children when the type=item has been encountered once
         Iterator<JSONObject> it = arr.iterator();//iterate through the passed child array arr
         while(it.hasNext()) {
             JSONObject obj = it.next();
             if ((Long)obj.get("selected") == 1){//if the selected value is 1
                 System.out.println(tabs + obj.get("name"));//then print the name value
                 search_for_selected_one((JSONArray)obj.get("children"),tabs + "    " );//and explore further children by recursively calling this function
             }
         }
    }
    static void search_for_solution(JSONArray arr, String tabs) {//function to explore the children when type=item is still not encountere
        Iterator<JSONObject> it = arr.iterator();//iterate through the passed array arr
        while(it.hasNext()) {
            JSONObject obj = it.next();
            if(((String)obj.get("type")).equals("item")) {  //if the type = item
                if ((Long)obj.get("selected") == 1){    //checking if selected value is 1
                    System.out.println(tabs + obj.get("name")); //if selected is 1,print the name value
                    search_for_selected_one((JSONArray)obj.get("children"), tabs + "    ");//explore the children
               }
            } else {
                JSONArray children = (JSONArray) obj.get("children");
                search_for_solution(children, tabs + "    ");//if type is not item then recursively call this function to explore the children
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        JSONParser parser = new JSONParser();
        try {
 
            Object obj = parser.parse(new FileReader("pro.json"));//reading the json file
 
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject body = (JSONObject)jsonObject.get("body");//to get inside the body object
            JSONArray rec = (JSONArray)body.get("Recommendations");//to get inside Recommendations array
            Iterator<JSONObject> it=rec.iterator();// an iterator to get objects of the array rec
            while(it.hasNext()){
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();//these statements are used to produce linespace between different Restraunt
                        JSONObject j=it.next();
                String s=(String)j.get("RestaurantName");//to get the name of the restraunt
                    System.out.println(s);//print the restraunt name
                    String tabs = "    ";//using tabs to insert indentation
                    JSONArray menu=(JSONArray)j.get("menu");//to read the menu array
                    Iterator<JSONObject> it1=menu.iterator();//iterating through the menu array
                    while(it1.hasNext()){
                        JSONObject j1=it1.next();
                        if(((String)j1.get("type")).equals("item")){//checkin if the type is item 
                            JSONArray children  = (JSONArray)j1.get("children");//get the children array of the given object
                            Long selected = (Long)j1.get("selected");//getting the value of selected object
                            Iterator<JSONObject> it2=children.iterator();
                            if(selected == 0)//if selected is 0 then exit from that child
                            {
                                continue;
                            }
                            search_for_selected_one((JSONArray)j1.get("children"), tabs);//if selected is 1 then go to this function to perform various operations
                        }
                        else{
                            JSONArray ch = (JSONArray)j1.get("children");//if the type value is not item then further explore the children
                            search_for_solution(ch, tabs);//exploring the children
                        }
                    }
                }
            } catch (Exception e){e.printStackTrace();}     
    }
}
    