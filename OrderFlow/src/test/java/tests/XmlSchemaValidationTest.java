package tests;

import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlSchemaValidationTest {

    @Test
    void shouldValidateProductXmlSchema() {


        String sampleXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <product>
                    <id>101</id>
                    <name>Running Shoes</name>
                    <category>Footwear</category>
                    <price>4499.00</price>
                </product>
                """;

        // S — load XSD file
        InputStream xsdStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                "schemas/product-schema.xsd"
                        );

        // — check file was found
        assertNotNull(xsdStream,
                "XSD file not found — check it is in src/test/resources/schemas/product-schema.xsd");

        // — validate
        assertDoesNotThrow(() -> {

            SchemaFactory factory =
                    SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI
                    );

            Schema schema = factory.newSchema(
                    new StreamSource(xsdStream)
            );

            Validator validator = schema.newValidator();

            validator.validate(
                    new StreamSource(
                            new StringReader(sampleXml)
                    )
            );

        });
    }
}