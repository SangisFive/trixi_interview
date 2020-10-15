package com.sangis.corejava.domain.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlMunicipalityParser  implements MunicipalityParser {

    public Iterable<BaseMunicipality> parse(File file) throws MunicipalityParserException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            ArrayList<BaseMunicipality> municipalities = new ArrayList();
//            long id = 1;
//            String name = "";
//
//            BaseMunicipality municipality = new BaseMunicipality(id, name);
            municipalities.add(null);

            //TODO: parse municipality from XML
            return municipalities;

        } catch (ParserConfigurationException e) {
            throw new MunicipalityParserException(e.getMessage());
        } catch (SAXException e) {
            throw new MunicipalityParserException(e.getMessage());
        } catch (IOException e) {
            throw new MunicipalityParserException(e.getMessage());
        }
    }
}
