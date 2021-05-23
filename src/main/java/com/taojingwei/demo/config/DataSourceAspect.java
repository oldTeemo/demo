package com.taojingwei.demo.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class DataSourceAspect implements Ordered {

    @Autowired
    private ApplicationContext applicationContext;

    private static final ThreadLocal<Stack<Map<DataSourceTransactionManager, TransactionStatus>>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 事务声明
     */
    private DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    {
        // 非只读模式
        def.setReadOnly(false);
        // 事务隔离级别：采用数据库的
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    @Pointcut("@annotation(com.taojingwei.demo.config.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource ds = method.getAnnotation(DataSource.class);
        Stack<Map<DataSourceTransactionManager, TransactionStatus>> pairStack = new Stack<>();
        for (String transactionManagerName : ds.value()) {
            DataSourceTransactionManager transactionManager = applicationContext.getBean(transactionManagerName, DataSourceTransactionManager.class);
            TransactionStatus transactionStatus = transactionManager.getTransaction(def);
            HashMap<DataSourceTransactionManager, TransactionStatus> x = new HashMap<DataSourceTransactionManager, TransactionStatus>();
            x.put(transactionManager, transactionStatus);
            pairStack.push(x);
        }
        THREAD_LOCAL.set(pairStack);
        point.proceed();
        while (!pairStack.empty()) {
            Map<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            for(DataSourceTransactionManager k:pair.keySet()){
                k.commit(pair.get(k));
            }
        }
        THREAD_LOCAL.remove();
    }

    @AfterThrowing("dataSourcePointCut()")
    public void afterThrowing() {
        // ※栈顶弹出（后进先出）
        Stack<Map<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Map<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            for(DataSourceTransactionManager k:pair.keySet()){
                k.rollback(pair.get(k));
            }
        }
        THREAD_LOCAL.remove();
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
