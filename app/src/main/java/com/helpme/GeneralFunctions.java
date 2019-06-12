package com.helpme;

public class GeneralFunctions {

    //Función para ajustar texto creando un enter en determinada cantidad de caracteres.
    public String cuttext(int pCantCaracteres,String pName){
        String name="";

        for(int i=0;i<pName.length();i++){

            if((i % pCantCaracteres == 0)&&(i>0))
                name=name+"\n";

            name=name+pName.substring(i,(i+1));
        }
        return name;
    }
}
