package com.sangis.corejava.infrastructure.parser;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlMunicipalityParserTest {
    private MunicipalityParser xmlMunicipalityParser = new XmlMunicipalityParser();


    @Test
    void parse() throws UnsupportedEncodingException, MunicipalityParserException {

        List<BaseMunicipality> testData = new ArrayList<BaseMunicipality>();
        for (int i = 1; i < 4; i++) {
            BaseMunicipality m = new BaseMunicipality(i, "Name of municipality " + i);
            testData.add(m);

            List<BaseMunicipalityPart> parts = new ArrayList<BaseMunicipalityPart>();
            for (int j = 1 ; j < 10 + i; j++) {
                int code = j*i;
                BaseMunicipalityPart p = new BaseMunicipalityPart(code, i, "Name of municipality part " + code);
                parts.add(p);
            }
            m.addParts(parts.iterator());
        }

        List<BaseMunicipality> results = new ArrayList<BaseMunicipality>();

        xmlMunicipalityParser.parse(getInputStream(testData)).forEach(results::add);

        compare(testData, results);
    }
    @Test
    void parseNoMunicipalities() throws UnsupportedEncodingException, MunicipalityParserException {

        List<BaseMunicipality> testData = new ArrayList<BaseMunicipality>();

        List<BaseMunicipality> results = new ArrayList<BaseMunicipality>();

        xmlMunicipalityParser.parse(getInputStream(testData)).forEach(results::add);

        compare(testData, results);
    }
    @Test
    void parseNoParts() throws UnsupportedEncodingException, MunicipalityParserException {

        List<BaseMunicipality> testData = new ArrayList<BaseMunicipality>();
        for (int i = 1; i < 4; i++) {
            BaseMunicipality m = new BaseMunicipality(i, "Name of municipality " + i);
            testData.add(m);
        }

        List<BaseMunicipality> results = new ArrayList<BaseMunicipality>();

        xmlMunicipalityParser.parse(getInputStream(testData)).forEach(results::add);

        compare(testData, results);
    }

    private InputStream getInputStream(Iterable<BaseMunicipality> municipalities) throws UnsupportedEncodingException {

        ArrayList<String> municipalitiesXmlEntries = new ArrayList<String>();
        ArrayList<String> partsXmlEntries = new ArrayList<String>();

        for (BaseMunicipality municipality : municipalities) {
            municipalitiesXmlEntries.add(makeMunicipalityXmlEntry(municipality.getCode(), municipality.getName()));
            Iterator<BaseMunicipalityPart> partsIterator = municipality.getParts();
            while (partsIterator.hasNext()) {
                BaseMunicipalityPart part = partsIterator.next();
                partsXmlEntries.add(makeMunicipalityPartXmlEntry(part.getCode(), part.getName(), part.getMunicipalityCode()));
            }
        }


        return new ByteArrayInputStream(
                prepareInput(municipalitiesXmlEntries, partsXmlEntries).getBytes("UTF-8"));
    }


    private void compare(List<BaseMunicipality> input, List<BaseMunicipality> results) {

        assertEquals(input.size(), results.size());

        results.sort(Comparator.comparingInt(BaseMunicipality::getCode));
        input.sort(Comparator.comparingInt(BaseMunicipality::getCode));

        for (int i = 0; i < input.size(); i++) {
            BaseMunicipality inp = input.get(i);
            BaseMunicipality out = results.get(i);

            assertEquals(inp.getCode(), out.getCode());
            assertEquals(inp.getName(), out.getName());

            Iterator<BaseMunicipalityPart> inpIt = inp.getParts();
            Iterator<BaseMunicipalityPart> outIt = inp.getParts();

            while (inpIt.hasNext()) {
                BaseMunicipalityPart inpPart = inpIt.next();
                BaseMunicipalityPart outPart = outIt.next();

                assertEquals(inpPart.getCode(), outPart.getCode());
                assertEquals(inpPart.getName(), outPart.getName());
                assertEquals(inpPart.getMunicipalityCode(), outPart.getMunicipalityCode());
            }


        }


    }


    private String prepareInput(Iterable<String> municipalities, Iterable<String> municipalityParts) {
        StringBuilder inputBuilder = new StringBuilder();
        inputBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<vf:VymennyFormat xmlns:gml=\"http://www.opengis.net/gml/3.2\"\n" +
                "                  xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
                "                  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                  xmlns:ami=\"urn:cz:isvs:ruian:schemas:AdrMistoIntTypy:v1\"\n" +
                "                  xmlns:base=\"urn:cz:isvs:ruian:schemas:BaseTypy:v1\"\n" +
                "                  xmlns:coi=\"urn:cz:isvs:ruian:schemas:CastObceIntTypy:v1\"\n" +
                "                  xmlns:com=\"urn:cz:isvs:ruian:schemas:CommonTypy:v1\"\n" +
                "                  xmlns:kui=\"urn:cz:isvs:ruian:schemas:KatUzIntTypy:v1\"\n" +
                "                  xmlns:kri=\"urn:cz:isvs:ruian:schemas:KrajIntTypy:v1\"\n" +
                "                  xmlns:mci=\"urn:cz:isvs:ruian:schemas:MomcIntTypy:v1\"\n" +
                "                  xmlns:mpi=\"urn:cz:isvs:ruian:schemas:MopIntTypy:v1\"\n" +
                "                  xmlns:obi=\"urn:cz:isvs:ruian:schemas:ObecIntTypy:v1\"\n" +
                "                  xmlns:oki=\"urn:cz:isvs:ruian:schemas:OkresIntTypy:v1\"\n" +
                "                  xmlns:opi=\"urn:cz:isvs:ruian:schemas:OrpIntTypy:v1\"\n" +
                "                  xmlns:pai=\"urn:cz:isvs:ruian:schemas:ParcelaIntTypy:v1\"\n" +
                "                  xmlns:pui=\"urn:cz:isvs:ruian:schemas:PouIntTypy:v1\"\n" +
                "                  xmlns:rsi=\"urn:cz:isvs:ruian:schemas:RegSouIntiTypy:v1\"\n" +
                "                  xmlns:spi=\"urn:cz:isvs:ruian:schemas:SpravObvIntTypy:v1\"\n" +
                "                  xmlns:sti=\"urn:cz:isvs:ruian:schemas:StatIntTypy:v1\"\n" +
                "                  xmlns:soi=\"urn:cz:isvs:ruian:schemas:StavObjIntTypy:v1\"\n" +
                "                  xmlns:uli=\"urn:cz:isvs:ruian:schemas:UliceIntTypy:v1\"\n" +
                "                  xmlns:vci=\"urn:cz:isvs:ruian:schemas:VuscIntTypy:v1\"\n" +
                "                  xmlns:vf=\"urn:cz:isvs:ruian:schemas:VymennyFormatTypy:v1\"\n" +
                "                  xmlns:zji=\"urn:cz:isvs:ruian:schemas:ZsjIntTypy:v1\"\n" +
                "                  xmlns:voi=\"urn:cz:isvs:ruian:schemas:VOIntTypy:v1\"\n" +
                "                  xsi:schemaLocation=\"urn:cz:isvs:ruian:schemas:VymennyFormatTypy:v1 ../ruian/xsd/vymenny_format/VymennyFormatTypy.xsd\">");

        inputBuilder.append("<vf:Hlavicka>\n" +
                "      <vf:VerzeVFR>2.1</vf:VerzeVFR>\n" +
                "      <vf:TypZaznamu>Platne</vf:TypZaznamu>\n" +
                "      <vf:TypDavky>Plna</vf:TypDavky>\n" +
                "      <vf:TypSouboru>OB_UZSZ</vf:TypSouboru>\n" +
                "      <vf:Datum>2020-10-01T00:16:03</vf:Datum>\n" +
                "      <vf:TransakceOd>\n" +
                "         <com:Id>0</com:Id>\n" +
                "         <com:Zapsano>2012-06-29T01:36:44</com:Zapsano>\n" +
                "      </vf:TransakceOd>\n" +
                "      <vf:TransakceDo>\n" +
                "         <com:Id>3529795</com:Id>\n" +
                "         <com:Zapsano>2020-09-30T23:59:01</com:Zapsano>\n" +
                "      </vf:TransakceDo>\n" +
                "      <vf:Metadata xlink:type=\"simple\"\n" +
                "                   xlink:href=\"http://cuzk.cz/ruian/metadata/20200930_OB_573060_UZSZ.xml\"/>\n" +
                "      <vf:PlatnostDatK>\n" +
                "         <com:ISUI>2020-09-30T23:00:44</com:ISUI>\n" +
                "         <com:ISKN>2020-09-30T23:59:01</com:ISKN>\n" +
                "      </vf:PlatnostDatK>\n" +
                "   </vf:Hlavicka>");
        inputBuilder.append("<vf:Data>");
        inputBuilder.append("<vf:Obce>");

        for (String municipality : municipalities) {
            inputBuilder.append(municipality);
        }

        inputBuilder.append("</vf:Obce>");
        inputBuilder.append("<vf:CastiObci>");
        for (String municipalityPart : municipalityParts) {
            inputBuilder.append(municipalityPart);
        }
        inputBuilder.append("</vf:CastiObci>");

        inputBuilder.append("</vf:Data>");
        inputBuilder.append("</vf:VymennyFormat>");
        return inputBuilder.toString();
    }


    private String makeMunicipalityXmlEntry(int code, String name) {
        return String.format("<vf:Obec gml:id=\"OB.%d\">\n" +
                "            <obi:Kod>%d</obi:Kod>\n" +
                "            <obi:Nazev>%s</obi:Nazev>\n" +
                "            <obi:StatusKod>3</obi:StatusKod>\n" +
                "            <obi:Okres>\n" +
                "               <oki:Kod>3604</oki:Kod>\n" +
                "            </obi:Okres>\n" +
                "            <obi:Pou>\n" +
                "               <pui:Kod>2186</pui:Kod>\n" +
                "            </obi:Pou>\n" +
                "            <obi:PlatiOd>2019-07-10T00:00:00</obi:PlatiOd>\n" +
                "            <obi:IdTransakce>2937100</obi:IdTransakce>\n" +
                "            <obi:GlobalniIdNavrhuZmeny>2042164</obi:GlobalniIdNavrhuZmeny>\n" +
                "            <obi:MluvnickeCharakteristiky>\n" +
                "               <com:Pad2>Kopidlna</com:Pad2>\n" +
                "               <com:Pad3>Kopidlnu</com:Pad3>\n" +
                "               <com:Pad4>Kopidlno</com:Pad4>\n" +
                "               <com:Pad6>Kopidlně</com:Pad6>\n" +
                "               <com:Pad7>Kopidlnem</com:Pad7>\n" +
                "            </obi:MluvnickeCharakteristiky>\n" +
                "            <obi:NutsLau>CZ0522573060</obi:NutsLau>\n" +
                "            <obi:Geometrie>\n" +
                "               <obi:DefinicniBod>\n" +
                "                  <gml:MultiPoint gml:id=\"DOB.573060\"\n" +
                "                                  srsName=\"urn:ogc:def:crs:EPSG::5514\"\n" +
                "                                  srsDimension=\"2\">\n" +
                "                     <gml:pointMembers>\n" +
                "                        <gml:Point gml:id=\"DOB.573060.1\">\n" +
                "                           <gml:pos>-679188.00 -1024096.00</gml:pos>\n" +
                "                        </gml:Point>\n" +
                "                     </gml:pointMembers>\n" +
                "                  </gml:MultiPoint>\n" +
                "               </obi:DefinicniBod>\n" +
                "            </obi:Geometrie>\n" +
                "         </vf:Obec>", code, code, name);
    }

    private String makeMunicipalityPartXmlEntry(int code, String name, int municipalityCode) {
        return String.format(" <vf:CastObce gml:id=\"CO.%d\">\n" +
                "            <coi:Kod>%d</coi:Kod>\n" +
                "            <coi:Nazev>%s</coi:Nazev>\n" +
                "            <coi:Obec>\n" +
                "               <obi:Kod>%d</obi:Kod>\n" +
                "            </coi:Obec>\n" +
                "            <coi:PlatiOd>2014-01-03T00:00:00</coi:PlatiOd>\n" +
                "            <coi:IdTransakce>449647</coi:IdTransakce>\n" +
                "            <coi:GlobalniIdNavrhuZmeny>473551</coi:GlobalniIdNavrhuZmeny>\n" +
                "            <coi:MluvnickeCharakteristiky>\n" +
                "               <com:Pad2>Ledkova</com:Pad2>\n" +
                "               <com:Pad3>Ledkovu</com:Pad3>\n" +
                "               <com:Pad4>Ledkov</com:Pad4>\n" +
                "               <com:Pad6>Ledkově</com:Pad6>\n" +
                "               <com:Pad7>Ledkovem</com:Pad7>\n" +
                "            </coi:MluvnickeCharakteristiky>\n" +
                "            <coi:Geometrie>\n" +
                "               <coi:DefinicniBod>\n" +
                "                  <gml:Point gml:id=\"DCO.69302\"\n" +
                "                             srsName=\"urn:ogc:def:crs:EPSG::5514\"\n" +
                "                             srsDimension=\"2\">\n" +
                "                     <gml:pos>-681339.00 -1022382.00</gml:pos>\n" +
                "                  </gml:Point>\n" +
                "               </coi:DefinicniBod>\n" +
                "            </coi:Geometrie>\n" +
                "         </vf:CastObce>", code, code, name, municipalityCode);
    }


}