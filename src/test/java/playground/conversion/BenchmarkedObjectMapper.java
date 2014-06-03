package playground.conversion;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Provides the configuration for an {@link com.fasterxml.jackson.databind.ObjectMapper} that is close to how an
 * ObjectMapper may be configured in a production environment in order to provide more accurate benchmarks.
 *
 * @author egillespie
 */
public class BenchmarkedObjectMapper extends ObjectMapper {
    public BenchmarkedObjectMapper() {
        registerModule(new GuavaModule());
        registerModule(new JodaModule());
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
        disable(MapperFeature.AUTO_DETECT_CREATORS);
    }
}
