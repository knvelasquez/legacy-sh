package es.w2m.SuperHeroeMantenimiento.AnotacionPersonalizada;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * */
@Aspect
@Component
public class TotalTiempoEjecucionAspect {    
	/**
	 * 
	 * 
	 * */
	@Pointcut("@annotation(es.w2m.SuperHeroeMantenimiento.AnotacionPersonalizada.TotalTiempoEjecucion)")
    public void handlingTimePointcut() {}
 	
	/**
	 * 
	 * 
	 * */
    @Around("handlingTimePointcut()")
    public Object handlingTimeAround(ProceedingJoinPoint puntoEntrada){
        try {        	
        	long horaInicio = System.currentTimeMillis();
        	MethodSignature firma = (MethodSignature) puntoEntrada.getSignature();            
            String nombreMetodo=firma.getMethod().getName();
            String nombreClase=puntoEntrada.getTarget().getClass().getCanonicalName();                                         
            System.out.println("@TotalTiempoEjecucion Iniciando medición:".concat(nombreClase.concat(".").concat(nombreMetodo)));
            Object continuar = puntoEntrada.proceed();
            System.out.println("@TotalTiempoEjecucion duración total: ".concat(String.valueOf((System.currentTimeMillis() - horaInicio))).concat(" milisegundos"));
            return continuar;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
