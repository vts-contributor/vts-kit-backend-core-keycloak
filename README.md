# Back End Core

This library provides utilities that make it easy to work with Spring Boot project.

### Feature List

- [Validation](#Validation)
- [RestfulAPI](#RestfulAPI)
- [SoapAPI](#SoapAPI)
- [CommonUtils](#CommonUtils)

## Usage

### Validation

By default, we provide some validation method beside using validate with annotation for your specific use cases.
```java
public static void isNullOrEmpty(String value, String msgCode) {
        String message = msgCode == null ? "Trường không được trống" : I18n.getMessage(msgCode);
        if(StringUtils.isNullOrEmpty(value)) throw new ValidateException(message);
}

public static void isEmail(String value, String msgCode) {
    String message = msgCode == null ? "Email không hợp lệ" : I18n.getMessage(msgCode);
    String regex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    if(!Pattern.matches(regex, value)) throw new ValidateException(message);
}

public static void isNumber(String value, String msgCode) {
    String message = msgCode == null ? "Trường chỉ được chứa số" : I18n.getMessage(msgCode);
    String regex = "\\d+";
    if(!Pattern.matches(regex, value)) throw new ValidateException(message);
}

public static void isSpecialCharacter(String value, String msgCode) {
    String message = msgCode == null ? "Trường không được chứa ký tự đặc biệt" : I18n.getMessage(msgCode);
    String regex = "^[_A-z0-9]*((-|\\s)*[_A-z0-9])*$";
    if(!Pattern.matches(regex, value)) throw new ValidateException(message);
}

public static void isTrim(String value, String msgCode) {
    String message = msgCode == null ? "Trường không được có ký tự trắng" : I18n.getMessage(msgCode);
    if(value.indexOf(" ") != -1) throw new ValidateException(message);
}
```
To use our method, just add below line of code to your file:
```java
ValidatorUtils.isEmail("tri@gmail.com", null);
ValidatorUtils.isNumber("123", null);
```
We also support internationalization, so if you want to modify your own error message, just add your message code like this:
```java
ValidatorUtils.isEmail("tri@gmail.com", "msg.email.valid");
ValidatorUtils.isNumber("123", "msg.number.valid");
```
In contrary, you can leave null like the above code and use our default error message.

### RestfulAPI

We choose RestTemplate for handling Restful API.

We use Builder Pattern for RestTemplate, below is an example of using RestTemplateBuilder:
```java
ResponseEntity<Object> result = new RestTemplateBuilder()
                                .setConnectTimeOut(3000)
                                .setReadTimeOut(2000)
                                .setProxy("10.30.176.10", 6776)
                                .setErrorHandler(new ErrorHandler() {
                                    @Override
                                    public void onError(ClientHttpResponse response) throws IOException {
                                        HttpStatus statusCode = response.getStatusCode();
                                            if(HttpStatus.NOT_FOUND.equals(response.getStatusCode())) {
                                                throw new CustomException("Không hợp lệ");
                                            }
                                    }
                                })
                                .build().doRequestRestTemplate(url, HttpMethod.POST, null, token, null);
```
You can set your custom proxy, custom error handler and other stuffs while building your own RestTemplate. For more information, you can check it out in [RestTemplateBuilder](src/main/java/vn/com/viettel/core/rest/RestTemplateBuilder.java) and [RestTemplateUtils](src/main/java/vn/com/viettel/core/utils/RestTemplateUtils.java).

### SoapAPI

To send your soap request, please follow the steps below:

**Step 1:** Converting your **Soap Request** to **SoapMessage**.
```java
String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:impl=\"http://impl.bulkSms.ws/\">"
			+ "<soapenv:Header/>"
			+ "<soapenv:Body>"
			+ "<impl:wsCpMt>"
			+ "<User>" + "vdtc" + "</User>"
			+ "<Password>" + "$18ac#75%@" + "</Password>"
			+ "<CPCode>" + "VDTC" + "</CPCode>"
			+ "<RequestID>" + "1" + "</RequestID>"
			+ "<UserID>" + "84981651642" + "</UserID>"
			+ "<ReceiverID>" + "84981651642" + "</ReceiverID>"
			+ "<ServiceID>" + "VTSHOP" + "</ServiceID>"
			+ "<CommandCode>" + "bulksms" + "</CommandCode>"
			+ "<Content>" + "Test 4sms 123" + "</Content>"
			+ "<ContentType>" + "0" + "</ContentType>"
			+ "</impl:wsCpMt>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
SOAPMessage mess = SoapMessageUtils.parseStringToSOAPMessage(request);
```
You can also build your own **SOAPMessage** manually like this:
```java
SoapMessageDTO soapMessageDTO = new SoapMessageDTO("vdtc", "$18ac#75%@", "VDTC", "VTSHOP", "84981651642", "Test 4sms 123", "0");
MessageFactory messageFactory = MessageFactory.newInstance();
SOAPMessage soapMessage = messageFactory.createMessage();
SOAPPart soapPart = soapMessage.getSOAPPart();

SOAPEnvelope envelope = soapPart.getEnvelope();
envelope.addNamespaceDeclaration("impl", "http://impl.bulkSms.ws/");

SOAPBody soapBody = envelope.getBody();
SOAPElement soapBodyElement = soapBody.addChildElement("wsCpMt", "impl");

SOAPElement soapUser = soapBodyElement.addChildElement("User");
soapUser.addTextNode(soapMessageDTO.getUsername());

SOAPElement soapPassword = soapBodyElement.addChildElement("Password");
soapPassword.addTextNode(soapMessageDTO.getPassword());

........

soapMessage.saveChanges();
return soapMessage;
```
For more detail, you can see example in [SoapMessageUtils](src/main/java/vn/com/viettel/core/utils/SoapMessageUtils.java) about building your own **SOAPMessage**. 

**Step 2:** Using the method below to send your **SOAPMessage**:
```java
SOAPMessage soapResponse = SoapServiceUtils.callWebService(mess, "http://ams.tinnhanthuonghieu.vn:8009/bulkapi?wsdl");
```
If you want to print your soapResponse or soapRequest, please add the following code:
```java
SoapServiceUtils.printSOAPMessage(soapMessage);
```

### CommonUtils
- By default, we provide some utils common class: Date Utils, String Utils, Number Utils, File Utils.     
  - Date Utils: Provides methods that are used to support date-related processing. you can check it out in [DateUtils](src/main/java/vn/com/viettel/core/utils/DateUtils.java)
  - File Utils: Provides methods used to process files. You can see details at [FileUtils](src/main/java/vn/com/viettel/core/utils/FileUtils.java)
  - Number Utils: Provides a method used to format numbers. You can see details at [FileUtils](src/main/java/vn/com/viettel/core/utils/NumberUtils.java)
  - String Utils: Provides methods used to process string type. You can see details at [StringUtils](src/main/java/vn/com/viettel/core/utils/StringUtils.java)

## Contribute

### Setting up the development environment

- **IDE:** Eclipse, Intellij IDEA
- **JDK:** >= JDK 8
- **Maven:** >= 3.6.0
- **Build:**

```
mvn clean install
```

### Contribute Guidelines

If you have any ideas, just open an issue and tell us what you think.

If you'd like to contribute, please refer [Contributors Guide](CONTRIBUTING.md).

## License

This code is under the [MIT License](https://opensource.org/licenses/MIT).

See the [LICENSE](LICENSE) file for required notices and attributions.