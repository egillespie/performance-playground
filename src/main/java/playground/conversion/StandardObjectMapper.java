package playground.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Provides as close to a default configuration for an {@link com.fasterxml.jackson.databind.ObjectMapper} as can be
 * done while still supported third party data types.
 *
 * @author egillespie
 */
public class StandardObjectMapper extends ObjectMapper {
    public StandardObjectMapper() {
        registerModule(new GuavaModule());
        registerModule(new JodaModule());
    }
}
