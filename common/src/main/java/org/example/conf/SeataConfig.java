// package org.example.conf;
//
// import com.zaxxer.hikari.HikariDataSource;
// import io.seata.rm.datasource.DataSourceProxy;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.util.StringUtils;
//
// import javax.sql.DataSource;
//
// @Configuration
// public class SeataConfig {
//
//     @Autowired
//     DataSourceProperties dataSourceProperties;
//
//     public DataSource dataSource(DataSourceProperties dataSourceProperties) {
//         HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//         if (StringUtils.hasText(dataSourceProperties.getName())) {
//             dataSource.setPoolName(dataSourceProperties.getName());
//         }
//         return new DataSourceProxy(dataSource);
//     }
// }
