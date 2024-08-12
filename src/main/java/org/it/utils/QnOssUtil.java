package org.it.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.InputStream;

public class QnOssUtil {
    //...生成上传凭证，然后准备上传
    private static final String ACCESS_KEY = "aX-E_zCOIYSyZd_oIPCgKcj__T-dvvK6fvkIA3Zq";
    private static final String SECRET_KEY = "ziKR31_tnpPn6y1Mk_KpuoMNZ9MEFH950OYKmXw3";
    private static final String BUCKET = "big-event9527";
    public static String uploadFile(String objectName, InputStream inputStream) {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/home/qiniu/test.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        // 校验
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 生成文件上传的令牌
        String upToken = auth.uploadToken(BUCKET);

        try {
            // 文件上传
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);

                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }
        return localFilePath;
    }
}
