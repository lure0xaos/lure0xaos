package gargoyle.l0x.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WhitelistInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        return Optional.of(handler)
//                .filter((handlerMethod) -> handlerMethod instanceof HandlerMethod)
//                .map((handlerMethod) -> (HandlerMethod) handlerMethod).map((method) ->
//                        Optional.ofNullable(method.getMethodAnnotation(Whitelist.class))
//                                .or(() -> Optional.ofNullable(findMergedAnnotation(method.getBeanType(), Whitelist.class)))
//                                .map(Whitelist::value)
//                                .map((Arrays::asList))
//                                .map((list) -> list.contains(request.getRemoteAddr()))
//                                .orElse(true))
//                .orElse(true);
        // fixme
        return true;
    }
}
