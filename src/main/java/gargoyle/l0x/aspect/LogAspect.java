package gargoyle.l0x.aspect;

import gargoyle.l0x.annotations.log.Log;
import gargoyle.l0x.config.LogConfig;
import gargoyle.l0x.services.log.EnterLogMessageBuilder;
import gargoyle.l0x.services.log.ExceptionLogMessageBuilder;
import gargoyle.l0x.services.log.ExitLogMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.function.Supplier;

import static gargoyle.l0x.aspect.LogIndentation.decrementIndentation;
import static gargoyle.l0x.aspect.LogIndentation.incrementIndentation;


@Aspect
@Component
@RequiredArgsConstructor
@SuppressWarnings("all")
public class LogAspect {

    private final LogConfig config;

    @Around("@annotation(annotation) && execution(public * *(..))")
    public Object logMethod(ProceedingJoinPoint pjp, Log annotation) throws Throwable {
        if (pjp instanceof MethodInvocationProceedingJoinPoint) {
            return log((MethodInvocationProceedingJoinPoint) pjp, annotation);
        } else {
            return pjp.proceed();
        }
    }

    private Object log(MethodInvocationProceedingJoinPoint point, Log annotation) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> declaringType = methodSignature.getDeclaringType();
        Logger logger = LoggerFactory.getLogger(declaringType);
        boolean isConstructor = JoinPoint.CONSTRUCTOR_EXECUTION.equals(point.getStaticPart().getKind());
        int indent = incrementIndentation();
        Object[] arguments = point.getArgs();
        try {
            log(() -> new EnterLogMessageBuilder()
                    .setConfig(config)
                    .setAnnotation(annotation)
                    .setMethod(method)
                    .setIndent(indent)
                    .setArguments(arguments)
                    .build(), logger, null);
            long start = System.currentTimeMillis();
            Object result = point.proceed();
            long end = System.currentTimeMillis();
            log(() -> new ExitLogMessageBuilder()
                    .setConfig(config)
                    .setAnnotation(annotation)
                    .setMethod(method)
                    .setIndent(indent)
                    .setArguments(arguments)
                    .setResult(result)
                    .setExecutionTime(end - start)
                    .build(), logger, null);
            return result;
        } catch (Throwable e) {
            log(() -> new ExceptionLogMessageBuilder()
                    .setConfig(config)
                    .setAnnotation(annotation)
                    .setMethod(method)
                    .setIndent(indent)
                    .setArguments(arguments)
                    .setException(e)
                    .build(), logger, e);
            throw e;
        } finally {
            decrementIndentation();
        }
    }

    public static void log(Supplier<String> message, Logger logger, Throwable exception) {
        if (logger.isTraceEnabled()) {
            logger.trace(message.get(), exception);
        }
    }
}
