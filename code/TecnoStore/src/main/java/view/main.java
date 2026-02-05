package view;

import controller.conecction;

public class main {

    public static void main(String[] args) {
        System.out.println("Testing...");
        
        conecction db= new conecction();
        
        db.conectar();
        
        
    }
}
