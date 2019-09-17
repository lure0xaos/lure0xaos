package gargoyle.l0x.aspect;

import gargoyle.l0x.annotations.alert.AlertReport;
import gargoyle.l0x.annotations.alert.AlertReportFailure;
import gargoyle.l0x.annotations.alert.AlertReportSuccess;
import gargoyle.l0x.services.alert.Alerts;
import gargoyle.l0x.util.Expr;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class AlertAspect {
    private final Alerts alerts;

    @SuppressWarnings({"ProhibitedExceptionThrown", "OverlyBroadThrowsClause", "ProhibitedExceptionDeclared",
            "FeatureEnvy", "ReturnOfNull"})
    @Around("@annotation(gargoyle.l0x.annotations.alert.AlertReport) && execution(public * *(..))")
    public Object onAlert(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp instanceof MethodInvocationProceedingJoinPoint) {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            AlertReport annotation = methodSignature.getMethod().getAnnotation(AlertReport.class);
            Object root = pjp.getThis();
            try {
                Object result = pjp.proceed();
                AlertReportSuccess success = annotation.success();
                alerts.alert(success.type(),
                        String.valueOf(Expr.eval(success.message(), root, success.type())),
                        String.valueOf(Expr.eval(success.description(), root, "")),
                        success.dismiss(), success.raw());
                return result;
            } catch (Exception e) {
                AlertReportFailure failure = annotation.failure();
                LoggerFactory.getLogger(methodSignature.getDeclaringType()).error(failure.message(), e);
                alerts.alert(failure.type(),
                        String.valueOf(Expr.eval(failure.message(), root, e.getLocalizedMessage())),
                        String.valueOf(Expr.eval(failure.description(), root, "")),
                        failure.dismiss(), failure.raw());
                Class<?> returnType = methodSignature.getReturnType();
                try {
                    return returnType.cast(Expr.eval(annotation.failure().returns(), root));
                } catch (EvaluationException | ParseException | ClassCastException ex) {
                    LoggerFactory.getLogger(methodSignature.getDeclaringType()).error(ex.getLocalizedMessage(), ex);
                    if (returnType.isPrimitive())
                        return returnType.getConstructor().newInstance();
                    return null;
                }
            } catch (Throwable e) {
                LoggerFactory.getLogger(methodSignature.getDeclaringType()).error(e.getLocalizedMessage(), e);
                throw e;
            }
        } else {
            return pjp.proceed();
        }
    }

}
