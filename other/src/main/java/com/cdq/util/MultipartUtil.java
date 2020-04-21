package com.cdq.util;

import com.cdq.model.Advertisement;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/21 20:45
 * @description ： ss
 * @modified By：ss
 * @version : 1.0.1
 */
public class MultipartUtil {

    private MultipartUtil(){

    }

    public static String getImaeUrl (HttpServletRequest request,String pex){
        MultipartHttpServletRequest multipartRequest = null;
        List<String> productImgs = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                    CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest.getFile("adverImg");
                    if (productImg != null) {
                        return  addThumils( productImg,pex);
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ConstansUtil.EMPTY;
    }
    private static String addThumils( CommonsMultipartFile multipartFile,String pex) throws IOException {
        ImageHolder imageHolder = new ImageHolder(multipartFile.getFileItem().getName(), multipartFile.getInputStream());
        String dest = PathUtil.getShopImagePath(pex);
        return ImageUtil.generateThumbnails(imageHolder, dest);
    }

}
