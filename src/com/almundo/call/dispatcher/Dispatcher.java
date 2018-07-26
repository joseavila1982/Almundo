package com.almundo.call.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.almundo.call.beans.Empleado;
import com.almundo.call.beans.Llamada;

public class Dispatcher {

	private final static Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	private ExecutorService ex;
	private PriorityBlockingQueue<Empleado> cola;
	private int llamadasDelDia;
	
	//Inicializador
	//Recibe la cantidad de llamadas que deber�a poder procesar a la vez,
	//Y el listado de empleados.
	public Dispatcher(int cantidadLlamadosConcurrentes, PriorityBlockingQueue<Empleado> empleados) {
		this.ex = Executors.newFixedThreadPool(cantidadLlamadosConcurrentes);
		this.cola = empleados;
		this.llamadasDelDia = 0;
	}
	
	//Asigna la llamada a un empleado disponible
	//Por la naturaleza de la PriorityBlockingQueue, quedar�n en espera los llamados
	//a los que no se les pueda asignar un empleado
	//(por eso el m�todo arroja interruptedException)
	public void dispatchCall(Llamada ll) throws InterruptedException {
		Empleado emp = cola.take();
		ex.submit(asignarLlamada(emp,ll));
	}
	
	//task para asignar un empleado a la llamada, y volver a insertarlo en la cola
	private Runnable asignarLlamada(Empleado emp, Llamada ll) {
		Runnable r = () -> {
			llamadasDelDia++;
			emp.atender(ll);
			cola.add(emp);
		};
		return r;
	}
	
	//metodo para cerrar el d�a del call center
	//a trav�s de los m�todos nativos del ExecutorService
	//que permiten esperar a que los threads dejen de trabajar
	public int clockOut(long timeout) {
		LOGGER.info("Orden de terminar el d�a enviada! Esperando a que se terminen los llamados en curso...");
		ex.shutdown();
        try {
            if (!ex.awaitTermination(timeout, TimeUnit.SECONDS)) {
            		LOGGER.warning("Se interrumpi� el funcinoamiento del callCenter y quedaron llamadas sin completar!");
                ex.shutdownNow();
            }
        } catch (InterruptedException e) {
        		LOGGER.warning("Interrupted!");
        }
		LOGGER.info("D�a terminado! Atendimos en total "+llamadasDelDia+" llamados! Gracias, vuelva prontos!");
		return llamadasDelDia;
	}
	
}
