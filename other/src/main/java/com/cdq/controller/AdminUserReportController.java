package com.cdq.controller;

import com.cdq.dto.ReportReasonExecution;
import com.cdq.dto.UserReportExecution;
import com.cdq.model.ReportReason;
import com.cdq.model.ReportResult;
import com.cdq.model.User;
import com.cdq.model.UserReport;
import com.cdq.service.ReportReasonService;
import com.cdq.service.UserReportService;
import com.cdq.util.ConstansUtil;
import com.cdq.util.HttpServletRequestUtil;
import com.cdq.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/13 4:54
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
@RestController
@RequestMapping(value = "/admin", method = RequestMethod.POST)
public class AdminUserReportController {

    @Autowired
    private UserReportService userReportService;
    @Autowired
    private ReportReasonService reportReasonService;

    @RequestMapping("/agupl")
    public Map getUserReportList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //接收参数
        UserReport userReport = new UserReport();
        String rStr = HttpServletRequestUtil.getString(request, "reportStr");
        if (rStr!=null&&!ConstansUtil.EMPTY.equals(rStr)){
             userReport = (UserReport) ObjectUtil.toPojo(rStr, UserReport.class);
        }
        int indexPage = HttpServletRequestUtil.getInt(request,"indexPage");
        //调用service层
        UserReportExecution result = userReportService.getUserReport(userReport,indexPage,10);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put("reportList",result.getUserReportList());
            modelMap.put("totalSize",result.getCount());
            double temp = ((double) result.getCount()) / 10;
            modelMap.put("totalPage", Math.ceil(temp));
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "/aarr" )
    public Map addReportResult(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //表示情况是否属实
        boolean isTrue = HttpServletRequestUtil.getBoolean(request,"isTrue");
        int reportId = HttpServletRequestUtil.getInt(request,"reportId");
        String remark = HttpServletRequestUtil.getString(request,"remark");
        UserReport userReport = new UserReport();
        userReport.setUserReportId(reportId);
        ReportResult reportResult = new ReportResult();
        reportResult.setUserReport(userReport);
        //处理结果
        UserReportExecution result = null;
        if (remark==null||ConstansUtil.EMPTY.equals(remark)){
            reportResult.setRemark(ConstansUtil.EMPTY);
        }else{
            reportResult.setRemark(remark);
        }
        if (isTrue){
            //如果情况属实
            reportResult.setResultReportStatus((byte) 0);
            byte userStatus = (byte) HttpServletRequestUtil.getInt(request,"userStatus");
            String userId = HttpServletRequestUtil.getString(request,"userId");
            User user = new User();
            user.setUserId(userId);
            user.setUserStatus(userStatus);
            //插入受理结果，修改用户状态
            result = userReportService.changeUserStatus(user);
        }else{
            reportResult.setResultReportStatus((byte) 1);
            //调用service层插入数据
            result = userReportService.addResult(reportResult);
        }
        if (result.getState() == 0) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping("/agrrl")
    public Map getReportReasonList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //调用service层
        ReportReasonExecution result = reportReasonService.selectAllReason();
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
            modelMap.put("reasonList",result.getReportReasonList());
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping("/aarrea")
    public Map addReportReason(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //接收参数
        byte parentId = (byte) HttpServletRequestUtil.getInt(request,"parentId");
        String reasonName = HttpServletRequestUtil.getString(request,"reasonName");
        try {
            ReportReason reportReason = new ReportReason();
            ReportReason parentReason = new ReportReason();
            //组合参数
            if (parentId>0){
                parentReason.setReportReasonId(parentId);
            }else{
                parentReason.setReportReasonId(null);
            }
            reportReason.setParentReason(parentReason);
            reportReason.setReportReasonName(reasonName);
            //调用service层
            ReportReasonExecution result = reportReasonService.addReason(reportReason);
            if (result.getState()==0){
                modelMap.put(ConstansUtil.SUCCESS,true);
            }else{
                modelMap.put(ConstansUtil.SUCCESS,false);
                modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
            }
        }catch (Exception e){
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping("/adrrea")
    public Map delReportReason(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //接收参数
        ReportReason reportReason = new ReportReason();
        int rStr =  HttpServletRequestUtil.getInt(request, "reasonId");
        reportReason.setReportReasonId((byte) rStr);
        //调用service层
        ReportReasonExecution result = reportReasonService.delReason(reportReason);
        if (result.getState()==0){
            modelMap.put(ConstansUtil.SUCCESS,true);
        }else{
            modelMap.put(ConstansUtil.SUCCESS,false);
            modelMap.put(ConstansUtil.ERRMSG,result.getStateInfo());
        }
        return modelMap;
    }

}
