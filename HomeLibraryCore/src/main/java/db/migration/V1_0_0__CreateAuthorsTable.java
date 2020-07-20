package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

public class V1_0_0__CreateAuthorsTable extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) throws Exception {
        final JdbcTemplate template = new JdbcTemplate(context.getConnection());
        template.execute("CREATE TABLE authors (\n"
                + "idx BIGINT primary key,\n"
                + "first_name VARCHAR(50),\n"
                + "last_name VARCHAR(50)\n"
                + ");"
        );
    }

}
