package com.sangis.corejava.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;
import com.sangis.corejava.utils.SingleItemIterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.EventListener;
import java.util.HashMap;

public class XmlMunicipalityParser implements MunicipalityParser {
    private static final String DATA_TAG = "Data";
    private static final String DATA_PREFIX = "vf";

    private static final String MUNICIPALITIES_TAG = "Obce";
    private static final String MUNICIPALITY_TAG = "Obec";
    private static final String MUNICIPALITY_PREFIX = "obi";

    private static final String MUNICIPALITY_PARTS_TAG = "CastiObci";
    private static final String MUNICIPALITY_PART_TAG = "CastObce";
    public static final String MUNICIPALITY_PART_PREFIX = "coi";


    private static final String NAME_TAG = "Nazev";
    private static final String CODE_TAG = "Kod";

    @Override
    public Iterable<BaseMunicipality> parse(InputStream inputStream) throws MunicipalityParserException {
        final XMLInputFactory factory = XMLInputFactory.newInstance();
        final HashMap<Integer, BaseMunicipality> municipalities = new HashMap<>();

        try {
            final XMLEventReader reader = factory.createXMLEventReader(inputStream);
            boolean endOfFileReached = skipUntilStartElement(reader, DATA_PREFIX, MUNICIPALITIES_TAG);
            if (endOfFileReached) return municipalities.values();

            endOfFileReached = doUntilEndElementFound(reader, DATA_PREFIX, MUNICIPALITIES_TAG, (element) -> {
                QName elementName = element.getName();
                if (!elementName.getPrefix().equals(DATA_PREFIX) || !elementName.getLocalPart().equals(MUNICIPALITY_TAG))
                    return;

                // Municipality found!
                BaseMunicipality municipality = parseMunicipality(reader);
                municipalities.put(municipality.getCode(), municipality);

            });
            if (endOfFileReached) return municipalities.values();

            endOfFileReached = skipUntilStartElement(reader, DATA_PREFIX, MUNICIPALITY_PARTS_TAG);
            if (endOfFileReached) return municipalities.values();

            // Municipality parts found!
            doUntilEndElementFound(reader, DATA_PREFIX, MUNICIPALITY_PARTS_TAG, (element) -> {
                QName elementName = element.getName();
                if (!elementName.getPrefix().equals(DATA_PREFIX) || !elementName.getLocalPart().equals(MUNICIPALITY_PART_TAG))
                    return;

                // Municipality part found!
                BaseMunicipalityPart municipalityPart = parseMunicipalityPart(reader);
                BaseMunicipality parent = municipalities.get(municipalityPart.getMunicipalityCode());
                parent.addParts(new SingleItemIterator<>(municipalityPart));

            });
            return municipalities.values();


        } catch (XMLStreamException e) {
            throw new MunicipalityParserException(e.getMessage());
        }


    }

    private boolean doUntilEndElementFound(final XMLEventReader reader, String prefix, String tagName, StartElementEventHandler handler) throws MunicipalityParserException {
        boolean searchForPrefix = false;
        boolean seacrhForTagName = false;

        //Input validation
        if (prefix != null) searchForPrefix = true;
        if (tagName != null) seacrhForTagName = true;
        if (!searchForPrefix && !seacrhForTagName) {
            throw new IllegalArgumentException("Searched prefix or tagName must be specified");
        }
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (searchForPrefix && !prefix.equals(endElement.getName().getPrefix())) {
                        // Skip if searching for prefix and prefix does not match
                        continue;
                    }
                    if (seacrhForTagName && !tagName.equals(endElement.getName().getLocalPart())) {
                        // Skip if searching for tagName and tagName does not match
                        continue;
                    }
                    return false;
                }
                if (event.isStartElement()) handler.handle(event.asStartElement());

            }
            return true;

        } catch (XMLStreamException e) {
            e.printStackTrace();
            throw new MunicipalityParserException(e.getMessage());
        }
    }


    private boolean skipUntilStartElement(final XMLEventReader reader, String prefix, String tagName) throws MunicipalityParserException, IllegalArgumentException {
        boolean searchForPrefix = false;
        boolean seacrhForTagName = false;

        //Input validation
        if (prefix != null) searchForPrefix = true;
        if (tagName != null) seacrhForTagName = true;
        if (!searchForPrefix && !seacrhForTagName) {
            throw new IllegalArgumentException("Searched prefix or tagName must be specified");
        }
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (searchForPrefix && !prefix.equals(startElement.getName().getPrefix())) {
                        // Skip if searching for prefix and prefix does not match
                        continue;
                    }
                    if (seacrhForTagName && !tagName.equals(startElement.getName().getLocalPart())) {
                        // Skip if searching for tagName and tagName does not match
                        continue;
                    }
                    // matching opening tag found
                    return false;
                }
            }
            return true;

        } catch (XMLStreamException e) {
            throw new MunicipalityParserException(e.getMessage());
        }
    }

    private BaseMunicipality parseMunicipality(final XMLEventReader reader) throws MunicipalityParserException, XMLStreamException {

        skipUntilStartElement(reader, MUNICIPALITY_PREFIX, CODE_TAG);
        int code = Integer.parseInt(reader.getElementText());

        skipUntilStartElement(reader, MUNICIPALITY_PREFIX, NAME_TAG);
        String name = reader.getElementText();

        if (code > 0 && !name.isEmpty()) {
            return new BaseMunicipality(code, name);
        }
        throw new MunicipalityParserException("Error parsing Municipality: code or name are not found or the values are not valid");

    }


    private BaseMunicipalityPart parseMunicipalityPart(final XMLEventReader reader) throws MunicipalityParserException, XMLStreamException {

        skipUntilStartElement(reader, MUNICIPALITY_PART_PREFIX, CODE_TAG);
        int code = Integer.parseInt(reader.getElementText());

        skipUntilStartElement(reader, MUNICIPALITY_PART_PREFIX, NAME_TAG);
        String name = reader.getElementText();

        skipUntilStartElement(reader, MUNICIPALITY_PREFIX, CODE_TAG);
        int municipalityCode = Integer.parseInt(reader.getElementText());

        if (code > 0 && !name.isEmpty() && municipalityCode > 0) {
            return new BaseMunicipalityPart(code, municipalityCode, name);
        }
        throw new MunicipalityParserException("Error parsing MunicipalityPart: code, name or municipalityCode are not found or the values are not valid");
    }


}

interface StartElementEventHandler {
    void handle(StartElement event) throws MunicipalityParserException, XMLStreamException;
}


