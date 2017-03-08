package com.linCu.view.utils;

import java.io.IOException;

import java.text.MessageFormat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.component.UIXInput;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

/**
 * General useful static utilies for working with JSF.
 * NOTE: Updated to use JSF 1.2 ExpressionFactory.
 *
 * @author Duncan Mills
 * @author Steve Muench
 * @author Ric Smith
 *
 * $Id: JSFUtils.java 2383 2007-09-17 16:25:37Z drmills $
 */
public final class JSFUtils {
  
  /*
   * Private constructors on Util and Helper classes
   */
  private JSFUtils(){}

  private static Log logger = LogFactory.getLog(JSFUtils.class);

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching object (or creating it).
   * @param expression EL expression
   * @return Managed object
   */
  public static Object resolveExpression(String expression) {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
    return valueExp.getValue(elContext);
  }

  public static String resolveRemoteUser() {
    FacesContext facesContext = getFacesContext();
    ExternalContext ectx = facesContext.getExternalContext();
    return ectx.getRemoteUser();
  }

  public static String resolveUserPrincipal() {
    FacesContext facesContext = getFacesContext();
    ExternalContext ectx = facesContext.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
    return request.getUserPrincipal().getName();
  }

  public static MethodExpression createMethodExpression(String expression, Class returnType, Class[] argTypes) {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    MethodExpression methodExpression = elFactory.createMethodExpression(elContext, expression, returnType, argTypes);
    return methodExpression;
  }

  public static ValueExpression createValueExpression(String expression, Object newValue) {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
    return valueExp;
  }

  public static Object resolveMethodExpression(String expression, Class returnType, Class[] argTypes,
    Object[] argValues) {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    MethodExpression methodExpression = elFactory.createMethodExpression(elContext, expression, returnType, argTypes);
    return methodExpression.invoke(elContext, argValues);
  }

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching Boolean.
   * @param expression EL expression
   * @return Managed object
   */
  public static Boolean resolveExpressionAsBoolean(String expression) {
    return (Boolean) resolveExpression(expression);
  }

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching String (or creating it).
   * @param expression EL expression
   * @return Managed object
   */
  public static String resolveExpressionAsString(String expression) {
    return (String) resolveExpression(expression);
  }

  /**
   * Convenience method for resolving a reference to a managed bean by userId
   * rather than by expression.
   * @param beanName userId of managed bean
   * @return Managed object
   */
  public static Object getManagedBeanValue(String beanName) {
    StringBuffer buff = new StringBuffer("#{");
    buff.append(beanName);
    buff.append("}");
    return resolveExpression(buff.toString());
  }

  /**
   * Method for setting a new object into a JSF managed bean
   * Note: will fail silently if the supplied object does
   * not match the type of the managed bean.
   * @param expression EL expression
   * @param newValue new value to set
   */
  public static void setExpressionValue(String expression, Object newValue) {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
    Class bindClass = valueExp.getType(elContext);
    if(bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
      valueExp.setValue(elContext, newValue);
    }
  }

  /**
   * Convenience method for setting the value of a managed bean by userId
   * rather than by expression.
   * @param beanName userId of managed bean
   * @param newValue new value to set
   */
  public static void setManagedBeanValue(String beanName, Object newValue) {
    StringBuffer buff = new StringBuffer("#{");
    buff.append(beanName);
    buff.append("}");
    setExpressionValue(buff.toString(), newValue);
  }


  /**
   * Convenience method for setting Session variables.
   * @param key object key
   * @param object value to store
   */

  public static void storeOnSession(String key, Object object) {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    sessionState.put(key, object);
  }

  /**
   * Convenience method for getting Session variables.
   * @param key object key
   * @return session object for key
   */
  public static Object getFromSession(String key) {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    return sessionState.get(key);
  }

  public static String getFromHeader(String key) {
    FacesContext ctx = getFacesContext();
    ExternalContext ectx = ctx.getExternalContext();
    return ectx.getRequestHeaderMap().get(key);
  }

  /**
   * Convenience method for getting Request variables.
   * @param key object key
   * @return session object for key
   */
  public static Object getFromRequest(String key) {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getRequestMap();
    return sessionState.get(key);
  }

  public static HttpServletResponse getResponse() {
    return (HttpServletResponse) getExternalContext().getResponse();
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) getExternalContext().getRequest();
  }

  public static ExternalContext getExternalContext() {
    return getFacesContext().getExternalContext();
  }

  /**
   * Pulls a String resource from the property bundle that
   * is defined under the application &lt;message-bundle&gt; element in
   * the faces config. Respects Locale
   * @param key string message key
   * @return Resource value or placeholder error String
   */
  public static String getStringFromBundle(String key) {
    return getStringSafely(key, null);
  }



  /**
   * Convenience method to construct a <code>FacesMesssage</code>
   * from a defined error key and severity
   * This assumes that the error keys follow the convention of
   * using <b>_detail</b> for the detailed part of the
   * message, otherwise the main message is returned for the
   * detail as well.
   * @param key for the error message in the resource bundle
   * @param severity severity of message
   * @return Faces Message object
   */
  public static FacesMessage getMessageFromBundle(String key, FacesMessage.Severity severity) {
    String summary = getStringSafely(key, null);
    String detail = getStringSafely(key + "_detail", summary);
    FacesMessage message = new FacesMessage(summary, detail);
    message.setSeverity(severity);
    return message;
  }

  public static FacesMessage getMessageFromBundle(String key, FacesMessage.Severity severity, Object... args) {
    String summary = getStringSafely(key, null);
    summary = MessageFormat.format(summary, args);
    String detail = getStringSafely(key + "_detail", summary);
    detail = MessageFormat.format(detail, args);
    FacesMessage message = new FacesMessage(summary, detail);
    message.setSeverity(severity);
    return message;
  }

  /**
   * Add JSF info message.
   * @param messageKey key for message in bundle
   */
  public static void addFacesInformationMessage(String messageKey) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(getRootViewComponentId(), getMessageFromBundle(messageKey, FacesMessage.SEVERITY_INFO));
  }

  /**
   * Add JSF info message with args
   * @param messageKey key for message in bundle
   * @param args arguments for text replacement in message
   */
  public static void addFacesInformationMessage(String messageKey, Object... args) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(getRootViewComponentId(), getMessageFromBundle(messageKey, FacesMessage.SEVERITY_INFO, args));
  }
  
  
  /**
   * Add JSF warn message.
   * @param messageKey key for message in bundle
   */
  public static void addFacesWarnMessage(String messageKey) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(getRootViewComponentId(), getMessageFromBundle(messageKey, FacesMessage.SEVERITY_WARN));
  }

  /**
   * Add JSF warn message with args
   * @param messageKey key for message in bundle
   * @param args arguments for text replacement in message
   */
  public static void addFacesWarnMessage(String messageKey, Object... args) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(getRootViewComponentId(), getMessageFromBundle(messageKey, FacesMessage.SEVERITY_WARN, args));
  }

  /**
   * Add JSF error message.
   * @param messageKey key for message in bundle
   */
  public static void addFacesErrorMessage(String messageKey) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(getRootViewComponentId(), getMessageFromBundle(messageKey, FacesMessage.SEVERITY_ERROR));
  }

  /**
   * Add JSF error message.
   * @param messageKey key for message in bundle
   */
  public static void addFacesErrorMessage(String messageKey, Object... params) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(getRootViewComponentId(), getMessageFromBundle(messageKey, FacesMessage.SEVERITY_ERROR, params));
  }

  /**
   * Add JSF error message for a specific component.
   * @param compId id for the component to which this message applies
   * @param messageKey key for message in bundle
   */
  public static void addFacesErrorMessage(String compId, String messageKey) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(compId, getMessageFromBundle(messageKey, FacesMessage.SEVERITY_ERROR));
  }

  public static void addFacesMessage(FacesMessage.Severity severity, String clientId, String summary, String detail) {
    FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
  }

  public static void addErrorMessage(String summary) {
    FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary));
  }

  public static void addErrorMessage(String summary, String detail) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
  }

  public static void addErrorMessageWithComponentId(String compId, String summary) {
    FacesContext.getCurrentInstance().addMessage(compId,
        new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary));
  }

  public static void addInformationMessage(String summary) {
    FacesContext ctx = getFacesContext();
    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
  }


  /**
   * Get view id of the view root.
   * @return view id of the view root
   */
  public static String getRootViewId() {
    return getFacesContext().getViewRoot().getViewId();
  }

  /**
   * Get component id of the view root.
   * @return component id of the view root
   */
  public static String getRootViewComponentId() {
    return getFacesContext().getViewRoot().getId();
  }

  /**
   * Get FacesContext.
   * @return FacesContext
   */
  public static FacesContext getFacesContext() {
    return FacesContext.getCurrentInstance();
  }

  /**
   * Internal method to pull out the correct local
   * message bundle
   */
  private static ResourceBundle getBundle() {
    FacesContext ctx = getFacesContext();
    return JSFUtils.getBundle(ctx.getApplication().getMessageBundle());
  }

  private static ResourceBundle getBundle(String bundleName) {
    FacesContext ctx = getFacesContext();
    UIViewRoot uiRoot = ctx.getViewRoot();
    Locale locale = uiRoot.getLocale();
    ClassLoader ldr = Thread.currentThread().getContextClassLoader();
    return ResourceBundle.getBundle(bundleName, locale, ldr);
  }

  /**
   * Get an HTTP Request parameter.
   * @param name parameter userId
   * @return parameter value
   */
  public static String getRequestParameter(String name) {
    HttpServletRequest req = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    return req.getParameter(name);
  }

  /**
   * Get an HTTP Request attribute.
   * @param name attribute userId
   * @return attribute value
   */
  public static Object getRequestAttribute(String name) {
    return getFacesContext().getExternalContext().getRequestMap().get(name);
  }

  /**
   * Get an HTTP Session.
   * @param name attribute userId
   * @return attribute value
   */
  public static HttpSession getSession() {
    return (HttpSession) getFacesContext().getExternalContext().getSession(false);
  }

  /**
   * Set an HTTP Request attribute.
   * @param name attribute userId
   * @param value attribute value
   */
  public static void setRequestAttribute(String name, Object value) {
    getFacesContext().getExternalContext().getRequestMap().put(name, value);
  }

  /**
   * Set an ADFFaces ViewScope attribute.
   * @param name attribute userId
   * @param value attribute value
   */
  public static void setViewScopeAttribute(String name, Object value) {
    AdfFacesContext.getCurrentInstance().getViewScope().put(name, value);
  }

  /**
   * Get an ADFFaces ViewScope attribute.
   * @param name attribute userId
   * @param value attribute value
   */
  public static Object getViewScopeAttribute(String name) {
    return AdfFacesContext.getCurrentInstance().getViewScope().get(name);
  }

  /*
   * Internal method to proxy for resource keys that don't exist
   */

  private static String getStringSafely(String key, String defaultValue) {

    String resource = null;

    try {
      try {
        ResourceBundle bundle = getBundle();
        resource = bundle.getString(key);
      } catch(MissingResourceException mrex) {
        ResourceBundle bundle = getBundle("com.pepkorit.common.view.ViewBundle");
        resource = bundle.getString(key);
      }
    } catch(MissingResourceException mrex) {
      if(defaultValue != null) {
        resource = defaultValue;
      } else {
        resource = "*- " + key + " -*";
      }
    }

    return resource;
  }

  /**
   * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this.
   * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
   * @param id UIComponent id
   * @return UIComponent object
   */
  public static UIComponent findComponentInRoot(String id) {
    UIComponent component = null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if(facesContext != null) {
      UIComponent root = facesContext.getViewRoot();
      component = findComponent(root, id);
    }
    return component;
  }

  /**
   * Locate an UIComponent from its root component.
   * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
   * @param base root Component (parent)
   * @param id UIComponent id
   * @return UIComponent object
   */
  public static UIComponent findComponent(UIComponent base, String id) {
    if(id.equals(base.getId())){
      return base;
    }

    UIComponent children = null;
    UIComponent result = null;
    Iterator childrens = base.getFacetsAndChildren();
    while(childrens.hasNext() && (result == null)) {
      children = (UIComponent) childrens.next();
      if(id.equals(children.getId())) {
        result = children;
        break;
      }
      result = findComponent(children, id);
      if(result != null) {
        break;
      }
    }
    return result;
  }

  /**
   * Locate an UIComponent from its root component with an ID starting with the given id.
   * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
   * @param base root Component (parent)
   * @param startWithId UIComponent id matching start with string
   * @return UIComponent object
   */
  public static UIComponent findFirstComponentWithIdStartingWith(UIComponent base, String startWithId) {
    if(base != null && base.getId().startsWith(startWithId)){
      return base;
    }

    UIComponent children = null;
    UIComponent result = null;
    Iterator childrens = base.getFacetsAndChildren();
    while(childrens.hasNext() && (result == null)) {
      children = (UIComponent) childrens.next();
      if(children != null && children.getId() != null && children.getId().startsWith(startWithId)) {
        result = children;
        break;
      }
      result = findFirstComponentWithIdStartingWith(children, startWithId);
      if(result != null) {
        break;
      }
    }
    return result;
  }

  public static Set<UIComponent> findComponentsOfType(Class type) {
    Set<UIComponent> results = null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if(facesContext != null) {
      UIComponent root = facesContext.getViewRoot();
      results = findChildrenOfType(root, type);
    }
    return results;
  }

  public static Set<UIComponent> findChildrenOfType(UIComponent base, Class type) {
    Set<UIComponent> results = new HashSet<UIComponent>();

    if(base == null) {
      return Collections.emptySet();
    }

    for(Iterator iter = base.getFacetsAndChildren(); iter.hasNext(); ) {
      UIComponent child = (UIComponent) iter.next();
      if(isType(type, child)) {
        results.add(child);
      } else {
        results.addAll(findChildrenOfType(child, type));
      }
    }

    return results;
  }

  public static UIComponent findParentOfType(UIComponent base, Class type) {
    UIComponent parent = base.getParent();
    if(parent == null){
      return null;
    }
    if(!isType(type, parent)) {
      return findParentOfType(parent, type);
    }
    return parent;
  }

  private static boolean isType(Class type, UIComponent component) {
    return component != null && component.getClass().getName().equals(type.getName());
  }

  /**
   * Method to create a redirect URL. The assumption is that the JSF servlet mapping is
   * "faces", which is the default
   *
   * @param view the JSP or JSPX page to redirect to
   * @return a URL to redirect to
   */
  public static String getPageURL(String view) {
    FacesContext facesContext = getFacesContext();
    ExternalContext externalContext = facesContext.getExternalContext();
    String url = ((HttpServletRequest) externalContext.getRequest()).getRequestURL().toString();
    StringBuffer newUrlBuffer = new StringBuffer();
    newUrlBuffer.append(url.substring(0, url.lastIndexOf("faces/")));
    newUrlBuffer.append("faces");
    String targetPageUrl = view.startsWith("/") ? view : "/" + view;
    newUrlBuffer.append(targetPageUrl);
    return newUrlBuffer.toString();
  }

  public static void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
    FacesContext ctx = getFacesContext();
    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
    try {
      dispatcher.forward(request, response);
    } catch(ServletException se) {
      logger.error(se.getMessage());
    } catch(IOException ie) {
      logger.error(ie.getMessage());
    }
    ctx.responseComplete();
  }

  public static void reloadCurrentPage() {
    FacesContext fc = FacesContext.getCurrentInstance();
    String refreshpage = fc.getViewRoot().getViewId();
    ViewHandler viewHandler = fc.getApplication().getViewHandler();
    UIViewRoot UIV = viewHandler.createView(fc, refreshpage);
    UIV.setViewId(refreshpage);
    fc.setViewRoot(UIV);
  }

  /**
   * Gets the toString value of a UIXInput or null.
   * @param input Trinidad uix input component.
   * @return String value.
   */
  public static String getValueAsString(UIXInput input) {
    String returnValue = null;
    if(input != null) {
      if(input.getSubmittedValue() != null) {
        return input.getSubmittedValue().toString();
      }
      if(input.getValue() != null) {
        return input.getValue().toString();
      }
    }
    return returnValue;
  }

  public static UIComponent findComponentByClientIdFromRoot(String compId) {
    UIViewRoot root = getFacesContext().getViewRoot();
    UIComponent comp = root.findComponent(compId);

    if(comp == null) {
      comp = findComponentByClientId(root, compId);
    }
    return comp;
  }

  public static UIComponent findComponentByClientId(UIComponent comp, String compId) {
    UIComponent returnComp = null;
    if(comp == null || compId == null) {
      return null;
    }
    if(compId.equals(comp.getClientId())) {
      return comp;
    }
    Iterator itr = comp.getFacetsAndChildren();
    while(itr.hasNext()) {
      UIComponent childComp = (UIComponent) itr.next();
      UIComponent result = findComponentByClientId(childComp, compId);
      if(result != null) {
        return result;
      }
    }
    return returnComp;
  }

  private static Object lastRowKeyFromSet(RowKeySet set) {
    Iterator it = set.iterator();
    Object rowKeyPointer = null;
    while(it.hasNext()) {
      rowKeyPointer = it.next();
    }
    return rowKeyPointer;
  }

  public static String retrieveNodeAttributeFromRowIndexForTreeModel(String attributeName, int rowIndex,
    TreeModel treeModel) {
    String attr = null;

    if(rowIndex > -1) {
      Object rowData = treeModel.getRowData(rowIndex);
      JUCtrlHierNodeBinding nodeBinding = (JUCtrlHierNodeBinding) rowData;

      if(nodeBinding != null) {
        attr = (String) nodeBinding.getAttribute(attributeName);
      }
    }
    return attr;
  }

  public static String retrieveNodeAttributeFromRowKeySetForTreeModel(String attributeName, RowKeySet set,
    TreeModel treeModel) {
    String attr = null;

    if((set != null) && (set.size() > 0)) {
      Object currentRowKey = JSFUtils.lastRowKeyFromSet(set);
      JUCtrlHierNodeBinding nodeBinding = (JUCtrlHierNodeBinding) treeModel.getRowData(currentRowKey);

      if(nodeBinding != null) {
        attr = (String) nodeBinding.getAttribute(attributeName);
      }
    }
    return attr;
  }

  public static void refreshIteratorAndRelatedUIComponent(String iteratorName, UIComponent component) {
    // refresh the Tree Iterator
    DCIteratorBinding iter = ADFUtils.findIterator(iteratorName);
    iter.executeQuery();

    // refresh the Tree Component via a Partial Target
    AdfFacesContext.getCurrentInstance().addPartialTarget(component);
  }
  
}
