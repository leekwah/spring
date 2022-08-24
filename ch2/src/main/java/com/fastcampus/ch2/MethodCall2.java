package com.fastcampus.ch2;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

public class MethodCall2 {
	public static void main(String[] args) throws Exception{
		// (1) YoilTellerMVC의 객체를 생성
		Class clazz = Class.forName("com.fastcampus.ch2.YoilTellerMVC");
		Object obj = clazz.newInstance();
		// (2) main 메서드의 정보를 가져온다. (main메서드가 있고, 매개변수가 일치해야한다. - 오버로딩 될 수도 있기 때문에)
		Method main = clazz.getDeclaredMethod("main", int.class, int.class, int.class, Model.class);
		// (3) 모델의 구현체, 모델을 생성한다. (모델은 인터페이스 - 객체를 생성할 수 없다.)
		Model model = new BindingAwareModelMap();
		System.out.println("[before] model="+model);
		// (4) 메인 메서드를 호출한다. - invoke라는 메서드를 사용해야한다.
		// String viewName = obj.main(2021, 10, 1, model); 객체 배열을 생성 후에 받아야한다. 아랫 줄과 동일한 내용이다.
		// 이 다음예제는 { 2021, 10, 1, model }을 동적으로 받았다.
		String viewName = (String)main.invoke(obj, new Object[] { 2021, 10, 1, model }); // Reflection API를 이용한 호출이다.
		System.out.println("viewName="+viewName);	
		
		// Model의 내용을 출력 
		System.out.println("[after] model="+model);
				
		// 텍스트 파일을 이용한 rendering
		render(model, viewName);			
	} // main
	
	static void render(Model model, String viewName) throws IOException {
		String result = "";
		
		// 1. 뷰의 내용을 한줄씩 읽어서 하나의 문자열로 만든다.
		// JSP 파일을 이용했음, 스캐너 이용, UTF-8로 인코딩
		Scanner sc = new Scanner(new File("src/main/webapp/WEB-INF/views/"+viewName+".jsp"), "utf-8");
		
		// 한줄씩
		while(sc.hasNextLine())
			result += sc.nextLine()+ System.lineSeparator();
		
		// 2. model을 map으로 변환 
		Map map = model.asMap();
		
		// 3.key를 하나씩 읽어서 template의 ${key}를 value바꾼다.
		Iterator it = map.keySet().iterator();
		
		while(it.hasNext()) {
			String key = (String)it.next();

			// 4. replace()로 key를 value 치환한다.
			result = result.replace("${"+key+"}", ""+map.get(key));
		}
		
		// 5.렌더링 결과를 출력한다. (콘솔에)
		System.out.println(result);
	}
}