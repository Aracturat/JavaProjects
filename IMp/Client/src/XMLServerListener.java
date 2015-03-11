import dialog.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;

public class XMLServerListener extends AbstractServerListener {

    /** XML input stream */
    private InputStream input;

    /** XML output stream */
    private OutputStream output;

    /**
     * Default constructor.
     *
     * @param socket socket for listening.
     * @param controller controller for processing controller.
     */
    public XMLServerListener(Socket socket, Controller controller) {
        super(socket, controller);

        //Initialize input and output stream.
        try {
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Start thread with this listener.
        this.start();
    }

    public void run() {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            while(true) {
                int len = input.read();
                byte[] inputByte = new byte[len];
                input.read(inputByte);

                Document doc = db.parse(new ByteArrayInputStream(inputByte));

                Element root = doc.getDocumentElement();

                switch (root.getNodeName()) {
                    case "MESSAGE":
                        parseMessage(doc);
                        break;

                    case "ACTION":
                        parseAction(doc, root.getAttribute("type"));
                        break;
                }
            }
        } catch (SAXException e) {
            System.out.println("SAX exception");
        } catch (ParserConfigurationException e) {
            System.out.println("Parser exception");
        } catch (IOException e) {
            System.out.println("I killed myself.");
            this.finalize();
        }
    }

    /**
     * Parse new Action from NodeList.
     *
     * @param doc document for parsing.
     * @param type type of action.
     */
    private void parseAction(Document doc, String type) {
        Action action = new Action(ActionType.valueOf(type));

        NodeList nList = doc.getElementsByTagName("parameter");


        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            Element eElement = (Element) nNode;

            String key = eElement.getAttribute("key");
            String value = eElement.getAttribute("value");
            action.add(key, value);
        }
        super.handle(action);
    }

    /**
     * Parse new Message from NodeList.
     *
     * @param doc document for parsing.
     */
    private void parseMessage(Document doc) {
        String username = doc.getDocumentElement().getAttribute("username");
        String text = doc.getDocumentElement().getAttribute("text");
        String sessionName = doc.getDocumentElement().getAttribute("session_name");

        super.handle(new Message(text, username, sessionName));
    }


    /**
     * Send message to user.
     *
     * @param message message for send.
     */
    public void send(Message message) {
        String username = message.getUsername();
        String text = message.getText();
        String sessionName = message.getSessionName();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element rootElement = doc.createElement("MESSAGE");
            rootElement.setAttribute("username", username);
            rootElement.setAttribute("text", text);
            rootElement.setAttribute("session_name", sessionName);

            doc.appendChild(rootElement);

            DOMSource source = new DOMSource(doc);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(outputStream);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

            byte[] byteArray = outputStream.toByteArray();

            //Write size.
            outputStream.write(byteArray.length);
            outputStream.write(byteArray);
        } catch (ParserConfigurationException e) {
            System.out.println("Parser exception");
        } catch (IOException e) {
            System.out.println("I killed myself.");
            this.finalize();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send action to user.
     *
     * @param action action for send.
     */
    public void send(Action action) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element rootElement = doc.createElement("Action");
            rootElement.setAttribute("type", action.getType().toString());

            for (String key : action.getAll()) {
                String value = action.get(key);
                Element staff = doc.createElement("parameter");

                staff.setAttribute(key, value);
                rootElement.appendChild(staff);
            }

            DOMSource source = new DOMSource(doc);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(outputStream);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

            byte[] byteArray = outputStream.toByteArray();

            //Write size.
            outputStream.write(byteArray.length);
            outputStream.write(byteArray);
        } catch (ParserConfigurationException e) {
            System.out.println("Parser exception");
        } catch (IOException e) {
            System.out.println("I killed myself.");
            this.finalize();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
