package csl;

import artifact.common.util.EntityFactory;

public class TableTest {

    public static void main(String... args) throws Exception {
        EntityFactory factory = EntityFactory.newInstance();
        factory.setConnection("172.16.0.62", 8066, "db_iboss_kjsc", "db_iboss", "db_iboss_t");
        factory.generate("kjsc_co","Company");
    }
}
