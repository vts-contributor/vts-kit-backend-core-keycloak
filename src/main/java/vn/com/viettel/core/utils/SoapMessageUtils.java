package vn.com.viettel.core.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.soap.*;

import vn.com.viettel.core.dto.request.SoapMessageDTO;


public class SoapMessageUtils {

    // Get example soap message
    private SOAPMessage getSOAPMessageExample() throws Exception {

        SoapMessageDTO soapMessageDTO = new SoapMessageDTO("A", "B", "C", "D", "E", "F", "0");
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("impl", "http://impl.bulkSms.ws/");

         // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElement = soapBody.addChildElement("wsCpMt", "impl");

        SOAPElement soapUser = soapBodyElement.addChildElement("User");
        soapUser.addTextNode(soapMessageDTO.getUsername());

        SOAPElement soapPassword = soapBodyElement.addChildElement("Password");
        soapPassword.addTextNode(soapMessageDTO.getPassword());

        SOAPElement soapCpCode = soapBodyElement.addChildElement("CPCode");
        soapCpCode.addTextNode(soapMessageDTO.getCpCode());

        SOAPElement soapRequestId = soapBodyElement.addChildElement("RequestID");
        soapRequestId.addTextNode("1");

        SOAPElement soapUserId = soapBodyElement.addChildElement("UserID");
        soapUserId.addTextNode(soapMessageDTO.getReceiver());

        SOAPElement soapReceiverId = soapBodyElement.addChildElement("ReceiverID");
        soapReceiverId.addTextNode(soapMessageDTO.getReceiver());

        SOAPElement soapServiceId = soapBodyElement.addChildElement("ServiceID");
        soapServiceId.addTextNode(soapMessageDTO.getSender());

        SOAPElement soapCommandCode = soapBodyElement.addChildElement("CommandCode");
        soapCommandCode.addTextNode("bulksms");

        SOAPElement soapContent = soapBodyElement.addChildElement("Content");
        soapContent.addTextNode(soapMessageDTO.getContent());

        SOAPElement soapContentType = soapBodyElement.addChildElement("ContentType");
        soapContentType.addTextNode(soapMessageDTO.getContentType());

        soapMessage.saveChanges();
        return soapMessage;

	}

    public static SOAPMessage parseStringToSOAPMessage(String request) throws Exception {
        InputStream is = new ByteArrayInputStream(request.getBytes());
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage(null, is);
        soapMessage.saveChanges();
        return soapMessage;
    }
	
}
