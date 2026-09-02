package sele8

//import grails.gorm.transactions.Transactional
//
//@Transactional
//class ScrapperService {
//
//    def serviceMethod() {
//
//    }
//}

import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
//import grails.gorm.transactions.Transactional

//@Transactional
class ScraperService {

    // Extraemos la URL de la configuración de Grails para que no esté hardcodeada
    def grailsApplication

    String ejecutarProcesoRemoto(String urlDestino) {
        // 1. Leer la URL del Hub desde application.yml (por defecto localhost si no existe)
        String hubUrl = grailsApplication.config.getProperty('selenium.hub.url', String, "http://localhost:4444/wd/hub")

        // 2. Configurar las opciones de Firefox
        FirefoxOptions options = new FirefoxOptions()
        options.addArguments("-headless") // Recomendado para servidores (evita consumir memoria de video)
        options.setAcceptInsecureCerts(true) // Omitir alertas de certificados SSL inválidos

        RemoteWebDriver driver = null
        String resultado = ""

        try {
            // 3. Conectar al Grid 4 remoto
            driver = new RemoteWebDriver(new URL(hubUrl), options)

            // 4. Realizar la automatización
            driver.get(urlDestino)

            // Ejemplo: Obtener el título de la página procesada
            resultado = driver.getTitle()

        } catch (Exception e) {
            log.error("Error ejecutando automatización en Selenium Grid: ${e.message}", e)
            resultado = "Error: ${e.message}"
        } finally {
            // 5. SIEMPRE cerrar la sesión para no saturar el Grid 4 de hilos muertos
            if (driver != null) {
                driver.quit()
            }
        }

        return resultado
    }
}
