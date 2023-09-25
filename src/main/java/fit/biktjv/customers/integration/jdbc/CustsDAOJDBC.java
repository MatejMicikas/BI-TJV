package fit.biktjv.customers.integration.jdbc;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;
import fit.biktjv.customers.rest.CustomersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CustsDAOJDBC implements CustsDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    @Transactional
    void init() {
        jdbcTemplate.update("create table if not exists customers(id long generated always as identity, name varchar(20))");
        //    jdbcTemplate.update("insert into customers values(default, 'Tom')");
    }

    @Override
    @Transactional
    public List<Cust> all() {
        return jdbcTemplate.query("select * from customers", new CustomerMapper());
    }

    @Override
    public Long create(Cust.CustDTO custDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("insert into customers values(default, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, custDTO.name());
            return ps;
        };
        PreparedStatementCreator psc2 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into customers values(default, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, custDTO.name());
                return ps;
            }

            ;
        };
        jdbcTemplate.update(psc, keyHolder);
        return (long) keyHolder.getKey();
    }

    @Override
    public void delete(Long id) {
        int res = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("delete from customers where (id = ?)");
            ps.setLong(1, id);
            return ps;
        });
        if (res != 1) throw new CustomersException("invalid cust id");
    }

    @Override
    public void changeName(Cust.CustDTO custDTO) {
        int res = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("update customers set name = ? where id = ?");
            ps.setString(1, custDTO.name());
            ps.setLong(2, custDTO.id());
            return ps;
        });
        if (res != 1) throw new CustomersException("invalid cust id");
    }

    @Override
    public Optional<Cust> findById(Long id) {

        List<Cust> res = jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement("select * from customers where (id = ?)");
            ps.setLong(1, id);
            return ps;
        }, new CustomerMapper());
        if (res.size() > 1) throw new RuntimeException("duplicate primary key");
        return res.stream().findFirst();
    }

    static class CustomerMapper implements RowMapper<Cust> {

        @Override
        public Cust mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Cust(rs.getLong(1), rs.getString(2));
        }
    }
}
