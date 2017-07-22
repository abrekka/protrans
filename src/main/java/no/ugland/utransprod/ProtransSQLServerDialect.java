package no.ugland.utransprod;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;

public class ProtransSQLServerDialect extends SQLServerDialect {
	public ProtransSQLServerDialect() {
		super();

		registerColumnType(Types.VARCHAR, "nvarchar($l)");
	}
}
