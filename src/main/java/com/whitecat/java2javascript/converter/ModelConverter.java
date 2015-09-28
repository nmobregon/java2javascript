package com.whitecat.java2javascript.converter;

import com.whitecat.java2javascript.model.ExampleEntity;

public class ModelConverter{

     public static void main(String []args){
        
        String appName = "app";
        
        try{
            Class[] clazzes = {ExampleEntity.class};
            
            for (Class clazz : clazzes){
                StringBuffer metadataBuffer = new StringBuffer();
                
                System.out.println(appName+".namespace('"+appName+".model."+clazz.getSimpleName()+"');");
                System.out.println(appName+".model."+clazz.getSimpleName()+" = function(dataObject) {");
                System.out.println("\tif(!(this instanceof "+appName+".model."+clazz.getSimpleName()+")) {");
                System.out.println("\t\treturn new "+appName+".model."+clazz.getSimpleName()+"(dataObject);");
                System.out.println("\t}");
                
                System.out.println("\tvar data = dataObject || {};");
    
                for (java.lang.reflect.Method me : clazz.getDeclaredMethods()){
                    if (me.getName().startsWith("get")){
                        String propName = me.getName().replace("get", "").substring(0,1).toLowerCase() + 
                                            me.getName().replace("get", "").substring(1);
                        
                        String type = me.getReturnType().getName().toLowerCase().substring(me.getReturnType().getName().lastIndexOf(".")+1).substring(0, 3).equals("int") ? "INTEGER" : "TEXT";
                        
                        System.out.println("\tthis." + propName+"= data."+propName+" || null;");
                        
                        metadataBuffer.append("\t\t\t{ name: '"+propName+"', type: '"+type+"' },\n");
                        
                    }
                }
                System.out.println("};");
                
                
                
                System.out.println(appName+".model."+clazz.getSimpleName()+".getMetadata = function() {");
                System.out.println("\treturn {");
                System.out.println("\t\tname: "+clazz.getSimpleName().toLowerCase()+",");
                System.out.println("\t\tattributes: [");
                System.out.println(metadataBuffer );
                System.out.println("\t\t]");
                System.out.println("\t};");
                System.out.println("};");    
            }
            
        }
        catch(Exception e){
            System.out.println(e);    
        }
       
        
     }
}
