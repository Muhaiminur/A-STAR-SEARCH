/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarsearch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.Stack;
/**
 *
 * @author nabil
 */
public class ASTARSEARCH {
    static int number_of_cities;
    static int number_of_roads;
    static String start;
    static String end;
    static int broken_road_count;
    static String broken_roads="";
    static String city_list[];
    static int graph_matrix[][];
    static boolean visited[];
    static int tuni[];
    
    public static void main(String[] args) throws IOException {
        
        // TODO code application logic here
        
        fileReader();
//        for(int i=0;i<city_list.length;i++){
//            System.out.print(city_list[i]+"->"+i+" , ");
//        }
//        System.out.println();    
        Dijkstra(2,12);
            
//            for (int i = 0; i < graph_matrix.length; i++) {
//                for (int j = 0; j < graph_matrix.length; j++) {
//                    if(graph_matrix[i][j]!=0){
//                    System.out.println(city_list[i]+","+city_list[j]+","+graph_matrix[i][j]);
//                    }
//                    
//                }
//                System.out.println();
//        }
    }// End of main method
    
    
    
   public static void fileReader() throws FileNotFoundException, IOException{
        BufferedReader br=new BufferedReader(new FileReader("SAMPLEINPUTONE"));
        
        String line=br.readLine();
        String temp[]=line.split(",");
        number_of_cities=Integer.parseInt(temp[0]);
        number_of_roads=Integer.parseInt(temp[1]);
        city_list=new String[number_of_cities];
        tuni=new int[number_of_cities];
        //System.out.println(city_list.length+" "+tuni.length);
        visited=new boolean[number_of_cities];
        graph_matrix=new int[number_of_cities][number_of_cities];
        
        start=br.readLine();
        System.out.println("SHORT PATH WILL BE");
        System.out.print(start+" ->");
        end=br.readLine();

        int count=0;

        for (int i = 0; i <number_of_roads; i++) {
            String temp2=br.readLine();
            String roads[]=temp2.split(",");
            
            int row=count;
           int r=search(city_list, roads[0]);
           if(r==-1){
               city_list[count]=roads[0];
               count++;
           }else{
               row=r;
           }
           int col=count;
           r=search(city_list, roads[1]);
           if(r==-1){
               city_list[count]=roads[1];
               count++;
           }else{
               col=r;
           }
            
            graph_matrix[row][col]=graph_matrix[col][row]=Integer.parseInt(roads[2]);
            
            
            
        }
       
        for (int i = 0; i <number_of_cities; i++) {
     
            String temp2[]=br.readLine().split(",");
            int index=search(city_list, temp2[0]);
            if(index==-1){
                System.err.println("lol");
            }else{
                tuni[index]=Integer.parseInt(temp2[1]);
            }
       }
        
        
    }// End of read File Method
    public static int search(String []arr, String val){
        //System.out.println(val);
        for (int i = 0; i < arr.length; i++) {
            
           if(arr[i]!=null){
                if( arr[i].equalsIgnoreCase(val)){
                   
                return i;     
            }
           }
        }
        return -1;
    }// End of function search
    
    
    public static void Dijkstra(int source, int destination){
        int parent[]=new int[number_of_cities];
        int distance[]=new int[number_of_cities];
        
        Stack<String> s=new Stack<String>();
        int size=number_of_cities;
        
        for (int i = 0; i < distance.length; i++) {
            if(i!=source){
                distance[i]=99999;
                //prev[i]=-99;
                parent[i]=-99;
            }
        }
        parent[source]=source;
        distance[source]=0; // Set the Distance of the source node to 0
        s.push(city_list[source]);
        visited[source]=true;
        
        int i=source;
        // the Next while loop is for traversing all the edges
        while(size!=1){
            /*
            *This for loop is for traversing all the neighbours of a vertex 
            */
             // This is min is for should I update the value of not.
            for (int j = 0; j < city_list.length; j++) {
                
                   if(graph_matrix[i][j]>0 && visited[j]!=true){
                      // System.out.print(graph_matrix[i][j]);
                       //System.out.println(" is from "+city_list[i]+" to "+city_list[j]);
                           // This is the checking if there exists a path from vertex i to vertex j
                       if(distance[j]>graph_matrix[i][j]+distance[i]){
                           // It means my current weight is greater or biggar than the new path
                           distance[j]=graph_matrix[i][j]+distance[i];
                           parent[j]=i;
                          // System.out.println("Parent of "+city_list[j]+" is "+ city_list[i] +" Distance is "+distance[j]);
                       }
                   }
            }
           // System.out.println("______________");
            int min=9999;
            int min_index=0;
            for (int j = 0; j < city_list.length; j++) {
                if(graph_matrix[i][j]>0  && visited[j]!=true){
                    // This is the checking if there exists a path from vertex i to vertex j
                    if(min>distance[j]){
                        min_index=j;
                       // System.out.println("So the minimum here is from " +city_list[i]+" to "+city_list[j]+ " and distance is "+distance[j]);
                        min=distance[j];
                    }
                }
            }
            
            size--;
            i=min_index;
           // System.out.println("As distance is "+min+" So, "+city_list[min_index]+" is selected parent is "+city_list[parent[i]]);
            visited[i]=true;
            s.push(city_list[i]);
            
           // System.out.println("************");
        }
        
        //System.out.println(s.toString());
        int k=destination;
        Stack<String> temp=new Stack<String>();
        //System.out.println("SHORT PATH WILL BE");
        temp.push(city_list[k]);
        while(parent[k]!=source){
           // System.out.println(city_list[parent[k]]+"->");
            temp.push(city_list[parent[k]]);
            k=parent[k];
        }
        s.push(city_list[parent[k]]);
        while(!temp.empty()){
            System.out.print(temp.pop()+"->");
        }
        System.out.println("");
        System.out.println("Total distance is "+distance[destination]);
        }//End of function Dijkstra
       
             
    }// End of the whole class