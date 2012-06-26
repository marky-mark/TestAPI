package com.mtt.api.model;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Joda DateTime deserialiser
 */
public class DateTimeDeserialiser extends JsonDeserializer<DateTime> {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeSerialiser.class);

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException, JsonProcessingException {
        String s = jp.getText();

        DateTimeFormatter japiDateFormat = ISODateTimeFormat.dateTimeNoMillis().withZone(DateTimeZone.UTC);

        DateTime date = null;
        try {
            date = japiDateFormat.parseDateTime(s);
        } catch (Exception ex) {
            logger.info("Cannot parse string date '" + s
                    + "' using ideal format");
        }

        DateTimeFormatter japiDateFormatNoTimeZone =
                ISODateTimeFormat.dateHourMinuteSecond().withZone(DateTimeZone.UTC);

        // TODO - once everything is happily using new date formats we
        // can/should remove this
        if (date == null) {
            try {
                date = japiDateFormatNoTimeZone.parseDateTime(s);
            } catch (Exception ex) {
                logger.info("Cannot parse string date '" + s
                        + "' using backup format without timezone");
            }
        }

        if (date == null) {
            try {
                date = new LocalDate(s).toDateTimeAtStartOfDay();
            } catch (Exception ex) {
                logger.warn("Cannot parse string date '" + s
                        + "' using basic date format", ex);
                throw new JsonParseException("Cannot unmarshall string " + s
                        + " as a legacy DateTime value", jp.getTokenLocation());
            }
        }
        return date;
    }
}



