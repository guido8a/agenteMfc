//package paquete
//
//import io.github.bonigarcia.wdm.WebDriverManager
//
////import grails.testing.mixins.integration.Integration
//
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.firefox.FirefoxDriver
//import org.openqa.selenium.remote.DesiredCapabilities
//
////import org.openqa.selenium.firefox.FirefoxOptions
//
//import spock.lang.Specification
//
////@Integration
//class FirefoxTestSeleniumSpec extends Specification {
//
//    WebDriver driver
//
//    def setup() {
//        // Descarga y configura automáticamente la versión correcta de GeckoDriver
//        WebDriverManager.firefoxdriver().setup()
//
//        // Configuración clásica compatible al 100% con Selenium 3 y Java 8
//        DesiredCapabilities caps = DesiredCapabilities.firefox()
//        caps.setCapability("marionette", true)
//
////        FirefoxOptions options = new FirefoxOptions()
//
//        // Opcional: Descomenta la línea de abajo si quieres ejecutar las pruebas en segundo plano (sin abrir la ventana)
//        // options.addArguments("-headless")
//
////        driver = new FirefoxDriver(options)
//        driver = new FirefoxDriver(caps)
//    }
//
//    def cleanup() {
//        if (driver != null) {
//            driver.quit() // Cierra el navegador Firefox por completo
//        }
//    }
//
//    void "Probar navegacion basica en Firefox"() {
//        when: "Abrimos una URL de prueba"
//        driver.get("https://google.com")
//
//        then: "Verificamos que cargó correctamente"
//        driver.title.contains("Google")
//        println "Título verificado en Firefox: " + driver.title
//    }
//}

package paquete

import sele8.SeleniumAutomatorService//package mi.paquete

class FirefoxTestSeleniumSpec {

    // Inyección automática del servicio creado
    SeleniumAutomatorService seleniumAutomatorService

    def init = { servletContext ->
        println "=== La aplicación Grails ha arrancado ==="

        // Ejecutamos la lógica de Selenium en el hilo principal de la app
        seleniumAutomatorService.ejecutarAutomatizacion()
    }

    def destroy = {
    }
}
