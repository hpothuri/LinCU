package com.linCu.view.controller;

import com.linCu.constants.LinCUConstants;
import com.linCu.view.backingBeans.UserSessionData;
import javax.faces.context.FacesContext;
import java.io.Serializable;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import oracle.adf.controller.TaskFlowId;
/**
 * This class manages the dynamic region on the index page
 * It provides the memory of which task flow is currently selected within the 
 * region and the methods to switch that value
 */
public class IndexDynamicRegion implements Serializable {
    @SuppressWarnings("compatibility:-5329817642937831563")
    private static final long serialVersionUID = -4633485426455218156L;
    private static final String CREDIT_UNION_TASKFLOW = "/WEB-INF/flows/Credit-Union-TF.xml#Credit-Union-TF";
    private static final String MEMBER_TASKFLOW = "/WEB-INF/flows/Member-Flow-TF.xml#Member-Flow-TF";
    private static final String USER_INFO_TASKFLOW = "/WEB-INF/flows/User-Info-TF.xml#User-Info-TF";
    private static final String CARD_REQUEST_TASKFLOW = "/WEB-INF/flows/Card-Request.xml#Card-Request";
    //private static final String USER_LOGIN = "/WEB-INF/flows/User-Info-TF.xml#User-Info-TF";
    private String taskFlowId = CREDIT_UNION_TASKFLOW;

    public IndexDynamicRegion() {
        FacesContext ctx = FacesContext.getCurrentInstance();  
        ExpressionFactory ef = ctx.getApplication().getExpressionFactory();  
        ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{user}", UserSessionData.class);  
        UserSessionData userSessionData = (UserSessionData)ve.getValue(ctx.getELContext());
        System.out.println("------userSessionData------------"+userSessionData.getUserRole());
        if(LinCUConstants.MSR.equalsIgnoreCase(userSessionData.getUserRole())){
            taskFlowId = MEMBER_TASKFLOW;
        }else if(LinCUConstants.MAN_ADMIN.equalsIgnoreCase(userSessionData.getUserRole())){
            taskFlowId = CREDIT_UNION_TASKFLOW;
        }else if(LinCUConstants.SUP.equalsIgnoreCase(userSessionData.getUserRole())){
            taskFlowId = CARD_REQUEST_TASKFLOW;
        }else if(LinCUConstants.PREPAID_ADMIN.equalsIgnoreCase(userSessionData.getUserRole())){
            taskFlowId = USER_INFO_TASKFLOW;
        }else{
            taskFlowId = CARD_REQUEST_TASKFLOW;
        }
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String creditUnion() {
        setDynamicTaskFlowId(CREDIT_UNION_TASKFLOW);
        return null;
    }

    public String member() {
        setDynamicTaskFlowId(MEMBER_TASKFLOW);
        return null;
    }

    public String userInfo() {
        setDynamicTaskFlowId(USER_INFO_TASKFLOW);
        return null;
    }
    
    public String cardRequest() {
        setDynamicTaskFlowId(CARD_REQUEST_TASKFLOW);
        return null;
    }
    
}
