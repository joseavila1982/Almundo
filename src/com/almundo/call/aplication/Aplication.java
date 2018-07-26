package com.almundo.call.aplication;

import java.util.concurrent.PriorityBlockingQueue;

import com.almundo.call.beans.Empleado;
import com.almundo.call.beans.Llamada;
import com.almundo.call.beans.Rango;
import com.almundo.call.dispatcher.Dispatcher;

public class Aplication {

	public static void main( String[] args ) throws InterruptedException
    {
        System.out.println("================CALL CENTER SIMULATOR================");
        
        //Instanciando estructuras
        
        PriorityBlockingQueue<Empleado> lista = crearListaDeEmpleados();
        Dispatcher dis = new Dispatcher(10, lista);
        
        for(int i = 0; i<10 ; i++) {
        		dis.dispatchCall(new Llamada());
        }
        
        dis.clockOut(15);
    }
    
    private static PriorityBlockingQueue<Empleado> crearListaDeEmpleados(){
    		PriorityBlockingQueue<Empleado> lista = new PriorityBlockingQueue<Empleado>();

    		//7 operadores
    		lista.add(new Empleado(Rango.OPERADOR,"Carlos Avila"));
    		lista.add(new Empleado(Rango.OPERADOR,"Alvaro Navarro"));
    		lista.add(new Empleado(Rango.OPERADOR,"ALvaro Lobelo"));
    		lista.add(new Empleado(Rango.OPERADOR,"Virina Fandino"));
    		lista.add(new Empleado(Rango.OPERADOR,"Claudia tuquerres"));
    		lista.add(new Empleado(Rango.OPERADOR,"Jose Alvaro"));
    		lista.add(new Empleado(Rango.OPERADOR,"Javier Jesus"));
    		
    		//2 supervisores
    		lista.add(new Empleado(Rango.SUPERVISOR,"Alfonso Gonzalez"));
    		lista.add(new Empleado(Rango.SUPERVISOR,"Donna Garcia"));
    		
    		//1 director
    		lista.add(new Empleado(Rango.DIRECTOR,"Ana Maria"));
    		
    		return lista;
    }
    
}
