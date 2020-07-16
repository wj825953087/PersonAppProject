package com.my.account.Service;

public interface Service {
    /**
     *
     * @param oper_path Servlet 地址
     * @return
     * @throws Exception
     */
    public  String HttpGET(String oper_path)throws  Exception;

    /**
     *
     * @param oper_path Servlet 地址
     * @return
     * @throws Exception
     */
    public String HttpPOST(String oper_path)throws  Exception;
}
