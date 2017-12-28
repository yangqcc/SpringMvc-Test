package com.yqc.servlet;

import com.yqc.annotation.Controller;
import com.yqc.annotation.Qualifier;
import com.yqc.annotation.RequestMapping;
import com.yqc.annotation.Service;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangqc
 */
@WebServlet(name = "DispatcherServlet")
public class DispatcherServlet extends HttpServlet {

  private List<String> classNames = new ArrayList<>();

  private Map<String, Object> beans = new HashMap<>();

  private Map<String, Method> handlerMap = new HashMap<>();

  @Override
  public void init(ServletConfig config) throws ServletException {
    //1.包的扫描
    scanPackage("com.yqc");
    for (String className : classNames) {
      System.out.println(className);
    }

    //2.实例化类
    instance();

    for (Map.Entry<String, Object> entry : beans.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }

    //3.依赖注入
    ioc();

    //4.建立url与controller里面的method对象的映射关系
    handlerMapping();
    for (Map.Entry<String, Method> entry : handlerMap.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
//    super.init(config);
  }


  @Override
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    String url = request.getRequestURI();
    String context = request.getContextPath();
    System.out.println("url:" + url);
    System.out.println("context:" + context);
    String path = url.replace(context, "");
    Method method = handlerMap.get(path);
    Object instance = beans.get("/" + path.split("/")[1]);
    System.out.println(instance);
    System.out.println(method);
    try {
      Object object = method.invoke(instance, null);
      System.out.println(instance.getClass().getName() + ":" + object);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    this.doPost(request, response);
  }

  private void handlerMapping() {
    if (beans.size() <= 0) {
      return;
    }
    for (Map.Entry<String, Object> entry : beans.entrySet()) {
      Object instance = entry.getValue();
      Class clazz = instance.getClass();
      if (clazz.isAnnotationPresent(Controller.class)) {
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        String classUrl = requestMapping.value();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
          if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping1 = method.getAnnotation(RequestMapping.class);
            String methodUrl = requestMapping1.value();
            handlerMap.put(classUrl + methodUrl, method);
          } else {
            continue;
          }
        }
      }
    }
  }

  private void ioc() {
    if (beans.size() <= 0) {
      return;
    }
    for (Map.Entry<String, Object> entry : beans.entrySet()) {
      Object instance = entry.getValue();
      Class clazz = instance.getClass();
      Field[] fields = clazz.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(Qualifier.class)) {
          Qualifier qualifier = field.getAnnotation(Qualifier.class);
          String value = qualifier.value();
          field.setAccessible(true);
          try {
            field.set(instance, beans.get(value));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private void instance() {
    if (classNames.size() <= 0) {
      return;
    }
    for (String className : classNames) {
      String cName = className.replace(".class", "");
      try {
        Class clazz = Class.forName(cName);
        if (clazz.isAnnotationPresent(Controller.class)) {
          Controller controller = (Controller) clazz.getAnnotation(Controller.class);
          Object instance = clazz.newInstance();
          RequestMapping requestMapping = (RequestMapping) clazz
              .getAnnotation(RequestMapping.class);
          String requestMappingName = requestMapping.value();
          beans.put(requestMappingName, instance);
        } else if (clazz.isAnnotationPresent(Service.class)) {
          Service service = (Service) clazz.getAnnotation(Service.class);
          Object instance = clazz.newInstance();
          String serviceName = service.value();
          beans.put(serviceName, instance);
        } else {
          continue;
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 包的扫描
   */
  private void scanPackage(String packageName) {
    //file: D:/workspace/com/yqc
    java.net.URL url = this.getClass().getClassLoader().getResource(replaceTo(packageName));
    String fileStr = url.getFile();
    System.out.println(fileStr);
    File file = new File(fileStr);
    //拿到路径下的所有的文件字符串
    String[] filesStr = file.list();
    for (String path : filesStr) {
      File filePath = new File(fileStr + path);
      //如果是文件夹
      if (filePath.isDirectory()) {
        scanPackage(packageName + "." + path);
      } else {
        classNames.add(packageName + "." + filePath.getName());
      }
    }
  }

  private String replaceTo(String packageName) {
    return packageName.replaceAll("\\.", "/");
  }

}
