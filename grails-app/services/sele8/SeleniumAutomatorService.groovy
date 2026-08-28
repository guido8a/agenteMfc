package sele8

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

class SeleniumAutomatorService {

    // Grails inyectará automáticamente este servicio donde lo llames
    void ejecutarAutomatizacion() {
        println "=== Iniciando automatización con Firefox ==="

        // 1. Configurar GeckoDriver automáticamente
        WebDriverManager.firefoxdriver().setup()

        // 2. Opciones de Firefox
        FirefoxOptions options = new FirefoxOptions()
        // Opcional: options.addArguments("-headless") // Si no quieres que se abra la ventana visual

        WebDriver driver = null

        try {
            // 3. Inicializar el navegador
            driver = new FirefoxDriver(options)

            // 4. Tu lógica de negocio / Web Scraping / Automatización
            driver.get("https://google.com")
            println "Navegación exitosa. El título web es: " + driver.title

            // Aquí puedes agregar clics, interactuar con formularios, etc.

        } catch (Exception e) {
            println "Ocurrió un error en Selenium: ${e.message}"
            e.printStackTrace()
        } finally {
            // 5. Asegurar el cierre del proceso de Firefox
            if (driver != null) {
                driver.quit()
                println "=== Navegador Firefox cerrado correctamente ==="
            }
        }
    }
}
