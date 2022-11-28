package vn.com.viettel.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapMessageDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3874335090425660475L;

    @XmlElement(name = "User")
    private String username;

    @XmlElement(name = "Password")
    private String password;

    @XmlElement(name = "CPCode")
    private String cpCode;

    @XmlElement(name = "ServiceID")
    private String sender;

    @XmlElement(name = "UserID")
    private String receiver;

    @XmlElement(name = "Content")
    private String content;

    @XmlElement(name = "ContentType")
    private String contentType;

}
