package vn.com.viettel.core.utils;

import org.w3c.dom.Document;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;

public class SoapServiceUtils {

	private SoapServiceUtils() {
	}

	/**
	 * Method used to print the SOAP Message
	 */
	public static void printSOAPMessage(SOAPMessage soapMessage) throws Exception
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapMessage.getSOAPPart().getContent();
		System.out.println("\nResponse SOAP Message = ");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
	}

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 */
	public static SOAPMessage callWebService(SOAPMessage mess,String urlWebService) throws Exception
	{
		printSOAPMessage(mess);
		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		//Send SOAP Message to SOAP Server
		SOAPMessage soapResponse = soapConnection.call(mess, urlWebService);
		soapConnection.close();
		return soapResponse;
	}

	public static Document toDocument(SOAPMessage soapMsg) throws TransformerConfigurationException, TransformerException, SOAPException {
		Source src = soapMsg.getSOAPPart().getContent();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMResult result = new DOMResult();
		transformer.transform(src, result);
		return (Document)result.getNode();
	}

}
