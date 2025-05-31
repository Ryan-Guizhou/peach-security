package com.peach.security.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.collect.Lists;
import com.peach.common.constant.*;
import com.peach.common.response.Response;
import com.peach.common.util.PeachCollectionUtil;
import com.peach.common.util.ThrowUtil;
import com.peach.common.util.encrypt.EncryptAbstract;
import com.peach.common.util.encrypt.EncryptFactory;
import com.peach.security.api.IImageValidateService;
import com.peach.security.util.VerifyImageUtil;
import com.peach.security.vo.LoginConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 17:44
 */
@Slf4j
@Indexed
@Service
public class ImageValidateServiceImpl implements IImageValidateService {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public void initCaptcha(String uniqueKey, LoginConfigVO loginConfigVO) {
        // 1、加载图片资源
        List<String> tempImgList = redisTemplate.opsForList().range(RedisConstant.KEY_VALIDATE_IMG, 0, -1);
        List<String> tempTplList = redisTemplate.opsForList().range(RedisConstant.KEY_VALIDATE_TPL, 0, -1);
        List<byte[]> tempImgByteList = toArrayByte(tempImgList);
        List<byte[]> tempTplByteList = toArrayByte(tempTplList);
        if (PeachCollectionUtil.isEmpty(tempImgByteList) || PeachCollectionUtil.isEmpty(tempTplByteList)) {
            // 初始化到redis
            try{
                tempImgByteList = loadImageResource(FilePathConstant.TARGET_IMAGE_PATH);
                tempTplByteList = loadImageResource(FilePathConstant.TMPL_IMAGE_PATH);
            }catch (Exception e){
                throw new RuntimeException("加载滑块图片失败"+e.getMessage());
            }
            redisTemplate.opsForList().leftPush(RedisConstant.KEY_VALIDATE_IMG, toArrayString(tempImgByteList));
            redisTemplate.opsForList().leftPush(RedisConstant.KEY_VALIDATE_TPL, toArrayString(tempTplByteList));
            redisTemplate.expire(RedisConstant.KEY_VALIDATE_IMG,RedisConstant.EXPIRE_VALIDATE_IMG, TimeUnit.HOURS);
            redisTemplate.expire(RedisConstant.KEY_VALIDATE_TPL,RedisConstant.EXPIRE_VALIDATE_TPL,TimeUnit.HOURS);
        }
        // 2、切割图片资源
        try {
            Random random = new SecureRandom();
            byte[] targetIs = tempImgByteList.get(random.nextInt(tempImgByteList.size()));
            byte[] templateIs = tempTplByteList.get(random.nextInt(tempTplByteList.size()));
            Map<String, Object> pictureMap = VerifyImageUtil.pictureTemplatesCut(templateIs, targetIs, "png", "jpg");
            String targetImage = Base64Utils.encodeToString((byte[]) pictureMap.get("newImage"));
            String sourceImage = Base64Utils.encodeToString((byte[]) pictureMap.get("oriCopyImage"));
            int x = (int) pictureMap.get("X");
            int y = (int) pictureMap.get("Y");
            loginConfigVO.setTargetImage(targetImage);
            loginConfigVO.setSourceImage(sourceImage);
            String token = DigestUtil.md5Hex(uniqueKey);
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("X", x);
            tokenMap.put("Y", y);
            //token 保存5分钟
            String finalToken = RedisConstant.KEY_VALIDATE_TOKEN + ":" + token;
            redisTemplate.opsForHash().putAll(finalToken, tokenMap);
            redisTemplate.expire(finalToken, RedisConstant.EXPIRE_VALIDATE_TOKEN, TimeUnit.SECONDS);
            EncryptAbstract instance = EncryptFactory.getInstance(EncryptConstant.AES);
            String encryptToken = instance.encrypt(token);
            loginConfigVO.setToken(encryptToken);
        }catch (Exception e){
            log.error("滑块验证码切割图片资源失败"+e.getMessage(),e);
            throw new RuntimeException("滑块验证码切割图片资源失败"+e.getMessage());
        }
    }

    /**
     * 根据文件路劲读取图片资源
     * @param filePath
     * @return
     */
    private List<byte[]> loadImageResource(String filePath) throws IOException {
        String target = URLDecoder.decode(ImageValidateServiceImpl.class.getClassLoader().getResource(filePath).getPath(), PubCommonConst.UTF_8);
        List<byte[]> resultImageList = Lists.newArrayList();
        if (target.startsWith("!/")) {
            // jar地址
            URL jarUrl = new URL("jar:" + target);
            JarURLConnection jarCon = (JarURLConnection) jarUrl.openConnection();
            JarFile jarFile = jarCon.getJarFile();
            Enumeration<JarEntry> jarEntrys = jarFile.entries();
            while (jarEntrys.hasMoreElements()) {
                JarEntry entry = jarEntrys.nextElement();
                String name = entry.getName();
                if (name.startsWith(filePath) && filePath.equals(name) && (name.endsWith(ImageTypeConstant.JPG) || name.endsWith(ImageTypeConstant.PNG))) {
                    byte[] resultByte = IOUtils.toByteArray(jarFile.getInputStream(entry));
                    resultImageList.add(resultByte);
                }
            }
            return resultImageList;
        }

        File targeFile = new File(target);
        if (targeFile.exists()) {
            File[] fs = targeFile.listFiles();
            ThrowUtil.throwIf(fs == null,new RuntimeException("文件列表为空"));
            for (File f : fs) {
                byte[] resultByte = IOUtils.toByteArray(new FileInputStream(f));
                resultImageList.add(resultByte);
            }
        }
        return resultImageList;
    }


    @Override
    public LoginConfigVO getCaptcha(String uniqueKey) {
        return null;
    }


    @Override
    public Response checkCaptcha(String token, int x, int y) {
        return null;
    }

    /**
     * List<String> 转 List<byte[]>
     * @param array
     * @return
     */
    private List<byte[]> toArrayByte(List<String> array) {
        List<byte[]> resultList = new ArrayList<byte[]>();
        if (array != null) {
            return resultList;
        }

        for (int i = 0; i < array.size(); i++) {
            byte[] bytes = Base64Utils.decodeFromString(array.get(i));
            resultList.add(bytes);
        }
        return resultList;
    }

    /**
     * List<byte[]> 转 List<String>
     * @param array
     * @return
     */
    private List<String> toArrayString(List<byte[]> array) {
        List<String> resultList = new ArrayList<>();
        if (array != null) {
            return resultList;
        }
        for (int i = 0; i < array.size(); i++) {
            String str = Base64Utils.encodeToString(array.get(i));
            resultList.add(str);
        }
        return resultList;
    }
}
