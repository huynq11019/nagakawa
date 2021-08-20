package com.nagakawa.guarantee.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nagakawa.guarantee.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comment
 *
 * @author nguyenhungbk96@gmail.com
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Value("${bulksms.user}")
    private String user;

    @Value("${bulksms.password}")
    private String password;

    @Value("${bulksms.cpcode}")
    private String cpcode;

    @Value("${bulksms.serviceid}")
    private String serviceid;

    @Value("${bulksms.remote_url}")
    private String remoteUrl;

    @Override
	public SmsResponse send(String sendTo, String message) {
        if (sendTo == null || message == null) {
            return new SmsResponse(0, "So dien thoai khong dung.");
        }

        final Pattern PHONE_REGX = Pattern.compile("(09|03|08|07|05|\\+84[9|3|8|7])+([0-9]{8})", Pattern.CASE_INSENSITIVE);
        boolean isPhoneNumber = PHONE_REGX.matcher(sendTo).matches();

        if (!isPhoneNumber) {
            return new SmsResponse(0, "So dien thoai khong dung.");
        }

        sendTo = String.valueOf("0".equals(String.valueOf(sendTo.charAt(0))) ? Long.valueOf("84" + Long.valueOf(sendTo)) : Long.valueOf(sendTo));

        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();

            soapMsg.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
            soapMsg.getSOAPPart().getEnvelope().addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
            soapMsg.getSOAPPart().getEnvelope().setPrefix("soapenv");
            soapMsg.getSOAPHeader().setPrefix("soapenv");
            soapMsg.getSOAPBody().setPrefix("soapenv");


            SOAPPart part = soapMsg.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            envelope.setAttribute("xmlns:impl", "http://impl.bulkSms.ws/");

            // SOAPHeader header = envelope.getHeader();

            SOAPBody body = envelope.getBody();
            SOAPBodyElement element = body.addBodyElement(envelope.createQName("wsCpMt", "impl"));
            element.addChildElement("User").addTextNode(user);
            element.addChildElement("Password").addTextNode(password);
            element.addChildElement("CPCode").addTextNode(cpcode);
            element.addChildElement("ServiceID").addTextNode(serviceid);
            element.addChildElement("RequestID").addTextNode("4");
            element.addChildElement("UserID").addTextNode(sendTo);
            element.addChildElement("ReceiverID").addTextNode(sendTo);
            element.addChildElement("CommandCode").addTextNode("bulksms");
            element.addChildElement("Content").addTextNode(message);
            element.addChildElement("ContentType").addTextNode("0");
            element.addChildElement("CountryCallingCode").addTextNode("84");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapMsg.writeTo(out);
            String requestBody = new String(out.toByteArray());


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.TEXT_XML_VALUE));

            ResponseEntity<String> exchange;

            RestTemplate restTemplate = new RestTemplate();
            exchange = restTemplate.exchange(remoteUrl, HttpMethod.POST, new HttpEntity<>(requestBody, headers),
                    String.class);
            /* Read response */
            Reader reader = new StringReader(exchange.getBody());
            XMLInputFactory responseFactory = XMLInputFactory.newInstance();
            responseFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
            responseFactory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);

            XMLStreamReader xsr = responseFactory.createXMLStreamReader(reader);
            xsr.nextTag();
            xsr.nextTag();
            xsr.nextTag();
            xsr.nextTag();

            JAXBContext jc = JAXBContext.newInstance(SmsResponse.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<SmsResponse> je = unmarshaller.unmarshal(xsr, SmsResponse.class);

            return je.getValue();
        } catch (Exception e) {
            LOGGER.error("Loi khi gui tin nhan: ", e);
        }

        return new SmsResponse(0, "gui tin nhan that bai.");
    }

    @Data
    @XmlRootElement(name = "return")
    @XmlAccessorType(XmlAccessType.FIELD)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SmsResponse {
        @XmlElement(name = "message")
        private String message;

        /* result=1 -> send thanh cong, result=0 -> send that bai */
        @XmlElement(name = "result")
        private int result;

        public SmsResponse(int result, String mess) {
            this.result = result;
            this.message = mess;
        }
    }
}