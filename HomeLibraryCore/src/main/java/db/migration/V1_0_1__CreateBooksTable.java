package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

public class V1_0_1__CreateBooksTable extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) throws Exception {
        final JdbcTemplate template = new JdbcTemplate(context.getConnection());
        template.execute("CREATE TABLE books (\n"
                + "isbn VARCHAR(17) primary key,\n"
                + "title VARCHAR(200),\n"
                + "genre VARCHAR(20),\n"
                + "no_of_copies int\n"
                + ");"
        );
    }
}
