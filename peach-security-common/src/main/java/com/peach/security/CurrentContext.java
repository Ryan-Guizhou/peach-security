package com.peach.security;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/24 0:00
 */
public class CurrentContext {

    /**
     * 使用ThreadLocal存储上下文,保证多线程下线程安全
     */
    private static ThreadLocal<CurrentContextEntity> context = new ThreadLocal<CurrentContextEntity>();

    /**
     * 获取上下文
     * @return
     */
    public static CurrentContextEntity getCurrentEntity() {
        return context.get();
    }

    /**
     * 设置上下文
     * @param currentContextEntity
     */
    public static void setCurrentEntity(CurrentContextEntity currentContextEntity) {
        context.set(currentContextEntity);
    }

    /**
     * 清楚上下文
     */
    public static void cleaCurrentEntity() {
        context.remove();
    }

    /**
     * 获取上下文用户信息
     * @return
     */
    public static CurrentUserDO getCurrentUser() {
        return context.get().getCurrentUserDO();
    }

    /**
     * 获取上下文语言信息
     * @return
     */
    public static String getLanguage(){
        return context.get().getLanguage();
    }
}
