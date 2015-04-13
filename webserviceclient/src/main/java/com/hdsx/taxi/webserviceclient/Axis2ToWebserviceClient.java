package com.hdsx.taxi.webserviceclient;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMContainer;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class Axis2ToWebserviceClient {

	private static EndpointReference targetEPR = new EndpointReference(
			"http://218.206.107.18:10010/Service_T.asmx?wsdl");// 接口WebService地址

	public static void main(String[] args) {
		String nameSpace = "http://tempuri.org/";
		String soapType = SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI;// 设定SOAP版本soap1.1
		// String
		// soapType=SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI;//设定SOAP版本soap1.2

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fromtime", "2015-01-01 01:01:00");
		map.put("totime", "2015-01-01 11:11:00");
		map.put("cphm", "");
		map.put("name", "");
		map.put("ID_ECERT", "");
		map.put("jylx", "0,4,5,6");

		String method = "queryrundatacount_qingdao";
		OMElement ome = sendToWebservice(nameSpace, method, map, soapType);
		System.out.println(ome.getLocalName());
		System.out.println(ome.getNamespaceURI());
		Iterator it = ome.getChildrenWithLocalName("return");
		OMContainer omc = (OMContainer) it.next();
		OMContainer result = (OMContainer) omc.getChildrenWithLocalName(
				"res_value").next();
		System.out.println(((OMElement)result).getText());

		map = new HashMap<String, Object>();
		map.put("fromtime", "2015-01-01 01:01:00");
		map.put("totime", "2015-01-01 11:11:00");
		map.put("cphm", "");
		map.put("name", "");
		map.put("ID_ECERT", "");
		map.put("PageSize", 20);
		map.put("CurrPageIndex", 1);
		map.put("jylx", "0,4,5,6");
		method = "queryrundata_qingdao";
		sendToWebservice(nameSpace, method, map, soapType);

		map = new HashMap<String, Object>();
		map.put("fromtime", "2015-01-01 01:01:00");
		map.put("totime", "2015-01-01 11:11:00");
		map.put("jylx", "0,4,5,6");
		method = "queryrundatahz_qingdao";
		sendToWebservice(nameSpace, method, map, soapType);

	}

	public static OMElement sendToWebservice(String nameSpace, String smethod,
			HashMap<String, Object> map, String soapType) {
		try {

			OMFactory fac = OMAbstractFactory.getOMFactory();

			OMNamespace omNs = fac.createOMNamespace(nameSpace, "ns2");// 命名空间

			// 请求参数设置
			Options options = new Options();
			options.setTo(targetEPR);// 设定webservice地址
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);// 设定传输协议
			options.setSoapVersionURI(soapType);// 设定SOAP版本soap1.1或者1.2

			// 客户端绑定参数设置
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);

			OMElement method2 = fac.createOMElement(smethod, omNs);// 要调用的接口方法名称
			// 设定访问的接口方法
			for (String key : map.keySet()) {
				if (SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI
						.equals(soapType)) {
					OMElement value1 = fac.createOMElement(new QName(key));// 方法的第一个参数名称
					value1.addChild(fac.createOMText(value1, map.get(key)
							.toString()));// 设定参数的值
					method2.addChild(value1);// 方法设置参数
				} else if (SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI
						.equals(soapType)) {
					OMElement value1 = fac.createOMElement(key, omNs);// 方法的第一个参数名称
					value1.addChild(fac.createOMText(value1, map.get(key)
							.toString()));// 设定参数的值
					method2.addChild(value1);// 方法设置参数
				}
			}

			// 设定其他方法参数，针对参数是数组的情况如何处理?可以考虑为参数添加child,

			// ............
			System.out.println(method2);
			OMElement result2 = sender.sendReceive(method2);// 调用接口方法
			System.out.println(result2);// 打印接口返回结果

			// 处理result2
			return result2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
