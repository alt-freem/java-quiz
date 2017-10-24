package test.design;

public class Enums {
    class Config {
    }

    interface Parser {
        Config parser(String str);
    }


    class XmlParser implements Parser {
        @Override
        public Config parser(String str) {
            return null;
        }
    }

    class JsonParser implements Parser {
        @Override
        public Config parser(String str) {
            return null;
        }
    }

    class YamlParser implements Parser {
        @Override
        public Config parser(String str) {
            return null;
        }
    }

    enum Format {
        XML,
        JSON,
        YAML
    }

    class ParsingService {

        public Config parse(String fmt, String str) {
            switch (Format.valueOf(fmt)) {
                case XML:
                    return new XmlParser().parser(str);
                case JSON:
                    return new JsonParser().parser(str);
                case YAML:
                    return new YamlParser().parser(str);
            }
            throw new IllegalArgumentException("Unsupported format: " + fmt);
        }

    }
}
