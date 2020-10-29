package com.lx.aspect;

import com.lx.sys.entity.Dept;
import com.lx.sys.vo.DeptVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    //缓存容器
    private Map<String,Object> CACHE_CONTANER = new HashMap<>();

    //切面表达式
    private static final String POINTCUT_DEPT_UPDATE="execution(* com.lx.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET="execution(* com.lx.sys.service.impl.DeptServiceImpl.getOne(..))";
    private static final String POINTCUT_DEPT_DELETE="execution(* com.lx.sys.service.impl.DeptServiceImpl.removeById(..))";

    private static final String CACHE_DEPT_PROFIX="dept:";

    /**
     * 查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        //从缓存中取出值
        Object res1 = CACHE_CONTANER.get(CACHE_DEPT_PROFIX + id);
        if (null != res1){
            return res1;
        }else {
            Dept res2 = (Dept) joinPoint.proceed();
            CACHE_CONTANER.put(CACHE_DEPT_PROFIX+id,res2);
            return res2;
        }
    }
    /**
     * 更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        DeptVo deptVo = (DeptVo) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            Dept dept = (Dept) CACHE_CONTANER.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if (null == dept){
                dept = new Dept();
                BeanUtils.copyProperties(deptVo,dept);
                CACHE_CONTANER.put(CACHE_DEPT_PROFIX+deptVo.getId(),dept);
            }
        }
        return isSuccess;
    }
    /**
     * 删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTANER.remove(CACHE_DEPT_PROFIX+id);
        }
        return isSuccess;
    }

}
