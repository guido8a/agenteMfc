package sele8

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

//class Application extends GrailsAutoConfiguration {
//    static void main(String[] args) {
//        GrailsApp.run(Application, args)
//    }
//}
//

//import grails.boot.GrailsApp
//import grails.boot.config.GrailsAutoConfiguration
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
//
//@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration, HibernateJpaAutoConfiguration])
//class Application extends GrailsAutoConfiguration {
//    static void main(String[] args) {
//        GrailsApp.run(Application, args)
//    }
//}

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

@EnableAutoConfiguration(exclude = [
        DataSourceAutoConfiguration,
        DataSourceTransactionManagerAutoConfiguration,
        HibernateJpaAutoConfiguration
])
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}
