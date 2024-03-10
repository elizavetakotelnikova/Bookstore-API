package com.bookstore.app.configuration;

import com.bookstore.app.valueObjects.OrderState;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnumType extends org.hibernate.type.EnumType {

    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        st.setObject(
                index,
                value != null ?
                        ((Enum) value).name().toLowerCase() :
                        null,
                Types.OTHER
        );
    }

    @Override
    public Enum nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String stateName = rs.getString(position);
        OrderState result = null;
        if (!rs.wasNull()) {
            result = OrderState.valueOf(stateName.toUpperCase());
        }
        return result;
    }
}
