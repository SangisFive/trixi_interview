package com.sangis.corejava.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;
import com.sangis.corejava.utils.SingleItemIterator;


import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;

public class XmlMunicipalityParser implements MunicipalityParser {
    private static final String DATA_TAG = "Data";
    private static final String DATA_PREFIX = "vf";

    private static final String MUNICIPALITIES_TAG = "Obce";
    private static final String MUNICIPALITY_TAG = "Obec";
    private static final String MUNICIPALITY_PREFIX = "obi";

    private static final String MUNICIPALITY_PARTS_TAG = "CastiObci";
    private static final String MUNICIPALITY_PART_TAG = "CastObce";

    private static final String NAME_TAG = "Nazev";
    private static final String CODE_TAG = "Kod";


    public Iterable<BaseMunicipality> parse(InputStream inputStream) throws MunicipalityParserException {
        final XMLInputFactory factory = XMLInputFactory.newInstance();
        HashMap<Integer, BaseMunicipality> municipalities = new HashMap<>();

        try {
            final XMLEventReader reader = factory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                final XMLEvent event = reader.nextEvent();
                if (event.isStartElement() && event.asStartElement().getName().getPrefix().equals(DATA_PREFIX)) {
                    String elementName = event.asStartElement().getName().getLocalPart();

                    switch (elementName) {
                        case MUNICIPALITY_TAG:
                            BaseMunicipality municipality = parseMunicipality(reader);
                            municipalities.put(municipality.getCode(), municipality);
                            break;
                        case MUNICIPALITY_PART_TAG:
                            BaseMunicipalityPart municipalityPart = parseMunicipalityPart(reader);
                            BaseMunicipality parentMunicipality = municipalities.get(municipalityPart.getMunicipalityCode());
                            if (parentMunicipality != null)
                                parentMunicipality.addParts(new SingleItemIterator<>(municipalityPart));
                            break;
                    }
                } else if (event.isEndElement()
                        && event.asEndElement().getName().getLocalPart().equals(DATA_TAG)
                        && event.asEndElement().getName().getPrefix().equals(DATA_PREFIX))
                    return municipalities.values();
            }
            throw new MunicipalityParserException("Wrong XML format <" + DATA_TAG + "> never started or never ended, but EOF was reached");
        } catch (XMLStreamException e) {
            throw new MunicipalityParserException(e.getMessage());
        }
    }


    private BaseMunicipality parseMunicipality(XMLEventReader reader) throws XMLStreamException, MunicipalityParserException {
        int code = -1;
        String name = null;
        while (reader.hasNext()) {
            final XMLEvent event = reader.nextEvent();
            if (event.isStartElement() && event.asStartElement().getName().getPrefix().equals(MUNICIPALITY_PREFIX)) {
                final StartElement element = event.asStartElement();
                final QName elementName = element.getName();
                switch (elementName.getLocalPart()) {
                    case NAME_TAG:
                        name = reader.getElementText();
                        break;
                    case CODE_TAG:
                        code = Integer.parseInt(reader.getElementText());
                }
            } else if (event.isEndElement()
                    && event.asEndElement().getName().getLocalPart().equals(MUNICIPALITY_TAG)
                    && event.asEndElement().getName().getPrefix().equals(DATA_PREFIX)) {
                return new BaseMunicipality(code, name);
            }

        }
        throw new MunicipalityParserException("Wrong XML format <" + MUNICIPALITY_TAG + "> tag opened, but EOF reached before closing tag was found");
    }

    private BaseMunicipalityPart parseMunicipalityPart(XMLEventReader reader) throws XMLStreamException, MunicipalityParserException {
        int code = -1;
        String name = null;
        int municipalityCode = -1;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                QName qname = element.getName();
                String elementName = qname.getLocalPart();
                switch (elementName) {
                    case CODE_TAG:
                        if (qname.getPrefix().equals("coi")) {
                            code = Integer.parseInt(reader.getElementText());
                        } else if (qname.getPrefix().equals("obi")) {
                            municipalityCode = Integer.parseInt(reader.getElementText());
                        }
                        break;
                    case NAME_TAG:
                        name = reader.getElementText();
                }
            } else if (event.isEndElement()
                    && event.asEndElement().getName().getLocalPart().equals(MUNICIPALITY_PART_TAG)
                    && event.asEndElement().getName().getPrefix().equals(DATA_PREFIX)) {
                return new BaseMunicipalityPart(code, municipalityCode, name);
            }

        }
        throw new MunicipalityParserException("Wrong XML format <" + MUNICIPALITY_PART_TAG + "> tag opened, but EOF reached before closing tag was found");
    }

}
