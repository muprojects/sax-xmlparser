/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxxmlparser;


import java.io.File;
import javafx.scene.control.TreeItem;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author dpleo
 */
public class SAXXMLLoader {
    
    private static TreeItem<String> SAXXMLRoot = new TreeItem();// must declare outside
    
    public static TreeItem<String> load(File xmlFile) throws Exception {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {
                    TreeItem item = new TreeItem<>(qName);
                    SAXXMLRoot.getChildren().add(item);
                    SAXXMLRoot = item;
                }

                @Override
                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                    SAXXMLRoot = SAXXMLRoot.getParent();
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    String data = String.valueOf(ch, start, length).trim();
                    if (!data.isEmpty()) {
                        SAXXMLRoot.getChildren().add(new TreeItem<>(data));
                    }
                }

            };
            
            // file, DefaultHandler
            saxParser.parse(xmlFile, handler);
            
            TreeItem<String> root = SAXXMLRoot.getChildren().get(0);
            SAXXMLRoot.getChildren().clear();
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
