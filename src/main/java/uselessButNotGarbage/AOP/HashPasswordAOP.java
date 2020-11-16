//package uselessButNotGarbage.AOP;
//
//import com.xr.treehole.entity.User;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class HashPasswordAOP {
//
//    @Around("execution(* com.xr.treehole.service.UserService.saveUser(..))")
//    public Object  HashPasswordBeforeSave(ProceedingJoinPoint joinPoint) throws Throwable{
//
//        Object[] params = joinPoint.getArgs();
//
//        Signature signature = joinPoint.getSignature();
//        String[] paramNames = ((MethodSignature) signature).getParameterNames();
//
//        int length = paramNames.length;
//
//        for (int i = 0; i < length; i++){
//            if (paramNames[i].equals("user")){
//                User user = (User) params[i];
//                user.setPassword(HashPassword(user.getPassword()));
//            }
//        }
//
//        return joinPoint.proceed(params);
//    }
//
//
//    private String HashPassword(String password){
//        return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//
//}
